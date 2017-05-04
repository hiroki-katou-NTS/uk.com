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
public class QctmtCpInitialCmtPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The ccd. */
	@Basic(optional = false)
	@Column(name = "CCD")
	private String ccd;

	/** The pay bonus atr. */
	@Basic(optional = false)
	@Column(name = "PAY_BONUS_ATR")
	public int payBonusAtr;

	/** The spare pay atr. */
	@Basic(optional = false)
	@Column(name = "SPARE_PAY_ATR")
	public int sparePayAtr;

	/**
	 * Instantiates a new qcmt comment month cp PK.
	 */
	public QctmtCpInitialCmtPK() {
		super();
	}

	/**
	 * Instantiates a new qctmt cp initial cmt PK.
	 *
	 * @param ccd the ccd
	 * @param payBonusAtr the pay bonus atr
	 * @param sparePayAtr the spare pay atr
	 */
	public QctmtCpInitialCmtPK(String ccd, int payBonusAtr, int sparePayAtr) {
		super();
		this.ccd = ccd;
		this.payBonusAtr = payBonusAtr;
		this.sparePayAtr = sparePayAtr;
	}

}
