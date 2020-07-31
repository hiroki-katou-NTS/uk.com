package nts.uk.ctx.at.schedule.infra.entity.shift.workcycle;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleInfo;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_WORKING_CYCLE_DTL")
public class KscmtWorkingCycleDtl extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public KscmtWorkingCycleDtlPK kscmtWorkingCycleDtlPK;

    @Basic(optional = false)
    @Column(name = "WKTP_CD")
    public String workTypeCode;

    @Basic(optional = true)
    @Column(name = "WKTM_CD")
    public String workTimeCode;

    @Basic(optional = false)
    @Column(name = "REPEAT_DAYS")
    public int days;

    @Override
    protected Object getKey() {
        return this.kscmtWorkingCycleDtlPK;
    }

    public static List<KscmtWorkingCycleDtl> toEntity(WorkCycle domain) {
        List<KscmtWorkingCycleDtl> result = domain.getInfos().stream().map(i -> new KscmtWorkingCycleDtl(
                new KscmtWorkingCycleDtlPK(domain.getCid(), domain.getCode().v(), i.getDispOrder().v()),
                i.getWorkInformation().getWorkTypeCode().v(),
                i.getWorkInformation().getWorkTimeCode().v(),
                i.getDays().v()
        )).collect(Collectors.toList());
        return result;
    }

    public static WorkCycleInfo toDomain(KscmtWorkingCycleDtl entity) {
        return new WorkCycleInfo(
                entity.days,
                entity.workTypeCode,
                entity.workTimeCode,
                entity.kscmtWorkingCycleDtlPK.dispOrder
        );
    }
}
