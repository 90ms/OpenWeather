import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.kotlinComponent() {
    implementation(Kotlin.KOTLIN_STDLIB)
    implementation(Kotlin.COROUTINES_CORE)
    implementation(Kotlin.COROUTINES_ANDROID)
}

fun DependencyHandlerScope.androidXComponent() {
    implementation(AndroidX.CORE_KTX)
    implementation(AndroidX.APP_COMPAT)
    implementation(AndroidX.ACTIVITY_KTX)
    implementation(AndroidX.FRAGMENT_KTX)
    implementation(AndroidX.CONSTRAINT_LAYOUT)
    implementation(AndroidX.NAVIGATION_FRAGMENT)
    implementation(AndroidX.NAVIGATION_UI)
    implementation(AndroidX.NAVIGATION_FRAGMENT_KTX)
    implementation(AndroidX.NAVIGATION_UI_KTX)
    implementation(AndroidX.SWIPE_REFRESH)
}

fun DependencyHandlerScope.lifeCycleComponent() {
    implementation(AndroidX.LIFECYCLE_VIEWMODEL_KTX)
    implementation(AndroidX.LIFECYCLE_LIVEDATA_KTX)
}

fun DependencyHandlerScope.hiltComponent() {
    implementation(Google.HILT_COMMON)
    implementation(Google.HILT_ANDROID)
    kapt(Google.HILT_ANDROID_COMPILER)
}

fun DependencyHandlerScope.retrofitComponent() {
    implementation(Network.RETROFIT)
    implementation(Network.RETROFIT_ADAPTER)
    implementation(Network.RETROFIT_CONVERTER)
}

fun DependencyHandlerScope.okHttpComponent() {
    implementation(Network.OKHTTP)
    implementation(Network.OKHTTP_LOGGING_INTERCEPTOR)
}

private fun DependencyHandler.implementation(depName: String) =
    add("implementation", depName)

private fun DependencyHandler.kapt(depName: String) =
    add("kapt", depName)

private fun DependencyHandler.annotationProcessor(depName: String) =
    add("annotationProcessor", depName)


