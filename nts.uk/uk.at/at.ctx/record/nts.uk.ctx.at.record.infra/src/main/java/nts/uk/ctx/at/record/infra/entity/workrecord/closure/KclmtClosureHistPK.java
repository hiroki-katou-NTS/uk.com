/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.workrecord.closure;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * The Class KclmtClosureHistPK.
 */
@Embeddable
public class KclmtClosureHistPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The ccid. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "CCID")
	private String ccid;
	
	/** The closure id. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "CLOSURE_ID")
	private String closureId;

	/**
	 * Instantiates a new kclmt closure hist PK.
	 */
	public KclmtClosureHistPK() {
	}

	/**
	 * Instantiates a new kclmt closure hist PK.
	 *
	 * @param ccid the ccid
	 * @param closureId the closure id
	 */
	public KclmtClosureHistPK(String ccid, String closureId) {
		this.ccid = ccid;
		this.closureId = closureId;
	}

	/**
	 * Gets the ccid.
	 *
	 * @return the ccid
	 */
	public String getCcid() {
		return ccid;
	}

	/**
	 * Sets the ccid.
	 *
	 * @param ccid the new ccid
	 */
	public void setCcid(String ccid) {
		this.ccid = ccid;
	}

	/**
	 * Gets the closure id.
	 *
	 * @return the closure id
	 */
	public String getClosureId() {
		return closureId;
	}

	/**
	 * Sets the closure id.
	 *
	 * @param closureId the new closure id
	 */
	public void setClosureId(String closureId) {
		this.closureId = closureId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (ccid != null ? ccid.hashCode() : 0);
		hash += (closureId != null ? closureId.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof KclmtClosureHistPK)) {
			return false;
		}
		KclmtClosureHistPK other = (KclmtClosureHistPK) object;
		if ((this.ccid == null && other.ccid != null)
			|| (this.ccid != null && !this.ccid.equals(other.ccid))) {
			return false;
		}
		if ((this.closureId == null && other.closureId != null)
			|| (this.closureId != null && !this.closureId.equals(other.closureId))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.KclmtClosureHistPK[ ccid=" + ccid + ", closureId=" + closureId + " ]";
	}
	

}
