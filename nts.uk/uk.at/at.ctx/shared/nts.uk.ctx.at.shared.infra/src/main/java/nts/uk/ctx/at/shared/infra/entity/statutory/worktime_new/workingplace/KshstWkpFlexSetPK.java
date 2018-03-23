/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshstWkpFlexSetPK.
 */
@Setter
@Getter
@Embeddable
public class KshstWkpFlexSetPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Size(min = 1, max = 17)
	@Column(name = "CID")
	private String cid;

	/** The wkp id. */
	@Size(min = 1, max = 36)
	@Column(name = "WKP_ID")
	private String wkpId;

	/** The year. */
	@Column(name = "YEAR")
	private int year;

	/**
	 * Instantiates a new kshst wkp flex set PK.
	 */
	public KshstWkpFlexSetPK() {
		super();
	}

	/**
	 * Instantiates a new kshst wkp flex set PK.
	 *
	 * @param cid
	 *            the cid
	 * @param wkpId
	 *            the wkp id
	 * @param year
	 *            the year
	 */
	public KshstWkpFlexSetPK(String cid, String wkpId, int year) {
		super();
		this.cid = cid;
		this.wkpId = wkpId;
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
		hash += (wkpId != null ? wkpId.hashCode() : 0);
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
		if (!(object instanceof KshstWkpFlexSetPK)) {
			return false;
		}
		KshstWkpFlexSetPK other = (KshstWkpFlexSetPK) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if ((this.wkpId == null && other.wkpId != null)
				|| (this.wkpId != null && !this.wkpId.equals(other.wkpId))) {
			return false;
		}
		if (this.year != other.year) {
			return false;
		}
		return true;
	}

}
