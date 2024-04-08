import java.io.File
import java.security.MessageDigest

class Comparer(private val dir1: File, private val dir2: File){

    private fun getHash(file: File?): String{
        val data = file?.readBytes()
        val digest = MessageDigest.getInstance("SHA-1")
        val hash = digest.digest(data)

        val value = StringBuilder()
        for (byte in hash) {
            val hex = Integer.toHexString(0xff and byte.toInt())
            if (hex.length == 1) {
                value.append('0')
            }
            value.append(hex)
        }
        return value.toString()
    }

    private fun compareDirectories(dir1: File, dir2: File, diff: MutableList<DifferenceItem>) {
        val dir1Files = dir1.listFiles()?.toList()?:emptyList()
        val dir2Files = dir2.listFiles()?.toList()?:emptyList()

        for (file1 in dir1Files) {
            val file2 = dir2Files.find { it.name == file1.name }

            if (file2 != null) {
                if (file1.isDirectory && file2.isDirectory) {
                    compareDirectories(file1, file2, diff)
                } else if (file1.isDirectory || file2.isDirectory) {
                    diff.add(DifferenceItem(file1.path, file2.path,"Different type"))
                } else if (!getHash(file1).equals(getHash(file2))) {
                    diff.add(DifferenceItem(file1.path, file2.path,"The difference in file content"))
                }
            } else {
                diff.add(DifferenceItem(file1.path, dir2.absolutePath + "/" + file1.name,"There is no such file in other artifact"))
            }
        }
    }

    fun compare(): MutableList<DifferenceItem> {
        val diff = mutableListOf<DifferenceItem>()
        compareDirectories(this.dir1, this.dir2, diff)
        return diff
    }

}