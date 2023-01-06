package com.example.surfbreak

import com.google.firebase.firestore.DocumentId

data class SurfBreak(val pointBreak: Boolean? = null,
                     val name: String? = null,
                     val latitude: Double? = null,
                     val longitude: Double? = null,
                     val goesRight: Boolean? = null,
                     val uploadUid: String? = ".",
                     @DocumentId val documentId: String = "")
