package nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.AppCommentSetDto;
import nts.uk.ctx.at.request.dom.setting.request.application.businesstrip.AppTripRequestSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppCommentSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.Comment;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.triprequestsetting.ContractCheck;
import nts.uk.shr.com.color.ColorCode;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BusinessTripSetDto {

    private String cid;

    private AppCommentSetDto appCommentSet;

    private Integer contraditionCheckAtr;

    public static BusinessTripSetDto fromDomain(AppTripRequestSet domain) {
        return new BusinessTripSetDto(
                domain.getCompanyId(),
                AppCommentSetDto.fromDomain(domain.getComment()),
                domain.getContractCheck().isPresent() ? domain.getContractCheck().get().value : null
        );
    }

    public AppTripRequestSet toDomain() {
        AppCommentSet appCommentSet = new AppCommentSet();
        appCommentSet.setBold(appCommentSet.isBold());
        appCommentSet.setColorCode(new ColorCode(this.getAppCommentSet().getColorCode()));
        appCommentSet.setComment(new Comment(this.getAppCommentSet().getComment()));
        return new AppTripRequestSet(
                this.getCid(),
                appCommentSet,
                this.getContraditionCheckAtr() == null ? Optional.empty() : Optional.ofNullable(EnumAdaptor.valueOf(this.getContraditionCheckAtr(), ContractCheck.class))
        );
    }

}
