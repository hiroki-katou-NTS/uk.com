/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.TableEntity;

/**
 * The Class ReportQpdmtPayday.
 */
@Entity
@Table(name = "QPDMT_PAYDAY")
public class ReportQpdmtPayday extends TableEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The qpdmt payday PK. */
	@EmbeddedId
	public ReportQpdmtPaydayPK qpdmtPaydayPK;

	/** The pay date. */
	@Column(name = "PAY_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate payDate;

	/** The std date. */
	@Column(name = "STD_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate stdDate;

	/** The social ins levy mon. */
	@Column(name = "SOCIAL_INS_LEVY_MON")
	public int socialInsLevyMon;

	/** The social ins std date. */
	@Column(name = "SOCIAL_INS_STD_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate socialInsStdDate;

	/** The income tax std date. */
	@Column(name = "INCOME_TAX_STD_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate incomeTaxStdDate;
	
	/** The emp ins std date. */
	@Column(name = "EMP_INS_STD_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate empInsStdDate;

	/** The stmt output mon. */
	@Column(name = "STMT_OUTPUT_MON")
	public int stmtOutputMon;

	/** The needed work day. */
	@Column(name = "NEEDED_WORK_DAY")
	public int neededWorkDay;

	/** The accounting closing. */
	@Column(name = "ACCOUNTING_CLOSING")
	public GeneralDate accountingClosing;

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((qpdmtPaydayPK == null) ? 0 : qpdmtPaydayPK.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportQpdmtPayday other = (ReportQpdmtPayday) obj;
		if (qpdmtPaydayPK == null) {
			if (other.qpdmtPaydayPK != null)
				return false;
		} else if (!qpdmtPaydayPK.equals(other.qpdmtPaydayPK))
			return false;
		return true;
	}
	
	
}
