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
 * The Class QctmtCommentMonthEmPK.
 */

/*
 * (non-Javadoc)
 * 
 * @see java.lang.Object#toString()
 */

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Data
@Embeddable
public class QctmtCommentMonthEmPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The ccd. */
	@Basic(optional = false)
	@Column(name = "CCD")
	private String ccd;

	/** The emp cd. */
	@Basic(optional = false)
	@Column(name = "EMPCD")
	private String empCd;

	/** The pay bonus atr. */
	@Basic(optional = false)
	@Column(name = "PAY_BONUS_ATR")
	private int payBonusAtr;

	/** The spare pay atr. */
	@Basic(optional = false)
	@Column(name = "SPARE_PAY_ATR")
	private int sparePayAtr;

	/** The processing ym. */
	@Basic(optional = false)
	@Column(name = "PROCESSING_YM")
	private int processingYm;

	/** The processing no. */
	@Basic(optional = false)
	@Column(name = "PROCESSING_NO")
	private int processingNo;

	/**
	 * Instantiates a new qctmt comment month em PK.
	 */
	public QctmtCommentMonthEmPK() {
		super();
	}

	/**
	 * Instantiates a new qctmt comment month em PK.
	 *
	 * @param ccd the ccd
	 * @param empCd the emp cd
	 * @param payBonusAtr the pay bonus atr
	 * @param sparePayAtr the spare pay atr
	 * @param processingYm the processing ym
	 * @param processingNo the processing no
	 */
	public QctmtCommentMonthEmPK(String ccd, String empCd, int payBonusAtr, int sparePayAtr,
		int processingYm, int processingNo) {
		super();
		this.ccd = ccd;
		this.empCd = empCd;
		this.payBonusAtr = payBonusAtr;
		this.sparePayAtr = sparePayAtr;
		this.processingYm = processingYm;
		this.processingNo = processingNo;
	}

	

}
