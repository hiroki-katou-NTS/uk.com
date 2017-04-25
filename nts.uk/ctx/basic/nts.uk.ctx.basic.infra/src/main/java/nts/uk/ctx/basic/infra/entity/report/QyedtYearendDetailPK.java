/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.report;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class QyedtYearendDetailPK.
 */
@Setter
@Getter
@Embeddable
public class QyedtYearendDetailPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The ccd. */
	@Basic(optional = false)
	@Column(name = "CCD")
	private short ccd;

	/** The pid. */
	@Basic(optional = false)
	@Column(name = "PID")
	private String pid;

	/** The year K. */
	@Basic(optional = false)
	@Column(name = "YEAR_K")
	private short yearK;

	/** The ye cnt. */
	@Basic(optional = false)
	@Column(name = "YE_CNT")
	private short yeCnt;

	/** The adj item no. */
	@Basic(optional = false)
	@Column(name = "ADJ_ITEM_NO")
	private short adjItemNo;

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (int) ccd;
		hash += (pid != null ? pid.hashCode() : 0);
		hash += (int) yearK;
		hash += (int) yeCnt;
		hash += (int) adjItemNo;
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof QyedtYearendDetailPK)) {
			return false;
		}
		QyedtYearendDetailPK other = (QyedtYearendDetailPK) object;
		if (this.ccd != other.ccd) {
			return false;
		}
		if ((this.pid == null && other.pid != null) || (this.pid != null && !this.pid.equals(other.pid))) {
			return false;
		}
		if (this.yearK != other.yearK) {
			return false;
		}
		if (this.yeCnt != other.yeCnt) {
			return false;
		}
		if (this.adjItemNo != other.adjItemNo) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.QyedtYearendDetailPK[ ccd=" + ccd + ", pid=" + pid + ", yearK=" + yearK + ", yeCnt=" + yeCnt
				+ ", adjItemNo=" + adjItemNo + " ]";
	}

}
