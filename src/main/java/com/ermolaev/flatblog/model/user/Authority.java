package com.ermolaev.flatblog.model.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Document(collection = "authority")
public class Authority implements GrantedAuthority {

  @NotNull
  @Size(max = 50)
  @Id
  private String name;


  @Override
  public String getAuthority() {
    return name;
  }
}
