package com.example.emotiguard.Models;

public class UserProfile {
  private String name,pas,email;

  public UserProfile() {
  }

  public UserProfile(String name, String email, String pas) {
    this.name = name;
    this.email = email;
    this.pas = pas;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPas() {
    return pas;
  }

  public void setPas(String pas) {
    this.pas = pas;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
