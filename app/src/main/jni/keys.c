#include <jni.h>
JNIEXPORT jstring JNICALL
Java_dev_ogabek_android_1imperative_network_Server_getDevelopment(JNIEnv *env, jobject instance) {
    return (*env)->  NewStringUTF(env, "https://www.episodate.com/");
}
JNIEXPORT jstring JNICALL
Java_dev_ogabek_android_1imperative_network_Server_getProduction(JNIEnv *env, jobject instance) {
    return (*env)->NewStringUTF(env, "https://www.episodate.com/");
}