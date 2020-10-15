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
 * The Class KshmtRoundingSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_ROUNDING_SET")
public class KshmtRoundingSet extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt rounding set PK. */
	@EmbeddedId
	protected KshmtRoundingSetPK kshmtRoundingSetPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The front rear atr. */
	@Column(name = "FRONT_REAR_ATR")
	private int frontRearAtr;

	/** The rounding time unit. */
	@Column(name = "ROUNDING_TIME_UNIT")
	private int roundingTimeUnit;

	
	//** The attendance minute later**//
	@Column(name = "ATTENDANCE_MINUTE_LATER")
	private int attendanceMinuteLater;
	
	//** The leave minute ago**//
	@Column(name = "ATTENDANCE_MINUTE_LATER")
	private int leaveWorkMinuteAgo;
	
	/**
	 * Instantiates a new kshmt rounding set.
	 */
	public KshmtRoundingSet() {
		super();
	}

	/**
	 * Instantiates a new kshmt rounding set.
	 *
	 * @param kshmtRoundingSetPK the kshmt rounding set PK
	 */
	public KshmtRoundingSet(KshmtRoundingSetPK kshmtRoundingSetPK) {
		super();
		this.kshmtRoundingSetPK = kshmtRoundingSetPK;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtRoundingSetPK != null ? kshmtRoundingSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtRoundingSet)) {
			return false;
		}
		KshmtRoundingSet other = (KshmtRoundingSet) object;
		if ((this.kshmtRoundingSetPK == null && other.kshmtRoundingSetPK != null)
				|| (this.kshmtRoundingSetPK != null
						&& !this.kshmtRoundingSetPK.equals(other.kshmtRoundingSetPK))) {
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
		return this.kshmtRoundingSetPK;
	}

}
