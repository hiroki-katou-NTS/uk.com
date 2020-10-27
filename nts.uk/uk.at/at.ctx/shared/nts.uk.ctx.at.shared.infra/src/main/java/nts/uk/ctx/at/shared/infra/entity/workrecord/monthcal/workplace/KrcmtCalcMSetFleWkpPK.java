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
 * The Class KrcmtCalcMSetFleWkpPK.
 */

/**
 * Gets the wkp id.
 *
 * @return the wkp id
 */
@Getter

/**
 * Sets the wkp id.
 *
 * @param wkpId the new wkp id
 */
@Setter
@Embeddable
public class KrcmtCalcMSetFleWkpPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The wkp id. */
	@Column(name = "WKP_ID")
	private String wkpId;

	/**
	 * Instantiates a new krcst wkp flex M cal set PK.
	 */
	public KrcmtCalcMSetFleWkpPK() {
		super();
	}

	/**
	 * Instantiates a new krcst wkp flex M cal set PK.
	 *
	 * @param cid the cid
	 * @param wkpId the wkp id
	 */
	public KrcmtCalcMSetFleWkpPK(String cid, String wkpId) {
		super();
		this.cid = cid;
		this.wkpId = wkpId;
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
		hash += (wkpId != null ? wkpId.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KrcmtCalcMSetFleWkpPK)) {
			return false;
		}
		KrcmtCalcMSetFleWkpPK other = (KrcmtCalcMSetFleWkpPK) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if ((this.wkpId == null && other.wkpId != null)
				|| (this.wkpId != null && !this.wkpId.equals(other.wkpId))) {
			return false;
		}
		return true;
	}

}
