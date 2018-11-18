package com.pentavex.owenpay.service;

import com.pentavex.owenpay.domain.Group;

import java.util.List;

public interface GroupService {
    List<Group> listAll();
    Group getById(Long id);
    Group saveOrUpdate(Group group);
    void delete(Long id);
}
