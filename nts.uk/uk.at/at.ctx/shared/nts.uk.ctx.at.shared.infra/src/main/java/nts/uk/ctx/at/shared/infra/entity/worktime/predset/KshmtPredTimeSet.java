/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.predset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KSHMT_PRED_TIME_SET")
@Getter
@Setter
public class KshmtPredTimeSet extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected KshmtPredTimeSetPK kshmtPredTimeSetPK;

	@Column(name = "RANGE_TIME_DAY")
	private Integer rangeTimeDay;

	@Column(name = "NIGHT_SHIFT_ATR")
	private Integer nightShiftAtr;

	@Column(name = "START_DATE_CLOCK")
	private Integer startDateClock;

	@Column(name = "PREDETERMINE_ATR")
	private Integer predetermineAtr;

	@Column(name = "MORNING_END_TIME")
	private Integer morningEndTime;

	@Column(name = "AFTERNOON_START_TIME")
	private Integer afternoonStartTime;

	@Column(name = "WORK_ADD_ONE_DAY")
	private Integer workAddOneDay;

	@Column(name = "WORK_ADD_MORNING")
	private Integer workAddMorning;

	@Column(name = "WORK_ADD_AFTERNOON")
	private Integer workAddAfternoon;

	@Column(name = "PRED_ONE_DAY")
	private Integer predOneDay;

	@Column(name = "PRED_MORNING")
	private Integer predMorning;

	@Column(name = "PRED_AFTERNOON")
	private Integer predAfternoon;

	public KshmtPredTimeSet() {
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtPredTimeSetPK != null ? kshmtPredTimeSetPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtPredTimeSet)) {
			return false;
		}
		KshmtPredTimeSet other = (KshmtPredTimeSet) object;
		if ((this.kshmtPredTimeSetPK == null && other.kshmtPredTimeSetPK != null)
				|| (this.kshmtPredTimeSetPK != null && !this.kshmtPredTimeSetPK.equals(other.kshmtPredTimeSetPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "javaapplication1.KshmtPredTimeSet[ kshmtPredTimeSetPK=" + kshmtPredTimeSetPK + " ]";
	}

	@Override
	protected Object getKey() {
		return this.kshmtPredTimeSetPK;
	}

}
