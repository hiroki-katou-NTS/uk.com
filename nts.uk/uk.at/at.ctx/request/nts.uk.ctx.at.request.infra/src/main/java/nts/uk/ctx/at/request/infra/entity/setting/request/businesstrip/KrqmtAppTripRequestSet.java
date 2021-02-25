package nts.uk.ctx.at.request.infra.entity.setting.request.businesstrip;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppCommentSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.Comment;
import nts.uk.ctx.at.request.dom.setting.request.application.businesstrip.AppTripRequestSet;
import nts.uk.shr.com.color.ColorCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "KRQMT_APP_TRIP")
public class KrqmtAppTripRequestSet extends ContractUkJpaEntity {

    /** 会社ID */
    @Id
    @Column(name = "CID")
    public String companyId;

    @Column(name="CONTRACT_CD")
    public String contractCd;

    @Basic(optional = true)
    @Column(name="COMMENT_CONTENT1")
    public String comment;

    @Column(name="COMMENT_FONT_WEIGHT1")
    public Integer bold;

    @Column(name="COMMENT_FONT_COLOR1")
    public String colorCode;

    @Override
    protected Object getKey() {
        return this.companyId;
    }

    public static KrqmtAppTripRequestSet toEntity(AppTripRequestSet domain) {
        return new KrqmtAppTripRequestSet(
                domain.getCompanyId(),
                AppContexts.user().contractCode(),
                domain.getComment().getComment().v(),
                domain.getComment().isBold() ? 1 : 0,
                domain.getComment().getColorCode().v()
        );
    }

    public AppTripRequestSet toDomain() {
        AppCommentSet appCommentSet = new AppCommentSet();
        appCommentSet.setComment(new Comment(this.getComment()));
        appCommentSet.setBold(this.getBold() == 1 ? true : false);
        appCommentSet.setColorCode(new ColorCode(this.getColorCode()));
        return new AppTripRequestSet(
                this.getCompanyId(),
                appCommentSet
        );
    }
}
