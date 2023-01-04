//package com.example.surfbreak
//
//import com.google.firebase.firestore.ktx.toObject
//
//object DataManager2 {
//    var spots = mutableListOf<SurfBreak>()
//
//    init {
//
//        docRef.addSnapshotListener { snapshot, e ->
//            if (snapshot != null) {
//                spots.clear()
//                for (document in snapshot.documents) {
//                    val spot = document.toObject<SurfBreak>()
//                    if (spot != null) {
//                        spots.add(spot)
//                    }
//                }
//            }
//        }
//    }
//}

//    init {
//        spots.add(SurfBreak("La CÔtes", 43.479821, -1.566661, true,false))
//        spots.add(SurfBreak("Milady", 43.464260, -1.575358, true,false))
//        spots.add(SurfBreak("Beliche", 37.025978,-8.962766, false,false))
//        spots.add(SurfBreak("Tonel", 37.007202, -8.947901, true,false))
//        spots.add(SurfBreak("Unstad", 68.269421,13.582259, true,true))
//
//    }
//}
