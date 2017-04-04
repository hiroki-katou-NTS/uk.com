/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.wageledger.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class QlsptLedgerFormDetail.
 */
@Getter
@Setter
@Entity
@Table(name = "QLSPT_LEDGER_FORM_DETAIL")
public class ReportQlsptLedgerFormDetail implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The qlspt ledger form detail PK. */
	@EmbeddedId
	protected ReportQlsptLedgerFormDetailPK qlsptLedgerFormDetailPK;

	/** The ins date. */
	@Column(name = "INS_DATE")
	@Temporal(TemporalType.TIME)
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
	@Temporal(TemporalType.TIME)
	private Date updDate;

	/** The upd ccd. */
	@Column(name = "UPD_CCD")
	private String updCcd;

	/** The upd scd. */
	@Column(name = "UPD_SCD")
	private String updScd;

	/** The exclus ver. */
	@Basic(optional = false)
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The disp order. */
	@Basic(optional = false)
	@Column(name = "DISP_ORDER")
	private int dispOrder;

	/** The qlspt ledger form head. */
	@JoinColumns({ @JoinColumn(name = "CCD", referencedColumnName = "CCD", insertable = false, updatable = false),
			@JoinColumn(name = "FORM_CD", referencedColumnName = "FORM_CD", insertable = false, updatable = false) })
	@ManyToOne(optional = false)
	private ReportQlsptLedgerFormHead qlsptLedgerFormHead;

	/**
	 * Instantiates a new qlspt ledger form detail.
	 */
	public ReportQlsptLedgerFormDetail() {
	}

	/**
	 * Instantiates a new qlspt ledger form detail.
	 *
	 * @param qlsptLedgerFormDetailPK
	 *            the qlspt ledger form detail PK
	 */
	public ReportQlsptLedgerFormDetail(ReportQlsptLedgerFormDetailPK qlsptLedgerFormDetailPK) {
		this.qlsptLedgerFormDetailPK = qlsptLedgerFormDetailPK;
	}

	/**
	 * Instantiates a new qlspt ledger form detail.
	 *
	 * @param qlsptLedgerFormDetailPK
	 *            the qlspt ledger form detail PK
	 * @param exclusVer
	 *            the exclus ver
	 * @param dispOrder
	 *            the disp order
	 */
	public ReportQlsptLedgerFormDetail(ReportQlsptLedgerFormDetailPK qlsptLedgerFormDetailPK, int exclusVer, int dispOrder) {
		this.qlsptLedgerFormDetailPK = qlsptLedgerFormDetailPK;
		this.exclusVer = exclusVer;
		this.dispOrder = dispOrder;
	}

	/**
	 * Instantiates a new qlspt ledger form detail.
	 *
	 * @param ccd
	 *            the ccd
	 * @param formCd
	 *            the form cd
	 * @param payBonusAtr
	 *            the pay bonus atr
	 * @param ctgAtr
	 *            the ctg atr
	 * @param aggregateAtr
	 *            the aggregate atr
	 * @param itemAgreCd
	 *            the item agre cd
	 */
	public ReportQlsptLedgerFormDetail(String ccd, String formCd, int payBonusAtr, int ctgAtr, int aggregateAtr,
			String itemAgreCd) {
		this.qlsptLedgerFormDetailPK = new ReportQlsptLedgerFormDetailPK(ccd, formCd, payBonusAtr, ctgAtr, aggregateAtr,
				itemAgreCd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (qlsptLedgerFormDetailPK != null ? qlsptLedgerFormDetailPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof ReportQlsptLedgerFormDetail)) {
			return false;
		}
		ReportQlsptLedgerFormDetail other = (ReportQlsptLedgerFormDetail) object;
		if ((this.qlsptLedgerFormDetailPK == null && other.qlsptLedgerFormDetailPK != null)
				|| (this.qlsptLedgerFormDetailPK != null
						&& !this.qlsptLedgerFormDetailPK.equals(other.qlsptLedgerFormDetailPK))) {
			return false;
		}
		return true;
	}
}
