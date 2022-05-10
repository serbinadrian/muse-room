package com.koley.musrights.repositories;

import com.koley.musrights.domains.UserAvatar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAvatarsRepository extends JpaRepository<UserAvatar, Long> {

}
