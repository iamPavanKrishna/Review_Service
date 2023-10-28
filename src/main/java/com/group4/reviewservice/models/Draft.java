package com.group4.reviewservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "drafts")
@Entity
public class Draft  extends BaseModel{

    @Column(nullable = false)
    private Integer stars;
    
    @Column(length = 200, nullable = false)
    private String text;


    // Getter, setter and toString() methods here...
}