/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.fixedset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshmtWtFixStmpRefTsPK.
 */
@Getter
@Setter
@Embeddable
public class KshmtWtFixStmpRefTsPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The worktime cd. */
	@Column(name = "WORKTIME_CD")
	private String worktimeCd;

	/** The work no. */
	@Column(name = "WORK_NO")
	private int workNo;

	/** The atr. */
	@Column(name = "ATR")
	private int atr;

	/**
	 * Instantiates a new kshmt fixed stamp reflect PK.
	 */
	public KshmtWtFixStmpRefTsPK() {
	}

	/**
	 * Instantiates a new kshmt fixed stamp reflect PK.
	 *
	 * @param cid
	 *            the cid
	 * @param worktimeCd
	 *            the worktime cd
	 * @param workNo
	 *            the work no
	 * @param atr
	 *            the atr
	 */
	public KshmtWtFixStmpRefTsPK(String cid, String worktimeCd, int workNo, int atr) {
		this.cid = cid;
		this.worktimeCd = worktimeCd;
		this.workNo = workNo;
		this.atr = atr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + atr;
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + workNo;
		result = prime * result + ((worktimeCd == null) ? 0 : worktimeCd.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		KshmtWtFixStmpRefTsPK other = (KshmtWtFixStmpRefTsPK) obj;
		if (atr != other.atr)
			return false;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (workNo != other.workNo)
			return false;
		if (worktimeCd == null) {
			if (other.worktimeCd != null)
				return false;
		} else if (!worktimeCd.equals(other.worktimeCd))
			return false;
		return true;
	}
}
