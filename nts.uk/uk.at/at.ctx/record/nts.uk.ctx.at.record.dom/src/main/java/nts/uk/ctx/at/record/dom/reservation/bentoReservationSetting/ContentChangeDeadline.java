package nts.uk.ctx.at.record.dom.reservation.bentoReservationSetting;


import lombok.AllArgsConstructor;

/**
 * 予約済みの内容変更期限内容
 */

@AllArgsConstructor
public enum  ContentChangeDeadline {

    /** The Always fixable. */
    // 常に修正可能
    ALLWAY_FIXABLE(0, "常に修正可能"),

    /** The Period from order. */
    // 注文からの期間
    PERIOD_FROM_ORDER(1, "注文からの期間"),

    /** Can be modified only during reception hours. */
    // 受付時間内のみ修正可能
    MODIFIED_DURING_RECEPTION_HOUR(2, "受付時間内のみ修正可能");

    public final int value;
    public final String name;

    private final static ContentChangeDeadline[] values = ContentChangeDeadline.values();

    public static ContentChangeDeadline valueOf(Integer value) {
        // Invalid object.
        if (value == null) {
            return null;
        }

        // Find value.
        for (ContentChangeDeadline val : ContentChangeDeadline.values) {
            if (val.value == value) {
                return val;
            }
        }

        // Not found.
        return null;
    }
}
