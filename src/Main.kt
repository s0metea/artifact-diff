import java.io.File

fun main(args: Array<String>) {
    if (args.size != 3) {
        println("Usage: Comparer <dir1> <dir2> <resultsDir>")
        return
    }
    val dir1 = File(args[0])
    val dir2 = File(args[1])
    val resultsDir = File(args[2])

    val comparer = Comparer(dir2, dir1)
    val diff = comparer.compare()
    val diffoscope = Diffoscope()

    if (diff.size == 0) {
        println("Artifacts are equal.")
    } else {
        println("Artifacts are not equal:\n")
        var i = 1
        for (s in diff) {
            println("$i: ")
            println(s)
            val fileName = resultsDir.absolutePath + "/${i}.html"
            diffoscope.runDiff(s.file1Path, s.file2Path, fileName)
            println("Saving results to $fileName\n")
            i++
        }
    }
}