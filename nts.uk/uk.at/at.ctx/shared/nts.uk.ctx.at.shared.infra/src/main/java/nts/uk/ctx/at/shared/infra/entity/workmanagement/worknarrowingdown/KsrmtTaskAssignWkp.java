package nts.uk.ctx.at.shared.infra.entity.workmanagement.worknarrowingdown;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;


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
}
