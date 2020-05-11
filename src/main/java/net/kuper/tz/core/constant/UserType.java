package net.kuper.tz.core.constant;

public enum UserType {
    ADMIN(1),
    CUSTOMER(2);

    public int value;

    UserType(int v) {
        this.value = v;
    }
}
