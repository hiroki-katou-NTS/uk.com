/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;

/**
 * The Class ReportQstdtPaymentHeader.
 */
@Setter
@Getter
@Entity
@Table(name = "QSTDT_PAYMENT_HEADER")
public class ReportQstdtPaymentHeader {
	
	/** The qstdt payment header PK. */
	@EmbeddedId
	public ReportQstdtPaymentHeaderPK qstdtPaymentHeaderPK;
	
	/** The employee code. */
	@Column(name = "INV_SCD")
	public String employeeCode;
	
	/** The company name. */
	@Column(name = "CNAME")
	public String companyName;
	
	/** The employee name. */
	@Column(name = "NAME_OFFICIAL")
	public String employeeName;
	
	/** The standard date. */
	@Basic(optional = false)
	@Column(name = "STD_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate standardDate;
	
	/** The employment code. */
	@Column(name = "EMPCD")
	public String employmentCode;
	
	/** The employment name. */
	@Column(name = "EMPNAME")
	public String employmentName;
	
	/** The department code. */
	@Column(name = "DEPCD")
	public String departmentCode;
	
	/** The department name. */
	@Column(name = "DEPNAME")
	public String departmentName;
	
	/** The external department code. */
	@Column(name = "DEP_OUTCD")
	public String externalDepartmentCode;
	
	/** The classification code. */
	@Column(name = "CLSCD")
	public String classificationCode;
	
	/** The classification name. */
	@Column(name = "CLSNAME")
	public String classificationName;
	
	/** The position code. */
	@Column(name = "JOBCD")
	public String positionCode;
	
	/** The position name. */
	@Column(name = "JOBNAME")
	public String positionName;
	
	/** The specification code. */
	@Basic(optional = false)
	@Column(name = "STMT_CD")
	public String specificationCode;
	
	/** The specification name. */
	@Column(name = "STMT_NAME")
	public String specificationName;
	
	/** The residence code. */
	@Column(name = "RESIDENCE_CD")
	public String residenceCode;
	
	/** The residence name. */
	@Column(name = "RESIDENCE_NAME")
	public String residenceName;
	
	/** The health insurance grade. */
	@Basic(optional = false)
	@Column(name = "HEALTH_INSU_GRADE")
	public int healthInsuranceGrade;
	
	/** The health insurance average earn. */
	@Basic(optional = false)
	@Column(name = "HEALTH_INSU_AVG_EARN")
	public int healthInsuranceAverageEarn;
	
	/** The age continuation insure atr. */
	@Basic(optional = false)
	@Column(name = "AGED_CONT_INSU_ATR")
	public int ageContinuationInsureAtr;
	
	/** The tenure atr. */
	@Basic(optional = false)
	@Column(name = "TENURE_ATR")
	public int tenureAtr;
	
	/** The tax atr. */
	@Basic(optional = false)
	@Column(name = "TAX_ATR")
	public int taxAtr;
	
	/** The pension insurance grade. */
	@Basic(optional = false)
	@Column(name = "PENSION_INSU_GRADE")
	public int pensionInsuranceGrade;
	
	/** The pension average earn. */
	@Basic(optional = false)
	@Column(name = "PENSION_AVG_EARN")
	public int pensionAverageEarn;
	
	/** The employment insurance atr. */
	@Basic(optional = false)
	@Column(name = "EMPINSU_ATR")
	public int employmentInsuranceAtr;
	
	/** The dependent number. */
	@Basic(optional = false)
	@Column(name = "DEPENDENT_NUMBER")
	public int dependentNumber;
	
	/** The work insurance calculate atr. */
	@Basic(optional = false)
	@Column(name = "WORK_INS_CALC_ATR")
	public int workInsuranceCalculateAtr;
	
	/** The insured atr. */
	@Basic(optional = false)
	@Column(name = "INSURED_ATR")
	public int insuredAtr;
	
	/** The bonus tax rate. */
	@Basic(optional = false)
	@Column(name = "BONUS_TAX_RATE")
	public int bonusTaxRate;
	
	/** The calc flag. */
	@Basic(optional = false)
	@Column(name = "CALC_FLG")
	public int calcFlag;
	
	/** The make method flag. */
	@Basic(optional = false)
	@Column(name = "MAKE_METHOD_FLG")
	public int makeMethodFlag;
	
	/** The print position category ATR 1. */
	@Basic(optional = false)
	@Column(name = "PR_POS1_CTG_ATR")
	public int printPositionCategoryATR1;
	
	/** The print position category ATR 2. */
	@Basic(optional = false)
	@Column(name = "PR_POS2_CTG_ATR")
	public int printPositionCategoryATR2;
	
	/** The print position category ATR 3. */
	@Basic(optional = false)
	@Column(name = "PR_POS3_CTG_ATR")
	public int printPositionCategoryATR3;
	
	/** The print position category ATR 4. */
	@Basic(optional = false)
	@Column(name = "PR_POS4_CTG_ATR")
	public int printPositionCategoryATR4;
	
	/** The print position category ATR 5. */
	@Basic(optional = false)
	@Column(name = "PR_POS5_CTG_ATR")
	public int printPositionCategoryATR5;
	
	/** The print position category lines 1. */
	@Basic(optional = false)
	@Column(name = "PR_POS1_CTG_LINES")
	public int printPositionCategoryLines1;
	
	/** The print position category lines 2. */
	@Basic(optional = false)
	@Column(name = "PR_POS2_CTG_LINES")
	public int printPositionCategoryLines2;
	
	/** The print position category lines 3. */
	@Basic(optional = false)
	@Column(name = "PR_POS3_CTG_LINES")
	public int printPositionCategoryLines3;
	
	/** The print position category lines 4. */
	@Basic(optional = false)
	@Column(name = "PR_POS4_CTG_LINES")
	public int printPositionCategoryLines4;
	
	/** The print position category lines 5. */
	@Basic(optional = false)
	@Column(name = "PR_POS5_CTG_LINES")
	public int printPositionCategoryLines5;
	
	/** The comment. */
	@Column(name = "MEMO")
	public String comment;

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ageContinuationInsureAtr;
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
		ReportQstdtPaymentHeader other = (ReportQstdtPaymentHeader) obj;
		if (ageContinuationInsureAtr != other.ageContinuationInsureAtr)
			return false;
		return true;
	}
}
