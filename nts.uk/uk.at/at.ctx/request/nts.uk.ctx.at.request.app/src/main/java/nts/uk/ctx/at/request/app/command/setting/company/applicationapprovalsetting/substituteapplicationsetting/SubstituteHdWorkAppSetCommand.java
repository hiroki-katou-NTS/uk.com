package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.substituteapplicationsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppCommentSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.Comment;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.substituteapplicationsetting.SubstituteHdWorkAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.substituteapplicationsetting.SubstituteHolidayAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.substituteapplicationsetting.SubstituteSimultaneousAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.substituteapplicationsetting.SubstituteWorkAppSet;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.shr.com.color.ColorCode;
import org.apache.commons.lang3.BooleanUtils;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubstituteHdWorkAppSetCommand {
    private int simultaneousApplyRequired;
    private int allowanceForAbsence;
    private String subHolidayComment;
    private String subHolidayColor;
    private boolean subHolidayBold;
    private String subWorkComment;
    private String subWorkColor;
    private boolean subWorkBold;

    public SubstituteHdWorkAppSet toDomain(String companyId) {
        return new SubstituteHdWorkAppSet(
                companyId,
                new SubstituteSimultaneousAppSet(
                        BooleanUtils.toBoolean(simultaneousApplyRequired)
//                        EnumAdaptor.valueOf(allowanceForAbsence, ApplyPermission.class)
                ),
                new SubstituteHolidayAppSet(
                        new AppCommentSet(
                                new Comment(subHolidayComment),
                                subHolidayBold,
                                new ColorCode(subHolidayColor)
                        )
                ),
                new SubstituteWorkAppSet(
                        new AppCommentSet(
                                new Comment(subWorkComment),
                                subWorkBold,
                                new ColorCode(subWorkColor)
                        )
                )
        );
    }
}
