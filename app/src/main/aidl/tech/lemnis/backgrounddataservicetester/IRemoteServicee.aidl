// IRemoteService.aidl
package tech.lemnis.backgrounddataservicetester;


interface IRemoteServicee {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    double doSomething(String largeString, int viewId, int delayInMilliSeconds);
}
