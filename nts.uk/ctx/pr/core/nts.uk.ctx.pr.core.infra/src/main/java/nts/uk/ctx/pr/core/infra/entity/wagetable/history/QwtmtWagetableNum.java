/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.wagetable.history;

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
 * The Class QwtmtWagetableNum.
 */
@Getter
@Setter
@Entity
@Table(name = "QWTMT_WAGETABLE_NUM")
public class QwtmtWagetableNum implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The qwtmt wagetable num PK. */
	@EmbeddedId
	protected QwtmtWagetableNumPK qwtmtWagetableNumPK;

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
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	/** The element str. */
	// consider using these annotations to enforce field validation
	@Basic(optional = false)
	@Column(name = "ELEMENT_STR")
	private BigDecimal elementStr;

	/** The element end. */
	@Basic(optional = false)
	@Column(name = "ELEMENT_END")
	private BigDecimal elementEnd;

	/** The element id. */
	@Basic(optional = false)
	@Column(name = "ELEMENT_ID")
	private String elementId;

	/**
	 * Instantiates a new qwtmt wagetable num.
	 */
	public QwtmtWagetableNum() {
		super();
	}

	/**
	 * Instantiates a new qwtmt wagetable num.
	 *
	 * @param qwtmtWagetableNumPK
	 *            the qwtmt wagetable num PK
	 */
	public QwtmtWagetableNum(QwtmtWagetableNumPK qwtmtWagetableNumPK) {
		this.qwtmtWagetableNumPK = qwtmtWagetableNumPK;
	}

	/**
	 * Instantiates a new qwtmt wagetable num.
	 *
	 * @param qwtmtWagetableNumPK
	 *            the qwtmt wagetable num PK
	 * @param exclusVer
	 *            the exclus ver
	 * @param elementStr
	 *            the element str
	 * @param elementEnd
	 *            the element end
	 * @param elementId
	 *            the element id
	 */
	public QwtmtWagetableNum(QwtmtWagetableNumPK qwtmtWagetableNumPK, int exclusVer, BigDecimal elementStr,
			BigDecimal elementEnd, String elementId) {
		this.qwtmtWagetableNumPK = qwtmtWagetableNumPK;
		this.exclusVer = exclusVer;
		this.elementStr = elementStr;
		this.elementEnd = elementEnd;
		this.elementId = elementId;
	}

	/**
	 * Instantiates a new qwtmt wagetable num.
	 *
	 * @param ccd
	 *            the ccd
	 * @param wageTableCd
	 *            the wage table cd
	 * @param histId
	 *            the hist id
	 * @param demensionNo
	 *            the demension no
	 * @param elementNumNo
	 *            the element num no
	 */
	public QwtmtWagetableNum(String ccd, String wageTableCd, String histId, short demensionNo, long elementNumNo) {
		this.qwtmtWagetableNumPK = new QwtmtWagetableNumPK(ccd, wageTableCd, histId, demensionNo, elementNumNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (qwtmtWagetableNumPK != null ? qwtmtWagetableNumPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof QwtmtWagetableNum)) {
			return false;
		}
		QwtmtWagetableNum other = (QwtmtWagetableNum) object;
		if ((this.qwtmtWagetableNumPK == null && other.qwtmtWagetableNumPK != null)
				|| (this.qwtmtWagetableNumPK != null && !this.qwtmtWagetableNumPK.equals(other.qwtmtWagetableNumPK))) {
			return false;
		}
		return true;
	}
}
