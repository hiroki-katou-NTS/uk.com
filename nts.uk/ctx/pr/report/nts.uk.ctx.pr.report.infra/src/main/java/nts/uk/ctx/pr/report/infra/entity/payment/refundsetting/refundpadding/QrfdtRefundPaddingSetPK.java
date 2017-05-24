/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.entity.payment.refundsetting.refundpadding;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class QrfdtRefundPaddingSetPK.
 */
@Getter
@Setter
@Embeddable
public class QrfdtRefundPaddingSetPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The ccd. */
	@Column(name = "CCD")
	private String ccd;

	/** The print type. */
	@Column(name = "PRINT_TYPE")
	private int printType;

	/**
	 * Instantiates a new qrfdt refund padding set PK.
	 */
	public QrfdtRefundPaddingSetPK() {
	}

	/**
	 * Instantiates a new qrfdt refund padding set PK.
	 *
	 * @param ccd the ccd
	 * @param printType the print type
	 */
	public QrfdtRefundPaddingSetPK(String ccd, short printType) {
		this.ccd = ccd;
		this.printType = printType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (ccd != null ? ccd.hashCode() : 0);
		hash += (int) printType;
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof QrfdtRefundPaddingSetPK)) {
			return false;
		}
		QrfdtRefundPaddingSetPK other = (QrfdtRefundPaddingSetPK) object;
		if ((this.ccd == null && other.ccd != null)
				|| (this.ccd != null && !this.ccd.equals(other.ccd))) {
			return false;
		}
		if (this.printType != other.printType) {
			return false;
		}
		return true;
	}
}
