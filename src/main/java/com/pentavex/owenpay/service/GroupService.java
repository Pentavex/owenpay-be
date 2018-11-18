package com.pentavex.owenpay.service;

import com.pentavex.owenpay.domain.Group;

import java.util.List;

public interface GroupService {
    List<Group> listAll();
    Group getById(Long id);
    Group saveOrUpdate(Group group);
    Group createGroupWithUsername(String groupname, String username);
    void delete(Long id);
}
