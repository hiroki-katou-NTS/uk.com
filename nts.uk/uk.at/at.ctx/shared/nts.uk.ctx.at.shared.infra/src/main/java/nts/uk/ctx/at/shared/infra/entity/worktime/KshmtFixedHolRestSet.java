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
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KshmtFixedHolRestSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_FIXED_HOL_REST_SET")
public class KshmtFixedHolRestSet extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt fixed hol rest set PK. */
	@EmbeddedId
	protected KshmtFixedHolRestSetPK kshmtFixedHolRestSetPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The period no. */
	@Column(name = "PERIOD_NO")
	private int periodNo;

	/** The start time. */
	@Column(name = "START_TIME")
	private int startTime;

	/** The end time. */
	@Column(name = "END_TIME")
	private int endTime;

	/**
	 * Instantiates a new kshmt fixed hol rest set.
	 */
	public KshmtFixedHolRestSet() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtFixedHolRestSetPK != null ? kshmtFixedHolRestSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtFixedHolRestSet)) {
			return false;
		}
		KshmtFixedHolRestSet other = (KshmtFixedHolRestSet) object;
		if ((this.kshmtFixedHolRestSetPK == null && other.kshmtFixedHolRestSetPK != null)
				|| (this.kshmtFixedHolRestSetPK != null
						&& !this.kshmtFixedHolRestSetPK.equals(other.kshmtFixedHolRestSetPK))) {
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
		return this.kshmtFixedHolRestSetPK;
	}

}
