package nts.uk.ctx.at.schedule.infra.entity.budget.premium;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Table(name="KSCMT_PER_COST_PREMIUM")
public class KscmtPerCostPremium extends ContractUkJpaEntity{
	@EmbeddedId
	public KmldpPremiumAttendancePK kmldpPremiumAttendancePK;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumns({
		@PrimaryKeyJoinColumn(name="CID",referencedColumnName="CID"), 
		@PrimaryKeyJoinColumn(name="HIS_ID",referencedColumnName="HIS_ID"),
		@PrimaryKeyJoinColumn(name="PREMIUM_NO",referencedColumnName="PREMIUM_NO")
	})
	public KscmtPerCostPremiRate kscmtPerCostPremiRate;
	
	@Override
	protected Object getKey() {
		return kmldpPremiumAttendancePK;
	}
}
