/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class ReportCmnmtEmp.
 */
@Entity
@Table(name="CMNMT_EMP")
@Getter
@Setter
public class ReportCmnmtEmp implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The cmnmt emp pk. */
	@EmbeddedId
	public ReportCmnmtEmpPK cmnmtEmpPk;
	
	/** The employment name. */
	@Column(name = "EMPNAME")
	public String employmentName;
	
	/** The close date no. */
	@Column(name = "CLOSE_DATE_NO")
	public int closeDateNo;
	
	/** The memo. */
	@Column(name = "MEMO")
	public String memo;
	
	/** The processing no. */
	@Column(name = "PROCESSING_NO")
	public int processingNo;
	
	/** The statutory holiday atr. */
	@Column(name = "STATUTORY_HOLIDAY_ATR")
	public int statutoryHolidayAtr;
	
	/** The employement out cd. */
	@Column(name = "EMP_OUT_CD")
	public String employementOutCd;
	
	/** The display flg. */
	@Column(name = "INIT_SELECT_SET")
	public int displayFlg;

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cmnmtEmpPk == null) ? 0 : cmnmtEmpPk.hashCode());
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
		ReportCmnmtEmp other = (ReportCmnmtEmp) obj;
		if (cmnmtEmpPk == null) {
			if (other.cmnmtEmpPk != null)
				return false;
		} else if (!cmnmtEmpPk.equals(other.cmnmtEmpPk))
			return false;
		return true;
	}
	
}
