package nts.uk.ctx.at.shared.infra.entity.workmanagement.workmaster;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.workmaster.Work;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

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
@Table(name = "KSRMT_TASK_CHILD")
public class KsrmtTaskChild extends ContractUkJpaEntity implements Serializable {

    @EmbeddedId
    public KsrmtTaskChildPk pk;

    @Override
    protected Object getKey() {
        return pk;
    }

    public static List<KsrmtTaskChild> toEntitys(Work domain) {
        String CID = AppContexts.user().companyId();
        int FRAMENO = domain.getTaskFrameNo().v();
        String CD = domain.getCode().v();
        return domain.getChildWorkList().stream().map(e -> new KsrmtTaskChild(
                new KsrmtTaskChildPk(
                        CID,
                        FRAMENO,
                        CD,
                        e.v()
                )
        )).collect(Collectors.toList());
    }
}
