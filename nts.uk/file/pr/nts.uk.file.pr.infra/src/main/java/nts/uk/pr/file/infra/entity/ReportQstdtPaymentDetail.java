/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class ReportQstdtPaymentDetail.
 */
@Getter
@Setter
@Entity
@Table(name = "QSTDT_PAYMENT_DETAIL")
public class ReportQstdtPaymentDetail {

	/** The qstdt payment detail PK. */
	@EmbeddedId
	public ReportQstdtPaymentDetailPK qstdtPaymentDetailPK;

	/** The employee code. */
	@Column(name = "INV_SCD")
	public String employeeCode;

	/** The value. */
	@Column(name = "VAL")
	public BigDecimal value;

	/** The correct flag. */
	@Column(name = "CORRECT_FLG")
	public int correctFlag;

	/** The tax ATR. */
	@Column(name = "TAX_ATR")
	public int taxATR;

	/** The limit amount. */
	@Column(name = "LIMIT_MNY")
	public int limitAmount;

	/** The social insurrance attribute. */
	@Column(name = "SOCIAL_INS_ATR")
	public int socialInsurranceAttribute;

	/** The labor subject attribute. */
	@Column(name = "LABOR_INS_ATR")
	public int laborSubjectAttribute;

	/** The fix pay ATR. */
	@Column(name = "FIX_PAY_ATR")
	public int fixPayATR;

	/** The average pay ATR. */
	@Column(name = "AVE_PAY_ATR")
	public int averagePayATR;

	/** The deduct attribute. */
	@Column(name = "DEDUCT_ATR")
	public int deductAttribute;

	/** The item atr. */
	@Column(name = "ITEM_ATR")
	public int itemAtr;

	/** The commute allow tax impose. */
	@Column(name = "COMMU_ALLOW_TAX_IMPOSE")
	public BigDecimal commuteAllowTaxImpose;

	/** The commute allow month. */
	@Column(name = "COMMU_ALLOW_MONTH")
	public BigDecimal commuteAllowMonth;

	/** The commute allow fraction. */
	@Column(name = "COMMU_ALLOW_FRACTION")
	public BigDecimal commuteAllowFraction;

	/** The print line position. */
	@Column(name = "PRINT_LINE_POS")
	public int printLinePosition;

	/** The column position. */
	@Column(name = "ITEM_POS_COLUMN")
	public int columnPosition;

	/** The qstdt payment header. */
	@ManyToOne(optional = false)
	@JoinColumns({ @JoinColumn(name = "CCD", referencedColumnName = "CCD", insertable = false, updatable = false),
			@JoinColumn(name = "PID", referencedColumnName = "PID", insertable = false, updatable = false),
			@JoinColumn(name = "PROCESSING_NO", referencedColumnName = "PROCESSING_NO", insertable = false, updatable = false),
			@JoinColumn(name = "PAY_BONUS_ATR", referencedColumnName = "PAY_BONUS_ATR", insertable = false, updatable = false),
			@JoinColumn(name = "PROCESSING_YM", referencedColumnName = "PROCESSING_YM", insertable = false, updatable = false),
			@JoinColumn(name = "SPARE_PAY_ATR", referencedColumnName = "SPARE_PAY_ATR", insertable = false, updatable = false) })
	public ReportQstdtPaymentHeader qstdtPaymentHeader;

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((qstdtPaymentDetailPK == null) ? 0 : qstdtPaymentDetailPK.hashCode());
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
		ReportQstdtPaymentDetail other = (ReportQstdtPaymentDetail) obj;
		if (qstdtPaymentDetailPK == null) {
			if (other.qstdtPaymentDetailPK != null)
				return false;
		} else if (!qstdtPaymentDetailPK.equals(other.qstdtPaymentDetailPK))
			return false;
		return true;
	}
}
