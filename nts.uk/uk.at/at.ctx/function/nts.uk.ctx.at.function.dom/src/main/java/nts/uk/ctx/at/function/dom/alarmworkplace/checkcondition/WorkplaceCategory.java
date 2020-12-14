package nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition;

import nts.arc.enums.EnumAdaptor;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.アラーム_職場別.アラームリスト_職場別のカテゴリ
 */
public enum WorkplaceCategory {

    // マスタチェック(基本)
    MASTER_CHECK_BASIC(0, "KAL020_21"),
    // マスタチェック(職場)
    MASTER_CHECK_WORKPLACE(1, "KAL020_210"),
    // マスタチェック(日次)
    MASTER_CHECK_DAILY(2, "KAL020_103"),
    // スケジュール／日次
    SCHEDULE_DAILY(3, "KAL020_300"),
    // 月次
    MONTHLY(4, "KAL020_401"),
    // 申請承認
    APPLICATION_APPROVAL(5, "KAL020_501");

    public final int value;
    public final String nameId;

    private WorkplaceCategory(int value, String nameId) {
        this.value = value;
        this.nameId = nameId;
    }

    public static WorkplaceCategory of(int value) {
        return EnumAdaptor.valueOf(value, WorkplaceCategory.class);
    }

}
