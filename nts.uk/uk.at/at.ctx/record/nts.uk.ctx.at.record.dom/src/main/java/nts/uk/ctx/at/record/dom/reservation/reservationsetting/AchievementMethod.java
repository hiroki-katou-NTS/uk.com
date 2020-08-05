package nts.uk.ctx.at.record.dom.reservation.reservationsetting;

import lombok.AllArgsConstructor;

/**
 * 達成方法
 */
@AllArgsConstructor
public enum AchievementMethod {

    ONLY_ORDERED(0),

    ALL_DATA(1);

    public int value;


    private final static AchievementMethod[] values = AchievementMethod.values();

    public static AchievementMethod valueOf(Integer value) {
        // Invalid object.
        if (value == null) {
            return null;
        }

        // Find value.
        for (AchievementMethod val : AchievementMethod.values) {
            if (val.value == value) {
                return val;
            }
        }

        // Not found.
        return null;
    }

}