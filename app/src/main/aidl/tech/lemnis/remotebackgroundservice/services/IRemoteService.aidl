// IRemoteService.aidl
package tech.lemnis.remotebackgroundservice.services;

import tech.lemnis.remotebackgroundservice.services.IAsyncCallback;

interface IRemoteService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    double doSomething(String largeString, int viewId, int delayInMilliSeconds);

    oneway void doSomethingAsync(String largeString, int viewId, int delayInMilliSeconds, IAsyncCallback asyncCallback);
}
