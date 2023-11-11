package com.group4.reviewservice.enums;

//This is ENUM for Reaction Type for "user_reactions" Entity
public enum ReactionTypeEnum {
    USEFUL, // String
    FUNNY, // String
    COOL; // String

    // This method converts the reactionType from String to ReactionTypeEnum
    public static ReactionTypeEnum fromString(String reactionType) {
        if (reactionType == null) {
            return null;
        }
        for (ReactionTypeEnum reactionTypeEnum : ReactionTypeEnum.values()) {
            if (reactionType.equalsIgnoreCase(reactionTypeEnum.toString())) {
                return reactionTypeEnum;
            }
        }
        return null;
    }
}