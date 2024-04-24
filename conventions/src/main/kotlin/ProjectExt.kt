
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.file.FileSystemLocation
import org.gradle.kotlin.dsl.the
import java.io.File

val Project.libs get() = the<LibrariesForLibs>()

typealias DirectoryTransform = Directory.() -> FileSystemLocation

fun Project.projectPath(projectName: String, action: DirectoryTransform) = project(projectName).projectPath(action)

fun Project.projectPath(action: DirectoryTransform): File {
    return action.invoke(layout.projectDirectory).asFile
}
