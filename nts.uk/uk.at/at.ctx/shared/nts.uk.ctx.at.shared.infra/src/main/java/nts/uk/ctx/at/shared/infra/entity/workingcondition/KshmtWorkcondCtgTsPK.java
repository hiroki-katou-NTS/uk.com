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
 * The Class KshmtWorkcondCtgTsPK.
 */
@Setter
@Getter
@Embeddable
public class KshmtWorkcondCtgTsPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The history id. */
	@Column(name = "HIST_ID")
	private String historyId;

	/** The per work cat atr. */
	@Column(name = "PER_WORK_CAT_ATR")
	private int perWorkCatAtr;

	/** The start time. */
	@Column(name = "CNT")
	private int cnt;

	/**
	 * Instantiates a new kshmt work cat time zone PK.
	 */
	public KshmtWorkcondCtgTsPK() {
		super();
	}

	/**
	 * Instantiates a new kshmt work cat time zone PK.
	 *
	 * @param historyId
	 *            the history id
	 * @param perWorkCatAtr
	 *            the per work cat atr
	 * @param cnt
	 *            the cnt
	 */
	public KshmtWorkcondCtgTsPK(String historyId, int perWorkCatAtr, int cnt) {
		super();
		this.historyId = historyId;
		this.perWorkCatAtr = perWorkCatAtr;
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
		hash += (int) perWorkCatAtr;
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
		if (!(object instanceof KshmtWorkcondCtgTsPK)) {
			return false;
		}
		KshmtWorkcondCtgTsPK other = (KshmtWorkcondCtgTsPK) object;
		if ((this.historyId == null && other.historyId != null)
				|| (this.historyId != null && !this.historyId.equals(other.historyId))) {
			return false;
		}
		if (this.perWorkCatAtr != other.perWorkCatAtr) {
			return false;
		}
		if (this.cnt != other.cnt) {
			return false;
		}
		return true;
	}

}
