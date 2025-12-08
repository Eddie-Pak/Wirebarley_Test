import com.android.build.gradle.internal.tasks.UnstrippedLibs.add
import internal.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidNetworkConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                add("implementation", libs.findBundle("retrofit").get())
                add("implementation", libs.findLibrary("moshi.core").get())
                add("ksp", libs.findLibrary("moshi.kotlin.codegen").get())
            }
        }
    }
}