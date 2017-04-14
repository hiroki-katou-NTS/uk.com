/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.entity.salarydetail;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class QlsptPaylstPrintSet.
 */
@Getter
@Setter
@Entity
@Table(name = "QLSPT_PAYLST_PRINT_SET")
public class QlsptPaylstPrintSet implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

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
	private int exclusVer;

	/** The ccd. */
	@Id
	@Column(name = "CCD")
	private String ccd;

	/** The show payment. */
	@Column(name = "SHOW_PAYMENT")
	private short showPayment;

	/** The sum person set. */
	@Column(name = "SUM_PERSON_SET")
	private short sumPersonSet;

	/** The sum month person set. */
	@Column(name = "SUM_MONTH_PERSON_SET")
	private short sumMonthPersonSet;

	/** The sum each deprt set. */
	@Column(name = "SUM_EACH_DEPRT_SET")
	private short sumEachDeprtSet;

	/** The sum month deprt set. */
	@Column(name = "SUM_MONTH_DEPRT_SET")
	private short sumMonthDeprtSet;

	/** The sum dep hrchy index set. */
	@Column(name = "SUM_DEP_HRCHY_INDEX_SET")
	private short sumDepHrchyIndexSet;

	/** The sum month dep hrchy set. */
	@Column(name = "SUM_MONTH_DEP_HRCHY_SET")
	private short sumMonthDepHrchySet;

	/** The hrchy index 1. */
	@Column(name = "HRCHY_INDEX1")
	private short hrchyIndex1;

	/** The hrchy index 2. */
	@Column(name = "HRCHY_INDEX2")
	private short hrchyIndex2;

	/** The hrchy index 3. */
	@Column(name = "HRCHY_INDEX3")
	private short hrchyIndex3;

	/** The hrchy index 4. */
	@Column(name = "HRCHY_INDEX4")
	private short hrchyIndex4;

	/** The hrchy index 5. */
	@Column(name = "HRCHY_INDEX5")
	private short hrchyIndex5;

	/** The total set. */
	@Column(name = "TOTAL_SET")
	private short totalSet;

	/** The month total set. */
	@Column(name = "MONTH_TOTAL_SET")
	private short monthTotalSet;

	/**
	 * Instantiates a new qlspt paylst print set.
	 */
	public QlsptPaylstPrintSet() {
		super();
	}

	/**
	 * Instantiates a new qlspt paylst print set.
	 *
	 * @param ccd
	 *            the ccd
	 */
	public QlsptPaylstPrintSet(String ccd) {
		super();
		this.ccd = ccd;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (ccd != null ? ccd.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof QlsptPaylstPrintSet)) {
			return false;
		}
		QlsptPaylstPrintSet other = (QlsptPaylstPrintSet) object;
		if ((this.ccd == null && other.ccd != null)
				|| (this.ccd != null && !this.ccd.equals(other.ccd))) {
			return false;
		}
		return true;
	}

}
