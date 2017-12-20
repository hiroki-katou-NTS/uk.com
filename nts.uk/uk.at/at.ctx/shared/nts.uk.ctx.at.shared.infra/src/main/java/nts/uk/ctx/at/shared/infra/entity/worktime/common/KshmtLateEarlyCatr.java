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

import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KSHMT_LATE_EARLY_CATR")
public class KshmtLateEarlyCatr extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	protected KshmtLateEarlyCatrPK kshmtLateEarlyCatrPK;

	@Column(name = "WORKTIME_SET_METHOD")
	private Integer worktimeSetMethod;

	@Column(name = "DEDUCTION_UNIT")
	private Integer deductionUnit;

	@Column(name = "DEDUCTION_ROUNDING")
	private Integer deductionRounding;

	@Column(name = "EXTRACT_LATE_EARLY_TIME")
	private Integer extractLateEarlyTime;

	@Column(name = "INCLUDE_WORKTIME")
	private Integer includeWorktime;

	@Column(name = "GRACE_TIME")
	private Integer graceTime;

	@Column(name = "RECORD_UNIT")
	private Integer recordUnit;

	@Column(name = "RECORD_ROUNDING")
	private Integer recordRounding;

	public KshmtLateEarlyCatr() {
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtLateEarlyCatrPK != null ? kshmtLateEarlyCatrPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof KshmtLateEarlyCatr)) {
			return false;
		}
		KshmtLateEarlyCatr other = (KshmtLateEarlyCatr) object;
		if ((this.kshmtLateEarlyCatrPK == null && other.kshmtLateEarlyCatrPK != null)
				|| (this.kshmtLateEarlyCatrPK != null
						&& !this.kshmtLateEarlyCatrPK.equals(other.kshmtLateEarlyCatrPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "javaapplication1.KshmtLateEarlyCatr[ kshmtLateEarlyCatrPK=" + kshmtLateEarlyCatrPK + " ]";
	}

	@Override
	protected Object getKey() {
		return this.kshmtLateEarlyCatrPK;
	}

}
