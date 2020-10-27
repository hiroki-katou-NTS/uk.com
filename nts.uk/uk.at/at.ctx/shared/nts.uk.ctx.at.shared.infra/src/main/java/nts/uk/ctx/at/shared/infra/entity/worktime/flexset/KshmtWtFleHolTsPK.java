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
 * The Class KshmtWtFleHolTsPK.
 */
@Getter
@Setter
@Embeddable
public class KshmtWtFleHolTsPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The worktime cd. */
	@Column(name = "WORKTIME_CD")
	private String worktimeCd;

	/** The worktime no. */
	@Column(name = "WORKTIME_NO")
	private int worktimeNo;

	/**
	 * Instantiates a new kshmt flex hol set PK.
	 */
	public KshmtWtFleHolTsPK() {
		super();
	}
	
	/**
	 * Instantiates a new kshmt flex hol set PK.
	 *
	 * @param cid the cid
	 * @param worktimeCd the worktime cd
	 * @param worktimeNo the worktime no
	 */
	public KshmtWtFleHolTsPK(String cid, String worktimeCd,int worktimeNo) {
		super();
		this.cid = cid;
		this.worktimeCd = worktimeCd;
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
		if (!(object instanceof KshmtWtFleHolTsPK)) {
			return false;
		}
		KshmtWtFleHolTsPK other = (KshmtWtFleHolTsPK) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if ((this.worktimeCd == null && other.worktimeCd != null)
				|| (this.worktimeCd != null && !this.worktimeCd.equals(other.worktimeCd))) {
			return false;
		}
		if (this.worktimeNo != other.worktimeNo) {
			return false;
		}
		return true;
	}
	

}
