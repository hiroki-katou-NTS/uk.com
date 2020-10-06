package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums;

/**
 * Enumeration: 帳票共通の設定区分
 * @author chinh.hm
 */
public enum  SettingClassificationCommon {
    // 0 定型選択
    STANDARD_SELECTION (0),

    // 1 自由設定
    FREE_SETTING (1);

    /** The value. */
    public final int value;

    private SettingClassificationCommon(int value) {
        this.value = value;
    }
}
