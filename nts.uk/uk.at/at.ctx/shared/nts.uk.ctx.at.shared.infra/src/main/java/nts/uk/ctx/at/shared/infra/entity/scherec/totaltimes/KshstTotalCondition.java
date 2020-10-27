/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshstTotalCondition.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHST_TOTAL_CONDITION")
public class KshstTotalCondition extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshst total condition PK. */
	@EmbeddedId
	protected KshstTotalConditionPK kshstTotalConditionPK;

	/** The upper limit set atr. */
	@Column(name = "UPPER_LIMIT_SET_ATR")
	private int upperLimitSetAtr;

	/** The lower limit set atr. */
	@Column(name = "LOWER_LIMIT_SET_ATR")
	private int lowerLimitSetAtr;

	/** The thresold upper limit. */
	@Column(name = "THRESOLD_UPPER_LIMIT")
	private Integer thresoldUpperLimit;

	/** The thresold lower limit. */
	@Column(name = "THRESOLD_LOWER_LIMIT")
	private Integer thresoldLowerLimit;
	
	
	/** The attendance item id. */
	@Column(name = "ATD_ITEM_ID")
	private Integer attendanceItemId;
	
	/**
	 * Instantiates a new kshst total condition.
	 */
	public KshstTotalCondition() {
		super();
	}

	/**
	 * Instantiates a new kshst total condition.
	 *
	 * @param kshstTotalConditionPK
	 *            the kshst total condition PK
	 */
	public KshstTotalCondition(KshstTotalConditionPK kshstTotalConditionPK) {
		this.kshstTotalConditionPK = kshstTotalConditionPK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshstTotalConditionPK != null ? kshstTotalConditionPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshstTotalCondition)) {
			return false;
		}
		KshstTotalCondition other = (KshstTotalCondition) object;
		if ((this.kshstTotalConditionPK == null && other.kshstTotalConditionPK != null)
				|| (this.kshstTotalConditionPK != null
						&& !this.kshstTotalConditionPK.equals(other.kshstTotalConditionPK))) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshstTotalConditionPK;
	}

}
