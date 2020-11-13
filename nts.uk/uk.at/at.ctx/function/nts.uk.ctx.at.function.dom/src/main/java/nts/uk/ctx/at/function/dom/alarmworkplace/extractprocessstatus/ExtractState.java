package nts.uk.ctx.at.function.dom.alarmworkplace.extractprocessstatus;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.就業機能.アラーム_職場別.抽出処理状況
 * 抽出状態
 *
 * @author Le Huu Dat
 */
public enum ExtractState {

    /**
     * 正常終了
     */
    SUCCESSFUL_COMPLE(0, "Enum_ExtractionState_SuccessfulComple"),

    /**
     * 異常終了
     */
    ABNORMAL_TERMI(1, "Enum_ExtractionState_AbnormalTermi"),

    /**
     * 処理中
     */
    PROCESSING(2, "Enum_ExtractionState_Processing"),

    /**
     * 中断
     */
    INTERRUPTION(3, "Enum_ExtractionState_Interruption");

    public final int value;

    /**
     * The name id.
     */
    public final String nameId;

    private ExtractState(int value, String nameId) {
        this.value = value;
        this.nameId = nameId;
    }
}
