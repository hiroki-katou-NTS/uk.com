package nts.uk.ctx.at.request.dom.setting.request.application.businesstrip;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppCommentSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.triprequestsetting.Comment;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.triprequestsetting.ContractCheck;

import java.util.Optional;

/**
 * 出張申請設定
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppTripRequestSet implements DomainAggregate {

    // 会社ID
    private String companyId;
    // コメント
    private AppCommentSet comment;


}
