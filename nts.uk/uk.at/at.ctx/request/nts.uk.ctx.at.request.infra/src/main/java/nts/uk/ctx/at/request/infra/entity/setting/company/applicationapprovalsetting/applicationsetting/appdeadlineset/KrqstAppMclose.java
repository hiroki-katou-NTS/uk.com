package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationsetting.appdeadlineset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.appdeadlineset.AppDeadlineSetting;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationsetting.KrqstApplicationSet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.申請締切設定.申請締切設定
 */
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KRQST_APP_MCLOSE")
public class KrqstAppMclose extends UkJpaEntity {

    @EmbeddedId
    private KrqstAppMclosePk pk;

    @Column(name = "MCLOSE_CRITERIA_ATR")
    private int closureCriteriaAtr;

    @Column(name = "MCLOSE_DAYS")
    private int closureDays;

    @Column(name = "USE_ATR")
    private int useAtr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CID", insertable = false, updatable = false)
    private KrqstApplicationSet applicationSetting;

    public AppDeadlineSetting toDomain() {
        return AppDeadlineSetting.createNew(useAtr, pk.closureId, closureDays, closureCriteriaAtr);
    }

    public KrqstAppMclose(AppDeadlineSetting domain, String companyId) {
        this.pk = new KrqstAppMclosePk(companyId, domain.getClosureId());
        this.closureCriteriaAtr = domain.getDeadlineCriteria().value;
        this.closureDays = domain.getDeadline().v();
        this.useAtr = domain.getUseAtr().value;
    }

    @Override
    protected Object getKey() {
        return this.pk;
    }
}
