package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.substituteapplicationsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppCommentSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.Comment;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.substituteapplicationsetting.SubstituteHdWorkAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.substituteapplicationsetting.SubstituteHolidayAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.substituteapplicationsetting.SubstituteSimultaneousAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.substituteapplicationsetting.SubstituteWorkAppSet;
import nts.uk.shr.com.color.ColorCode;
import nts.uk.shr.com.context.AppContexts;

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
    
    public SubstituteHdWorkAppSet toDomain() {
        return new SubstituteHdWorkAppSet(
                AppContexts.user().companyId(), 
                new SubstituteSimultaneousAppSet(simultaneousApplyRequired == 1), 
                new SubstituteHolidayAppSet(new AppCommentSet(new Comment(subHolidayComment), subHolidayBold, new ColorCode(subHolidayColor))), 
                new SubstituteWorkAppSet(new AppCommentSet(new Comment(subWorkComment), subWorkBold, new ColorCode(subWorkColor))));
    }
}
