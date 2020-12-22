package nts.uk.ctx.at.schedule.infra.entity.budget.premium;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "KSCMT_PER_COST_CALC")
@AllArgsConstructor
@NoArgsConstructor
public class KscmtPerCostCal  extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public KscmtPerCostCalPk pk;

    @Column(name = "UNIT_PRICE_ATR")
    public int unitPriceAtr;

    @Column(name = "MEMO")
    public String memo;

    @Column(name = "UNIT_PRICE_ROUNDING")
    public int unitPriceRounding;

    @Column(name = "COST_UNIT")
    public String costUnit;

    @Column(name = "COST_ROUNDING")
    public int costRounding;

    @Column(name = "UNITPRICE_SETTING_METHOD")
    public int unitPriceSettingMethod;

    @Column(name = "WORKING_HOURS_UNITPRICE_ATR")
    public int wokkingHoursUnitPriceAtr;

    @Override
    protected Object getKey() {
        return null;
    }
}
