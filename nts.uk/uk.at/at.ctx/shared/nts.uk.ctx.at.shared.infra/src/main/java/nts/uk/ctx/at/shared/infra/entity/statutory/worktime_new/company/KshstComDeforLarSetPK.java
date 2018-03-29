/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshstComDeforLarSetPK.
 */
@Getter
@Setter
@Embeddable
public class KshstComDeforLarSetPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The year. */
	@Column(name = "YEAR")
	private int year;

	/**
	 * Instantiates a new kshst com defor lar set PK.
	 */
	public KshstComDeforLarSetPK() {
		super();
	}

	/**
	 * Instantiates a new kshst com defor lar set PK.
	 *
	 * @param cid the cid
	 * @param year the year
	 */
	public KshstComDeforLarSetPK(String cid, int year) {
		super();
		this.cid = cid;
		this.year = year;
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
		hash += (int) year;
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshstComDeforLarSetPK)) {
			return false;
		}
		KshstComDeforLarSetPK other = (KshstComDeforLarSetPK) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if (this.year != other.year) {
			return false;
		}
		return true;
	}

}
