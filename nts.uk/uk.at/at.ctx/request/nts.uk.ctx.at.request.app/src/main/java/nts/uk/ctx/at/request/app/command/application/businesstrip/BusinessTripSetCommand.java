package nts.uk.ctx.at.request.app.command.application.businesstrip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.AppCommentSetDto;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppCommentSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.Comment;
import nts.uk.ctx.at.request.dom.setting.request.application.businesstrip.AppTripRequestSet;
import nts.uk.shr.com.color.ColorCode;
import nts.uk.shr.com.context.AppContexts;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessTripSetCommand {

    // コメント
    private AppCommentSetDto appCommentSet;

    public AppTripRequestSet toDomain() {
        AppCommentSet appCommentSet = new AppCommentSet();
        appCommentSet.setBold(appCommentSet.isBold());
        appCommentSet.setColorCode(new ColorCode(this.getAppCommentSet().getColorCode()));
        appCommentSet.setComment(new Comment(this.getAppCommentSet().getComment()));
        return new AppTripRequestSet(
                AppContexts.user().companyId(),
                appCommentSet
        );
    }

}
