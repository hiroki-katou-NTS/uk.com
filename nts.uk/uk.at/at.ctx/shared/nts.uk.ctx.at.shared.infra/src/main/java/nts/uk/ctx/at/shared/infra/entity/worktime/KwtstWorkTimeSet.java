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
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KwtstWorkTimeSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KWTST_WORK_TIME_SET")
public class KwtstWorkTimeSet extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kwtst work time set PK. */
	@EmbeddedId
	protected KwtstWorkTimeSetPK kwtstWorkTimeSetPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The range time day. */
	@Column(name = "RANGE_TIME_DAY")
	private int rangeTimeDay;

	/** The addition set id. */
	@Column(name = "ADDITION_SET_ID")
	private String additionSetId;

	/** The night shift atr. */
	@Column(name = "NIGHT_SHIFT_ATR")
	private int nightShiftAtr;

	/** The start date clock. */
	@Column(name = "START_DATE_CLOCK")
	private int startDateClock;

	/** The predetermine atr. */
	@Column(name = "PREDETERMINE_ATR")
	private int predetermineAtr;

	/** The morning end time. */
	@Column(name = "MORNING_END_TIME")
	private int morningEndTime;

	/** The afternoon start time. */
	@Column(name = "AFTERNOON_START_TIME")
	private int afternoonStartTime;

	/**
	 * Instantiates a new kwtst work time set.
	 */
	public KwtstWorkTimeSet() {
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
		hash += (kwtstWorkTimeSetPK != null ? kwtstWorkTimeSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KwtstWorkTimeSet)) {
			return false;
		}
		KwtstWorkTimeSet other = (KwtstWorkTimeSet) object;
		if ((this.kwtstWorkTimeSetPK == null && other.kwtstWorkTimeSetPK != null)
				|| (this.kwtstWorkTimeSetPK != null
						&& !this.kwtstWorkTimeSetPK.equals(other.kwtstWorkTimeSetPK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kwtstWorkTimeSetPK;
	}

}
