package nts.uk.ctx.at.request.infra.entity.applicationreflect;

import lombok.*;
import nts.uk.ctx.at.request.dom.applicationreflect.AppReflectExecutionCondition;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "KRQMT_APP_REFLECT_CND")
public class KrqmtAppReflectCnd extends ContractUkJpaEntity {
    @Id
    @Column(name = "CID")
    private String companyId;

    @Column(name = "PRE_SCHE_REFLECT_ATR")
    private int applyBeforeWorkSchedule;

    @Column(name = "SCHE_CONFIRM_REFLECT_ATR")
    private int evenIfScheduleConfirmed;

    @Column(name = "ATD_CONFIRM_REFLECT_ATR")
    private int evenIfRecordConfirmed;

    public AppReflectExecutionCondition toDomain() {
        return AppReflectExecutionCondition.create(companyId, applyBeforeWorkSchedule, evenIfScheduleConfirmed, evenIfRecordConfirmed);
    }

    public static KrqmtAppReflectCnd fromDomain(AppReflectExecutionCondition domain) {
        return new KrqmtAppReflectCnd(domain.getCompanyId(),
                domain.getApplyBeforeWorkSchedule().value, domain.getEvenIfScheduleConfirmed().value,
                domain.getEvenIfWorkRecordConfirmed().value);
    }

    @Override
    protected Object getKey() {
        return companyId;
    }
}
