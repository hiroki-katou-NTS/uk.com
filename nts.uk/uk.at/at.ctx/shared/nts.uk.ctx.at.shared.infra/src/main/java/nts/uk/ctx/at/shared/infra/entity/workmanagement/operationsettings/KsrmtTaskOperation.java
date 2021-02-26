package nts.uk.ctx.at.shared.infra.entity.workmanagement.operationsettings;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workmanagement.operationsettings.WorkOperationSetting;
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
    public String companyID;

    @Column(name = "OPE_ATR")
    public int useATR;

    @Override
    protected Object getKey() {
        return null;
    }

    public WorkOperationSetting toDomain() {
        return new WorkOperationSetting(this.useATR);
    }
}
