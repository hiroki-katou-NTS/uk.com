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
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KshmtWorktimeCommonSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WORKTIME_COMMON_SET")
public class KshmtWorktimeCommonSet extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt worktime common set PK. */
	@EmbeddedId
	protected KshmtWorktimeCommonSetPK kshmtWorktimeCommonSetPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The over day calc set. */
	@Column(name = "OVER_DAY_CALC_SET")
	private int overDayCalcSet;

	/** The use interval exemp time. */
	@Column(name = "USE_INTERVAL_EXEMP_TIME")
	private int useIntervalExempTime;

	/** The interval exemp unit. */
	@Column(name = "INTERVAL_EXEMP_UNIT")
	private int intervalExempUnit;

	/** The interval exemp rounding. */
	@Column(name = "INTERVAL_EXEMP_ROUNDING")
	private int intervalExempRounding;

	/** The interval time. */
	@Column(name = "INTERVAL_TIME")
	private int intervalTime;

	/** The use interval time. */
	@Column(name = "USE_INTERVAL_TIME")
	private int useIntervalTime;

	/** The raising salary set. */
	@Size(min = 1, max = 3)
	@Column(name = "RAISING_SALARY_SET")
	private String raisingSalarySet;

	/** The nur timezone work use. */
	@Column(name = "NUR_TIMEZONE_WORK_USE")
	private int nurTimezoneWorkUse;

	/** The emp time deduct. */
	@Column(name = "EMP_TIME_DEDUCT")
	private int empTimeDeduct;

	/** The child care work use. */
	@Column(name = "CHILD_CARE_WORK_USE")
	private int childCareWorkUse;

	/** The late night unit. */
	@Column(name = "LATE_NIGHT_UNIT")
	private int lateNightUnit;

	/** The late night rounding. */
	@Column(name = "LATE_NIGHT_ROUNDING")
	private int lateNightRounding;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtWorktimeCommonSetPK != null ? kshmtWorktimeCommonSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWorktimeCommonSet)) {
			return false;
		}
		KshmtWorktimeCommonSet other = (KshmtWorktimeCommonSet) object;
		if ((this.kshmtWorktimeCommonSetPK == null && other.kshmtWorktimeCommonSetPK != null)
				|| (this.kshmtWorktimeCommonSetPK != null
						&& !this.kshmtWorktimeCommonSetPK.equals(other.kshmtWorktimeCommonSetPK))) {
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
		return this.kshmtWorktimeCommonSetPK;
	}

}
