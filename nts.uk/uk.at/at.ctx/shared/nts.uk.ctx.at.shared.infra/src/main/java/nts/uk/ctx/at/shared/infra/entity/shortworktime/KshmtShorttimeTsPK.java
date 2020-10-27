/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.shortworktime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshmtShorttimeTsPK.
 */
@Getter
@Setter
@Embeddable
public class KshmtShorttimeTsPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The sid. */
	@Column(name = "SID")
	private String sid;

	/** The hist id. */
	@Column(name = "HIST_ID")
	private String histId;
	
	/** The time no. */
	@Column(name = "CNT")
	private Integer timeNo;
	

	/**
	 * Instantiates a new bshmt schild care frame PK.
	 */
	public KshmtShorttimeTsPK() {
		super();
	}

	/**
	 * Instantiates a new bshmt schild care frame PK.
	 *
	 * @param sid
	 *            the sid
	 * @param histId
	 *            the hist id
	 * @param strClock
	 *            the str clock
	 */
	public KshmtShorttimeTsPK(String sid, String histId, Integer timeNo) {
		this.sid = sid;
		this.histId = histId;
		this.timeNo = timeNo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (sid != null ? sid.hashCode() : 0);
		hash += (histId != null ? histId.hashCode() : 0);
		hash += (int) timeNo;
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtShorttimeTsPK)) {
			return false;
		}
		KshmtShorttimeTsPK other = (KshmtShorttimeTsPK) object;
		if ((this.sid == null && other.sid != null) || (this.sid != null && !this.sid.equals(other.sid))) {
			return false;
		}
		if ((this.histId == null && other.histId != null)
				|| (this.histId != null && !this.histId.equals(other.histId))) {
			return false;
		}
		if (this.timeNo != other.timeNo) {
			return false;
		}
		return true;
	}

}
