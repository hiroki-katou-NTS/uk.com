package nts.uk.ctx.at.schedule.infra.entity.shift.workcycle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleInfo;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "KSCMT_WORKING_CYCLE")
public class KscmtWorkingCycle extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public KscmtWorkingCyclePK kscmtWorkingCyclePK;

    @Basic(optional = false)
    @Column(name = "CONTRACT_CD")
    public String contractCd;

    @Basic(optional = false)
    @Column(name = "NAME")
    public String workCycleName;

    @Override
    protected Object getKey() {
        return this.kscmtWorkingCyclePK;
    }

    public static WorkCycle toDomain(KscmtWorkingCycle entity, List<KscmtWorkingCycleDtl> entityValues){
        List<WorkCycleInfo> infos = new ArrayList<WorkCycleInfo>();
        entityValues.stream().forEach(item -> {
            WorkCycleInfo info = new WorkCycleInfo(
                    item.days,
                    item.workTypeCode,
                    item.workTimeCode,
                    item.kscmtWorkingCycleDtlPK.dispOrder
            );
            infos.add(info);
        });
        WorkCycle result = new WorkCycle(
                entity.kscmtWorkingCyclePK.cid,
                entity.kscmtWorkingCyclePK.workCycleCode,
                entity.workCycleName,
                infos
        );
        return result;
    }

    public static KscmtWorkingCycle toEntity(WorkCycle domain) {
        return new KscmtWorkingCycle(
                new KscmtWorkingCyclePK(domain.getCid(), domain.getCode().v()),
                AppContexts.user().contractCode(),
                domain.getName().v()
        );
    }
}
