package nts.uk.ctx.at.shared.infra.entity.taskmanagement.tasknarrowingdown;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.taskmanagement.aggregateroot.tasknarrowingdown.NarrowingDownTaskByWorkplace;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "KSRMT_TASK_ASSIGN_WKP")
public class KsrmtTaskAssignWkp extends ContractCompanyUkJpaEntity implements Serializable {

    public static final long serialVersionUID = 1L;
    @EmbeddedId
    public KsrmtTaskAssignWkpPk pk;

    @Override
    protected Object getKey() {
        return pk;
    }

    public static List<KsrmtTaskAssignWkp> toEntitys(NarrowingDownTaskByWorkplace domain) {
        return domain.getTaskCodeList().stream().map(e -> new KsrmtTaskAssignWkp(
                new KsrmtTaskAssignWkpPk(
                        domain.getWorkPlaceId(),
                        domain.getTaskFrameNo().v(),
                        e.v()
                )
        )).collect(Collectors.toList());

    }
}
