package nts.uk.ctx.at.request.app.command.setting.request.application.businesstrip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppCommentSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.Comment;
import nts.uk.ctx.at.request.dom.setting.request.application.businesstrip.AppTripRequestSet;
import nts.uk.shr.com.color.ColorCode;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BusinessTripSetCommand {

    private String commentContent;

    private String commentColor;

    private Boolean commentBold;


    public AppTripRequestSet toDomain(String companyId) {
        AppCommentSet appCommentSet = new AppCommentSet();
        appCommentSet.setBold(commentBold);
        appCommentSet.setColorCode(new ColorCode(commentColor));
        appCommentSet.setComment(new Comment(commentContent));
        return new AppTripRequestSet(
                companyId,
                appCommentSet
        );
    }

}
