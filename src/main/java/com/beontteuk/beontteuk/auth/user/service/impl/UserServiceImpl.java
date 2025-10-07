package com.beontteuk.beontteuk.auth.user.service.impl;

import com.beontteuk.beontteuk.auth.user.domain.User;
import com.beontteuk.beontteuk.auth.user.exception.UserAlreadyExistsException;
import com.beontteuk.beontteuk.auth.user.exception.UserNotFoundException;
import com.beontteuk.beontteuk.auth.user.repository.UserRepository;
import com.beontteuk.beontteuk.auth.user.service.UserService;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(String userId){
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public void saveUser(User user) {
        //회원등록
        int count = userRepository.countByUserId(user.getUserId());
        if (count > 0) {
            throw new UserAlreadyExistsException("already exists user: " + user.getUserId());
        }
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        //회원수정
        int count = userRepository.countByUserId(user.getUserId());
        if (count == 0) {
            throw new UserNotFoundException("not found user: " + user.getUserId());
        }
        userRepository.update(user);
    }

    @Override
    public void deleteUser(String userId) {
        //회원삭제
        int count = userRepository.countByUserId(userId);
        if (count == 0) {
            throw new UserNotFoundException("not found user: " + userId);
        }
        userRepository.deleteByUserId(userId);
    }

    @Override
    public User doLogin(String userId, String userPassword) {
        //로그인 구현, userId, userPassword로 일치하는 회원 조회
        User user = userRepository.findByUserIdAndUserPassword(userId, userPassword)
                .orElseThrow(() -> new UserNotFoundException("invalid user or password"));
        userRepository.updateLatestLoginAtByUserId(userId, java.time.LocalDateTime.now());
        return user;
    }

}
