/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.entity.payment.contact;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class QctmtCommentMonthPsPK.
 */
@Setter
@Getter
@Embeddable
public class QctmtCommentMonthPsPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The ccd. */
	@Basic(optional = false)
	@Column(name = "CCD")
	private String ccd;

	/** The p id. */
	@Basic(optional = false)
	@Column(name = "PID")
	private String pId;

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
	 * Instantiates a new qctmt comment month ps PK.
	 */
	public QctmtCommentMonthPsPK() {
		super();
	}

	/**
	 * Instantiates a new qctmt comment month ps PK.
	 *
	 * @param ccd the ccd
	 * @param pId the id
	 * @param processingNo the processing no
	 * @param payBonusAtr the pay bonus atr
	 * @param processingYm the processing ym
	 * @param sparePayAtr the spare pay atr
	 */
	public QctmtCommentMonthPsPK(String ccd, String pId, int processingNo, int payBonusAtr, int processingYm,
			int sparePayAtr) {
		super();
		this.ccd = ccd;
		this.pId = pId;
		this.processingNo = processingNo;
		this.payBonusAtr = payBonusAtr;
		this.processingYm = processingYm;
		this.sparePayAtr = sparePayAtr;
	}

}
