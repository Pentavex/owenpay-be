package com.pentavex.owenpay.repository;

import com.pentavex.owenpay.domain.Group;
import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<Group, Long> {
    Group findByGroupname(String groupname);
}


