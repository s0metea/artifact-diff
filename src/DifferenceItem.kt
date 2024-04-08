
data class DifferenceItem(val file1Path: String, val file2Path: String?, val description: String) {
    override fun toString(): String {
        return "${this.file1Path} ${this.file2Path} ${this.description}"
    }
}
