/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.wagetable;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.infra.entity.wagetable.element.QwtmtWagetableElement;

/**
 * The Class QwtmtWagetableHead.
 */
@Getter
@Setter
@Entity
@Table(name = "QWTMT_WAGETABLE_HEAD")
public class QwtmtWagetableHead implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The qwtmt wagetable head PK. */
	@EmbeddedId
	protected QwtmtWagetableHeadPK qwtmtWagetableHeadPK;

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

	/** The wage table name. */
	@Basic(optional = false)
	@Column(name = "WAGE_TABLE_NAME")
	private String wageTableName;

	/** The demension set. */
	@Basic(optional = false)
	@Column(name = "DEMENSION_SET")
	private int demensionSet;

	/** The memo. */
	@Column(name = "MEMO")
	private String memo;

	/** The wagetable element list. */
	@OrderBy("demensionNo ASC")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "qwtmtWagetableHead")
	private List<QwtmtWagetableElement> wagetableElementList;

	/**
	 * Instantiates a new qwtmt wagetable head.
	 */
	public QwtmtWagetableHead() {
		super();
	}

	/**
	 * Instantiates a new qwtmt wagetable head.
	 *
	 * @param qwtmtWagetableHeadPK
	 *            the qwtmt wagetable head PK
	 */
	public QwtmtWagetableHead(QwtmtWagetableHeadPK qwtmtWagetableHeadPK) {
		this.qwtmtWagetableHeadPK = qwtmtWagetableHeadPK;
	}

	/**
	 * Instantiates a new qwtmt wagetable head.
	 *
	 * @param qwtmtWagetableHeadPK
	 *            the qwtmt wagetable head PK
	 * @param exclusVer
	 *            the exclus ver
	 * @param wageTableName
	 *            the wage table name
	 * @param demensionSet
	 *            the demension set
	 */
	public QwtmtWagetableHead(QwtmtWagetableHeadPK qwtmtWagetableHeadPK, int exclusVer,
			String wageTableName, short demensionSet) {
		this.qwtmtWagetableHeadPK = qwtmtWagetableHeadPK;
		this.exclusVer = exclusVer;
		this.wageTableName = wageTableName;
		this.demensionSet = demensionSet;
	}

	/**
	 * Instantiates a new qwtmt wagetable head.
	 *
	 * @param ccd
	 *            the ccd
	 * @param wageTableCd
	 *            the wage table cd
	 */
	public QwtmtWagetableHead(String ccd, String wageTableCd) {
		this.qwtmtWagetableHeadPK = new QwtmtWagetableHeadPK(ccd, wageTableCd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (qwtmtWagetableHeadPK != null ? qwtmtWagetableHeadPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof QwtmtWagetableHead)) {
			return false;
		}
		QwtmtWagetableHead other = (QwtmtWagetableHead) object;
		if ((this.qwtmtWagetableHeadPK == null && other.qwtmtWagetableHeadPK != null)
				|| (this.qwtmtWagetableHeadPK != null
						&& !this.qwtmtWagetableHeadPK.equals(other.qwtmtWagetableHeadPK))) {
			return false;
		}
		return true;
	}
}
