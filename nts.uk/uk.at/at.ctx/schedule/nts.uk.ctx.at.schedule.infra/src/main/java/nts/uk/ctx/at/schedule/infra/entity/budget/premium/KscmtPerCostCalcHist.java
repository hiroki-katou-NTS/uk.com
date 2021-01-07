package nts.uk.ctx.at.schedule.infra.entity.budget.premium;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "KSCMT_PER_COST_CALC_HIST")
@AllArgsConstructor
@NoArgsConstructor
public class KscmtPerCostCalcHist extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public KscmtPerCostCalcPk pk;
    @Column(name = "START_DATE")
    public GeneralDate startDate;

    @Column(name = "END_DATE")
    public GeneralDate endDate;

    @Override
    protected Object getKey() {
        return null;
    }
}
