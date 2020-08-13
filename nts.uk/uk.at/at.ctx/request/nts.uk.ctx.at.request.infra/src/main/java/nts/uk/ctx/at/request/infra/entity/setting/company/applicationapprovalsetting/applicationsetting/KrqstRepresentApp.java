package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationsetting;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.AppSetForProxyApp;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.代行申請で利用できる申請設定
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KRQST_REPRESENT_APP")
public class KrqstRepresentApp extends UkJpaEntity {
    @EmbeddedId
    private KrqstRepresentAppPk pk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CID", insertable = false, updatable = false)
    private KrqstApplicationSet applicationSetting;

    @Override
    protected Object getKey() {
        return this.pk;
    }

    private KrqstRepresentApp(String companyId, int applicationType, int optionAtr) {
        this.pk = new KrqstRepresentAppPk(companyId, applicationType, optionAtr);
    }

    public AppSetForProxyApp toDomain() {
        ApplicationType appType = EnumAdaptor.valueOf(this.pk.applicationType, ApplicationType.class);
        switch (appType) {
            case OVER_TIME_APPLICATION:
                return AppSetForProxyApp.create(this.pk.applicationType, this.pk.optionAtr, null);
            case STAMP_APPLICATION:
                return AppSetForProxyApp.create(this.pk.applicationType, null, this.pk.optionAtr);
            default:
                return AppSetForProxyApp.create(this.pk.applicationType, null, null);
        }
    }

    public static KrqstRepresentApp fromDomain(AppSetForProxyApp domain, String companyId) {
        switch (domain.getAppType()) {
            case OVER_TIME_APPLICATION:
                return new KrqstRepresentApp(companyId, domain.getAppType().value, domain.getOpOvertimeAppAtr().map(o -> o.value).orElse(0));
            case STAMP_APPLICATION:
                return new KrqstRepresentApp(companyId, domain.getAppType().value, domain.getOpStampRequestMode().map(o -> o.value).orElse(0));
            default:
                return new KrqstRepresentApp(companyId, domain.getAppType().value, 0);

        }
    }
}
