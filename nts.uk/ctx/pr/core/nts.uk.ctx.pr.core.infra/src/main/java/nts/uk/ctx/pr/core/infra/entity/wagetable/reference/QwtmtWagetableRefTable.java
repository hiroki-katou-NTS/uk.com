/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.wagetable.reference;

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
 * The Class QwtmtWagetableRefTable.
 */
@Getter
@Setter
@Entity
@Table(name = "QWTMT_WAGETABLE_REF_TABLE")
public class QwtmtWagetableRefTable implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The qwtmt wagetable ref table PK. */
	@EmbeddedId
	protected QwtmtWagetableRefTablePK qwtmtWagetableRefTablePK;

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

	/** The ref table name. */
	@Basic(optional = false)
	@Column(name = "REF_TABLE_NAME")
	private String refTableName;

	/** The wage ref table. */
	@Basic(optional = false)
	@Column(name = "WAGE_REF_TABLE")
	private String wageRefTable;

	/** The wage ref field. */
	@Basic(optional = false)
	@Column(name = "WAGE_REF_FIELD")
	private String wageRefField;

	/** The wage ref disp field. */
	@Basic(optional = false)
	@Column(name = "WAGE_REF_DISP_FIELD")
	private String wageRefDispField;

	/** The wage person table. */
	@Basic(optional = false)
	@Column(name = "WAGE_PERSON_TABLE")
	private String wagePersonTable;

	/** The wage person field. */
	@Basic(optional = false)
	@Column(name = "WAGE_PERSON_FIELD")
	private String wagePersonField;

	/** The wage ref query. */
	@Column(name = "WAGE_REF_QUERY")
	private String wageRefQuery;

	/** The wage person query. */
	@Column(name = "WAGE_PERSON_QUERY")
	private String wagePersonQuery;

	/**
	 * Instantiates a new qwtmt wagetable ref table.
	 */
	public QwtmtWagetableRefTable() {
		super();
	}

	/**
	 * Instantiates a new qwtmt wagetable ref table.
	 *
	 * @param qwtmtWagetableRefTablePK
	 *            the qwtmt wagetable ref table PK
	 */
	public QwtmtWagetableRefTable(QwtmtWagetableRefTablePK qwtmtWagetableRefTablePK) {
		this.qwtmtWagetableRefTablePK = qwtmtWagetableRefTablePK;
	}

	/**
	 * Instantiates a new qwtmt wagetable ref table.
	 *
	 * @param qwtmtWagetableRefTablePK
	 *            the qwtmt wagetable ref table PK
	 * @param exclusVer
	 *            the exclus ver
	 * @param refTableName
	 *            the ref table name
	 * @param wageRefTable
	 *            the wage ref table
	 * @param wageRefField
	 *            the wage ref field
	 * @param wageRefDispField
	 *            the wage ref disp field
	 * @param wagePersonTable
	 *            the wage person table
	 * @param wagePersonField
	 *            the wage person field
	 */
	public QwtmtWagetableRefTable(QwtmtWagetableRefTablePK qwtmtWagetableRefTablePK, int exclusVer, String refTableName,
			String wageRefTable, String wageRefField, String wageRefDispField, String wagePersonTable,
			String wagePersonField) {
		this.qwtmtWagetableRefTablePK = qwtmtWagetableRefTablePK;
		this.exclusVer = exclusVer;
		this.refTableName = refTableName;
		this.wageRefTable = wageRefTable;
		this.wageRefField = wageRefField;
		this.wageRefDispField = wageRefDispField;
		this.wagePersonTable = wagePersonTable;
		this.wagePersonField = wagePersonField;
	}

	/**
	 * Instantiates a new qwtmt wagetable ref table.
	 *
	 * @param ccd
	 *            the ccd
	 * @param refTableNo
	 *            the ref table no
	 */
	public QwtmtWagetableRefTable(String ccd, String refTableNo) {
		this.qwtmtWagetableRefTablePK = new QwtmtWagetableRefTablePK(ccd, refTableNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (qwtmtWagetableRefTablePK != null ? qwtmtWagetableRefTablePK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof QwtmtWagetableRefTable)) {
			return false;
		}
		QwtmtWagetableRefTable other = (QwtmtWagetableRefTable) object;
		if ((this.qwtmtWagetableRefTablePK == null && other.qwtmtWagetableRefTablePK != null)
				|| (this.qwtmtWagetableRefTablePK != null
						&& !this.qwtmtWagetableRefTablePK.equals(other.qwtmtWagetableRefTablePK))) {
			return false;
		}
		return true;
	}

}
