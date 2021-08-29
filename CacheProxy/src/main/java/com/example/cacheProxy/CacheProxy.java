package com.example.cacheProxy;

public interface CacheProxy {
    Boolean isDeviceIdIdentical(String compareDeviceId);
    void setDeviceId(String deviceId);
    Object getStorageDataByName(String nameOfStorageData);
    boolean addNewData(String nameOfStorageData, Object data);
    int numberOfStorageData();
    void resetStorage();
}
