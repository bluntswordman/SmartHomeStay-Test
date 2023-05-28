package com.smarthomestay.dto;

import com.smarthomestay.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  private Role role;
  private String username;
  private String email;
  private String password;
}
