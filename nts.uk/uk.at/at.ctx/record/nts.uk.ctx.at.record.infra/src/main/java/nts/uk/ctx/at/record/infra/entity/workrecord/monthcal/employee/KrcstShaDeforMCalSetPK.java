/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.employee;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KrcstShaDeforMCalSetPK.
 */

/**
 * Gets the sid.
 *
 * @return the sid
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

/**
 * Sets the sid.
 *
 * @param sid the new sid
 */
@Setter
@Embeddable
public class KrcstShaDeforMCalSetPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The sid. */
	@Column(name = "SID")
	private String sid;
	
	/**
	 * Instantiates a new krcst sha defor M cal set PK.
	 */
	public KrcstShaDeforMCalSetPK() {
		super();
	}
	
	/**
	 * Instantiates a new krcst sha defor M cal set PK.
	 *
	 * @param cid the cid
	 * @param sid the sid
	 */
	public KrcstShaDeforMCalSetPK(String cid, String sid) {
		super();
		this.cid = cid;
		this.sid = sid;
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
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof KrcstShaDeforMCalSetPK)) {
			return false;
		}
		KrcstShaDeforMCalSetPK other = (KrcstShaDeforMCalSetPK) object;
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
