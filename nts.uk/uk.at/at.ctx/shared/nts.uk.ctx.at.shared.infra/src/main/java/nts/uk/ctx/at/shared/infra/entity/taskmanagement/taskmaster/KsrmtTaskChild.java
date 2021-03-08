package nts.uk.ctx.at.shared.infra.entity.taskmanagement.taskmaster;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.taskmanagement.aggregateroot.taskmaster.Tasks;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "KSRMT_TASK_CHILD")
public class KsrmtTaskChild extends ContractUkJpaEntity implements Serializable {

    @EmbeddedId
    public KsrmtTaskChildPk pk;

    @Override
    protected Object getKey() {
        return pk;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "CID", referencedColumnName = "CID",insertable = false, updatable = false),
            @JoinColumn(name = "FRAME_NO", referencedColumnName = "FRAME_NO",insertable = false, updatable = false),
            @JoinColumn(name = "CD", referencedColumnName = "CD",insertable = false, updatable = false)
    })
    private KsrmtTaskMaster ksrmtTaskMaster;

    public KsrmtTaskChild(KsrmtTaskChildPk pk){
        super();
        this.pk = pk;
    }
    public static List<KsrmtTaskChild> toEntittys(Tasks domain) {
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
