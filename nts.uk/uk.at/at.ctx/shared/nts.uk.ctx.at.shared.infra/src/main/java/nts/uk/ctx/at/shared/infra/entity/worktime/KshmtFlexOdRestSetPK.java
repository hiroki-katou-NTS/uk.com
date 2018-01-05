/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshmtFlexOdRestSetPK.
 */
@Getter
@Setter
@Embeddable
public class KshmtFlexOdRestSetPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The worktime cd. */
	@Column(name = "WORKTIME_CD")
	private String worktimeCd;

	/** The period no. */
	@Column(name = "PERIOD_NO")
	private int periodNo;

	/**
	 * Instantiates a new kshmt flex od rest set PK.
	 */
	public KshmtFlexOdRestSetPK() {
		super();
	}
	

	/**
	 * Instantiates a new kshmt flex od rest set PK.
	 *
	 * @param cid the cid
	 * @param worktimeCd the worktime cd
	 * @param periodNo the period no
	 */
	public KshmtFlexOdRestSetPK(String cid, String worktimeCd, int periodNo) {
		super();
		this.cid = cid;
		this.worktimeCd = worktimeCd;
		this.periodNo = periodNo;
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
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtFlexOdRestSetPK)) {
			return false;
		}
		KshmtFlexOdRestSetPK other = (KshmtFlexOdRestSetPK) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if ((this.worktimeCd == null && other.worktimeCd != null)
				|| (this.worktimeCd != null && !this.worktimeCd.equals(other.worktimeCd))) {
			return false;
		}
		return true;
	}


}
