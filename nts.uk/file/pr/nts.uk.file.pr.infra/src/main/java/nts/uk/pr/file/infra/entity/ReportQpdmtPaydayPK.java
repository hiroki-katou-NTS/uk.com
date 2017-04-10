/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The Class ReportQpdmtPaydayPK.
 */
@Embeddable
public class ReportQpdmtPaydayPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The ccd. */
	@Column(name = "CCD")
	public String ccd;

	/** The processing no. */
	@Column(name = "PROCESSING_NO")
	public int processingNo;

	/** The pay bonus atr. */
	@Column(name = "PAY_BONUS_ATR")
	public int payBonusAtr;

	/** The processing ym. */
	@Column(name = "PROCESSING_YM")
	public int processingYm;

	/** The spare pay atr. */
	@Column(name = "SPARE_PAY_ATR")
	public int sparePayAtr;

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ccd == null) ? 0 : ccd.hashCode());
		result = prime * result + payBonusAtr;
		result = prime * result + processingNo;
		result = prime * result + processingYm;
		result = prime * result + sparePayAtr;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportQpdmtPaydayPK other = (ReportQpdmtPaydayPK) obj;
		if (ccd == null) {
			if (other.ccd != null)
				return false;
		} else if (!ccd.equals(other.ccd))
			return false;
		if (payBonusAtr != other.payBonusAtr)
			return false;
		if (processingNo != other.processingNo)
			return false;
		if (processingYm != other.processingYm)
			return false;
		if (sparePayAtr != other.sparePayAtr)
			return false;
		return true;
	}
}
