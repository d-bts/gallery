/**
 * Original: https://github.com/googlecodelabs/mlkit-android/blob/master/object-detection/final/app/src/main/java/com/google/mlkit/codelab/objectdetection/MainActivity.kt
 */

package com.simplemobiletools.gallery.pro.asynctasks

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.util.Log
import com.google.mlkit.vision.common.InputImage
/*import com.google.mlkit.vision.objects.DetectedObject
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions*/

class DetectObjectAsynctask(
    val context: Context
) {

    /*/**
     * ML Kit Object Detection function. We'll add ML Kit code here in the codelab.
     */
    private fun runObjectDetection(bitmap: Bitmap) {
        val image = InputImage.fromBitmap(bitmap, 0)

        val options = ObjectDetectorOptions.Builder()
            .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
            .enableMultipleObjects()
            .enableClassification()
            .build()
        val objectDetector = ObjectDetection.getClient(options)

        objectDetector.process(image).addOnSuccessListener { results ->
            debugPrint(results)

            // Parse ML Kit's DetectedObject and create corresponding visualization data
            val detectedObjects = results.map {
                var text = "Unknown"

                // We will show the top confident detection result if it exist
                if (it.labels.isNotEmpty()) {
                    val firstLabel = it.labels.first()
                    text = "${firstLabel.text}, ${firstLabel.confidence.times(100).toInt()}%"
                }
                BoxWithText(it.boundingBox, text)
            }

        }.addOnFailureListener {
           // Log.e(context.TAG, it.message.toString())
        }
    }

    /**
     * Print out the object detection result to Logcat.
     */
    private fun debugPrint(detectedObjects: List<DetectedObject>) {
        detectedObjects.forEachIndexed { index, detectedObject ->
            val box = detectedObject.boundingBox

           /* Log.d(TAG, "Detected object: $index")
            Log.d(TAG, " trackingId: ${detectedObject.trackingId}")
            Log.d(TAG, " boundingBox: (${box.left}, ${box.top}) - (${box.right},${box.bottom})")
            detectedObject.labels.forEach {
                Log.d(TAG, " categories: ${it.text}")
                Log.d(TAG, " confidence: ${it.confidence}")
            }*/
        }
    }*/
}

/**
 * A general-purpose data class to store detection result for visualization
 */
data class BoxWithText(val box: Rect, val text: String)
