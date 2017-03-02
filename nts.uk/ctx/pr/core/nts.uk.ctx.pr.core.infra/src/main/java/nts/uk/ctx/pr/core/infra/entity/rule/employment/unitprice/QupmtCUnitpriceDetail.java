/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.rule.employment.unitprice;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * The Class QupmtCUnitpriceDetail.
 */
@Getter
@Setter
@Entity
@Table(name = "QUPMT_C_UNITPRICE_DETAIL")
public class QupmtCUnitpriceDetail implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The qupmt C unitprice detail PK. */
	@EmbeddedId
	protected QupmtCUnitpriceDetailPK qupmtCUnitpriceDetailPK;

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

	/** The str ym. */
	@Basic(optional = false)
	@Column(name = "STR_YM")
	private int strYm;

	/** The end ym. */
	@Basic(optional = false)
	@Column(name = "END_YM")
	private int endYm;

	/** The c unitprice. */
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Basic(optional = false)
	@Column(name = "C_UNITPRICE")
	private BigDecimal cUnitprice;

	/** The fix pay set. */
	@Basic(optional = false)
	@Column(name = "FIX_PAY_SET")
	private int fixPaySet;

	/** The fix pay atr. */
	@Basic(optional = false)
	@Column(name = "FIX_PAY_ATR")
	private int fixPayAtr;

	/** The fix pay atr monthly. */
	@Basic(optional = false)
	@Column(name = "FIX_PAY_ATR_MONTHLY")
	private int fixPayAtrMonthly;

	/** The fix pay atr daymonth. */
	@Basic(optional = false)
	@Column(name = "FIX_PAY_ATR_DAYMONTH")
	private int fixPayAtrDaymonth;

	/** The fix pay atr daily. */
	@Basic(optional = false)
	@Column(name = "FIX_PAY_ATR_DAILY")
	private int fixPayAtrDaily;

	/** The fix pay atr hourly. */
	@Basic(optional = false)
	@Column(name = "FIX_PAY_ATR_HOURLY")
	private int fixPayAtrHourly;

	/** The memo. */
	@Column(name = "MEMO")
	private String memo;

	/**
	 * Instantiates a new qupmt C unitprice detail.
	 */
	public QupmtCUnitpriceDetail() {
	}

	/**
	 * Instantiates a new qupmt C unitprice detail.
	 *
	 * @param qupmtCUnitpriceDetailPK
	 *            the qupmt C unitprice detail PK
	 */
	public QupmtCUnitpriceDetail(QupmtCUnitpriceDetailPK qupmtCUnitpriceDetailPK) {
		this.qupmtCUnitpriceDetailPK = qupmtCUnitpriceDetailPK;
	}

	/**
	 * Instantiates a new qupmt C unitprice detail.
	 *
	 * @param qupmtCUnitpriceDetailPK
	 *            the qupmt C unitprice detail PK
	 * @param exclusVer
	 *            the exclus ver
	 * @param strYm
	 *            the str ym
	 * @param endYm
	 *            the end ym
	 * @param cUnitprice
	 *            the c unitprice
	 * @param fixPaySet
	 *            the fix pay set
	 * @param fixPayAtr
	 *            the fix pay atr
	 * @param fixPayAtrMonthly
	 *            the fix pay atr monthly
	 * @param fixPayAtrDaymonth
	 *            the fix pay atr daymonth
	 * @param fixPayAtrDaily
	 *            the fix pay atr daily
	 * @param fixPayAtrHourly
	 *            the fix pay atr hourly
	 */
	public QupmtCUnitpriceDetail(QupmtCUnitpriceDetailPK qupmtCUnitpriceDetailPK, int exclusVer, int strYm, int endYm,
			BigDecimal cUnitprice, short fixPaySet, short fixPayAtr, short fixPayAtrMonthly, short fixPayAtrDaymonth,
			short fixPayAtrDaily, short fixPayAtrHourly) {
		this.qupmtCUnitpriceDetailPK = qupmtCUnitpriceDetailPK;
		this.exclusVer = exclusVer;
		this.strYm = strYm;
		this.endYm = endYm;
		this.cUnitprice = cUnitprice;
		this.fixPaySet = fixPaySet;
		this.fixPayAtr = fixPayAtr;
		this.fixPayAtrMonthly = fixPayAtrMonthly;
		this.fixPayAtrDaymonth = fixPayAtrDaymonth;
		this.fixPayAtrDaily = fixPayAtrDaily;
		this.fixPayAtrHourly = fixPayAtrHourly;
	}

	/**
	 * Instantiates a new qupmt C unitprice detail.
	 *
	 * @param ccd
	 *            the ccd
	 * @param cUnitpriceCd
	 *            the c unitprice cd
	 * @param histId
	 *            the hist id
	 */
	public QupmtCUnitpriceDetail(String ccd, String cUnitpriceCd, String histId) {
		this.qupmtCUnitpriceDetailPK = new QupmtCUnitpriceDetailPK(ccd, cUnitpriceCd, histId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (qupmtCUnitpriceDetailPK != null ? qupmtCUnitpriceDetailPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof QupmtCUnitpriceDetail)) {
			return false;
		}
		QupmtCUnitpriceDetail other = (QupmtCUnitpriceDetail) object;
		if ((this.qupmtCUnitpriceDetailPK == null && other.qupmtCUnitpriceDetailPK != null)
				|| (this.qupmtCUnitpriceDetailPK != null
						&& !this.qupmtCUnitpriceDetailPK.equals(other.qupmtCUnitpriceDetailPK))) {
			return false;
		}
		return true;
	}
}
