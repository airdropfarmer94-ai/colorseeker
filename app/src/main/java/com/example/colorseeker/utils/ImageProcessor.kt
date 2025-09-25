package com.example.colorseeker.utils

import android.graphics.Bitmap
import android.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ImageProcessor {
    
    /**
     * Converts a bitmap image to an ARGB pixel grid for puzzle generation
     */
    suspend fun bitmapToPixelGrid(
        bitmap: Bitmap,
        targetWidth: Int,
        targetHeight: Int
    ): List<List<Int>> = withContext(Dispatchers.Default) {
        // Scale bitmap to target dimensions
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true)
        
        val pixelGrid = mutableListOf<List<Int>>()
        
        for (y in 0 until targetHeight) {
            val row = mutableListOf<Int>()
            for (x in 0 until targetWidth) {
                val pixel = scaledBitmap.getPixel(x, y)
                row.add(pixel)
            }
            pixelGrid.add(row)
        }
        
        scaledBitmap.recycle()
        pixelGrid
    }
    
    /**
     * Reduces colors in the image to create a more manageable palette
     */
    suspend fun quantizeColors(
        pixelGrid: List<List<Int>>,
        maxColors: Int = 12
    ): Pair<List<List<Int>>, List<Int>> = withContext(Dispatchers.Default) {
        // Extract all unique colors
        val allColors = mutableSetOf<Int>()
        pixelGrid.forEach { row ->
            row.forEach { pixel ->
                allColors.add(pixel)
            }
        }
        
        // If we already have fewer colors than max, return as is
        if (allColors.size <= maxColors) {
            return@withContext Pair(pixelGrid, allColors.toList())
        }
        
        // Simple color quantization using K-means clustering
        val palette = kMeansColorClustering(allColors.toList(), maxColors)
        
        // Map each pixel to nearest palette color
        val quantizedGrid = pixelGrid.map { row ->
            row.map { pixel ->
                findNearestColor(pixel, palette)
            }
        }
        
        Pair(quantizedGrid, palette)
    }
    
    /**
     * Generates an SVG preview of the pixel grid
     */
    fun generateSVGPreview(
        pixelGrid: List<List<Int>>,
        cellSize: Int = 10
    ): String {
        val width = pixelGrid.firstOrNull()?.size ?: 0
        val height = pixelGrid.size
        val svgWidth = width * cellSize
        val svgHeight = height * cellSize
        
        val svg = StringBuilder()
        svg.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
        svg.append("<svg width=\"$svgWidth\" height=\"$svgHeight\" xmlns=\"http://www.w3.org/2000/svg\">\n")
        
        for (y in pixelGrid.indices) {
            for (x in pixelGrid[y].indices) {
                val color = pixelGrid[y][x]
                val hexColor = String.format("#%08X", color)
                val rectX = x * cellSize
                val rectY = y * cellSize
                
                svg.append("  <rect x=\"$rectX\" y=\"$rectY\" width=\"$cellSize\" height=\"$cellSize\" fill=\"$hexColor\" />\n")
            }
        }
        
        svg.append("</svg>")
        return svg.toString()
    }
    
    /**
     * Simple K-means color clustering for palette reduction
     */
    private fun kMeansColorClustering(colors: List<Int>, k: Int): List<Int> {
        if (colors.size <= k) return colors
        
        // Initialize centroids randomly
        val centroids = colors.shuffled().take(k).toMutableList()
        
        repeat(10) { // Perform 10 iterations
            val clusters = mutableMapOf<Int, MutableList<Int>>()
            
            // Initialize clusters
            centroids.forEach { centroid ->
                clusters[centroid] = mutableListOf()
            }
            
            // Assign colors to nearest centroid
            colors.forEach { color ->
                val nearestCentroid = centroids.minByOrNull { colorDistance(color, it) } ?: centroids.first()
                clusters[nearestCentroid]?.add(color)
            }
            
            // Update centroids
            for (i in centroids.indices) {
                val cluster = clusters[centroids[i]] ?: continue
                if (cluster.isNotEmpty()) {
                    centroids[i] = averageColor(cluster)
                }
            }
        }
        
        return centroids
    }
    
    /**
     * Find the nearest color in palette
     */
    private fun findNearestColor(color: Int, palette: List<Int>): Int {
        return palette.minByOrNull { colorDistance(color, it) } ?: color
    }
    
    /**
     * Calculate distance between two colors in RGB space
     */
    private fun colorDistance(color1: Int, color2: Int): Double {
        val r1 = Color.red(color1)
        val g1 = Color.green(color1)
        val b1 = Color.blue(color1)
        
        val r2 = Color.red(color2)
        val g2 = Color.green(color2)
        val b2 = Color.blue(color2)
        
        val deltaR = r1 - r2
        val deltaG = g1 - g2
        val deltaB = b1 - b2
        
        return kotlin.math.sqrt((deltaR * deltaR + deltaG * deltaG + deltaB * deltaB).toDouble())
    }
    
    /**
     * Calculate average color from a list of colors
     */
    private fun averageColor(colors: List<Int>): Int {
        if (colors.isEmpty()) return Color.BLACK
        
        var totalR = 0
        var totalG = 0
        var totalB = 0
        var totalA = 0
        
        colors.forEach { color ->
            totalR += Color.red(color)
            totalG += Color.green(color)
            totalB += Color.blue(color)
            totalA += Color.alpha(color)
        }
        
        val count = colors.size
        return Color.argb(
            totalA / count,
            totalR / count,
            totalG / count,
            totalB / count
        )
    }
}