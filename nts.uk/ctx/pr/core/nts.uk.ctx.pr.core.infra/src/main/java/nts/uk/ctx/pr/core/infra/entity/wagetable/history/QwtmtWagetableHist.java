/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.wagetable.history;

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
 * The Class QwtmtWagetableHist.
 */
@Getter
@Setter
@Entity
@Table(name = "QWTMT_WAGETABLE_HIST")
public class QwtmtWagetableHist implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The qwtmt wagetable hist PK. */
	@EmbeddedId
	protected QwtmtWagetableHistPK qwtmtWagetableHistPK;

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

	/**
	 * Instantiates a new qwtmt wagetable hist.
	 */
	public QwtmtWagetableHist() {
		super();
	}

	/**
	 * Instantiates a new qwtmt wagetable hist.
	 *
	 * @param qwtmtWagetableHistPK
	 *            the qwtmt wagetable hist PK
	 */
	public QwtmtWagetableHist(QwtmtWagetableHistPK qwtmtWagetableHistPK) {
		this.qwtmtWagetableHistPK = qwtmtWagetableHistPK;
	}

	/**
	 * Instantiates a new qwtmt wagetable hist.
	 *
	 * @param qwtmtWagetableHistPK
	 *            the qwtmt wagetable hist PK
	 * @param exclusVer
	 *            the exclus ver
	 * @param strYm
	 *            the str ym
	 * @param endYm
	 *            the end ym
	 */
	public QwtmtWagetableHist(QwtmtWagetableHistPK qwtmtWagetableHistPK, int exclusVer, int strYm, int endYm) {
		this.qwtmtWagetableHistPK = qwtmtWagetableHistPK;
		this.exclusVer = exclusVer;
		this.strYm = strYm;
		this.endYm = endYm;
	}

	/**
	 * Instantiates a new qwtmt wagetable hist.
	 *
	 * @param ccd
	 *            the ccd
	 * @param wageTableCd
	 *            the wage table cd
	 * @param histId
	 *            the hist id
	 */
	public QwtmtWagetableHist(String ccd, String wageTableCd, String histId) {
		this.qwtmtWagetableHistPK = new QwtmtWagetableHistPK(ccd, wageTableCd, histId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (qwtmtWagetableHistPK != null ? qwtmtWagetableHistPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof QwtmtWagetableHist)) {
			return false;
		}
		QwtmtWagetableHist other = (QwtmtWagetableHist) object;
		if ((this.qwtmtWagetableHistPK == null && other.qwtmtWagetableHistPK != null)
				|| (this.qwtmtWagetableHistPK != null
						&& !this.qwtmtWagetableHistPK.equals(other.qwtmtWagetableHistPK))) {
			return false;
		}
		return true;
	}
}
