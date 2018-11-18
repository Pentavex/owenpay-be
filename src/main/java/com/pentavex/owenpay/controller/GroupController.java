package com.pentavex.owenpay.controller;

import com.pentavex.owenpay.domain.Group;
import com.pentavex.owenpay.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import swaggergen.controller.GroupApi;
import swaggergen.model.CreateGroupRequest;
import swaggergen.model.CreateGroupResponse;

import javax.validation.Valid;

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

}
