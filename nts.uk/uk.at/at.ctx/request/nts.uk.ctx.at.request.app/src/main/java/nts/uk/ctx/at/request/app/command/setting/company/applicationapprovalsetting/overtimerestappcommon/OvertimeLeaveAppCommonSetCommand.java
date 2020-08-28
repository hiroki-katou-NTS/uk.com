package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.overtimerestappcommon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeLeaveAppCommonSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OvertimeLeaveAppCommonSetCommand {
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

    public OvertimeLeaveAppCommonSet toDomain() {
        return OvertimeLeaveAppCommonSet.create(
                preExcessDisplaySetting,
                extratimeExcessAtr,
                extratimeDisplayAtr,
                performanceExcessAtr,
                checkOvertimeInstructionRegister,
                checkDeviationRegister,
                overrideSet);
    }
}
