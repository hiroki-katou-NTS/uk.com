package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.setting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.DisplayReason;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DisplayReasonDto {
    private String companyID;

    /**
     * 定型理由の表示
     */
    private int displayFixedReason;

    /**
     * 申請理由の表示
     */
    private int displayAppReason;

    /**
     * 申請種類
     */
    private int appType;

    /**
     * 休暇申請の種類
     */
    private Integer holidayAppType;

    public static DisplayReasonDto fromDomain(DisplayReason domain) {
        return new DisplayReasonDto(
                domain.getCompanyID(),
                domain.getDisplayFixedReason().value,
                domain.getDisplayAppReason().value,
                domain.getAppType().value,
                domain.getOpHolidayAppType().map(i -> i.value).orElse(null));
    }
}
