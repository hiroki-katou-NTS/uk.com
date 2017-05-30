/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.entity.payment.contact;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

/**
 * The Class QcmtCommentMonthCpPK.
 */

/*
 * (non-Javadoc)
 * 
 * @see java.lang.Object#toString()
 */
@Data
@Embeddable
public class QcmtCommentMonthCpPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The ccd. */
	@Basic(optional = false)
	@Column(name = "CCD")
	private String ccd;

	/** The processing no. */
	@Basic(optional = false)
	@Column(name = "PROCESSING_NO")
	private int processingNo;

	/** The pay bonus atr. */
	@Basic(optional = false)
	@Column(name = "PAY_BONUS_ATR")
	private int payBonusAtr;

	/** The processing ym. */
	@Basic(optional = false)
	@Column(name = "PROCESSING_YM")
	private int processingYm;

	/** The spare pay atr. */
	@Basic(optional = false)
	@Column(name = "SPARE_PAY_ATR")
	private int sparePayAtr;

	/**
	 * Instantiates a new qcmt comment month cp PK.
	 */
	public QcmtCommentMonthCpPK() {
		super();
	}
	
	/**
	 * Instantiates a new qcmt comment month cp PK.
	 *
	 * @param ccd the ccd
	 * @param processingNo the processing no
	 * @param payBonusAtr the pay bonus atr
	 * @param processingYm the processing ym
	 * @param sparePayAtr the spare pay atr
	 */
	public QcmtCommentMonthCpPK(String ccd, int processingNo, int payBonusAtr, int processingYm,
		int sparePayAtr) {
		super();
		this.ccd = ccd;
		this.processingNo = processingNo;
		this.payBonusAtr = payBonusAtr;
		this.processingYm = processingYm;
		this.sparePayAtr = sparePayAtr;
	}
	
}
