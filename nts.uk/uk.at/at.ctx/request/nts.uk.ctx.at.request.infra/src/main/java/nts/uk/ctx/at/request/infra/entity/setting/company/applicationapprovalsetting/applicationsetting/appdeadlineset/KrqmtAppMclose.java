package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationsetting.appdeadlineset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.appdeadlineset.AppDeadlineSetting;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationsetting.KrqmtApplicationSet;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.*;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.申請締切設定.申請締切設定
 */

@Entity
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Table(name = "KRQMT_APP_MCLOSE")
public class KrqmtAppMclose extends ContractUkJpaEntity {

    @EmbeddedId
    private KrqmtAppMclosePk pk;

    @Column(name = "MCLOSE_CRITERIA_ATR")
    private int closureCriteriaAtr;

    @Column(name = "MCLOSE_DAYS")
    private int closureDays;

    @Column(name = "USE_ATR")
    private int useAtr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CID", insertable = false, updatable = false)
    private KrqmtApplicationSet applicationSetting;

    public AppDeadlineSetting toDomain() {
        return AppDeadlineSetting.createNew(useAtr, pk.closureId, closureDays, closureCriteriaAtr);
    }

    public static KrqmtAppMclose create(String companyId, AppDeadlineSetting domain) {
        KrqmtAppMclose entity = new KrqmtAppMclose();
        entity.pk = new KrqmtAppMclosePk(companyId, domain.getClosureId());
        entity.closureCriteriaAtr = domain.getDeadlineCriteria().value;
        entity.closureDays = domain.getDeadline().v();
        entity.useAtr = domain.getUseAtr().value;
        return entity;
    }

    @Override
    protected Object getKey() {
        return this.pk;
    }
}
