/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.totaltimes;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshstTotalSubjectsPK.
 */
@Getter
@Setter
@Embeddable
public class KshstTotalSubjectsPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The total times no. */
	@Column(name = "TOTAL_TIMES_NO")
	private short totalTimesNo;

	/** The work type atr. */
	@Column(name = "WORK_TYPE_ATR")
	private short workTypeAtr;

	/**
	 * Instantiates a new kshst total subjects PK.
	 */
	public KshstTotalSubjectsPK() {
		super();
	}

	/**
	 * Instantiates a new kshst total subjects PK.
	 *
	 * @param cid
	 *            the cid
	 * @param totalTimesNo
	 *            the total times no
	 * @param workTypeAtr
	 *            the work type atr
	 */
	public KshstTotalSubjectsPK(String cid, short totalTimesNo, short workTypeAtr) {
		this.cid = cid;
		this.totalTimesNo = totalTimesNo;
		this.workTypeAtr = workTypeAtr;
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
		hash += (int) workTypeAtr;
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshstTotalSubjectsPK)) {
			return false;
		}
		KshstTotalSubjectsPK other = (KshstTotalSubjectsPK) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if (this.totalTimesNo != other.totalTimesNo) {
			return false;
		}
		if (this.workTypeAtr != other.workTypeAtr) {
			return false;
		}
		return true;
	}

}
