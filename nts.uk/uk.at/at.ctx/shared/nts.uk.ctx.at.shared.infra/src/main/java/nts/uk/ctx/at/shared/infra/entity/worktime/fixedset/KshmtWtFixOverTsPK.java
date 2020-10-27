/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.fixedset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshmtWtFixOverTsPK.
 */
@Getter
@Setter
@Embeddable
public class KshmtWtFixOverTsPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The worktime cd. */
	@Column(name = "WORKTIME_CD")
	private String worktimeCd;

	/** The am pm atr. */
	@Column(name = "AM_PM_ATR")
	private int amPmAtr;

	/** The worktime no. */
	@Column(name = "WORKTIME_NO")
	private int worktimeNo;

	/**
	 * Instantiates a new kshmt fixed ot time set PK.
	 */
	public KshmtWtFixOverTsPK() {
	}
	
	/**
	 * Instantiates a new kshmt fixed ot time set PK.
	 *
	 * @param cid the cid
	 * @param worktimeCd the worktime cd
	 * @param amPmAtr the am pm atr
	 * @param worktimeNo the worktime no
	 */
	public KshmtWtFixOverTsPK(String cid, String worktimeCd, int amPmAtr, int worktimeNo) {
		super();
		this.cid = cid;
		this.worktimeCd = worktimeCd;
		this.amPmAtr = amPmAtr;
		this.worktimeNo = worktimeNo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (cid != null ? cid.hashCode() : 0);
		hash += (worktimeCd != null ? worktimeCd.hashCode() : 0);
		hash += (int) amPmAtr;
		hash += (int) worktimeNo;
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtFixOverTsPK)) {
			return false;
		}
		KshmtWtFixOverTsPK other = (KshmtWtFixOverTsPK) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if ((this.worktimeCd == null && other.worktimeCd != null)
				|| (this.worktimeCd != null && !this.worktimeCd.equals(other.worktimeCd))) {
			return false;
		}
		if (this.amPmAtr != other.amPmAtr) {
			return false;
		}
		if (this.worktimeNo != other.worktimeNo) {
			return false;
		}
		return true;
	}

}
