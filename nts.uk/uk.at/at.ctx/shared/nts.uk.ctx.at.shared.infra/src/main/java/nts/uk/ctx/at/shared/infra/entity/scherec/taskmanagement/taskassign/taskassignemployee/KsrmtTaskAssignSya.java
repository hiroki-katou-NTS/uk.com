package nts.uk.ctx.at.shared.infra.entity.scherec.taskmanagement.taskassign.taskassignemployee;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignemployee.TaskAssignEmployee;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "KSRMT_TASK_ASSIGN_SYA")
@AllArgsConstructor
@NoArgsConstructor
public class KsrmtTaskAssignSya extends ContractCompanyUkJpaEntity {
    @EmbeddedId
    public KsrmtTaskAssignSyaPk pk;

    @Override
    protected Object getKey() {
        return this.pk;
    }

    public static KsrmtTaskAssignSya create(TaskAssignEmployee domain) {
        return new KsrmtTaskAssignSya(new KsrmtTaskAssignSyaPk(domain.getEmployeeId(), domain.getTaskFrameNo().v(), domain.getTaskCode().v()));
    }

    public TaskAssignEmployee toDomain() {
        return new TaskAssignEmployee(this.pk.employeeId, new TaskFrameNo(this.pk.taskFrameNo), new TaskCode(this.pk.taskCode));
    }
}
