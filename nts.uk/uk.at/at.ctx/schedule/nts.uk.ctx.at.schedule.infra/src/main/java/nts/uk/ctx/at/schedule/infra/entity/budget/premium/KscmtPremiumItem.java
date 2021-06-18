package nts.uk.ctx.at.schedule.infra.entity.budget.premium;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.aggregateset.KscmtEstAggregate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;

//import javax.persistence.JoinTable;
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
@Getter
@Table(name="KSCMT_PREMIUM_ITEM")
public class KscmtPremiumItem extends ContractUkJpaEntity {
	@EmbeddedId
	public KmnmpPremiumItemPK kmnmpPremiumItemPK;
	
	@Column(name="PREMIUM_NAME")
	public String name;
	
	@Column(name="USE_ATR")
	public int useAtr;

//	@OneToOne
//	@PrimaryKeyJoinColumns(value = {
//		@PrimaryKeyJoinColumn(name="CID",referencedColumnName="CID"),
//		@PrimaryKeyJoinColumn(name="PREMIUM_NO",referencedColumnName="PREMIUM_NO")
//    })
//	public KscmtPerCostPremiRate kmlstPremiumSet;
	/** The kscst est aggregate set. */
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false) })
	public KscmtEstAggregate kscstEstAggregateSet;
	@Override
	protected Object getKey() {
		return kmnmpPremiumItemPK;
	}
}
