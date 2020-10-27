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
 * The Class KscstPerCostExtraItem.
 */
@Setter
@Getter
@Entity
@Table(name = "KSCST_PER_COST_EXTRA_ITEM")
public class KscstPerCostExtraItem extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The kscst per cost extra item PK. */
	@EmbeddedId
    protected KscstPerCostExtraItemPK kscstPerCostExtraItemPK;
	
	/** The kscst est aggregate set. */
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false) })
	public KscstEstAggregateSet kscstEstAggregateSet;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kscstPerCostExtraItemPK;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((kscstPerCostExtraItemPK == null) ? 0 : kscstPerCostExtraItemPK.hashCode());
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
		KscstPerCostExtraItem other = (KscstPerCostExtraItem) obj;
		if (kscstPerCostExtraItemPK == null) {
			if (other.kscstPerCostExtraItemPK != null)
				return false;
		} else if (!kscstPerCostExtraItemPK.equals(other.kscstPerCostExtraItemPK))
			return false;
		return true;
	}

	/**
	 * Instantiates a new kscst per cost extra item.
	 */
	public KscstPerCostExtraItem() {
		super();
	}

	/**
	 * Instantiates a new kscst per cost extra item.
	 *
	 * @param kscstPerCostExtraItemPK the kscst per cost extra item PK
	 */
	public KscstPerCostExtraItem(KscstPerCostExtraItemPK kscstPerCostExtraItemPK) {
		super();
		this.kscstPerCostExtraItemPK = kscstPerCostExtraItemPK;
	}
	
	
}
