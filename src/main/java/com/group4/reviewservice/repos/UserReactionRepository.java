package com.group4.reviewservice.repos;

import com.group4.reviewservice.enums.ReactionTypeEnum;
import com.group4.reviewservice.models.Review;
import com.group4.reviewservice.models.UserReaction;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


// This a Data Access Layer for the UserReaction Database
@Repository
public interface UserReactionRepository extends JpaRepository<UserReaction, UUID> {
    
    Optional<UserReaction> findByReviewAndUserIdAndReactionType(Review review, UUID userId, ReactionTypeEnum reactionType);  // This method fetches the review by mapping review, userId, reactionType
    void deleteByReview(Review review);  // This method deletes the review by mapping review
}