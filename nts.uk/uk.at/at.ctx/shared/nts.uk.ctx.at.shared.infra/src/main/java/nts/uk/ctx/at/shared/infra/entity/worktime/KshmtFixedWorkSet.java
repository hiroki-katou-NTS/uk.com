/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshmtFixedWorkSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_FIXED_WORK_SET")
public class KshmtFixedWorkSet implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt fixed work set PK. */
	@EmbeddedId
	protected KshmtFixedWorkSetPK kshmtFixedWorkSetPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The use half day. */
	@Column(name = "USE_HALF_DAY")
	private int useHalfDay;

	/** The legal ot set. */
	@Column(name = "LEGAL_OT_SET")
	private int legalOtSet;

	/** The calc method. */
	@Column(name = "CALC_METHOD")
	private int calcMethod;

	/** The lev rest calc type. */
	@Column(name = "LEV_REST_CALC_TYPE")
	private int levRestCalcType;

	/**
	 * Instantiates a new kshmt fixed work set.
	 */
	public KshmtFixedWorkSet() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtFixedWorkSetPK != null ? kshmtFixedWorkSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtFixedWorkSet)) {
			return false;
		}
		KshmtFixedWorkSet other = (KshmtFixedWorkSet) object;
		if ((this.kshmtFixedWorkSetPK == null && other.kshmtFixedWorkSetPK != null)
				|| (this.kshmtFixedWorkSetPK != null
						&& !this.kshmtFixedWorkSetPK.equals(other.kshmtFixedWorkSetPK))) {
			return false;
		}
		return true;
	}
}
