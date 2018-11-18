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
import swaggergen.model.CreateGroupRequest;
import swaggergen.model.GeneralResponse;
import swaggergen.model.GroupListUsersRequest;

import javax.validation.Valid;
import java.util.List;

@RestController
public class GroupController implements GroupApi {

    @Autowired
    private GroupService groupService;

    @Override
    public ResponseEntity<GeneralResponse> createGroup(@Valid @RequestBody final CreateGroupRequest request) {

        Group savedGroup = groupService.createGroupWithUsername(request.getGroupname(), request.getUsername());

        GeneralResponse response = new GeneralResponse();
        response.setMessage(savedGroup.getGroupname());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<GeneralResponse> addUserToGroup(
            @Valid @RequestBody final AddUserToGroupRequest addUserToGroupRequest) {

        groupService.addUserToGroupByIds(addUserToGroupRequest.getGroupId(), addUserToGroupRequest.getUserId());
        GeneralResponse response = new GeneralResponse();

        // TODO: change this message
        response.setMessage("Successfully Added");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<GeneralResponse> getUserList(
            @Valid @RequestBody final GroupListUsersRequest groupListUsersRequest) {

        List<User> userList = groupService.getUsersByGroupId(groupListUsersRequest.getGroupId());

        GeneralResponse response = new GeneralResponse();
        response.setMessage("");
        for (User user : userList) {
            // TODO: change this to a meaningful response
            response.setMessage(response.getMessage() + " " + user.getUsername());
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
