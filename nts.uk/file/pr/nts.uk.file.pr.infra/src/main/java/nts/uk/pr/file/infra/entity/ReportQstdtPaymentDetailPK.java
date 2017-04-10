/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class ReportQstdtPaymentDetailPK.
 */
@Embeddable
@Getter
@Setter
public class ReportQstdtPaymentDetailPK {
	
	/** The company code. */
	@Column(name = "CCD")
	public String companyCode;
	
	/** The person id. */
	@Column(name = "PID")
	public String personId;
	
	/** The processing no. */
	@Column(name = "PROCESSING_NO")
	public int processingNo;
	
	/** The pay bonus attribute. */
	@Column(name = "PAY_BONUS_ATR")
	public int payBonusAttribute;
	
	/** The processing YM. */
	@Column(name = "PROCESSING_YM")
	public int processingYM;
	
	/** The spare pay attribute. */
	@Column(name = "SPARE_PAY_ATR")
	public int sparePayAttribute;
	
	/** The category ATR. */
	@Column(name = "CTG_ATR")
	public int categoryATR;
	
	/** The item code. */
	@Column(name = "ITEM_CD")
	public String itemCode;

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + categoryATR;
		result = prime * result + ((companyCode == null) ? 0 : companyCode.hashCode());
		result = prime * result + ((itemCode == null) ? 0 : itemCode.hashCode());
		result = prime * result + payBonusAttribute;
		result = prime * result + ((personId == null) ? 0 : personId.hashCode());
		result = prime * result + processingNo;
		result = prime * result + processingYM;
		result = prime * result + sparePayAttribute;
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
		ReportQstdtPaymentDetailPK other = (ReportQstdtPaymentDetailPK) obj;
		if (categoryATR != other.categoryATR)
			return false;
		if (companyCode == null) {
			if (other.companyCode != null)
				return false;
		} else if (!companyCode.equals(other.companyCode))
			return false;
		if (itemCode == null) {
			if (other.itemCode != null)
				return false;
		} else if (!itemCode.equals(other.itemCode))
			return false;
		if (payBonusAttribute != other.payBonusAttribute)
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
		if (sparePayAttribute != other.sparePayAttribute)
			return false;
		return true;
	}
}
