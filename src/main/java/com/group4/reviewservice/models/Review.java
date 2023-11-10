package com.group4.reviewservice.models;

import com.group4.reviewservice.enums.AttacthmentTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reviews")
@Entity
public class Review{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID serviceId;
    
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
    
    @Column(length = 200, nullable = false)
    private String text;

    @Column(nullable = false, columnDefinition = "varchar(20) not null")
    @Enumerated(EnumType.STRING)
    private AttacthmentTypeEnum attacthmentTypeEnum;

    @Column(nullable = false)
    private String attachmentUrl;

    @Column(nullable = false)
    private Long likes = 0L;  // Default value is 0

    @Column(nullable = false)
    private Long dislikes = 0L;  // Default value is 0

    // Other methods...

    public void incrementLikes() {
        this.likes++;
    }

    public void incrementDislikes() {
        this.dislikes++;
    }

}