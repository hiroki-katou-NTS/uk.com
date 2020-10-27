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
 * The Class KshmtShorttimeHistItemPK.
 */
@Setter
@Getter
@Embeddable
public class KshmtShorttimeHistItemPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The sid. */
	@Column(name = "SID")
	private String sid;

	/** The hist id. */
	@Column(name = "HIST_ID")
	private String histId;

	/**
	 * Instantiates a new bshmt worktime hist item PK.
	 */
	public KshmtShorttimeHistItemPK() {
		super();
	}

	/**
	 * Instantiates a new bshmt worktime hist item PK.
	 *
	 * @param sid the sid
	 * @param histId the hist id
	 */
	public KshmtShorttimeHistItemPK(String sid, String histId) {
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
		if (!(object instanceof KshmtShorttimeHistItemPK)) {
			return false;
		}
		KshmtShorttimeHistItemPK other = (KshmtShorttimeHistItemPK) object;
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
