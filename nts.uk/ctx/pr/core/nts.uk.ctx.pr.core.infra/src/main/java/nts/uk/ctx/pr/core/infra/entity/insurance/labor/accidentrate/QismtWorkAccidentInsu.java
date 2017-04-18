/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.insurance.labor.accidentrate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import nts.uk.ctx.pr.core.infra.entity.insurance.labor.businesstype.QismtBusinessType;

/**
 * The Class QismtWorkAccidentInsu.
 */
@Data
@Entity
@Table(name = "QISMT_WORK_ACCIDENT_INSU")
public class QismtWorkAccidentInsu implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The qismt work accident insu PK. */
	@EmbeddedId
	protected QismtWorkAccidentInsuPK qismtWorkAccidentInsuPK;

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
	@Column(name = "EXCLUS_VER")
	private long exclusVer;

	/** The str ym. */
	@Column(name = "STR_YM")
	private int strYm;

	/** The end ym. */
	@Column(name = "END_YM")
	private int endYm;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	/** The wa insu rate. */
	// consider using these annotations to enforce field validation
	@Column(name = "WA_INSU_RATE")
	private BigDecimal waInsuRate;

	/** The wa insu round. */
	@Column(name = "WA_INSU_ROUND")
	private int waInsuRound;

	/** The qismt business type. */
	@JoinColumn(name = "CCD", referencedColumnName = "CCD", insertable = false, updatable = false)
	@ManyToOne(optional = false)
	private QismtBusinessType qismtBusinessType;

	/**
	 * Instantiates a new qismt work accident insu.
	 */
	public QismtWorkAccidentInsu() {
		super();
	}

	/**
	 * Instantiates a new qismt work accident insu.
	 *
	 * @param qismtWorkAccidentInsuPK
	 *            the qismt work accident insu PK
	 */
	public QismtWorkAccidentInsu(QismtWorkAccidentInsuPK qismtWorkAccidentInsuPK) {
		this.qismtWorkAccidentInsuPK = qismtWorkAccidentInsuPK;
	}

	/**
	 * Instantiates a new qismt work accident insu.
	 *
	 * @param ccd
	 *            the ccd
	 * @param histId
	 *            the hist id
	 * @param waInsuCd
	 *            the wa insu cd
	 */
	public QismtWorkAccidentInsu(String ccd, String histId, int waInsuCd) {
		this.qismtWorkAccidentInsuPK = new QismtWorkAccidentInsuPK(ccd, histId, waInsuCd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (qismtWorkAccidentInsuPK != null ? qismtWorkAccidentInsuPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof QismtWorkAccidentInsu)) {
			return false;
		}
		QismtWorkAccidentInsu other = (QismtWorkAccidentInsu) object;
		if ((this.qismtWorkAccidentInsuPK == null && other.qismtWorkAccidentInsuPK != null)
				|| (this.qismtWorkAccidentInsuPK != null
						&& !this.qismtWorkAccidentInsuPK.equals(other.qismtWorkAccidentInsuPK))) {
			return false;
		}
		return true;
	}

}
