/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.fixedset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtFixedWorkTimeSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_FIXED_WORK_TIME_SET")
public class KshmtFixedWorkTimeSet extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt fixed work time set PK. */
	@EmbeddedId
	protected KshmtFixedWorkTimeSetPK kshmtFixedWorkTimeSetPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The unit. */
	@Column(name = "UNIT")
	private int unit;

	/** The rounding. */
	@Column(name = "ROUNDING")
	private int rounding;

	/** The time str. */
	@Column(name = "TIME_STR")
	private int timeStr;

	/** The time end. */
	@Column(name = "TIME_END")
	private int timeEnd;

	/**
	 * Instantiates a new kshmt fixed work time set.
	 */
	public KshmtFixedWorkTimeSet() {
	}
	
	/**
	 * Instantiates a new kshmt fixed work time set.
	 *
	 * @param kshmtFixedWorkTimeSetPK the kshmt fixed work time set PK
	 */
	public KshmtFixedWorkTimeSet(KshmtFixedWorkTimeSetPK kshmtFixedWorkTimeSetPK) {
		this.kshmtFixedWorkTimeSetPK = kshmtFixedWorkTimeSetPK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtFixedWorkTimeSetPK != null ? kshmtFixedWorkTimeSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtFixedWorkTimeSet)) {
			return false;
		}
		KshmtFixedWorkTimeSet other = (KshmtFixedWorkTimeSet) object;
		if ((this.kshmtFixedWorkTimeSetPK == null && other.kshmtFixedWorkTimeSetPK != null)
				|| (this.kshmtFixedWorkTimeSetPK != null
						&& !this.kshmtFixedWorkTimeSetPK.equals(other.kshmtFixedWorkTimeSetPK))) {
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
		return this.kshmtFixedWorkTimeSetPK;
	}

}
