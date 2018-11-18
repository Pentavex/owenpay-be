package com.pentavex.owenpay.controller;

import com.pentavex.owenpay.domain.Group;
import com.pentavex.owenpay.domain.User;
import com.pentavex.owenpay.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import swaggergen.controller.GroupApi;
import swaggergen.model.AddUserToGroupRequest;
import swaggergen.model.AddUserToGroupResponse;
import swaggergen.model.CreateGroupRequest;
import swaggergen.model.CreateGroupResponse;
import swaggergen.model.GroupListUsersRequest;
import swaggergen.model.GroupListUsersResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
public class GroupController implements GroupApi {

    @Autowired
    private GroupService groupService;

    @Override
    public ResponseEntity<CreateGroupResponse> createGroup(@Valid @RequestBody final CreateGroupRequest request) {

        Group savedGroup = groupService.createGroupWithUsername(request.getGroupname(), request.getUsername());

        CreateGroupResponse response = new CreateGroupResponse();
        response.setMessage(savedGroup.getGroupname());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<AddUserToGroupResponse> addUserToGroup(
            @Valid @RequestBody final AddUserToGroupRequest addUserToGroupRequest) {

        groupService.addUserToGroupByIds(addUserToGroupRequest.getGroupId(), addUserToGroupRequest.getUserId());
        AddUserToGroupResponse response = new AddUserToGroupResponse();

        // TODO: change this message
        response.setMessage("Successfully Added");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<GroupListUsersResponse> getUserList(
            @Valid @RequestBody final GroupListUsersRequest groupListUsersRequest) {

        List<User> userList = groupService.getUsersByGroupId(groupListUsersRequest.getGroupId());

        GroupListUsersResponse response = new GroupListUsersResponse();
        response.setMessage("");
        for (User user : userList) {
            // TODO: change this to a meaningful response
            response.setMessage(response.getMessage() + " " + user.getUsername());
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
