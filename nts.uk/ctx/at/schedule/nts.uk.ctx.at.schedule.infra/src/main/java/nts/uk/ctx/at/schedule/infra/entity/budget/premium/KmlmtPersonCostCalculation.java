package nts.uk.ctx.at.schedule.infra.entity.budget.premium;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="KMLMT_COST_CALC_SET")
public class KmlmtPersonCostCalculation extends UkJpaEntity{
	@EmbeddedId
	public KmlmpPersonCostCalculationPK kmlmpPersonCostCalculationPK;
	
	@Column(name="MEMO")
	public String memo;
	
	@Column(name="UNITPRICE_ATR")
	public int unitPrice;
	
	@Column(name="START_DATE")
	public GeneralDate startDate;
	
	@Column(name="END_DATE")
	public GeneralDate endDate;
	
	@OneToMany(targetEntity=KmlstPremiumSet.class, cascade = CascadeType.ALL, mappedBy = "personCost")
	@JoinTable(name = "KMLST_PREMIUM_SET")
	public List<KmlstPremiumSet> premiumSets;

	@Override
	protected Object getKey() {
		return kmlmpPersonCostCalculationPK;
	}
}
