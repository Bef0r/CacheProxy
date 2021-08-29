package com.example.cacheProxy;

import junit.framework.TestCase;

public class CacheProxyImpTest extends TestCase {
    CacheProxy cacheProxy;
    CacheProxy otherOneProxyInstance;
    TestPersonClass testPerson;
    TestAnimalClass testAnimal;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        cacheProxy =  CacheProxyImp.getInstance();
    }

    public void testSingletonPatter() {
        assertNull(otherOneProxyInstance);
        assertNotNull(cacheProxy);
        otherOneProxyInstance = CacheProxyImp.getInstance();
        assertEquals(cacheProxy, otherOneProxyInstance);
    }

    public void testIsIdIdentical() {
        String deviceId = "testDeviceId";
        String secondDeviceId = "123456";
        String wrongId = "wrong";
        cacheProxy.setDeviceId(deviceId);
        assertTrue(cacheProxy.isDeviceIdIdentical(deviceId));
        assertFalse(cacheProxy.isDeviceIdIdentical(wrongId));
        cacheProxy.setDeviceId(secondDeviceId);
        assertFalse(cacheProxy.isDeviceIdIdentical(deviceId));
        assertTrue(cacheProxy.isDeviceIdIdentical(secondDeviceId));
        cacheProxy.resetStorage();
        assertTrue(cacheProxy.isDeviceIdIdentical(secondDeviceId));
    }

    public void testGenericChange() {
        cacheProxy.addNewData("testPerson", new TestPersonClass("test", 1));
        assertNotNull(cacheProxy.getStorageDataByName("testPerson"));
        if (!(cacheProxy.getStorageDataByName("testPerson") instanceof TestPersonClass)) {
            fail();
        }
        cacheProxy.addNewData("testAnimal", new TestAnimalClass("testCase", "testOwnerName"));
        if (!(cacheProxy.getStorageDataByName("testAnimal") instanceof TestAnimalClass)) {
            fail();
        }
    }

    public void testFunctionality() {
        String personName = "Peter";
        int personAge = 21;
        String animalName = "Max";
        String ownerName = "Steve";
        String deviceId = "testDeviceId";

        checkLoggerProxyStorageIsNull();
        cacheProxy.addNewData("testPerson", new TestPersonClass(personName, personAge));
        cacheProxy.addNewData("testAnimal", new TestAnimalClass(animalName, ownerName));
        checkLoggerProxyStorageIsNotNull();
        convertStorageDataToCorrectForm();
        checkStorageData(animalName, ownerName, personName, personAge);
        cacheProxy.setDeviceId(deviceId);
        assertTrue(cacheProxy.isDeviceIdIdentical(deviceId));
        testAnimal = null;
        testAnimal = (TestAnimalClass) cacheProxy.getStorageDataByName("testAnimal");
        assertNull(testAnimal);
        try {
            testAnimal = (TestAnimalClass) cacheProxy.getStorageDataByName("testPerson");
        } catch (Exception exception) {
            fail();
        }
        assertNull(testAnimal);
        cacheProxy.resetStorage();
        checkLoggerProxyStorageIsNull();
    }

    protected void convertStorageDataToCorrectForm() {
        try {
            testAnimal = (TestAnimalClass) cacheProxy.getStorageDataByName("testAnimal");
            testPerson = (TestPersonClass) cacheProxy.getStorageDataByName("testPerson");
        } catch (Exception exception) {
            fail();
        }
    }

    protected void checkLoggerProxyStorageIsNull() {
        assertNotNull(cacheProxy);
        assertEquals(cacheProxy.numberOfStorageData(), 0);
    }

    protected void checkLoggerProxyStorageIsNotNull() {
        assertNotNull(cacheProxy);
        assertEquals(cacheProxy.numberOfStorageData(), 2);
    }

    protected void checkStorageData(String animalName, String ownerName, String personName, int personAge) {
        assertNotNull(testAnimal);
        assertNotNull(testPerson);
        assertEquals(testAnimal.getName(), animalName);
        assertEquals(testAnimal.getOwnerName(), ownerName);
        assertEquals(testPerson.getName(), personName);
        assertEquals(testPerson.getAge(), personAge);
    }


    public void testNotExistId(){
        assertNull(cacheProxy.getStorageDataByName("test"));
    }

    public class TestPersonClass {
        private String name;
        private int age;

        public TestPersonClass(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }

    public class TestAnimalClass {
        String name;
        String ownerName;

        public TestAnimalClass(String name, String ownerName) {
            this.name = name;
            this.ownerName = ownerName;
        }

        public String getName() {
            return name;
        }

        public String getOwnerName() {
            return ownerName;
        }
    }
}