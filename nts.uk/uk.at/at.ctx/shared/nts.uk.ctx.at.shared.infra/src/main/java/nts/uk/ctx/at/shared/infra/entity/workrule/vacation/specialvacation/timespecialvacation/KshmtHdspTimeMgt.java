package nts.uk.ctx.at.shared.infra.entity.workrule.vacation.specialvacation.timespecialvacation;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;
import nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation.TimeSpecialLeaveManagementSetting;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "KSHMT_HDSP_TIME_MGT")
@NoArgsConstructor
@AllArgsConstructor
public class KshmtHdspTimeMgt extends ContractUkJpaEntity{

    @Id
    @Column(name = "CID")
    public String companyId;

    @Column(name = "MGT_ATR")
    public int managementAtr;

    @Column(name = "USE_UNIT")
    public int useUnit;

    @Override
    protected Object getKey() {
        return companyId;
    }

    public TimeSpecialLeaveManagementSetting toDomain() {
        return new TimeSpecialLeaveManagementSetting(
                companyId,
                new TimeVacationDigestUnit(EnumAdaptor.valueOf(managementAtr, ManageDistinct.class),
                		EnumAdaptor.valueOf(useUnit, TimeDigestiveUnit.class))
        );
    }

    public static KshmtHdspTimeMgt fromDomain(TimeSpecialLeaveManagementSetting domain) {
        return new KshmtHdspTimeMgt(domain.getCompanyId(),
        		domain.getTimeVacationDigestUnit().getManage().value,
        		domain.getTimeVacationDigestUnit().getDigestUnit().value);
    }
}
