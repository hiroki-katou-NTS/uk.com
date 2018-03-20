/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

/**
 * The Class KshstComNormalSetPK.
 */
@Embeddable
public class KshstComNormalSetPK implements Serializable {
	
	/** The cid. */
	@Size(min = 1, max = 17)
	@Column(name = "CID")
	private String cid;

	/** The year. */
	@Column(name = "YEAR")
	private short year;

	/**
	 * Instantiates a new kshst com normal set PK.
	 */
	public KshstComNormalSetPK() {
	}

	/**
	 * Instantiates a new kshst com normal set PK.
	 *
	 * @param cid the cid
	 * @param year the year
	 */
	public KshstComNormalSetPK(String cid, short year) {
		this.cid = cid;
		this.year = year;
	}

	/**
	 * Gets the cid.
	 *
	 * @return the cid
	 */
	public String getCid() {
		return cid;
	}

	/**
	 * Sets the cid.
	 *
	 * @param cid the new cid
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}

	/**
	 * Gets the year.
	 *
	 * @return the year
	 */
	public short getYear() {
		return year;
	}

	/**
	 * Sets the year.
	 *
	 * @param year the new year
	 */
	public void setYear(short year) {
		this.year = year;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (cid != null ? cid.hashCode() : 0);
		hash += (int) year;
		return hash;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshstComNormalSetPK)) {
			return false;
		}
		KshstComNormalSetPK other = (KshstComNormalSetPK) object;
		if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if (this.year != other.year) {
			return false;
		}
		return true;
	}

}
