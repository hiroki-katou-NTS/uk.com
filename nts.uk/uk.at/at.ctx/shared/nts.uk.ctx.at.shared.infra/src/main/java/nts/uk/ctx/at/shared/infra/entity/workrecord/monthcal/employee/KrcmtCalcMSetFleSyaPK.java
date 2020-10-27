/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.employee;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KrcmtCalcMSetFleSyaPK.
 */

/**
 * Gets the sid.
 *
 * @return the sid
 */
@Getter

/**
 * Sets the sid.
 *
 * @param sid the new sid
 */
@Setter
@Embeddable
public class KrcmtCalcMSetFleSyaPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The sid. */
	@Column(name = "SID")
	private String sid;
	
	/**
	 * Instantiates a new krcst sha flex M cal set PK.
	 *
	 * @param cid the cid
	 * @param sid the sid
	 */
	public KrcmtCalcMSetFleSyaPK(String cid, String sid) {
		super();
		this.cid = cid;
		this.sid = sid;
	}

	/**
	 * Instantiates a new krcst sha flex M cal set PK.
	 */
	public KrcmtCalcMSetFleSyaPK() {
		super();
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
		hash += (sid != null ? sid.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KrcmtCalcMSetFleSyaPK)) {
			return false;
		}
		KrcmtCalcMSetFleSyaPK other = (KrcmtCalcMSetFleSyaPK) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if ((this.sid == null && other.sid != null)
				|| (this.sid != null && !this.sid.equals(other.sid))) {
			return false;
		}
		return true;
	}

}
