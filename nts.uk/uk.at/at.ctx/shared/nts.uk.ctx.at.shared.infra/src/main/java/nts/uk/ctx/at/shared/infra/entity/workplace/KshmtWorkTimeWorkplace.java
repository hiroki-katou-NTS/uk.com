package nts.uk.ctx.at.shared.infra.entity.workplace;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.workplace.WorkTimeWorkplace;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tutk
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHMT_WORKTIME_WORKPLACE")
public class KshmtWorkTimeWorkplace extends UkJpaEntity {

    @EmbeddedId
    public KshmtWorkTimeWorkplacePK kshmtWorkTimeWorkplacePK;

    @Override
    protected Object getKey() {
        return kshmtWorkTimeWorkplacePK;
    }

    public static List<KshmtWorkTimeWorkplace> toEntity(WorkTimeWorkplace domain) {
        return domain.getWorkTimeCodes().stream().map(x -> {
            return new KshmtWorkTimeWorkplace(new KshmtWorkTimeWorkplacePK(domain.getCompanyID(), domain.getWorkplaceID(),x.v()));
        }).collect(Collectors.toList());
    }

    public static WorkTimeWorkplace toDomain(List<KshmtWorkTimeWorkplace> listEntity) {

        if (listEntity.size() == 0) return null;

        List<WorkTimeCode> workTimeCodes = listEntity.stream().map(x -> {
            return new WorkTimeCode(x.kshmtWorkTimeWorkplacePK.workTimeID);
        }).collect(Collectors.toList());

        return new WorkTimeWorkplace(listEntity.get(0).kshmtWorkTimeWorkplacePK.companyID,listEntity.get(0).kshmtWorkTimeWorkplacePK.workplaceID,workTimeCodes);
    }

}
