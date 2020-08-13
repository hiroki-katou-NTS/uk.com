package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationstandardreason;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.DisplayReason;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppReasonStandard;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.ReasonTypeItem;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
import org.apache.commons.lang3.BooleanUtils;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請定型理由.申請定型理由
 */

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KRCMT_APP_REASON")
public class KrcmtAppReason extends UkJpaEntity {
    @EmbeddedId
    private KrcmtAppReasonPk pk;

    @Column(name = "DISPORDER")
    private int displayOrder;

    @Column(name = "REASON_TEMP")
    private String reasonTemp;

    @Column(name = "DEFAULT_ATR")
    private int defaultAtr;

    public static AppReasonStandard toDomain(List<KrcmtAppReason> entities) {
        if (CollectionUtil.isEmpty(entities)) return null;
        String companyId = entities.get(0).pk.companyId;
        int applicationType = entities.get(0).pk.applicationType;
        List<ReasonTypeItem> reasonTypeItemLst = entities.stream().map(e -> ReasonTypeItem.createNew(e.pk.reasonCode, e.displayOrder, BooleanUtils.toBoolean(e.defaultAtr), e.reasonTemp)).collect(Collectors.toList());
        return new AppReasonStandard(
                companyId,
                EnumAdaptor.valueOf(applicationType, ApplicationType.class),
                reasonTypeItemLst,
                Optional.empty());
    }

    @Override
    protected Object getKey() {
        return this.pk;
    }

    private String getCompanyId() {
        return this.pk.companyId;
    }

    private int getAppType() {
        return this.pk.applicationType;
    }
}
