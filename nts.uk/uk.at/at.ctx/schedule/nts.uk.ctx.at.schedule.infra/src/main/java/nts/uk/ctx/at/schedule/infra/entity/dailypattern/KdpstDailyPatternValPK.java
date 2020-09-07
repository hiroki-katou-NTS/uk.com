/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.dailypattern;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KdpstDailyPatternValPK.
 */
@Setter
@Getter
@Embeddable
public class KdpstDailyPatternValPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The pattern cd. */
	@Column(name = "CD")
	private String patternCd;

	/** The disp order. */
	@Column(name = "CYCLE_ORDER")
	private Integer dispOrder;

	/**
	 * Instantiates a new kdpst daily pattern val PK.
	 */
	public KdpstDailyPatternValPK() {
		super();
	}

	/**
	 * Instantiates a new kdpst daily pattern val PK.
	 *
	 * @param patternCd
	 *            the pattern cd
	 * @param dispOrder
	 *            the disp order
	 * @param cid
	 *            the cid
	 */
	public KdpstDailyPatternValPK(String cid, String patternCd, Integer dispOrder) {
		this.patternCd = patternCd;
		this.dispOrder = dispOrder;
		this.cid = cid;
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
		hash += (patternCd != null ? patternCd.hashCode() : 0);
		hash += (dispOrder != null ? dispOrder.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KdpstDailyPatternValPK)) {
			return false;
		}
		KdpstDailyPatternValPK other = (KdpstDailyPatternValPK) object;
		if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		if ((this.patternCd == null && other.patternCd != null)
				|| (this.patternCd != null && !this.patternCd.equals(other.patternCd))) {
			return false;
		}
		if ((this.dispOrder == null && other.dispOrder != null)
				|| (this.dispOrder != null && !this.dispOrder.equals(other.dispOrder))) {
			return false;
		}
		return true;
	}
}
