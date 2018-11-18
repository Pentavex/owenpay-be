package com.pentavex.owenpay.service.Imp;

import com.pentavex.owenpay.domain.Group;
import com.pentavex.owenpay.repository.GroupRepository;
import com.pentavex.owenpay.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupServiceImp implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public List<Group> listAll() {
        List<Group> groups = new ArrayList<>();
        groupRepository.findAll().forEach(groups::add);
        return groups;
    }

    @Override
    public Group getById(final Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    @Override
    public Group saveOrUpdate(final Group group) {
        groupRepository.save(group);
        return group;
    }

    @Override
    public void delete(final Long id) {
        groupRepository.deleteById(id);
    }
}
