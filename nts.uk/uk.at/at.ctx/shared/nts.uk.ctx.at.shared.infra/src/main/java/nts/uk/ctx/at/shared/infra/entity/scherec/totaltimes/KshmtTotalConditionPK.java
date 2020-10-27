/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshmtTotalConditionPK.
 */
@Getter
@Setter
@Embeddable
public class KshmtTotalConditionPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The total times no. */
	@Column(name = "TOTAL_TIMES_NO")
	private Integer totalTimesNo;

	/**
	 * Instantiates a new kshst total condition PK.
	 */
	public KshmtTotalConditionPK() {
		super();
	}

	/**
	 * Instantiates a new kshst total condition PK.
	 *
	 * @param cid
	 *            the cid
	 * @param totalTimesNo
	 *            the total times no
	 */
	public KshmtTotalConditionPK(String cid, Integer totalTimesNo) {
		this.cid = cid;
		this.totalTimesNo = totalTimesNo;
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
		hash += (int) totalTimesNo;
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtTotalConditionPK)) {
			return false;
		}
		KshmtTotalConditionPK other = (KshmtTotalConditionPK) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if (this.totalTimesNo != other.totalTimesNo) {
			return false;
		}
		return true;
	}

}
