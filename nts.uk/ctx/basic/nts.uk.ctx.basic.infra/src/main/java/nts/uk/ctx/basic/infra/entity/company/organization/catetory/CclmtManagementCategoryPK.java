/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.catetory;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;	

/**
 * The Class CclmtManagementCategoryPK.
 */
@Getter
@Setter
@Embeddable
public class CclmtManagementCategoryPK implements Serializable {

	
	private static final long serialVersionUID = 1L;

	/** The ccid. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "CCID")
	private String ccid;

	/** The code. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "CODE")
	private String code;

	/**
	 * Instantiates a new cclmt management category PK.
	 */
	public CclmtManagementCategoryPK() {
	}

	/**
	 * Instantiates a new cclmt management category PK.
	 *
	 * @param ccid the ccid
	 * @param code the code
	 */
	public CclmtManagementCategoryPK(String ccid, String code) {
		this.ccid = ccid;
		this.code = code;
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
	 * @param ccid
	 *            the new ccid
	 */
	public void setCcid(String ccid) {
		this.ccid = ccid;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(String code) {
		this.code = code;
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
		hash += (code != null ? code.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof CclmtManagementCategoryPK)) {
			return false;
		}
		CclmtManagementCategoryPK other = (CclmtManagementCategoryPK) object;
		if ((this.ccid == null && other.ccid != null)
			|| (this.ccid != null && !this.ccid.equals(other.ccid))) {
			return false;
		}
		if ((this.code == null && other.code != null)
			|| (this.code != null && !this.code.equals(other.code))) {
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
		return "entity.CclmtManagementCategoryPK[ ccid=" + ccid + ", code=" + code + " ]";
	}

}
