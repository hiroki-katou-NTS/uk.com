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
 * The Class QwtmtWagetableCd.
 */
@Getter
@Setter
@Entity
@Table(name = "QWTMT_WAGETABLE_CD")
public class QwtmtWagetableCd implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The qwtmt wagetable cd PK. */
	@EmbeddedId
	protected QwtmtWagetableCdPK qwtmtWagetableCdPK;

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

	/** The element id. */
	@Basic(optional = false)
	@Column(name = "ELEMENT_ID")
	private String elementId;

	/**
	 * Instantiates a new qwtmt wagetable cd.
	 */
	public QwtmtWagetableCd() {
	}

	/**
	 * Instantiates a new qwtmt wagetable cd.
	 *
	 * @param qwtmtWagetableCdPK
	 *            the qwtmt wagetable cd PK
	 */
	public QwtmtWagetableCd(QwtmtWagetableCdPK qwtmtWagetableCdPK) {
		this.qwtmtWagetableCdPK = qwtmtWagetableCdPK;
	}

	/**
	 * Instantiates a new qwtmt wagetable cd.
	 *
	 * @param qwtmtWagetableCdPK
	 *            the qwtmt wagetable cd PK
	 * @param exclusVer
	 *            the exclus ver
	 * @param elementId
	 *            the element id
	 */
	public QwtmtWagetableCd(QwtmtWagetableCdPK qwtmtWagetableCdPK, int exclusVer, String elementId) {
		this.qwtmtWagetableCdPK = qwtmtWagetableCdPK;
		this.exclusVer = exclusVer;
		this.elementId = elementId;
	}

	/**
	 * Instantiates a new qwtmt wagetable cd.
	 *
	 * @param ccd
	 *            the ccd
	 * @param wageTableCd
	 *            the wage table cd
	 * @param histId
	 *            the hist id
	 * @param demensionNo
	 *            the demension no
	 * @param elementCd
	 *            the element cd
	 */
	public QwtmtWagetableCd(String ccd, String wageTableCd, String histId, short demensionNo, String elementCd) {
		this.qwtmtWagetableCdPK = new QwtmtWagetableCdPK(ccd, wageTableCd, histId, demensionNo, elementCd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (qwtmtWagetableCdPK != null ? qwtmtWagetableCdPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof QwtmtWagetableCd)) {
			return false;
		}
		QwtmtWagetableCd other = (QwtmtWagetableCd) object;
		if ((this.qwtmtWagetableCdPK == null && other.qwtmtWagetableCdPK != null)
				|| (this.qwtmtWagetableCdPK != null && !this.qwtmtWagetableCdPK.equals(other.qwtmtWagetableCdPK))) {
			return false;
		}
		return true;
	}
}
