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
 * The Class KshmtTotalSubjectsPK.
 */
@Getter
@Setter
@Embeddable
public class KshmtTotalSubjectsPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The total times no. */
	@Column(name = "TOTAL_TIMES_NO")
	private Integer totalTimesNo;

	/** The work type atr. */
	@Column(name = "WORK_TYPE_ATR")
	private Integer workTypeAtr;
	
	/** The work type cd. */
	@Column(name = "WORK_TYPE_CD")
	private String workTypeCd;

	/**
	 * Instantiates a new kshst total subjects PK.
	 */
	public KshmtTotalSubjectsPK() {
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
	public KshmtTotalSubjectsPK(String cid, Integer totalTimesNo, Integer workTypeAtr, String workTypeCd) {
		this.cid = cid;
		this.totalTimesNo = totalTimesNo;
		this.workTypeAtr = workTypeAtr;
		this.workTypeCd = workTypeCd;
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
		if (!(object instanceof KshmtTotalSubjectsPK)) {
			return false;
		}
		KshmtTotalSubjectsPK other = (KshmtTotalSubjectsPK) object;
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
		if (this.workTypeCd != other.workTypeCd) {
			return false;
		}
		return true;
	}


}
