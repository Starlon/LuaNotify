LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_C_INCLUDES := jni/include
LOCAL_MODULE	:= luascript
LOCAL_SRC_FILES := $(addprefix /, $(notdir $(wildcard $(LOCAL_PATH)/*.c) $(wildcard $(LOCAL_PATH)/*.cpp)))
LOCAL_CFLAGS    += $(ARCH_CFLAGS) -fexceptions -frtti
LOCAL_SHARED_LIBRARIES := gnustl

LOCAL_LDLIBS += -L$(call host-path, $(LOCAL_PATH))/../LuaJIT/$(TARGET_ARCH_ABI) -lluajit

LOCAL_ARM_MODE := arm

include $(BUILD_SHARED_LIBRARY)
