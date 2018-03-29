/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.monthlyattditem;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KrcmtMonAttendanceItemPK.
 */
@Embeddable
@Getter
@Setter
public class KrcmtMonAttendanceItemPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The m atd item id. */
	@Column(name = "M_ATD_ITEM_ID")
	private int mAtdItemId;

	/**
	 * Instantiates a new krcmt mon attendance item PK.
	 */
	public KrcmtMonAttendanceItemPK() {
		super();
	}

	/**
	 * Instantiates a new krcmt mon attendance item PK.
	 *
	 * @param cid the cid
	 * @param mAtdItemId the m atd item id
	 */
	public KrcmtMonAttendanceItemPK(String cid, int mAtdItemId) {
		this.cid = cid;
		this.mAtdItemId = mAtdItemId;
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
		hash += (int) mAtdItemId;
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KrcmtMonAttendanceItemPK)) {
			return false;
		}
		KrcmtMonAttendanceItemPK other = (KrcmtMonAttendanceItemPK) object;
		if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if (this.mAtdItemId != other.mAtdItemId) {
			return false;
		}
		return true;
	}

}
