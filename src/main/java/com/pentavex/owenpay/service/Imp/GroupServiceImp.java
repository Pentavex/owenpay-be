package com.pentavex.owenpay.service.Imp;

import com.pentavex.owenpay.domain.Group;
import com.pentavex.owenpay.domain.User;
import com.pentavex.owenpay.domain.UserGroupMapEntity;
import com.pentavex.owenpay.repository.GroupRepository;
import com.pentavex.owenpay.repository.UserGroupMapRepository;
import com.pentavex.owenpay.repository.UserRepository;
import com.pentavex.owenpay.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImp implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserGroupMapRepository userGroupMapRepository;

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
    public Group createGroupWithUsername(final String groupname, final String username) {

        User user = userRepository.findByUsername(username);
        Group newGroup = new Group(groupname, user.getId());
        Group savedGroup = groupRepository.save(newGroup);
        addUserToGroupByIds(savedGroup.getId(), user.getId());

        return savedGroup;
    }

    @Override
    public void delete(final Long id) {
        groupRepository.deleteById(id);
    }

    @Override
    public List<User> getUsersByGroupId(final Long groupId) {

        List<UserGroupMapEntity> userGroupMapList = userGroupMapRepository.findAllByGroupId(groupId);

        List<User> users = new ArrayList<User>();
        for (UserGroupMapEntity userGroupMapEntity: userGroupMapList) {
            Long userId = userGroupMapEntity.getUserId();
            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                users.add(user.get());
            }
        }

        return users;
    }

    @Override
    public void addUserToGroupByIds(final Long groupId, final Long userId) {
        UserGroupMapEntity userGroupMapEntity = new UserGroupMapEntity();
        userGroupMapEntity.setGroupId(groupId);
        userGroupMapEntity.setUserId(userId);
        userGroupMapRepository.save(userGroupMapEntity);
    }
}
