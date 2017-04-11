/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.wagetable.history;

import java.io.Serializable;
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

import lombok.Getter;
import lombok.Setter;

/**
 * The Class QwtmtWagetableMny.
 */
@Getter
@Setter
@Entity
@Table(name = "QWTMT_WAGETABLE_MNY")
public class QwtmtWagetableMny implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The qwtmt wagetable mny PK. */
	@EmbeddedId
	protected QwtmtWagetableMnyPK qwtmtWagetableMnyPK;

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

	/** The value mny. */
	@Basic(optional = false)
	@Column(name = "VALUE_MNY")
	private long valueMny;

	/** The qwtmt wagetable hist. */
	@JoinColumns({
			@JoinColumn(name = "CCD", referencedColumnName = "CCD", insertable = false, updatable = false),
			@JoinColumn(name = "WAGE_TABLE_CD", referencedColumnName = "WAGE_TABLE_CD", insertable = false, updatable = false),
			@JoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID", insertable = false, updatable = false) })
	@ManyToOne(cascade = CascadeType.ALL)
	private QwtmtWagetableHist qwtmtWagetableHist;

	/**
	 * Instantiates a new qwtmt wagetable mny.
	 */
	public QwtmtWagetableMny() {
		super();
	}

	/**
	 * Instantiates a new qwtmt wagetable mny.
	 *
	 * @param qwtmtWagetableMnyPK
	 *            the qwtmt wagetable mny PK
	 */
	public QwtmtWagetableMny(QwtmtWagetableMnyPK qwtmtWagetableMnyPK) {
		this.qwtmtWagetableMnyPK = qwtmtWagetableMnyPK;
	}

	/**
	 * Instantiates a new qwtmt wagetable mny.
	 *
	 * @param qwtmtWagetableMnyPK
	 *            the qwtmt wagetable mny PK
	 * @param exclusVer
	 *            the exclus ver
	 * @param valueMny
	 *            the value mny
	 */
	public QwtmtWagetableMny(QwtmtWagetableMnyPK qwtmtWagetableMnyPK, int exclusVer,
			long valueMny) {
		this.qwtmtWagetableMnyPK = qwtmtWagetableMnyPK;
		this.exclusVer = exclusVer;
		this.valueMny = valueMny;
	}

	/**
	 * Instantiates a new qwtmt wagetable mny.
	 *
	 * @param ccd
	 *            the ccd
	 * @param wageTableCd
	 *            the wage table cd
	 * @param histId
	 *            the hist id
	 * @param element1Id
	 *            the element 1 id
	 * @param element2Id
	 *            the element 2 id
	 * @param element3Id
	 *            the element 3 id
	 */
	public QwtmtWagetableMny(String ccd, String wageTableCd, String histId, String element1Id,
			String element2Id, String element3Id) {
		this.qwtmtWagetableMnyPK = new QwtmtWagetableMnyPK(ccd, wageTableCd, histId, element1Id,
				element2Id, element3Id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (qwtmtWagetableMnyPK != null ? qwtmtWagetableMnyPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof QwtmtWagetableMny)) {
			return false;
		}
		QwtmtWagetableMny other = (QwtmtWagetableMny) object;
		if ((this.qwtmtWagetableMnyPK == null && other.qwtmtWagetableMnyPK != null)
				|| (this.qwtmtWagetableMnyPK != null
						&& !this.qwtmtWagetableMnyPK.equals(other.qwtmtWagetableMnyPK))) {
			return false;
		}
		return true;
	}
}
