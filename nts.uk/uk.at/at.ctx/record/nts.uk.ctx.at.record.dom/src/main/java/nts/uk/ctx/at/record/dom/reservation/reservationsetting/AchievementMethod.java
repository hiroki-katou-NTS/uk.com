package nts.uk.ctx.at.record.dom.reservation.reservationsetting;

import lombok.AllArgsConstructor;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.支給賞与額履歴.予約.予約設定.実績集計方法
 */
@AllArgsConstructor
public enum AchievementMethod {
	
	// 注文済みに設定したデータのみ注文数、注文金額に集計する
    ONLY_ORDERED(0),
    
    // 全データが注文数、注文金額に集計する
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