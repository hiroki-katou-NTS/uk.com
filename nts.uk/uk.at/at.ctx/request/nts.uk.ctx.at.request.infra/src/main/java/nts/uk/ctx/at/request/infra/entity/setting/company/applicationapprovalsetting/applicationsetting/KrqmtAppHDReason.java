package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationsetting;

import lombok.*;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.DisplayReason;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * refactor 4
 * 休暇申請申請理由表示
 */

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KRQMT_APP_HD_REASON")
public class KrqmtAppHDReason extends ContractUkJpaEntity {
    @EmbeddedId
    private KrqmtAppHDReasonPk pk;

    @Column(name = "FIXED_REASON_DISP_ATR")
    private int displayFixedAtr;

    @Column(name = "REASON_DISP_ATR")
    private int displayAppAtr;

    public DisplayReason toDomain() {
        return DisplayReason.createHolidayAppDisplayReason(pk.companyId, displayFixedAtr, displayAppAtr, pk.holidayApplicationType);
    }

    public static KrqmtAppHDReason fromDomain(DisplayReason domain) {
        return new KrqmtAppHDReason(
                new KrqmtAppHDReasonPk(domain.getCompanyID(), domain.getOpHolidayAppType().get().value),
                domain.getDisplayFixedReason().value,
                domain.getDisplayAppReason().value
        );
    }

    @Override
    protected Object getKey() {
        return this.pk;
    }
}
