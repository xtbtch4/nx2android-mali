cmake_minimum_required(VERSION 3.22.1)
project(nx2mali_probe C)
set(CMAKE_C_STANDARD 11)
set(ROOT ${CMAKE_CURRENT_LIST_DIR}/../../../../../probes)

add_library(vkprobe SHARED ${ROOT}/vkprobe.c vkprobe_jni.c)
target_compile_definitions(vkprobe PRIVATE main=probe_main)
target_link_libraries(vkprobe android log dl)

add_library(cpuprobe SHARED ${ROOT}/cpuprobe.c cpuprobe_jni.c)
target_compile_definitions(cpuprobe PRIVATE main=probe_main)
target_link_libraries(cpuprobe android log)

add_library(vkzc SHARED ${ROOT}/vkzc.c vkzc_jni.c)
target_compile_definitions(vkzc PRIVATE main=probe_main)
target_link_libraries(vkzc android log dl)
