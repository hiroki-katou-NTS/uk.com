package nts.uk.ctx.at.shared.infra.entity.scherec.taskmanagement.operationsetting;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsetting.TaskOperationSetting;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "KSRMT_TASK_OPERATION")
@AllArgsConstructor
@NoArgsConstructor
public class KsrmtTaskOperation extends ContractUkJpaEntity {
    @Id
    @Column(name = "CID")
    public String companyId;

    @Column(name = "OPE_ATR")
    public int useATR;

    @Override
    protected Object getKey() {
        return this.companyId;
    }

    public TaskOperationSetting toDomain() {
        return new TaskOperationSetting(this.useATR);
    }
}
