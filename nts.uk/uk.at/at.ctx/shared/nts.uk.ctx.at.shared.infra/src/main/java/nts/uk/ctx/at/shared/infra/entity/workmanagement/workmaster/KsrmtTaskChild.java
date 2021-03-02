package nts.uk.ctx.at.shared.infra.entity.workmanagement.workmaster;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "KSRMT_TASK_CHILD")
public class KsrmtTaskChild extends ContractUkJpaEntity implements Serializable{

    @EmbeddedId
    public KsrmtTaskChildPk pk;
    @Override
    protected Object getKey() {
        return pk;
    }
}
