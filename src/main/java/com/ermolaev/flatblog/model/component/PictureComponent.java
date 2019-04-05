package com.ermolaev.flatblog.model.component;

import com.ermolaev.flatblog.model.ComponentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PictureComponent implements Component {

  private final ComponentType type = ComponentType.PICTURE;

  private String location;
  private int width;
  private int height;
}
