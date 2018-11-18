package com.pentavex.owenpay.repository;

import com.pentavex.owenpay.domain.UserGroupMapEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserGroupMapRepository extends CrudRepository<UserGroupMapEntity, Long> {
    List<UserGroupMapEntity> findAllByGroupId(Long groupId);
}
