package nts.uk.ctx.at.shared.infra.entity.scherec.taskmanagement.taskassign.taskassignworkplace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignworkplace.NarrowingDownTaskByWorkplace;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "KSRMT_TASK_ASSIGN_WKP")
public class KsrmtTaskAssignWkp extends ContractUkJpaEntity implements Serializable {

    public static final long serialVersionUID = 1L;

    @EmbeddedId
    public KsrmtTaskAssignWkpPk pk;

    @Column(name = "CID")
    public String companyId;

    @Override
    protected Object getKey() {
        return pk;
    }

    @PreUpdate
    private void setUpdateContractInfo() {
        this.contractCd = AppContexts.user().contractCode();
    }

    public static List<KsrmtTaskAssignWkp> toEntitys(NarrowingDownTaskByWorkplace domain) {
        return domain.getTaskCodeList().stream().map(e -> new KsrmtTaskAssignWkp(
                new KsrmtTaskAssignWkpPk(
                        domain.getWorkPlaceId(),
                        domain.getTaskFrameNo().v(),
                        e.v()
                ),
                AppContexts.user().companyId()
        )).collect(Collectors.toList());

    }
}
