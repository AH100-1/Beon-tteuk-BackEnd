//package com.beontteuk.beontteuk.auth.user.repository.impl;
//
//import com.beontteuk.beontteuk.auth.user.domain.User;
//import com.beontteuk.beontteuk.auth.user.repository.UserRepository;
//import lombok.extern.slf4j.Slf4j;
//
//import java.sql.*;
//import java.time.LocalDateTime;
//import java.util.Objects;
//import java.util.Optional;
//
//@Slf4j
//public class UserRepositoryImpl implements UserRepository {
//
//    @Override
//    public Optional<User> findByUserIdAndUserPassword(String userId, String userPassword) {
//        /*회원의 아이디와 비밀번호를 이용해서 조회하는 코드 입니다.(로그인)
//         */
//        Connection connection = DbConnectionThreadLocal.getConnection();
//        String sql = "select user_id, user_name, user_password, user_birth, user_auth, user_point, created_at, latest_login_at from users where user_id=? and user_password =?";
//
//        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
//            psmt.setString(1, userId);
//            psmt.setString(2, userPassword);
//            try (ResultSet rs = psmt.executeQuery()) {
//                if (rs.next()) {
//                    User user = new User(
//                            rs.getString("user_id"),
//                            rs.getString("user_name"),
//                            rs.getString("user_password"),
//                            rs.getString("user_birth"),
//                            User.Auth.valueOf(rs.getString("user_auth")),
//                            rs.getInt("user_point"),
//                            Objects.nonNull(rs.getTimestamp("created_at")) ? rs.getTimestamp("created_at").toLocalDateTime() : null,
//                            Objects.nonNull(rs.getTimestamp("latest_login_at")) ? rs.getTimestamp("latest_login_at").toLocalDateTime() : null
//                    );
//                    return Optional.of(user);
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        return Optional.empty();
//    }
//
//    @Override
//    public Optional<User> findById(String userId) {
//        //회원조회
//        Connection connection = DbConnectionThreadLocal.getConnection();
//        String sql = "select user_id, user_name, user_password, user_birth, user_auth, user_point, created_at, latest_login_at from users where user_id=?";
//        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
//            psmt.setString(1, userId);
//            try (ResultSet rs = psmt.executeQuery()) {
//                if (rs.next()) {
//                    User user = new User(
//                            rs.getString("user_id"),
//                            rs.getString("user_name"),
//                            rs.getString("user_password"),
//                            rs.getString("user_birth"),
//                            User.Auth.valueOf(rs.getString("user_auth")),
//                            rs.getInt("user_point"),
//                            Objects.nonNull(rs.getTimestamp("created_at")) ? rs.getTimestamp("created_at").toLocalDateTime() : null,
//                            Objects.nonNull(rs.getTimestamp("latest_login_at")) ? rs.getTimestamp("latest_login_at").toLocalDateTime() : null
//                    );
//                    return Optional.of(user);
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return Optional.empty();
//    }
//
//    @Override
//    public int save(User user) {
//        //회원등록, executeUpdate()을 반환합니다.
//        Connection connection = DbConnectionThreadLocal.getConnection();
//        String sql = "insert into users(user_id, user_name, user_password, user_birth, user_auth, user_point, created_at, latest_login_at) values(?,?,?,?,?,?,?,?)";
//        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
//            psmt.setString(1, user.getUserId());
//            psmt.setString(2, user.getUserName());
//            psmt.setString(3, user.getUserPassword());
//            psmt.setString(4, user.getUserBirth());
//            psmt.setString(5, user.getUserAuth().name());
//            psmt.setInt(6, user.getUserPoint());
//            if (user.getCreatedAt() != null) {
//                psmt.setTimestamp(7, Timestamp.valueOf(user.getCreatedAt()));
//            } else {
//                psmt.setTimestamp(7, null);
//            }
//            if (user.getLatestLoginAt() != null) {
//                psmt.setTimestamp(8, Timestamp.valueOf(user.getLatestLoginAt()));
//            } else {
//                psmt.setTimestamp(8, null);
//            }
//            return psmt.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public int deleteByUserId(String userId) {
//        //회원삭제, executeUpdate()을 반환합니다.
//        Connection connection = DbConnectionThreadLocal.getConnection();
//        String sql = "delete from users where user_id=?";
//        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
//            psmt.setString(1, userId);
//            return psmt.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public int update(User user) {
//        //회원수정, executeUpdate()을 반환합니다.
//        Connection connection = DbConnectionThreadLocal.getConnection();
//        String sql = "update users set user_name=?, user_password=?, user_birth=?, user_auth=?, user_point=? where user_id=?";
//        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
//            psmt.setString(1, user.getUserName());
//            psmt.setString(2, user.getUserPassword());
//            psmt.setString(3, user.getUserBirth());
//            psmt.setString(4, user.getUserAuth().name());
//            psmt.setInt(5, user.getUserPoint());
//            psmt.setString(6, user.getUserId());
//            return psmt.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public int updateLatestLoginAtByUserId(String userId, LocalDateTime latestLoginAt) {
//        //마지막 로그인 시간 업데이트, executeUpdate()을 반환합니다.
//        Connection connection = DbConnectionThreadLocal.getConnection();
//        String sql = "update users set latest_login_at=? where user_id=?";
//        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
//            if (latestLoginAt != null) {
//                psmt.setTimestamp(1, Timestamp.valueOf(latestLoginAt));
//            } else {
//                psmt.setTimestamp(1, null);
//            }
//            psmt.setString(2, userId);
//            return psmt.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public int countByUserId(String userId) {
//        //UserId와 일치하는 회원의 count를 반환합니다.
//        Connection connection = DbConnectionThreadLocal.getConnection();
//        String sql = "select count(1) as cnt from users where user_id=?";
//        try (PreparedStatement psmt = connection.prepareStatement(sql)) {
//            psmt.setString(1, userId);
//            try (ResultSet rs = psmt.executeQuery()) {
//                if (rs.next()) {
//                    return rs.getInt("cnt");
//                }
//            }
//            return 0;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//}
