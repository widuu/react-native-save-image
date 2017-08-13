package com.widuu;

/**
 * Created by widuu on 2017/8/12.
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.bridge.JavaScriptModule;
//
//public class SaveImagePackage implements ReactPackage {
//
//
//    @Override
//    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
//        return Arrays.asList(new NativeModule[]{
//                new SaveImageModule(reactContext),
//        });
//    }
//
//    @Override
//    public List<Class<? extends JavaScriptModule>> createJSModules() {
//        return Collections.emptyList();
//    }
//
//    @Override
//    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
//        return Collections.emptyList();
//    }
//}

public class SaveImagePackage implements ReactPackage{

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        return Arrays.asList(new NativeModule[]{
                new SaveImageModule(reactContext),
        });
    }
	
    @Override
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }
	
    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}
