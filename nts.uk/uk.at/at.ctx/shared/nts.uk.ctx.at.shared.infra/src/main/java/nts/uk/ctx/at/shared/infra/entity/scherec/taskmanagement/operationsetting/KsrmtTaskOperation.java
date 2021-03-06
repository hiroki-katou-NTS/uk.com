package nts.uk.ctx.at.shared.infra.entity.scherec.taskmanagement.operationsetting;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsettings.TaskOperationSetting;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "KSRMT_TASK_OPERATION")
public class KsrmtTaskOperation extends ContractUkJpaEntity implements Serializable {

    /**
     * 職場ID:職場別作業の絞込->職場ID
     */
    @Id
    @Column(name = "CID")
    public String CID;

    /**
     * 作業運用方法: 作業運用設定	->	作業運用方法
     */
    @Column(name = "OPE_ATR")
    public int OPEATR;

    @Override
    protected Object getKey() {
        return CID;
    }


    public static KsrmtTaskOperation toEntity(TaskOperationSetting domain) {
        return new KsrmtTaskOperation(
                AppContexts.user().companyId(),
                domain.getTaskOperationMethod().value
        );
    }
}
