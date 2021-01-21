package nts.uk.ctx.at.request.infra.entity.setting.workplace.requestbyworkplace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApplicationUseSetting;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApprovalFunctionSet;
import nts.uk.ctx.at.request.dom.setting.workplace.requestbycompany.RequestByCompany;
import nts.uk.ctx.at.request.dom.setting.workplace.requestbyworkplace.RequestByWorkplace;
import nts.uk.ctx.at.request.infra.entity.setting.workplace.requestbycompany.KrqmtAppApvCmp;
import nts.uk.ctx.at.request.infra.entity.setting.workplace.requestbycompany.KrqmtAppApvCmpPk;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "KRQMT_APP_APV_WKP")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class KrqmtAppApvWkp extends ContractUkJpaEntity {
    @EmbeddedId
    private KrqmtAppApvWkpPk pk;

    @Column(name = "USE_ATR")
    private int useAtr;

    @Column(name = "MEMO")
    private String memo;

    @Override
    protected Object getKey() {
        return pk;
    }

    public static RequestByWorkplace toDomain(List<KrqmtAppApvWkp> entities) {
        if (CollectionUtil.isEmpty(entities)) return null;
        String companyId = entities.get(0).getPk().companyId;
        String workplaceId = entities.get(0).getPk().workplaceId;
        return new RequestByWorkplace(
                companyId,
                workplaceId,
                new ApprovalFunctionSet(entities.stream().map(e -> ApplicationUseSetting.createNew(e.useAtr, e.pk.appType, e.memo)).collect(Collectors.toList()))
        );
    }

    public static List<KrqmtAppApvWkp> fromDomain(RequestByWorkplace domain) {
        return domain.getApprovalFunctionSet().getAppUseSetLst().stream().map(s -> new KrqmtAppApvWkp(
                new KrqmtAppApvWkpPk(domain.getCompanyID(), domain.getWorkplaceID(), s.getAppType().value),
                s.getUseDivision().value,
                s.getMemo().v()
        )).collect(Collectors.toList());
    }

    public static KrqmtAppApvWkp fromDomain(String companyId, String workplaceId, ApplicationUseSetting domain) {
        return new KrqmtAppApvWkp(
                new KrqmtAppApvWkpPk(companyId, workplaceId, domain.getAppType().value),
                domain.getUseDivision().value,
                domain.getMemo().v()
        );
    }

    public void update(ApplicationUseSetting setting) {
        if (setting.getAppType().value == this.pk.appType) {
            this.useAtr = setting.getUseDivision().value;
            this.memo = setting.getMemo().v();
        }
    }
}
