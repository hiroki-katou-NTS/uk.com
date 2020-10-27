/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.workingcondition;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshmtWorkcondWeekPK.
 */
@Getter
@Setter
@Embeddable
public class KshmtWorkcondWeekPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The history id. */
	@Column(name = "HIST_ID")
	private String historyId;

	/** The per work day off atr. */
	@Column(name = "PER_WORK_DAY_OFF_ATR")
	private int perWorkDayOffAtr;

	/**
	 * Instantiates a new kshmt personal day of week PK.
	 */
	public KshmtWorkcondWeekPK() {
		super();
	}

	/**
	 * Instantiates a new kshmt personal day of week PK.
	 *
	 * @param historyId
	 *            the history id
	 * @param perWorkDayOffAtr
	 *            the per work day off atr
	 */
	public KshmtWorkcondWeekPK(String historyId, int perWorkDayOffAtr) {
		super();
		this.historyId = historyId;
		this.perWorkDayOffAtr = perWorkDayOffAtr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (historyId != null ? historyId.hashCode() : 0);
		hash += (int) perWorkDayOffAtr;
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWorkcondWeekPK)) {
			return false;
		}
		KshmtWorkcondWeekPK other = (KshmtWorkcondWeekPK) object;
		if ((this.historyId == null && other.historyId != null)
				|| (this.historyId != null && !this.historyId.equals(other.historyId))) {
			return false;
		}
		if (this.perWorkDayOffAtr != other.perWorkDayOffAtr) {
			return false;
		}
		return true;
	}

}
