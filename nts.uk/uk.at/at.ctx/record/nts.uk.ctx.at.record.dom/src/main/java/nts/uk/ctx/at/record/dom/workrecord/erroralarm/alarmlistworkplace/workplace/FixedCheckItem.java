package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック（職場）.固定のチェック職場項目
 */
public enum FixedCheckItem {

    // 基準時間の未設定
    NO_REF_TIME(1,"基準時間の未設定"),
    // 36協定目安時間の未設定
    TIME_NOT_SET_FOR_36_ESTIMATED(2,"36協定目安時間の未設定"),
    // 公休日数の未設定
    UNSET_OF_HD(3,"公休日数の未設定"),
    // 目安時間・金額時間の未設定
    ESTIMATED_OR_AMOUNT_TIME_NOT_SET(4, "目安時間・金額時間の未設定"),
    // 管理者未登録
    NOT_REGISTERED(5, "管理者未登録");

    public int value;

    public String nameId;

    private FixedCheckItem (int value,String nameId) {
        this.value = value;
        this.nameId = nameId;
    }

}
