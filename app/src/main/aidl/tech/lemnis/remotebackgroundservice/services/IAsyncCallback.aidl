package tech.lemnis.remotebackgroundservice.services;


oneway interface IAsyncCallback {
    void handleResponse(String name);
}
