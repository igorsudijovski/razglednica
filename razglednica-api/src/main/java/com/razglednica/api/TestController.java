package com.razglednica.api;

import com.razglednica.model.CustomModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @GetMapping("/blabla/123")
  public ResponseEntity<CustomModel> blabla() {
    CustomModel model = new CustomModel();
    model.setFirstName("first");
    model.setLastName("last name");
    return ResponseEntity.ok(model);
  }

}
