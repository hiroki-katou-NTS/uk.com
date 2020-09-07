package nts.uk.ctx.at.schedule.infra.entity.shift.workcycle;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscmtWorkingCycleDtlPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "CID")
    public String cid;

    @Column(name = "CD")
    public String workingCycleCode;

    @Column(name = "CYCLE_ORDER")
    public int dispOrder;

}
