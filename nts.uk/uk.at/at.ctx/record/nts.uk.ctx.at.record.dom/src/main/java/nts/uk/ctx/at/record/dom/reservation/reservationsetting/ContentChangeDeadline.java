package nts.uk.ctx.at.record.dom.reservation.reservationsetting;


import lombok.AllArgsConstructor;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.支給賞与額履歴.予約.予約設定.予約済みの内容変更期限内容
 */

@AllArgsConstructor
public enum  ContentChangeDeadline {

    // 常に修正可能
    ALLWAY_FIXABLE(0, "常に修正可能"),
    
    // 受付時間内は修正可能
    MODIFIED_DURING_RECEPTION_HOUR(1, "受付時間内は修正可能"),

    // 注文日からの○日間修正可能
	MODIFIED_FROM_ORDER_DATE(2, "注文日からの○日間修正可能");

    

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
