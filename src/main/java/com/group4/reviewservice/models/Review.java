package com.group4.reviewservice.models;

import com.group4.reviewservice.enums.AttachmentTypeEnum;
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


// This is a schema/model for the "reviews" Entity
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

    @Column(nullable = false, columnDefinition = "varchar(20)")
    @Enumerated(EnumType.STRING)
    private AttachmentTypeEnum attachmentTypeEnum;

    @Column(nullable = false)
    private String attachmentUrl;

    @Column(nullable = false)
    private int usefulCount;

    @Column(nullable = false)
    private int funnyCount;

    @Column(nullable = false)
    private int coolCount;

    // Create methods to increment and decrement reactions
    public void incrementUseful() {
        usefulCount++;
    }

    public void decrementUseful() {
        if (usefulCount > 0) {
            usefulCount--;
        }
    }

    public void incrementFunny() {
        funnyCount++;
    }

    public void decrementFunny() {
        if (funnyCount > 0) {
            funnyCount--;
        }
    }

    public void incrementCool() {
        coolCount++;
    }

    public void decrementCool() {
        if (coolCount > 0) {
            coolCount--;
        }
    }

}