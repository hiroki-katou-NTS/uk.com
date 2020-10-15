package nts.uk.ctx.at.request.app.find.setting.request.application.businesstrip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.AppCommentSetDto;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppCommentSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.Comment;
import nts.uk.ctx.at.request.dom.setting.request.application.businesstrip.AppTripRequestSet;
import nts.uk.shr.com.color.ColorCode;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BusinessTripSetDto {

    private String cid;

    private AppCommentSetDto appCommentSet;


    public static BusinessTripSetDto fromDomain(AppTripRequestSet domain) {
        return new BusinessTripSetDto(
                domain.getCompanyId(),
                AppCommentSetDto.fromDomain(domain.getComment())
        );
    }

    public AppTripRequestSet toDomain() {
        AppCommentSet appCommentSet = new AppCommentSet();
        appCommentSet.setBold(appCommentSet.isBold());
        appCommentSet.setColorCode(new ColorCode(this.getAppCommentSet().getColorCode()));
        appCommentSet.setComment(new Comment(this.getAppCommentSet().getComment()));
        return new AppTripRequestSet(
                this.getCid(),
                appCommentSet
        );
    }

}
