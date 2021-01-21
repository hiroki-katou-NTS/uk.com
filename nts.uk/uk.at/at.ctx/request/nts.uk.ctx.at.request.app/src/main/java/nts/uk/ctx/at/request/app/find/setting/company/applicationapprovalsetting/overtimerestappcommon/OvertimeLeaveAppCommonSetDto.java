package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.overtimerestappcommon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeLeaveAppCommonSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OvertimeLeaveAppCommonSetDto {
    /**
     * 事前超過表示設定
     */
    private int preExcessDisplaySetting;

    /**
     * 時間外超過区分
     */
    private int extratimeExcessAtr;

    /**
     * 時間外表示区分
     */
    private int extratimeDisplayAtr;

    /**
     * 実績超過区分
     */
    private int performanceExcessAtr;

    /**
     * 登録時の指示時間超過チェック
     */
    private int checkOvertimeInstructionRegister;

    /**
     * 登録時の乖離時間チェック
     */
    private int checkDeviationRegister;

    /**
     * 実績超過打刻優先設定
     */
    private int overrideSet;

    public static OvertimeLeaveAppCommonSetDto fromDomain(OvertimeLeaveAppCommonSet domain) {
        return new OvertimeLeaveAppCommonSetDto(
                domain.getPreExcessDisplaySetting().value,
                domain.getExtratimeExcessAtr().value,
                domain.getExtratimeDisplayAtr().value,
                domain.getPerformanceExcessAtr().value,
                domain.getCheckOvertimeInstructionRegister().value,
                domain.getCheckDeviationRegister().value,
                domain.getOverrideSet().value
        );
    }
}
