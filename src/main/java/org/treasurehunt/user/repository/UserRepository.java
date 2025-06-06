package org.treasurehunt.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.treasurehunt.common.repository.criteria.CriteriaRepository;
import org.treasurehunt.user.repository.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findByEmailOrUsernameIgnoreCase(String email, String username);

    Optional<User> findByRefreshToken(String refreshToken);

    @Modifying
    @Query("""
            UPDATE User user
            SET user.refreshToken = null,
                user.refreshTokenExpiry = null
            WHERE user.email = :email
            """)
    void invalidateRefreshTokenByEmail(String email);

    @Query("SELECT u.profilePicture FROM User u WHERE u.id = :id")
    String getUserProfilePicById(@Param("id") Long id);

    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
