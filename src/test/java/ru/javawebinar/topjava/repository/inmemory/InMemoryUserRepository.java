package ru.javawebinar.topjava.repository.inmemory;


import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.testData.UserTestData;
import ru.javawebinar.topjava.model.User;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.testData.UserTestData.ADMIN;
import static ru.javawebinar.topjava.testData.UserTestData.USER;

//TODO исправлены только красные ошибка, переписать реализацию в зависимости от основного кода

@Repository
public class InMemoryUserRepository extends InMemoryBaseRepository<User>{

    public void init() {
        entryMap.clear();
        entryMap.put(UserTestData.USER_ID, USER);
        entryMap.put(UserTestData.ADMIN_ID, ADMIN);
    }


    public List<User> getAll() {
        return getCollection().stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
                .collect(Collectors.toList());
    }


    public User getByEmail(String email) {
        Objects.requireNonNull(email, "email must not be null");
        return getCollection().stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst()
                .orElse(null);
    }
}