package nts.uk.ctx.at.schedule.infra.entity.budget.premium;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Table(name="KSCMT_PER_COST_CALC")
public class KmlmtPersonCostCalculation extends ContractUkJpaEntity{
	@EmbeddedId
	public KmlmpPersonCostCalculationPK kmlmpPersonCostCalculationPK;
	
	@Column(name="START_DATE")
	public GeneralDate startDate;
	
	@Column(name="END_DATE")
	public GeneralDate endDate;
	
	@Column(name="UNITPRICE_ATR")
	public int unitPrice;
	
	@Column(name="MEMO")
	public String memo;
	
	@OneToMany(targetEntity=KscmtPerCostPremiRate.class, cascade = CascadeType.ALL, mappedBy = "kmlmtPersonCostCalculation", orphanRemoval = true)
	@JoinTable(name = "KSCMT_PER_COST_PREMI_RATE")
	public List<KscmtPerCostPremiRate> kscmtPerCostPremiRates;

	@Override
	protected Object getKey() {
		return kmlmpPersonCostCalculationPK;
	}
}
