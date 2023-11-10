package com.group4.reviewservice.enums;

public enum ReactionTypeEnum {
    USEFUL,
    FUNNY,
    COOL;

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