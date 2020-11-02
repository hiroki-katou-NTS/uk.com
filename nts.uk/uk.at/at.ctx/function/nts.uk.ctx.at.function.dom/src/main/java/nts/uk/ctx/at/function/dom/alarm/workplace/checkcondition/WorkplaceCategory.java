package nts.uk.ctx.at.function.dom.alarm.workplace.checkcondition;

import nts.arc.enums.EnumAdaptor;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.アラーム_職場別.アラームリスト_職場別のカテゴリ
 */
public enum WorkplaceCategory {

    // マスタチェック(基本)
    MASTER_CHECK_BASIC(0, "マスタチェック(基本)"),
    // マスタチェック(職場)
    MASTER_CHECK_WORKPLACE(1, "マスタチェック(職場)"),
    // マスタチェック(日次)
    MASTER_CHECK_DAILY(2, "マスタチェック(日次)"),
    // スケジュール／日次
    SCHEDULE_DAILY(3, "スケジュール／日次"),
    // 月次
    MONTHLY(4, "月次"),
    // 申請承認
    APPLICATION_APPROVAL(5, "申請承認");

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
