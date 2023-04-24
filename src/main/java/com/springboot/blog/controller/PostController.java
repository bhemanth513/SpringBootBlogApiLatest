package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "CRUD Rest Api for Post Resource")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    
    
    @Operation(
    		summary = "Create Post Rest API",
    		description = "Create Rest API is used to save the post into database"
    		)
    @ApiResponse(
    		responseCode = "201",
    		description = "Http status 201 created"
    		)
    //enables autherization header for api
    @SecurityRequirement(name = "Bearer authentication")	
    // create blog post rest api
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @Operation(
    		summary = "Get Post Rest API",
    		description = "Get All the posts from the db"
    		)
    @ApiResponse(
    		responseCode = "200",
    		description = "Http status 200 success"
    		)
    // get all posts rest api
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }


    @Operation(
    		summary = "Get PostById Rest API",
    		description = "Get post for the given id from the db"
    		)
    @ApiResponse(
    		responseCode = "200",
    		description = "Http status 200 success"
    		)
    // get post by id
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }


    @Operation(
    		summary = "Update Post Rest API",
    		description = "update the given post in the db"
    		)
    @ApiResponse(
    		responseCode = "200",
    		description = "Http status 200 success"
    		)
    
    //enables autherization header for perticular api
    @SecurityRequirement(name = "Bearer authentication")
    // update post by id rest api
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") long id){

       PostDto postResponse = postService.updatePost(postDto, id);

       return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }


    @Operation(
    		summary = "delete Post Rest API",
    		description = "delete the post from the db"
    		)
    @ApiResponse(
    		responseCode = "200",
    		description = "Http status 200 success"
    		)
    //enables autherization header for perticular api
    @SecurityRequirement(name = "Bearer authentication")
    // delete post rest api
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){

        postService.deletePostById(id);

        return new ResponseEntity<>("Post entity deleted successfully.", HttpStatus.OK);
    }

    // Build Get Posts by Category REST API
    // http://localhost:8080/api/posts/category/3
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("id") Long categoryId){
        List<PostDto> postDtos = postService.getPostsByCategory(categoryId);
        return ResponseEntity.ok(postDtos);
    }
}
