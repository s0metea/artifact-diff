import java.io.BufferedReader
import java.io.InputStreamReader

class Diffoscope {

    fun runDiff(dirA: String, dirB: String?, output: String): String {
        val processBuilder = ProcessBuilder("diffoscope", dirA, dirB, "--html", output, "--new-file")
        val process = processBuilder.start()
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val output = reader.readText()
        process.waitFor()
        return output
    }
}