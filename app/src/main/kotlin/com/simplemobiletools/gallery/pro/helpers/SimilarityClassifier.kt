/* Copyright 2019 The TensorFlow Authors. All Rights Reserved.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==============================================================================*/
package com.simplemobiletools.gallery.pro.helpers

interface SimilarityClassifier {
    /** An immutable result returned by a Classifier describing what was recognized.  */
    class Recognition(
        /**
         * A unique identifier for what has been recognized. Specific to the class, not the instance of
         * the object.
         */
        private val id: String,
        /** Display name for the recognition.  */
        private var title: String, distance: Float
    ) {
        private val distance: Float
        private var extra: Any?

        init {
            this.title = title
            this.distance = distance
            this.extra = null
        }

        fun setExtra(extra: Any?) {
            this.extra = extra
        }

        fun getExtra(): Any? {
            return this.extra
        }

        override fun toString(): String {
            var resultString = ""
            if (id != null) {
                resultString += "[$id] "
            }
            if (title != null) {
                resultString += "$title "
            }
            if (distance != null) {
                resultString += String.format("(%.1f%%) ", distance * 100.0f)
            }
            return resultString.trim { it <= ' ' }
        }
    }
}
