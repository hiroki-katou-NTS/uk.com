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
 * The Class KshmtShorttimeHistPK.
 */
@Setter
@Getter
@Embeddable
public class KshmtShorttimeHistPK implements Serializable {

	/** The Constant serialVersionUID. */
	public static final long serialVersionUID = 1L;

	/** The sid. */
	@Column(name = "SID")
	public String sid;

	/** The hist id. */
	@Column(name = "HIST_ID")
	public String histId;

	/**
	 * Instantiates a new bshmt worktime hist PK.
	 */
	public KshmtShorttimeHistPK() {
		super();
	}

	/**
	 * Instantiates a new bshmt worktime hist PK.
	 *
	 * @param sid
	 *            the sid
	 * @param histId
	 *            the hist id
	 */
	public KshmtShorttimeHistPK(String sid, String histId) {
		this.sid = sid;
		this.histId = histId;
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
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtShorttimeHistPK)) {
			return false;
		}
		KshmtShorttimeHistPK other = (KshmtShorttimeHistPK) object;
		if ((this.sid == null && other.sid != null) || (this.sid != null && !this.sid.equals(other.sid))) {
			return false;
		}
		if ((this.histId == null && other.histId != null)
				|| (this.histId != null && !this.histId.equals(other.histId))) {
			return false;
		}
		return true;
	}

}
