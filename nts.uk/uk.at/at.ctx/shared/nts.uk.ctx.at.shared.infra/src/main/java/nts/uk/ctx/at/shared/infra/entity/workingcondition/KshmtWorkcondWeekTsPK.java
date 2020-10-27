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
 * The Class KshmtWorkcondWeekTsPK.
 */
@Getter
@Setter
@Embeddable
public class KshmtWorkcondWeekTsPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The history id. */
	@Column(name = "HIST_ID")
	private String historyId;

	/** The per work day off atr. */
	@Column(name = "PER_WORK_DAY_OFF_ATR")
	private int perWorkDayOffAtr;

	/** The start cnt. */
	@Column(name = "CNT")
	private int cnt;

	/**
	 * Instantiates a new kshmt dayofweek time zone PK.
	 */
	public KshmtWorkcondWeekTsPK() {
		super();
	}

	/**
	 * Instantiates a new kshmt dayofweek time zone PK.
	 *
	 * @param historyId
	 *            the history id
	 * @param perWorkDayOffAtr
	 *            the per work day off atr
	 * @param cnt
	 *            the cnt
	 */
	public KshmtWorkcondWeekTsPK(String historyId, int perWorkDayOffAtr, int cnt) {
		super();
		this.historyId = historyId;
		this.perWorkDayOffAtr = perWorkDayOffAtr;
		this.cnt = cnt;
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
		hash += (int) cnt;
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWorkcondWeekTsPK)) {
			return false;
		}
		KshmtWorkcondWeekTsPK other = (KshmtWorkcondWeekTsPK) object;
		if ((this.historyId == null && other.historyId != null)
				|| (this.historyId != null && !this.historyId.equals(other.historyId))) {
			return false;
		}
		if (this.perWorkDayOffAtr != other.perWorkDayOffAtr) {
			return false;
		}
		if (this.cnt != other.cnt) {
			return false;
		}
		return true;
	}

}
