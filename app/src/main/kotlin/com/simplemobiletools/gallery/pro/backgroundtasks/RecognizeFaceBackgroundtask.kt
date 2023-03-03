package com.simplemobiletools.gallery.pro.backgroundtasks

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.simplemobiletools.gallery.pro.extensions.scanMediaForRecognizables
import com.simplemobiletools.gallery.pro.helpers.SimilarityClassifier.Recognition
import org.tensorflow.lite.Interpreter
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.util.concurrent.Executors


class RecognizeFaceBackgroundtask(
    val context: Context
) {

    private lateinit var registered: HashMap<String, Recognition> //saved Faces

    private lateinit var faceDetector : FaceDetector
    private val executor = Executors.newSingleThreadExecutor()

    var IMAGE_MEAN = 128.0f
    var IMAGE_STD = 128.0f
    var OUTPUT_SIZE = 192 //Output size of model

    private val SELECT_PICTURE = 1
    private lateinit var tfLite: Interpreter

    val modelFile = "mobile_face_net.tflite"

    init {
        System.out.println("Init face recognition")
        //registered = readFromSP(); //Load saved faces from memory when app starts

        //Load model
        try {
            var tfLite = loadModelFile(context, modelFile)?.let { Interpreter(it) }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        //Initialize Face Detector
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_NONE)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_NONE)
            .enableTracking()
            .build()
        faceDetector = FaceDetection.getClient(options)
    }

    @Throws(IOException::class)
    private fun loadModelFile(activity: Context, MODEL_FILE: String): MappedByteBuffer? {
        val fileDescriptor = activity.assets.openFd(MODEL_FILE)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel: FileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    fun detectFaces() {
        val media = context.scanMediaForRecognizables()

        for (medium in media) {
            val image: InputImage
            try {
                image = InputImage.fromFilePath(context, Uri.fromFile(File(medium.path)))
                val result = faceDetector.process(image)
                    .addOnSuccessListener { faces ->
                        // Task completed successfully
                        for (face in faces) {
                            Log.d("RecognizableFaceBackgroundtask", face.trackingId.toString())
                            if (face.trackingId != null) {
                                val id = face.trackingId
                            }
                        }
                    }
                    .addOnFailureListener { e ->
                        // Task failed with an exception
                        e.printStackTrace()
                    }
            } catch (e: IOException) {
                e.printStackTrace()
                break
            }
        }
    }

    //Load Faces from Shared Preferences.Json String to Recognition object
    private fun readFromSP(): HashMap<String, Recognition> {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("HashMap", MODE_PRIVATE)
        val defValue = Gson().toJson(HashMap<String, Recognition>())
        val json = sharedPreferences.getString("map", defValue)
        // System.out.println("Output json"+json.toString());
        val token: TypeToken<HashMap<String, Recognition>> = object : TypeToken<HashMap<String, Recognition>>() {}
        val retrievedMap: HashMap<String, Recognition> = Gson().fromJson(json, token.type)
        // System.out.println("Output map"+retrievedMap.toString());

        //During type conversion and save/load procedure,format changes(eg float converted to double).
        //So embeddings need to be extracted from it in required format(eg.double to float).
        for ((_, value) in retrievedMap.entries) {
            val output = Array<FloatArray>(1) { FloatArray(OUTPUT_SIZE) }
            var arrayList = value.getExtra()
            if (arrayList is ArrayList<*>) {
                arrayList = arrayList[0]
                if (arrayList is ArrayList<*>) {
                    for (counter in 0 until arrayList.size) {
                        output[0][counter] = (arrayList[counter] as Double).toFloat()
                    }
                    value.setExtra(output)
                }
            }

            //System.out.println("Entry output "+entry.getKey()+" "+entry.getValue().getExtra() );
        }
        //        System.out.println("OUTPUT"+ Arrays.deepToString(outut));
        Toast.makeText(context, "Recognitions Loaded", Toast.LENGTH_SHORT).show()
        return retrievedMap
    }

}
