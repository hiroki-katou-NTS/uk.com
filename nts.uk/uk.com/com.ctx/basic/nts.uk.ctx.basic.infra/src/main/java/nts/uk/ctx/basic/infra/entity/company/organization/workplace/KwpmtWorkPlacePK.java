/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.workplace;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KwpmtWorkPlacePK.
 */
@Getter
@Setter
@Embeddable
public class KwpmtWorkPlacePK implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The ccid. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "CCID")
	private String ccid;

	/** The wkpid. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "WKPID")
	private String wkpid;
	 
 	/** The wkpcd. */
 	@NotNull
	@Column(name = "WKPCD")
	private String wkpcd;
	/**
	 * Instantiates a new kwpmt work place PK.
	 */
	public KwpmtWorkPlacePK() {
	}

	/**
	 * Instantiates a new kwpmt work place PK.
	 *
	 * @param ccid the ccid
	 * @param wkpid the wkpid
	 */
	public KwpmtWorkPlacePK(String ccid, String wkpid) {
		this.ccid = ccid;
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
		hash += (ccid != null ? ccid.hashCode() : 0);
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
		if (!(object instanceof KwpmtWorkPlacePK)) {
			return false;
		}
		KwpmtWorkPlacePK other = (KwpmtWorkPlacePK) object;
		if ((this.ccid == null && other.ccid != null)
			|| (this.ccid != null && !this.ccid.equals(other.ccid))) {
			return false;
		}
		if ((this.wkpid == null && other.wkpid != null)
			|| (this.wkpid != null && !this.wkpid.equals(other.wkpid))) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.KwpmtWorkPlacePK[ ccid=" + ccid + ", wkpid=" + wkpid + " ]";
	}

}
