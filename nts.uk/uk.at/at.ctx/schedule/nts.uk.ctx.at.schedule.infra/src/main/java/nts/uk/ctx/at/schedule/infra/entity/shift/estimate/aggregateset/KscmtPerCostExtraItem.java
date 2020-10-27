/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.aggregateset;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KscmtPerCostExtraItem.
 */
@Setter
@Getter
@Entity
@Table(name = "KSCMT_PER_COST_EXTRA_ITEM")
public class KscmtPerCostExtraItem extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The kscst per cost extra item PK. */
	@EmbeddedId
    protected KscmtPerCostExtraItemPK kscmtPerCostExtraItemPK;
	
	/** The kscst est aggregate set. */
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false) })
	public KscmtEstAggregate kscmtEstAggregate;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kscmtPerCostExtraItemPK;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((kscmtPerCostExtraItemPK == null) ? 0 : kscmtPerCostExtraItemPK.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		KscmtPerCostExtraItem other = (KscmtPerCostExtraItem) obj;
		if (kscmtPerCostExtraItemPK == null) {
			if (other.kscmtPerCostExtraItemPK != null)
				return false;
		} else if (!kscmtPerCostExtraItemPK.equals(other.kscmtPerCostExtraItemPK))
			return false;
		return true;
	}

	/**
	 * Instantiates a new kscst per cost extra item.
	 */
	public KscmtPerCostExtraItem() {
		super();
	}

	/**
	 * Instantiates a new kscst per cost extra item.
	 *
	 * @param kscmtPerCostExtraItemPK the kscst per cost extra item PK
	 */
	public KscmtPerCostExtraItem(KscmtPerCostExtraItemPK kscmtPerCostExtraItemPK) {
		super();
		this.kscmtPerCostExtraItemPK = kscmtPerCostExtraItemPK;
	}
	
	
}
