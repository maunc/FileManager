package com.ax.filemanager.data

import java.io.Serializable

data class FileData(
    var fileType: Int,
    var fileName: String,
    var absolutePath: String = "",//文件/目录的绝对路径
    var sizeBytes: Long = 0L,//文件大小
    var lastModifiedMillis: Long = 0L,//最后修改时间（毫秒）
    var extension: String? = null,//扩展名
    var isHidden: Boolean = false,//是否隐藏
    var canRead: Boolean = false,//当前进程是否可读/可写
    var canWrite: Boolean = false,
    var canExecute: Boolean = false,//当前进程是否可执行（主要针对可执行文件）
    var parentPath: String? = null,//父目录绝对路径（目录本身也会有 parent）
    var exists: Boolean = true,//是否存在（理论上 listFiles 返回的通常存在）
) : Serializable {
    companion object {
        const val LOCAL_FILE_TYPE_DIR = 0        // 文件夹
        const val LOCAL_FILE_TYPE_IMAGE = 1      // 图片
        const val LOCAL_FILE_TYPE_VIDEO = 2      // 视频
        const val LOCAL_FILE_TYPE_AUDIO = 3      // 音频
        const val LOCAL_FILE_TYPE_WORD = 4   // 文档
        const val LOCAL_FILE_TYPE_PDF = 5   // PDF
        const val LOCAL_FILE_TYPE_XLS = 6   // XLS
        const val LOCAL_FILE_TYPE_PPT = 7   // PPT
        const val LOCAL_FILE_TYPE_ARCHIVE = 8    // 压缩包
        const val LOCAL_FILE_TYPE_APK = 9        // 安装包
        const val LOCAL_FILE_TYPE_TEXT = 10       // 文本
        const val LOCAL_FILE_TYPE_UNKNOWN = 11    // 未知/其他

        private val EXT_IMAGE = setOf(
            "jpg", "jpeg", "png", "gif", "bmp", "webp", "heic", "heif", "svg", "ico"
        )
        private val EXT_VIDEO = setOf(
            "mp4", "mkv", "avi", "mov", "wmv", "flv", "webm", "m4v", "3gp", "m3u8", "ts"
        )
        private val EXT_AUDIO = setOf(
            "mp3", "m4a", "wav", "flac", "aac", "ogg", "wma", "ape", "opus"
        )
        private val EXT_WORD = setOf(
            "pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx",
            "odt", "ods", "odp"
        )
        private val EXT_XLS = setOf("xls", "xlsx")
        private val EXT_PPT = setOf("ppt", "pptx")
        private val EXT_TEXT = setOf("txt", "rtf", "csv", "json", "xml", "log", "md", "properties")
        private val EXT_ARCHIVE = setOf(
            "zip", "rar", "7z", "tar", "gz", "bz2", "xz", "iso"
        )

        /**
         * 根据扩展名返回文件类型（小写匹配，不含点）
         */
        fun getFileTypeFromExtension(extension: String?): Int {
            val ext = extension?.lowercase()?.trim() ?: return LOCAL_FILE_TYPE_UNKNOWN
            if (ext.isEmpty()) return LOCAL_FILE_TYPE_UNKNOWN
            return when {
                EXT_IMAGE.contains(ext) -> LOCAL_FILE_TYPE_IMAGE
                EXT_VIDEO.contains(ext) -> LOCAL_FILE_TYPE_VIDEO
                EXT_AUDIO.contains(ext) -> LOCAL_FILE_TYPE_AUDIO
                EXT_WORD.contains(ext) -> LOCAL_FILE_TYPE_WORD
                EXT_XLS.contains(ext) -> LOCAL_FILE_TYPE_XLS
                EXT_PPT.contains(ext) -> LOCAL_FILE_TYPE_PPT
                EXT_TEXT.contains(ext) -> LOCAL_FILE_TYPE_TEXT
                EXT_ARCHIVE.contains(ext) -> LOCAL_FILE_TYPE_ARCHIVE
                ext == "pdf" -> LOCAL_FILE_TYPE_PDF
                ext == "apk" -> LOCAL_FILE_TYPE_APK
                else -> LOCAL_FILE_TYPE_UNKNOWN
            }
        }
    }
}
