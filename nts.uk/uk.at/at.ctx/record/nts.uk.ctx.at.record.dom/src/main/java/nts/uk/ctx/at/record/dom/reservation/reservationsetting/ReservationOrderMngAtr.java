package nts.uk.ctx.at.record.dom.reservation.reservationsetting;

import lombok.AllArgsConstructor;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.支給賞与額履歴.予約.予約設定.予約発注機能管理区分
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum ReservationOrderMngAtr {
	
    // 管理しない
    CANNOT_MANAGE(0, "管理しない"),

    // 管理する
    CAN_MANAGE(1, "管理する");

    public final int value;
    public final String name;
}
