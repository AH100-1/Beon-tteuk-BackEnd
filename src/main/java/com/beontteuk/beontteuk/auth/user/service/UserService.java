package com.beontteuk.beontteuk.auth.user.service;

import com.beontteuk.beontteuk.auth.user.domain.User;

public interface UserService {

    User getUser(String userId);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUser(String userId);

    User doLogin(String userId, String userPassword);

}
