/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.predset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshmtWtComPredTsPK.
 */
@Getter
@Setter
@Embeddable
public class KshmtWtComPredTsPK implements Serializable {

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
	private Integer workNo;

	/**
	 * Instantiates a new kshmt work time sheet set PK.
	 */
	public KshmtWtComPredTsPK() {
		super();
	}
	

	/**
	 * Instantiates a new kshmt work time sheet set PK.
	 *
	 * @param cid the cid
	 * @param worktimeCd the worktime cd
	 * @param workNo the work no
	 */
	public KshmtWtComPredTsPK(String cid, String worktimeCd, Integer workNo) {
		super();
		this.cid = cid;
		this.worktimeCd = worktimeCd;
		this.workNo = workNo;
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
		hash += (int) workNo;
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtComPredTsPK)) {
			return false;
		}
		KshmtWtComPredTsPK other = (KshmtWtComPredTsPK) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if ((this.worktimeCd == null && other.worktimeCd != null)
				|| (this.worktimeCd != null && !this.worktimeCd.equals(other.worktimeCd))) {
			return false;
		}
		if (this.workNo != other.workNo) {
			return false;
		}
		return true;
	}







}
