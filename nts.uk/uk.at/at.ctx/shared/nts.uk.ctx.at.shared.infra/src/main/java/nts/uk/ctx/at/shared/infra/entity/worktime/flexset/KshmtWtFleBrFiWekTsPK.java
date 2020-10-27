/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.flexset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshmtWtFleBrFiWekTsPK.
 */
@Getter
@Setter
@Embeddable
public class KshmtWtFleBrFiWekTsPK implements Serializable {

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
	
	/** The period no. */
	@Column(name = "PERIOD_NO")
	private Integer periodNo;

	/**
	 * Instantiates a new kshmt flex ha fix rest PK.
	 */
	public KshmtWtFleBrFiWekTsPK() {
		super();
	}
	
	/**
	 * Instantiates a new kshmt flex ha fix rest PK.
	 *
	 * @param cid the cid
	 * @param worktimeCd the worktime cd
	 * @param amPmAtr the am pm atr
	 * @param periodNo the period no
	 */
	public KshmtWtFleBrFiWekTsPK(String cid, String worktimeCd, int amPmAtr, int periodNo) {
		super();
		this.cid = cid;
		this.worktimeCd = worktimeCd;
		this.amPmAtr = amPmAtr;
		this.periodNo = periodNo;
	}

	/**
	 * Instantiates a new kshmt flex ha fix rest PK.
	 *
	 * @param cid the cid
	 * @param worktimeCd the worktime cd
	 */
	public KshmtWtFleBrFiWekTsPK(String cid, String worktimeCd) {
		super();
		this.cid = cid;
		this.worktimeCd = worktimeCd;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amPmAtr;
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + ((periodNo == null) ? 0 : periodNo.hashCode());
		result = prime * result + ((worktimeCd == null) ? 0 : worktimeCd.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KshmtWtFleBrFiWekTsPK other = (KshmtWtFleBrFiWekTsPK) obj;
		if (amPmAtr != other.amPmAtr)
			return false;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (periodNo == null) {
			if (other.periodNo != null)
				return false;
		} else if (!periodNo.equals(other.periodNo))
			return false;
		if (worktimeCd == null) {
			if (other.worktimeCd != null)
				return false;
		} else if (!worktimeCd.equals(other.worktimeCd))
			return false;
		return true;
	}

}
