package com.fintechlabs;

import java.util.Date;
import java.util.UUID;

public class TestPerson {

    public static void main(String[] args) {
        PersonMapStore personMapStore = new PersonMapStore();
        Person person = new Person();
        person.setFirstName("First SQL");
        person.setLastName("Last SQL #1");
        person.setDateCreated(new Date());
        person.setLastUpdated(new Date());
        person.setEmailAddress("sql1@email.com");
        person.setPhoneNumber("9182736450");
        personMapStore.store(UUID.randomUUID().toString(), person);

//        personMapStore.delete("72bb0cef-dcfb-4ee3-8bf4-b5a457939383");
    }

}
