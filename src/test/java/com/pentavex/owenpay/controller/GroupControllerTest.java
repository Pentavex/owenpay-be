package com.pentavex.owenpay.controller;

import com.pentavex.owenpay.BaseTestClass;
import com.pentavex.owenpay.domain.Group;
import com.pentavex.owenpay.domain.User;
import com.pentavex.owenpay.repository.GroupRepository;
import com.pentavex.owenpay.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import swaggergen.model.AddUserToGroupRequest;
import swaggergen.model.CreateGroupRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GroupControllerTest extends BaseTestClass {

    private final static String GROUPNAME = "Owenpay Group";
    private final static Long OWNER_ID = new Long(1);
    private final static Long NON_EXISTING_ID = new Long(12345);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Before
    public void setUp() {

        super.setUp();

        MockitoAnnotations.initMocks(this);
    }

    @After
    public void after() {
        super.after();
    }

    @Test
    public void createGroupThenSuccess() throws Exception {

        User user = new User();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);

        userRepository.save(user);

        CreateGroupRequest request = new CreateGroupRequest();
        request.setGroupname(GROUPNAME);
        request.setUsername(USERNAME);

        mockMvc.perform(post("/group/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        userRepository.deleteAll();
    }

    @Test
    public void createGroupWithNonExistingUserThenNotFoundError() throws Exception {

        CreateGroupRequest request = new CreateGroupRequest();
        request.setGroupname(GROUPNAME);
        request.setUsername(USERNAME);

        mockMvc.perform(post("/group/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addMemberToGroupThenSuccess() throws Exception {

        User user = new User();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);

        User savedUser = userRepository.save(user);

        Group group = new Group();
        group.setOwnerId(savedUser.getId());
        group.setGroupname(PASSWORD);

        Group savedGroup = groupRepository.save(group);

        AddUserToGroupRequest request = new AddUserToGroupRequest();
        request.setGroupId(savedGroup.getId());
        request.setUserId(savedUser.getId());

        mockMvc.perform(post("/group/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        userRepository.deleteAll();
        groupRepository.deleteAll();
    }

    @Test
    public void addNonExistingUserToGroupThenNotFoundError() throws Exception {

        Group group = new Group();
        group.setOwnerId(OWNER_ID);
        group.setGroupname(PASSWORD);

        Group savedGroup = groupRepository.save(group);

        AddUserToGroupRequest request = new AddUserToGroupRequest();
        request.setGroupId(savedGroup.getId());
        request.setUserId(NON_EXISTING_ID);

        mockMvc.perform(post("/group/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());

        groupRepository.deleteAll();
    }

    @Test
    public void addMemberToNonExistingGroupThenNotFoundError() throws Exception {

        User user = new User();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);

        User savedUser = userRepository.save(user);

        AddUserToGroupRequest request = new AddUserToGroupRequest();
        request.setGroupId(NON_EXISTING_ID);
        request.setUserId(savedUser.getId());

        mockMvc.perform(post("/group/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());

        userRepository.deleteAll();
    }
}
