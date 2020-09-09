package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.substituteapplicationsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.substituteapplicationsetting.SubstituteHdWorkAppSet;
import org.apache.commons.lang3.BooleanUtils;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubstituteHdWorkAppSetDto {
    private int simultaneousApplyRequired;
//    private int allowanceForAbsence;
    private String subHolidayComment;
    private String subHolidayColor;
    private boolean subHolidayBold;
    private String subWorkComment;
    private String subWorkColor;
    private boolean subWorkBold;

    public static SubstituteHdWorkAppSetDto fromDomain(SubstituteHdWorkAppSet domain) {
        return new SubstituteHdWorkAppSetDto(
                BooleanUtils.toInteger(domain.getSimultaneousSetting().isSimultaneousApplyRequired()),
//                domain.getSimultaneousSetting().getAllowanceForAbsence().value,
                domain.getSubstituteHolidaySetting().getComment().getComment().v(),
                domain.getSubstituteHolidaySetting().getComment().getColorCode().v(),
                domain.getSubstituteHolidaySetting().getComment().isBold(),
                domain.getSubstituteWorkSetting().getComment().getComment().v(),
                domain.getSubstituteWorkSetting().getComment().getColorCode().v(),
                domain.getSubstituteWorkSetting().getComment().isBold()
        );
    }
}
