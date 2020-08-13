package nts.uk.ctx.at.request.infra.entity.applicationreflect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.applicationreflect.service.AppReflectExecutionCondition;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class KrqstAppReflectCnd extends UkJpaEntity {
    @Id
    @Column(name = "CID")
    private String companyId;

    @Column(name = "CID")
    private int applyBeforeWorkSchedule;

    @Column(name = "CID")
    private int evenIfScheduleConfirmed;

    @Column(name = "CID")
    private int evenIfRecordConfirmed;

    public AppReflectExecutionCondition toDomain() {
        return AppReflectExecutionCondition.create(companyId, applyBeforeWorkSchedule, evenIfScheduleConfirmed, evenIfRecordConfirmed);
    }

    public static KrqstAppReflectCnd fromDomain(AppReflectExecutionCondition domain) {
        return new KrqstAppReflectCnd(domain.getCompanyId(),
                domain.getApplyBeforeWorkSchedule().value, domain.getEvenIfScheduleConfirmed().value,
                domain.getEvenIfWorkRecordConfirmed().value);
    }

    @Override
    protected Object getKey() {
        return companyId;
    }
}
