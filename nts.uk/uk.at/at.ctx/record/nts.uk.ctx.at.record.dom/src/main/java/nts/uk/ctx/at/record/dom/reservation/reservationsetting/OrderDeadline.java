package nts.uk.ctx.at.record.dom.reservation.reservationsetting;

import lombok.AllArgsConstructor;

/**
 * 注文済み期限方法
 */

@AllArgsConstructor
public enum  OrderDeadline {

    /** The always. */
    // 常時
    ALWAYS(0, "常時"),

    /** During the closing period. */
    // 締め期間中
    DURING_CLOSING_PERIOD(1, "締め期間中");

    public final int value;
    public final String name;


    private final static OrderDeadline[] values = OrderDeadline.values();

    public static OrderDeadline valueOf(Integer value) {
        // Invalid object.
        if (value == null) {
            return null;
        }

        // Find value.
        for (OrderDeadline val : OrderDeadline.values) {
            if (val.value == value) {
                return val;
            }
        }

        // Not found.
        return null;
    }
}
