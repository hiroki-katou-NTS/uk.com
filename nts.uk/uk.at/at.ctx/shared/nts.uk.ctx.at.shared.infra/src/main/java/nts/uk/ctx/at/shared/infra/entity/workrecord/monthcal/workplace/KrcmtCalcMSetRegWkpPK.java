/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.workplace;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KrcmtCalcMSetRegWkpPK.
 */

/**
 * Gets the wkpid.
 *
 * @return the wkpid
 */
@Getter

/**
 * Sets the wkpid.
 *
 * @param wkpid the new wkpid
 */
@Setter
@Embeddable
public class KrcmtCalcMSetRegWkpPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The wkpid. */
	@Column(name = "WKPID")
	private String wkpid;

	/**
	 * Instantiates a new krcst wkp reg M cal set PK.
	 */
	public KrcmtCalcMSetRegWkpPK() {
		super();
	}
	
	/**
	 * Instantiates a new krcst wkp reg M cal set PK.
	 *
	 * @param cid the cid
	 * @param wkpid the wkpid
	 */
	public KrcmtCalcMSetRegWkpPK(String cid, String wkpid) {
		super();
		this.cid = cid;
		this.wkpid = wkpid;
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
		hash += (wkpid != null ? wkpid.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KrcmtCalcMSetRegWkpPK)) {
			return false;
		}
		KrcmtCalcMSetRegWkpPK other = (KrcmtCalcMSetRegWkpPK) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if ((this.wkpid == null && other.wkpid != null)
				|| (this.wkpid != null && !this.wkpid.equals(other.wkpid))) {
			return false;
		}
		return true;
	}

}
