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
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KshmtOtherLateEarly.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_OTHER_LATE_EARLY")
public class KshmtOtherLateEarly extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt other late early PK. */
	@EmbeddedId
	protected KshmtOtherLateEarlyPK kshmtOtherLateEarlyPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The deduction unit. */
	@Column(name = "DEDUCTION_UNIT")
	private int deductionUnit;

	/** The deduction rounding. */
	@Column(name = "DEDUCTION_ROUNDING")
	private int deductionRounding;



	/** The include worktime. */
	@Column(name = "INCLUDE_WORKTIME")
	private int includeWorktime;

	/** The grace time. */
	@Column(name = "GRACE_TIME")
	private int graceTime;

	/** The record unit. */
	@Column(name = "RECORD_UNIT")
	private int recordUnit;

	/** The record rounding. */
	@Column(name = "RECORD_ROUNDING")
	private int recordRounding;

	/**
	 * Instantiates a new kshmt other late early.
	 */
	public KshmtOtherLateEarly() {
		super();
	}
	
	/**
	 * Instantiates a new kshmt other late early.
	 *
	 * @param pk the pk
	 */
	public KshmtOtherLateEarly(KshmtOtherLateEarlyPK pk) {
		super();
		this.kshmtOtherLateEarlyPK = pk;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtOtherLateEarlyPK != null ? kshmtOtherLateEarlyPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtOtherLateEarly)) {
			return false;
		}
		KshmtOtherLateEarly other = (KshmtOtherLateEarly) object;
		if ((this.kshmtOtherLateEarlyPK == null && other.kshmtOtherLateEarlyPK != null)
				|| (this.kshmtOtherLateEarlyPK != null
						&& !this.kshmtOtherLateEarlyPK.equals(other.kshmtOtherLateEarlyPK))) {
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
		return this.kshmtOtherLateEarlyPK;
	}
}
