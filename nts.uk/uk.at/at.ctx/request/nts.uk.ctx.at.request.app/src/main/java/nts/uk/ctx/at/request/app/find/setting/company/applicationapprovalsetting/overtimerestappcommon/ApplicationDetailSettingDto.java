package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.overtimerestappcommon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.ApplicationDetailSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.AtWorkAtr;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import org.apache.commons.lang3.BooleanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDetailSettingDto {
    /**
     * 指示が必須
     */
    private int requiredInstruction;

    /**
     * 事前必須設定
     */
    private int preRequireSet;

    /**
     * 時間入力利用区分
     */
    private int timeInputUse;

    /**
     * 時刻計算利用区分
     */
    private int timeCalUse;

    /**
     * 出退勤時刻初期表示区分
     */
    private int atworkTimeBeginDisp;

    /**
     * 退勤時刻がない時システム時刻を表示するか
     */
    private int dispSystemTimeWhenNoWorkTime;

    public static ApplicationDetailSettingDto fromDomain(ApplicationDetailSetting domain) {
        return new ApplicationDetailSettingDto(
                BooleanUtils.toInteger(domain.getRequiredInstruction()),
                domain.getPreRequireSet().value,
                domain.getTimeInputUse().value,
                domain.getTimeCalUse().value,
                domain.getAtworkTimeBeginDisp().value,
                BooleanUtils.toInteger(domain.isDispSystemTimeWhenNoWorkTime())
        );
    }
}
