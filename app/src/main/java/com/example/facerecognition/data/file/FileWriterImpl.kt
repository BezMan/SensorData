package com.example.facerecognition.data.file

import android.content.Context
import android.net.Uri
import com.example.facerecognition.core.Resource
import com.example.facerecognition.utils.DateTimeUtils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class FileWriterImpl @Inject constructor(
    private val context: Context
): IFileWriter {


    override suspend fun writeFile(byteArray: ByteArray): Resource<String> {
        return saveFile(byteArray, IFileWriter.FILE_NAME+"-"+DateTimeUtils.fileNameFormatToString())
    }

    private fun saveFile(byteArray: ByteArray, fileName:String): Resource<String> {
        val file = context.filesDir
        val folder = File(file,"/export_app/")
        deleteDir(folder)
        if(!folder.exists()){
            if(!folder.mkdir()){
                //Error
            }else{
                folder.mkdirs()
            }
        }

        val fileName = File(folder,"$fileName.csv")
        val os = FileOutputStream(fileName)
        return try{
            val path = Uri.fromFile(fileName).path ?: return Resource.Error("Could not get file path")
            os.write(byteArray)
            Resource.Success(path)
        }catch(e: IOException){
            //Log error
            Resource.Error(errorMessage = e.localizedMessage ?: "unknown error")
        }finally {
            os.close()
        }
    }

    private fun deleteDir(dir:File?):Boolean{
        return if (dir != null && dir.isDirectory){
            val children = dir.list()
            if(children!=null){
                for (i in children.indices){
                    val success = deleteDir(File(dir,children[i]))
                    if(!success){
                        return false
                    }
                }
            }
            dir.delete()
        }else if (dir != null && dir.isFile){
            dir.delete()
        }else{
            false
        }
    }

}