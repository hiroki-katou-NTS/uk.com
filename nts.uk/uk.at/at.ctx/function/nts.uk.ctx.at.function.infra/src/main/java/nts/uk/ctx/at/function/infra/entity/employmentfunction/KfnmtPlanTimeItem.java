/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.infra.entity.employmentfunction;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Getter
@Setter
@Table(name = "KFNMT_PLAN_TIME_ITEM")
public class KfnmtPlanTimeItem extends ContractUkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The kfnst plan time item PK. */
	@EmbeddedId
	protected KfnmtPlanTimeItemPK kfnmtPlanTimeItemPK;

	/**
	 * Instantiates a new kfnst plan time item.
	 */
	public KfnmtPlanTimeItem() {
		super();
	}

	/**
	 * Instantiates a new kfnst plan time item.
	 *
	 * @param kfnmtPlanTimeItemPK the kfnst plan time item PK
	 */
	public KfnmtPlanTimeItem(KfnmtPlanTimeItemPK kfnmtPlanTimeItemPK) {
		this.kfnmtPlanTimeItemPK = kfnmtPlanTimeItemPK;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((kfnmtPlanTimeItemPK == null) ? 0 : kfnmtPlanTimeItemPK.hashCode());
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
		KfnmtPlanTimeItem other = (KfnmtPlanTimeItem) obj;
		if (kfnmtPlanTimeItemPK == null) {
			if (other.kfnmtPlanTimeItemPK != null)
				return false;
		} else if (!kfnmtPlanTimeItemPK.equals(other.kfnmtPlanTimeItemPK))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return null;
	}

}
