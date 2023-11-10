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

    //@Column(nullable = false)
    //private Draft draft;  // need to clarify this

    // //Getters

    // public UUID getUserId() {
    //     return userId;
    // }
    
    // public UUID getServiceId() {
    //     return serviceId;
    // }

    // public Date getCreatedAt() {
    //     return createdAt;
    // }

    // public Date getUpdatedAt() {
    //     return updatedAt;
    // }

    // public String getText() {
    //     return text;
    // }

    // public Long getLike() {
    //     return like;
    // }

    // }

    // public AttacthmentTypeEnum getAttacthmentTypeEnum() {
    //     return attacthmentTypeEnum;
    // }

    // public String getAttachmentUrl() {
    //     return attachmentUrl;
    // }

    // //Setters

    // public Review setUserId(UUID userId) {
    //     this.userId = userId;
    //     return this;
    // }

    // public Review setServiceId(UUID serviceId) {
    //     this.serviceId = serviceId;
    //     return this;
    // }

    // public Review setCreatedAt(Date createdAt) {
    //     this.createdAt = createdAt;
    //     return this;
    // }

    // public Review setUpdatedAt(Date updatedAt) {
    //     this.updatedAt = updatedAt;
    //     return this;
    // }

    // public Review setText(String text) {
    //     this.text = text;
    //     return this;
    // }

    // public Review setUseful(String useful) {
    //     this.like = like;
    //     return this;
    // }

    // public Review setAttacthmentTypeEnum(AttacthmentTypeEnum attacthmentTypeEnum) {
    //     this.attacthmentTypeEnum = attacthmentTypeEnum;
    //     return this;
    // }

    // public Review setAttachmentUrl(String attachmentUrl) {
    //     this.attachmentUrl = attachmentUrl;
    //     return this;
    // }

    // //toString

    // @Override
    // public String toString() {
    //     return "Review{" +
    //             "userId=" + userId +
    //             ", serviceId=" + serviceId +
    //             ", createdAt=" + createdAt +
    //             ", updatedAt=" + updatedAt +
    //             ", text='" + text + '\'' +
    //             ", attacthmentTypeEnum=" + attacthmentTypeEnum +
    //             ", attachmentUrl='" + attachmentUrl + '\'' +
    //             '}';
    // }
    

}