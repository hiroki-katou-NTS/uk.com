/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.wagetable.history;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.infra.entity.wagetable.element.QwtmtWagetableElement;

/**
 * The Class QwtmtWagetableEleHist.
 */
@Getter
@Setter
@Entity
@Table(name = "QWTMT_WAGETABLE_ELE_HIST")
public class QwtmtWagetableEleHist implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The qwtmt wagetable ele hist PK. */
	@EmbeddedId
	protected QwtmtWagetableEleHistPK qwtmtWagetableEleHistPK;

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

	/** The demension upper limit. */
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Column(name = "DEMENSION_UPPER_LIMIT")
	private BigDecimal demensionUpperLimit;

	/** The demension lower limit. */
	@Column(name = "DEMENSION_LOWER_LIMIT")
	private BigDecimal demensionLowerLimit;

	/** The demension interval. */
	@Column(name = "DEMENSION_INTERVAL")
	private BigDecimal demensionInterval;

	/** The qwtmt wagetable element. */
	@JoinColumns({
			@JoinColumn(name = "CCD", referencedColumnName = "CCD", insertable = false, updatable = false),
			@JoinColumn(name = "WAGE_TABLE_CD", referencedColumnName = "WAGE_TABLE_CD", insertable = false, updatable = false),
			@JoinColumn(name = "DEMENSION_NO", referencedColumnName = "DEMENSION_NO", insertable = false, updatable = false) })
	@ManyToOne(optional = false)
	private QwtmtWagetableElement qwtmtWagetableElement;

	/** The qwtmt wagetable cd list. */
	@JoinColumns({
			@JoinColumn(name = "CCD", referencedColumnName = "CCD", insertable = false, updatable = false),
			@JoinColumn(name = "WAGE_TABLE_CD", referencedColumnName = "WAGE_TABLE_CD", insertable = false, updatable = false),
			@JoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID", insertable = false, updatable = false),
			@JoinColumn(name = "DEMENSION_NO", referencedColumnName = "DEMENSION_NO", insertable = false, updatable = false) })
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<QwtmtWagetableCd> qwtmtWagetableCdList;

	/** The qwtmt wagetable num list. */
	@JoinColumns({
			@JoinColumn(name = "CCD", referencedColumnName = "CCD", insertable = false, updatable = false),
			@JoinColumn(name = "WAGE_TABLE_CD", referencedColumnName = "WAGE_TABLE_CD", insertable = false, updatable = false),
			@JoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID", insertable = false, updatable = false),
			@JoinColumn(name = "DEMENSION_NO", referencedColumnName = "DEMENSION_NO", insertable = false, updatable = false) })
	@OrderBy("elementNumNo asc")
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<QwtmtWagetableNum> qwtmtWagetableNumList;

	/**
	 * Instantiates a new qwtmt wagetable ele hist.
	 */
	public QwtmtWagetableEleHist() {
		super();
	}

	/**
	 * Instantiates a new qwtmt wagetable ele hist.
	 *
	 * @param qwtmtWagetableEleHistPK
	 *            the qwtmt wagetable ele hist PK
	 */
	public QwtmtWagetableEleHist(QwtmtWagetableEleHistPK qwtmtWagetableEleHistPK) {
		this.qwtmtWagetableEleHistPK = qwtmtWagetableEleHistPK;
	}

	/**
	 * Instantiates a new qwtmt wagetable ele hist.
	 *
	 * @param ccd
	 *            the ccd
	 * @param wageTableCd
	 *            the wage table cd
	 * @param histId
	 *            the hist id
	 * @param demensionNo
	 *            the demension no
	 */
	public QwtmtWagetableEleHist(String ccd, String wageTableCd, String histId,
			Integer demensionNo) {
		this.qwtmtWagetableEleHistPK = new QwtmtWagetableEleHistPK(ccd, wageTableCd, histId,
				demensionNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (qwtmtWagetableEleHistPK != null ? qwtmtWagetableEleHistPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof QwtmtWagetableEleHist)) {
			return false;
		}
		QwtmtWagetableEleHist other = (QwtmtWagetableEleHist) object;
		if ((this.qwtmtWagetableEleHistPK == null && other.qwtmtWagetableEleHistPK != null)
				|| (this.qwtmtWagetableEleHistPK != null
						&& !this.qwtmtWagetableEleHistPK.equals(other.qwtmtWagetableEleHistPK))) {
			return false;
		}
		return true;
	}
}
