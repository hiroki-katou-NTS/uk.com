/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.rule.employment.unitprice;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

/**
 * The Class QupmtCUnitpriceHist.
 */
@Data
@Entity
@Table(name = "QUPMT_C_UNITPRICE_HIST")
public class QupmtCUnitpriceHist implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The qupmt C unitprice hist PK. */
	@EmbeddedId
	protected QupmtCUnitpriceHistPK qupmtCUnitpriceHistPK;

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
	private long exclusVer;

	/** The str ym. */
	@Basic(optional = false)
	@Column(name = "STR_YM")
	private int strYm;

	/** The end ym. */
	@Basic(optional = false)
	@Column(name = "END_YM")
	private int endYm;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	/** The budget. */
	// consider using these annotations to enforce field validation
	@Basic(optional = false)
	@Column(name = "BUDGET")
	private BigDecimal budget;

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

	/** The qupmt C unitprice head. */
	@JoinColumns({ @JoinColumn(name = "CCD", referencedColumnName = "CCD", insertable = false, updatable = false),
			@JoinColumn(name = "C_UNITPRICE_CD", referencedColumnName = "C_UNITPRICE_CD", insertable = false, updatable = false) })
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	private QupmtCUnitpriceHead qupmtCUnitpriceHead;

	/**
	 * Instantiates a new qupmt C unitprice hist.
	 */
	public QupmtCUnitpriceHist() {
	}

	/**
	 * Instantiates a new qupmt C unitprice hist.
	 *
	 * @param qupmtCUnitpriceHistPK
	 *            the qupmt C unitprice hist PK
	 */
	public QupmtCUnitpriceHist(QupmtCUnitpriceHistPK qupmtCUnitpriceHistPK) {
		this.qupmtCUnitpriceHistPK = qupmtCUnitpriceHistPK;
	}

	/**
	 * Instantiates a new qupmt C unitprice hist.
	 *
	 * @param ccd
	 *            the ccd
	 * @param cUnitpriceCd
	 *            the c unitprice cd
	 * @param histId
	 *            the hist id
	 */
	public QupmtCUnitpriceHist(String ccd, String cUnitpriceCd, String histId) {
		this.qupmtCUnitpriceHistPK = new QupmtCUnitpriceHistPK(ccd, cUnitpriceCd, histId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (qupmtCUnitpriceHistPK != null ? qupmtCUnitpriceHistPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof QupmtCUnitpriceHist)) {
			return false;
		}
		QupmtCUnitpriceHist other = (QupmtCUnitpriceHist) object;
		if ((this.qupmtCUnitpriceHistPK == null && other.qupmtCUnitpriceHistPK != null)
				|| (this.qupmtCUnitpriceHistPK != null
						&& !this.qupmtCUnitpriceHistPK.equals(other.qupmtCUnitpriceHistPK))) {
			return false;
		}
		return true;
	}

}
