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
 * The Class KshmtDiffTimeWorkSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_DIFF_TIME_WORK_SET")
public class KshmtDiffTimeWorkSet extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt diff time work set PK. */
	@EmbeddedId
	protected KshmtDiffTimeWorkSetPK kshmtDiffTimeWorkSetPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The dt calc method. */
	@Column(name = "DT_CALC_METHOD")
	private int dtCalcMethod;

	/** The dt common rest set. */
	@Column(name = "DT_COMMON_REST_SET")
	private int dtCommonRestSet;

	/** The use half day. */
	@Column(name = "USE_HALF_DAY")
	private int useHalfDay;

	/** The ot set. */
	@Column(name = "OT_SET")
	private int otSet;

	/** The change ahead. */
	@Column(name = "CHANGE_AHEAD")
	private int changeAhead;

	/** The change behind. */
	@Column(name = "CHANGE_BEHIND")
	private int changeBehind;

	/** The front rear atr. */
	@Column(name = "FRONT_REAR_ATR")
	private int frontRearAtr;

	/** The time rounding unit. */
	@Column(name = "TIME_ROUNDING_UNIT")
	private int timeRoundingUnit;

	/**
	 * Instantiates a new kshmt diff time work set.
	 */
	public KshmtDiffTimeWorkSet() {
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
		hash += (kshmtDiffTimeWorkSetPK != null ? kshmtDiffTimeWorkSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtDiffTimeWorkSet)) {
			return false;
		}
		KshmtDiffTimeWorkSet other = (KshmtDiffTimeWorkSet) object;
		if ((this.kshmtDiffTimeWorkSetPK == null && other.kshmtDiffTimeWorkSetPK != null)
				|| (this.kshmtDiffTimeWorkSetPK != null
						&& !this.kshmtDiffTimeWorkSetPK.equals(other.kshmtDiffTimeWorkSetPK))) {
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
		return this.kshmtDiffTimeWorkSetPK;
	}

}
