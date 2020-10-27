package nts.uk.ctx.at.schedule.infra.entity.budget.premium;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
//import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
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
@Table(name="KSCMT_PREMIUM_ITEM")
public class KscmtPremiumItem extends ContractUkJpaEntity {
	@EmbeddedId
	public KmnmpPremiumItemPK kmnmpPremiumItemPK;
	
	@Column(name="PREMIUM_NAME")
	public String name;
	
	@Column(name="USE_ATR")
	public int useAtr;
	
	@OneToOne
	@PrimaryKeyJoinColumns(value = {
		@PrimaryKeyJoinColumn(name="CID",referencedColumnName="CID"),
		@PrimaryKeyJoinColumn(name="PREMIUM_NO",referencedColumnName="PREMIUM_NO")
    })
	public KscmtPerCostPremiRate kscmtPerCostPremiRate;

	@Override
	protected Object getKey() {
		return kmnmpPremiumItemPK;
	}
}
