/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtWtComGooutRound.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_COM_GOOUT_ROUND")
public class KshmtWtComGooutRound extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt special round out PK. */
	@EmbeddedId
	protected KshmtWtComGooutRoundPK kshmtWtComGooutRoundPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The pub deduct method. */
	@Column(name = "PUB_DEDUCT_METHOD")
	private int pubDeductMethod;

	/** The pub deduct unit. */
	@Column(name = "PUB_DEDUCT_UNIT")
	private int pubDeductUnit;

	/** The pub deduct rounding. */
	@Column(name = "PUB_DEDUCT_ROUNDING")
	private int pubDeductRounding;

	/** The pub rounding method. */
	@Column(name = "PUB_ROUNDING_METHOD")
	private int pubRoundingMethod;

	/** The pub rounding unit. */
	@Column(name = "PUB_ROUNDING_UNIT")
	private int pubRoundingUnit;

	/** The pub rounding. */
	@Column(name = "PUB_ROUNDING")
	private int pubRounding;

	/** The personal deduct method. */
	@Column(name = "PERSONAL_DEDUCT_METHOD")
	private int personalDeductMethod;

	/** The personal deduct unit. */
	@Column(name = "PERSONAL_DEDUCT_UNIT")
	private int personalDeductUnit;

	/** The personal deduct rounding. */
	@Column(name = "PERSONAL_DEDUCT_ROUNDING")
	private int personalDeductRounding;

	/** The personal rounding method. */
	@Column(name = "PERSONAL_ROUNDING_METHOD")
	private int personalRoundingMethod;

	/** The personal rounding unit. */
	@Column(name = "PERSONAL_ROUNDING_UNIT")
	private int personalRoundingUnit;

	/** The personal rounding. */
	@Column(name = "PERSONAL_ROUNDING")
	private int personalRounding;

	/**
	 * Instantiates a new kshmt special round out.
	 */
	public KshmtWtComGooutRound() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtWtComGooutRoundPK != null ? kshmtWtComGooutRoundPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtComGooutRound)) {
			return false;
		}
		KshmtWtComGooutRound other = (KshmtWtComGooutRound) object;
		if ((this.kshmtWtComGooutRoundPK == null && other.kshmtWtComGooutRoundPK != null)
				|| (this.kshmtWtComGooutRoundPK != null
						&& !this.kshmtWtComGooutRoundPK.equals(other.kshmtWtComGooutRoundPK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshmtWtComGooutRoundPK;
	}

}
