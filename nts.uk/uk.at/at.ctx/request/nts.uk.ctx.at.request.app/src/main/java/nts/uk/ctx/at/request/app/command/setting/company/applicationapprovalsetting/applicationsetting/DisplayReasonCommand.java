package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.applicationsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.DisplayReason;
import org.apache.commons.lang3.BooleanUtils;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DisplayReasonCommand {
    /**
     * 定型理由の表示
     */
    private boolean displayFixedReason;

    /**
     * 申請理由の表示
     */
    private boolean displayAppReason;

    /**
     * 申請種類
     */
    private int appType;

    /**
     * 休暇申請の種類
     */
    private Integer holidayAppType;

    public DisplayReason toDomain(String companyId) {
        if (appType == ApplicationType.ABSENCE_APPLICATION.value)
            return DisplayReason.createHolidayAppDisplayReason(
                    companyId,
                    BooleanUtils.toInteger(displayFixedReason),
                    BooleanUtils.toInteger(displayAppReason),
                    holidayAppType);
        else
            return DisplayReason.createAppDisplayReason(
                    companyId,
                    BooleanUtils.toInteger(displayFixedReason),
                    BooleanUtils.toInteger(displayAppReason),
                    appType);
    }
}
