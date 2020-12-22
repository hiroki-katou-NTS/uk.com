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
@Table(name = "KSCMT_PER_COST_PREMI_RATE")
@AllArgsConstructor
@NoArgsConstructor
public class KscmtPerCostPremiRate extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public KscmtPerCostPremiRatePk pk;

    @Column(name = "PREMIUM_RATE")
    public int premiumRate;

    @Column(name = "UNIT_PRICE")
    public int unitPrice;

    @Override
    protected Object getKey() {
        return pk;
    }
}
