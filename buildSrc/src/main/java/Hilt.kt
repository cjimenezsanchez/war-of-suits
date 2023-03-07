object Hilt {

    const val version = "2.44"
    private const val hiltPath = "com.google.dagger"

    const val hiltAndroid = "$hiltPath:hilt-android"
    const val hiltCompiler = "$hiltPath:hilt-compiler"

    const val plugin = "$hiltPath.hilt.android"
    const val android = "$hiltAndroid:$version"
    const val compiler = "$hiltCompiler:$version"
}