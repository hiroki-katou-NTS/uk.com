/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.wageledger.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class QlsptLedgerFormHead.
 */
@Getter
@Setter
@Entity
@Table(name = "QLSPT_LEDGER_FORM_HEAD")
public class ReportQlsptLedgerFormHead implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The qlspt ledger form head PK. */
	@EmbeddedId
	protected ReportQlsptLedgerFormHeadPK qlsptLedgerFormHeadPK;

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

	/** The form name. */
	@Column(name = "FORM_NAME")
	private String formName;

	/** The print 1 page by person set. */
	@Basic(optional = false)
	@Column(name = "PRINT_1PAGE_BY_PERSON_SET")
	private int print1pageByPersonSet;

	/** The qlspt ledger form detail list. */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "qlsptLedgerFormHead", fetch = FetchType.LAZY,
			orphanRemoval = true)
	private List<ReportQlsptLedgerFormDetail> qlsptLedgerFormDetailList;

	/**
	 * Instantiates a new qlspt ledger form head.
	 */
	public ReportQlsptLedgerFormHead() {
	}

	/**
	 * Instantiates a new qlspt ledger form head.
	 *
	 * @param qlsptLedgerFormHeadPK
	 *            the qlspt ledger form head PK
	 */
	public ReportQlsptLedgerFormHead(ReportQlsptLedgerFormHeadPK qlsptLedgerFormHeadPK) {
		this.qlsptLedgerFormHeadPK = qlsptLedgerFormHeadPK;
	}

	/**
	 * Instantiates a new qlspt ledger form head.
	 *
	 * @param qlsptLedgerFormHeadPK
	 *            the qlspt ledger form head PK
	 * @param exclusVer
	 *            the exclus ver
	 * @param print1pageByPersonSet
	 *            the print 1 page by person set
	 */
	public ReportQlsptLedgerFormHead(ReportQlsptLedgerFormHeadPK qlsptLedgerFormHeadPK, int exclusVer, int print1pageByPersonSet) {
		this.qlsptLedgerFormHeadPK = qlsptLedgerFormHeadPK;
		this.exclusVer = exclusVer;
		this.print1pageByPersonSet = print1pageByPersonSet;
	}

	/**
	 * Instantiates a new qlspt ledger form head.
	 *
	 * @param ccd
	 *            the ccd
	 * @param formCd
	 *            the form cd
	 */
	public ReportQlsptLedgerFormHead(String ccd, String formCd) {
		this.qlsptLedgerFormHeadPK = new ReportQlsptLedgerFormHeadPK(ccd, formCd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (qlsptLedgerFormHeadPK != null ? qlsptLedgerFormHeadPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof ReportQlsptLedgerFormHead)) {
			return false;
		}
		ReportQlsptLedgerFormHead other = (ReportQlsptLedgerFormHead) object;
		if ((this.qlsptLedgerFormHeadPK == null && other.qlsptLedgerFormHeadPK != null)
				|| (this.qlsptLedgerFormHeadPK != null
						&& !this.qlsptLedgerFormHeadPK.equals(other.qlsptLedgerFormHeadPK))) {
			return false;
		}
		return true;
	}

}
