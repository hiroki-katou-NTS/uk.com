/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class ReportQstdtPaymentHeaderPK.
 */
@Getter
@Setter
@Embeddable
public class ReportQstdtPaymentHeaderPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The company code. */
	@Column(name = "CCD")
	public String companyCode;
	
	/** The person id. */
	@Column(name = "PID")
	public String personId;
	
	/** The processing no. */
	@Column(name = "PROCESSING_NO")
	public int processingNo;
	
	/** The pay bonus atr. */
	@Column(name = "PAY_BONUS_ATR")
	public int payBonusAtr;
	
	/** The processing YM. */
	@Column(name = "PROCESSING_YM")
	public int processingYM;
	
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
		result = prime * result + ((companyCode == null) ? 0 : companyCode.hashCode());
		result = prime * result + payBonusAtr;
		result = prime * result + ((personId == null) ? 0 : personId.hashCode());
		result = prime * result + processingNo;
		result = prime * result + processingYM;
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
		ReportQstdtPaymentHeaderPK other = (ReportQstdtPaymentHeaderPK) obj;
		if (companyCode == null) {
			if (other.companyCode != null)
				return false;
		} else if (!companyCode.equals(other.companyCode))
			return false;
		if (payBonusAtr != other.payBonusAtr)
			return false;
		if (personId == null) {
			if (other.personId != null)
				return false;
		} else if (!personId.equals(other.personId))
			return false;
		if (processingNo != other.processingNo)
			return false;
		if (processingYM != other.processingYM)
			return false;
		if (sparePayAtr != other.sparePayAtr)
			return false;
		return true;
	}
	
}
