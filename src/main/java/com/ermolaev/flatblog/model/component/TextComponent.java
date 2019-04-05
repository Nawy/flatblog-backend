package com.ermolaev.flatblog.model.component;

import com.ermolaev.flatblog.model.ComponentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TextComponent implements Component {

  private final ComponentType type = ComponentType.TEXT;

  private String header;
  private String text;
}
