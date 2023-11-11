package com.group4.reviewservice.models;

import com.group4.reviewservice.enums.ReactionTypeEnum;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;


// This is a schema/model for the "user_reactions" Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_reactions")
@Entity
public class UserReaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    @ManyToOne
    private Review review; // Link to the review the reaction is associated with

    @Column(nullable = false)
    private UUID userId; // Link to the user who reacted

    @Column(nullable = false, columnDefinition = "varchar(20)")
    @Enumerated(EnumType.STRING)
    private ReactionTypeEnum reactionType; // Enum to represent "useful," "funny," or "cool"
}
