#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
extern int probe_main(int, char **);
JNIEXPORT jstring JNICALL Java_com_xtbtch4_nx2mali_MainActivity_runCpu(JNIEnv *env, jobject self) {
    FILE *f = tmpfile(); if (!f) return (*env)->NewStringUTF(env, "tmpfile failed");
    fflush(stdout); int saved = dup(STDOUT_FILENO); dup2(fileno(f), STDOUT_FILENO);
    char *argv[] = {"cpuprobe", NULL}; int rc = probe_main(1, argv);
    fflush(stdout); dup2(saved, STDOUT_FILENO); close(saved);
    fseek(f, 0, SEEK_END); long n = ftell(f); if (n < 0) n = 0; if (n > 60000) n = 60000;
    rewind(f); char *buf = calloc((size_t)n + 128, 1); fread(buf, 1, (size_t)n, f); fclose(f);
    char tail[64]; snprintf(tail, sizeof tail, "\n\nexit code: %d", rc);
    strncat(buf, tail, 127); jstring out = (*env)->NewStringUTF(env, buf); free(buf); return out;
}
