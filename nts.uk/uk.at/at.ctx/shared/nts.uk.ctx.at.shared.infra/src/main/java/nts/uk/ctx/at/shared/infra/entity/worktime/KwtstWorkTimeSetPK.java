/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KwtstWorkTimeSetPK.
 */
@Getter
@Setter
@Embeddable
public class KwtstWorkTimeSetPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The sift cd. */
	@Column(name = "SIFT_CD")
	private String siftCd;

	/**
	 * Instantiates a new kwtst work time set PK.
	 */
	public KwtstWorkTimeSetPK() {
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
		hash += (siftCd != null ? siftCd.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KwtstWorkTimeSetPK)) {
			return false;
		}
		KwtstWorkTimeSetPK other = (KwtstWorkTimeSetPK) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if ((this.siftCd == null && other.siftCd != null)
				|| (this.siftCd != null && !this.siftCd.equals(other.siftCd))) {
			return false;
		}
		return true;
	}

}
