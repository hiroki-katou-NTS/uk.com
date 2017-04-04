/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;

/**
 * The Class ReportPclmtPersonEmpContract.
 */
@Entity
@Table(name="PCLMT_PERSON_EMP_CONTRACT")
public class ReportPclmtPersonEmpContract {

	/** The pclmt person emp contract PK. */
	@EmbeddedId
	public ReportPclmtPersonEmpContractPK pclmtPersonEmpContractPK;
	
	/** The end D. */
	@Column(name ="END_D")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate endD;
	
	/** The exp D. */
	@Column(name ="EXP_D")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate expD;
	
	/** The issue date. */
	@Column(name ="ISSUE_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate issueDate;
	
	/** The issue sts. */
	@Column(name ="ISSUE_STS")
	public int issueSts;
	
	/** The emp cd. */
	@Column(name ="EMPCD")
	public String empCd;
	
	/** The payroll calc atr. */
	@Column(name ="PAYROLL_CALC_ATR")
	public int payrollCalcAtr;
	
	/** The payroll system. */
	@Column(name ="PAYROLL_SYSTEM")
	public int payrollSystem;
	
	/** The labor association atr. */
	@Column(name ="LABOR_ASSOCIATION_ATR")
	public int laborAssociationAtr;
	
	/** The contract priod atr. */
	@Column(name ="CONTRACT_PERIOD_ATR")
	public int contractPriodAtr;
	
	/** The other remark. */
	@Column(name ="OTHER_REMARK")
	public String otherRemark;
	
	/** The auto contract renewal. */
	@Column(name ="AUTO_CONTRACT_RENEWAL")
	public int autoContractRenewal;
	
	/** The firing pre paydate. */
	@Column(name ="FIRING_PRE_PAYDATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate firingPrePaydate;

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pclmtPersonEmpContractPK == null) ? 0 
				: pclmtPersonEmpContractPK.hashCode());
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
		ReportPclmtPersonEmpContract other = (ReportPclmtPersonEmpContract) obj;
		if (pclmtPersonEmpContractPK == null) {
			if (other.pclmtPersonEmpContractPK != null)
				return false;
		} else if (!pclmtPersonEmpContractPK.equals(other.pclmtPersonEmpContractPK))
			return false;
		return true;
	}
}
