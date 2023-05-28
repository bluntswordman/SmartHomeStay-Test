package com.smarthomestay.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smarthomestay.constant.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private long id;

  @Enumerated(EnumType.STRING)
  private Role role;

  @Column(length = 100, name = "username")
  private String username;

  @Column(length = 100, name = "email", unique = true, nullable = false)
  private String email;

  @Column(name = "password", nullable = false)
  @JsonIgnore
  private String password;

  @Column(name = "is_check_in")
  private Boolean isCheckin = false;

  @Column(name = "is_check_out")
  private Boolean isCheckout = false;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  private List<Bill> bills;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  private Room room;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
