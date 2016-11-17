package nts.uk.ctx.pr.proto.infra.entity.paymentdata;

import java.util.List;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import nts.arc.layer.infra.data.entity.AggregateTableEntity;

//@Entity
@Table(name = "QSTDT_PAYMENT_HEADER")
public class QstdtPaymentHeader extends AggregateTableEntity {
	
	@EmbeddedId
	public QstdtPaymentHeaderPK qstdtPaymentHeaderPK;
	
	@Column(name = "INV_SCD")
	public String employeeCode;
	
	@Column(name = "CNAME")
	public String companyName;
	
	@Column(name = "NAME_OFFICIAL")
	public String employeeName;
	
	@Basic(optional = false)
	@Column(name = "STD_DATE")
	@Temporal(TemporalType.DATE)
	public LocalDate standardDate;
	
	@Column(name = "EMPCD")
	public String employmentCode;
	
	@Column(name = "EMPNAME")
	public String employmentName;
	
	@Column(name = "DEPCD")
	public String departmentCode;
	
	@Column(name = "DEPNAME")
	public String departmentName;
	
	@Column(name = "DEP_OUTCD")
	public String externalDepartmentCode;
	
	@Column(name = "CLSCD")
	public String classificationCode;
	
	@Column(name = "CLSNAME")
	public String classificationName;
	
	@Column(name = "JOBCD")
	public String positionCode;
	
	@Column(name = "JOBNAME")
	public String positionName;
	
	@Basic(optional = false)
	@Column(name = "STMT_CD")
	public String specificationCode;
	
	@Column(name = "STMT_NAME")
	public String specificationName;
	
	@Column(name = "RESIDENCE_CD")
	public String residenceCode;
	
	@Column(name = "RESIDENCE_NAME")
	public String residenceName;
	
	@Basic(optional = false)
	@Column(name = "HEALTH_INSU_GRADE")
	public int healthInsuranceGrade;
	
	@Basic(optional = false)
	@Column(name = "HEALTH_INSU_AVG_EARN")
	public int healthInsuranceAverageEarn;
	
	@Basic(optional = false)
	@Column(name = "AGED_CONT_INSU_ATR")
	public int ageContinuationInsureAtr;
	
	@Basic(optional = false)
	@Column(name = "TENURE_ATR")
	public int tenureAtr;
	
	@Basic(optional = false)
	@Column(name = "TAX_ATR")
	public int taxAtr;
	
	@Basic(optional = false)
	@Column(name = "PENSION_INSU_GRADE")
	public int pensionInsuranceGrade;
	
	@Basic(optional = false)
	@Column(name = "PENSION_AVG_EARN")
	public int pensionAverageEarn;
	
	@Basic(optional = false)
	@Column(name = "EMPINSU_ATR")
	public int employementInsuranceAtr;
	
	@Basic(optional = false)
	@Column(name = "DEPENDENT_NUMBER")
	public int dependentNumber;
	
	@Basic(optional = false)
	@Column(name = "WORK_INS_CALC_ATR")
	public int workInsuranceCalculateAtr;
	
	@Basic(optional = false)
	@Column(name = "INSURED_ATR")
	public int insuredAtr;
	
	@Basic(optional = false)
	@Column(name = "BONUS_TAX_RATE")
	public int bonusTaxRate;
	
	@Basic(optional = false)
	@Column(name = "CALC_FLG")
	public int calcFlag;
	
	@Basic(optional = false)
	@Column(name = "MAKE_METHOD_FLG")
	public int makeMethodFlag;
	
	@Basic(optional = false)
	@Column(name = "PR_POS1_CTG_ATR")
	public String printPositionCategoryATR1;
	
	@Basic(optional = false)
	@Column(name = "PR_POS2_CTG_ATR")
	public String printPositionCategoryATR2;
	
	@Basic(optional = false)
	@Column(name = "PR_POS3_CTG_ATR")
	public String printPositionCategoryATR3;
	
	@Basic(optional = false)
	@Column(name = "PR_POS4_CTG_ATR")
	public String printPositionCategoryATR4;
	
	@Basic(optional = false)
	@Column(name = "PR_POS5_CTG_ATR")
	public String printPositionCategoryATR5;
	
	@Basic(optional = false)
	@Column(name = "PR_POS1_CTG_LINES")
	public String printPositionCategoryLines1;
	
	@Basic(optional = false)
	@Column(name = "PR_POS2_CTG_LINES")
	public String printPositionCategoryLines2;
	
	@Basic(optional = false)
	@Column(name = "PR_POS3_CTG_LINES")
	public String printPositionCategoryLines3;
	
	@Basic(optional = false)
	@Column(name = "PR_POS4_CTG_LINES")
	public String printPositionCategoryLines4;
	
	@Basic(optional = false)
	@Column(name = "PR_POS5_CTG_LINES")
	public String printPositionCategoryLines5;
	
	@Column(name = "MEMO")
	public String comment;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "qstdtPaymentHeader")
	private List<QstdtPaymentDetail> qstdtPaymentDetailCollection;
	
}
