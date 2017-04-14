/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.wagetable.reference;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class QwtmtWagetableRefPay.
 */
@Getter
@Setter
@Entity
@Table(name = "QWTMT_WAGETABLE_REF_PAY")
public class QwtmtWagetableRefPay implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The qwtmt wagetable ref pay PK. */
	@EmbeddedId
	protected QwtmtWagetableRefPayPK qwtmtWagetableRefPayPK;

	/** The ins date. */
	@Column(name = "INS_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date insDate;

	/** The ins ccd. */
	@Column(name = "INS_CCD")
	private String insCcd;

	/** The ins scd. */
	@Column(name = "INS_SCD")
	private String insScd;

	/** The ins pg. */
	@Column(name = "INS_PG")
	private String insPg;

	/** The upd date. */
	@Column(name = "UPD_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updDate;

	/** The upd ccd. */
	@Column(name = "UPD_CCD")
	private String updCcd;

	/** The upd scd. */
	@Column(name = "UPD_SCD")
	private String updScd;

	/** The upd pg. */
	@Column(name = "UPD_PG")
	private String updPg;

	/** The exclus ver. */
	@Basic(optional = false)
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The ref pay name. */
	@Basic(optional = false)
	@Column(name = "REF_PAY_NAME")
	private String refPayName;

	/** The wage ref ctg atr. */
	@Basic(optional = false)
	@Column(name = "WAGE_REF_CTG_ATR")
	private short wageRefCtgAtr;

	/** The wage ref item cd. */
	@Basic(optional = false)
	@Column(name = "WAGE_REF_ITEM_CD")
	private String wageRefItemCd;

	/**
	 * Instantiates a new qwtmt wagetable ref pay.
	 */
	public QwtmtWagetableRefPay() {
	}

	/**
	 * Instantiates a new qwtmt wagetable ref pay.
	 *
	 * @param qwtmtWagetableRefPayPK
	 *            the qwtmt wagetable ref pay PK
	 */
	public QwtmtWagetableRefPay(QwtmtWagetableRefPayPK qwtmtWagetableRefPayPK) {
		this.qwtmtWagetableRefPayPK = qwtmtWagetableRefPayPK;
	}

	/**
	 * Instantiates a new qwtmt wagetable ref pay.
	 *
	 * @param qwtmtWagetableRefPayPK
	 *            the qwtmt wagetable ref pay PK
	 * @param exclusVer
	 *            the exclus ver
	 * @param refPayName
	 *            the ref pay name
	 * @param wageRefCtgAtr
	 *            the wage ref ctg atr
	 * @param wageRefItemCd
	 *            the wage ref item cd
	 */
	public QwtmtWagetableRefPay(QwtmtWagetableRefPayPK qwtmtWagetableRefPayPK, int exclusVer, String refPayName,
			short wageRefCtgAtr, String wageRefItemCd) {
		this.qwtmtWagetableRefPayPK = qwtmtWagetableRefPayPK;
		this.exclusVer = exclusVer;
		this.refPayName = refPayName;
		this.wageRefCtgAtr = wageRefCtgAtr;
		this.wageRefItemCd = wageRefItemCd;
	}

	/**
	 * Instantiates a new qwtmt wagetable ref pay.
	 *
	 * @param ccd
	 *            the ccd
	 * @param refPayNo
	 *            the ref pay no
	 */
	public QwtmtWagetableRefPay(String ccd, String refPayNo) {
		this.qwtmtWagetableRefPayPK = new QwtmtWagetableRefPayPK(ccd, refPayNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (qwtmtWagetableRefPayPK != null ? qwtmtWagetableRefPayPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof QwtmtWagetableRefPay)) {
			return false;
		}
		QwtmtWagetableRefPay other = (QwtmtWagetableRefPay) object;
		if ((this.qwtmtWagetableRefPayPK == null && other.qwtmtWagetableRefPayPK != null)
				|| (this.qwtmtWagetableRefPayPK != null
						&& !this.qwtmtWagetableRefPayPK.equals(other.qwtmtWagetableRefPayPK))) {
			return false;
		}
		return true;
	}

}
