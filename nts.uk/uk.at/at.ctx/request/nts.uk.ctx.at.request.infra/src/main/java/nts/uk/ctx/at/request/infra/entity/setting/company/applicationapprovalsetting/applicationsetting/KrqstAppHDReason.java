package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationsetting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.DisplayReason;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * refactor 4
 * 休暇申請申請理由表示
 */

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KRCMT_APP_HD_REASON")
public class KrqstAppHDReason extends UkJpaEntity {
    @EmbeddedId
    private KrqstAppHDReasonPk pk;

    @Column(name = "FIXED_REASON_DISP_ATR")
    private int displayFixedAtr;

    @Column(name = "REASON_DISP_ATR")
    private int displayAppAtr;

    public DisplayReason toDomain() {
        return DisplayReason.createHolidayAppDisplayReason(pk.companyId, displayFixedAtr, displayAppAtr, pk.holidayApplicationType);
    }

    @Override
    protected Object getKey() {
        return this.pk;
    }
}
