package com.example.cacheProxy;

import java.util.HashMap;
import java.util.Map;

public class CacheProxyImp implements CacheProxy {

    private static CacheProxyImp loggerProxy;
    private String deviceId;
    private Map<String, Object> storageData;

    public static CacheProxyImp getInstance() {
        if (loggerProxy == null) {
            loggerProxy = new CacheProxyImp();
        }
        return loggerProxy;
    }

    protected CacheProxyImp() {
        storageData = new HashMap<>();
    }

    @Override
    public void resetStorage() {
        storageData.clear();
    }

    @Override
    public Boolean isDeviceIdIdentical(String compareDeviceId) {
        return deviceId == compareDeviceId ? true : false;
    }

    @Override
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        resetStorage();
    }

    @Override
    public Object getStorageDataByName(String nameOfStorageData) {
        return storageData.get(nameOfStorageData);
    }

    @Override
    public boolean addNewData(String nameOfStorageData, Object data) {
        storageData.put(nameOfStorageData, data);
        return storageData.containsKey(nameOfStorageData);
    }

    @Override
    public int numberOfStorageData() {
        return storageData.size();
    }
}
