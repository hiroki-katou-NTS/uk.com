package nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.itemrangeset.RangeSettingEnum;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.itemrangeset.StatementItemRangeSetting;

@Getter
public class ItemRangeSetExportData {

    /**
     * エラー上限値設定
     */
    private RangeSettingEnum errorUpperSettingAtr;

    /**
     * エラー下限値設定
     */
    private RangeSettingEnum errorLowerSettingAtr;

    /**
     * アラーム上限値設定
     */
    private RangeSettingEnum alarmUpperSettingAtr;

    /**
     * アラーム下限値設定
     */
    private RangeSettingEnum alarmLowerSettingAtr;

    public ItemRangeSetExportData(StatementItemRangeSetting domain) {
        this.errorUpperSettingAtr = domain.getErrorRangeSet().getErrorUpperLimitSetting().getErrorUpperLimitSettingAtr();
        this.errorLowerSettingAtr = domain.getErrorRangeSet().getErrorLowerLimitSetting().getErrorLowerLimitSettingAtr();
        this.alarmUpperSettingAtr = domain.getAlarmRangeSet().getAlarmUpperLimitSetting().getAlarmUpperLimitSettingAtr();
        this.alarmLowerSettingAtr = domain.getAlarmRangeSet().getAlarmLowerLimitSetting().getAlarmLowerLimitSettingAtr();
    }
}
