package com.katanox.api.model.orm;

public enum ChargeType {
    per_night,
    once;

    public static ChargeType fromString(String value) {
        for (ChargeType chargeType : ChargeType.values()) {
            if (chargeType.name().equalsIgnoreCase(value)) {
                return chargeType;
            }
        }
        throw new IllegalArgumentException("Unexpected value: " + value);
    }
}
