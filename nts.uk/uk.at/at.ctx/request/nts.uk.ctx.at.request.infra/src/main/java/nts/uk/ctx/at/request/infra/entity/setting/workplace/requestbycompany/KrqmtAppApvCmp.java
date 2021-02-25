package nts.uk.ctx.at.request.infra.entity.setting.workplace.requestbycompany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApplicationUseSetting;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApprovalFunctionSet;
import nts.uk.ctx.at.request.dom.setting.workplace.requestbycompany.RequestByCompany;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "KRQMT_APP_APV_CMP")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class KrqmtAppApvCmp extends ContractUkJpaEntity {
    @EmbeddedId
    private KrqmtAppApvCmpPk pk;

    @Column(name = "USE_ATR")
    private int useAtr;

    @Column(name = "MEMO")
    private String memo;

    @Override
    protected Object getKey() {
        return pk;
    }

    public static RequestByCompany toDomain(List<KrqmtAppApvCmp> entities) {
        if (CollectionUtil.isEmpty(entities)) return null;
        String companyId = entities.get(0).getPk().companyId;
        return new RequestByCompany(
                companyId,
                new ApprovalFunctionSet(entities.stream().map(e -> ApplicationUseSetting.createNew(e.useAtr, e.pk.appType, e.memo)).collect(Collectors.toList()))
        );
    }

    public static List<KrqmtAppApvCmp> fromDomain(RequestByCompany domain) {
        return domain.getApprovalFunctionSet().getAppUseSetLst().stream().map(s -> new KrqmtAppApvCmp(
                new KrqmtAppApvCmpPk(domain.getCompanyID(), s.getAppType().value),
                s.getUseDivision().value,
                s.getMemo().v()
        )).collect(Collectors.toList());
    }

    public static KrqmtAppApvCmp fromDomain(String companyId, ApplicationUseSetting domain) {
        return new KrqmtAppApvCmp(
                new KrqmtAppApvCmpPk(companyId, domain.getAppType().value),
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
