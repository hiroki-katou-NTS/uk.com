package nts.uk.ctx.at.record.dom.workrecord.alarmlist.errorsetting;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック(基本).アラームチェック区分
 */
public enum AlarmCheckCls {

    ERROR(1,"エラー"),

    ALARM(2,"アラーム"),

    EXTRACT_CONDITION(3,"抽出条件");

    public int value;

    public String nameId;

    private AlarmCheckCls (int value,String nameId) {
        this.value = value;
        this.nameId = nameId;
    }

}
