package nts.uk.ctx.at.shared.infra.entity.remainingnumber.annlea;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KRCMT_HDPAID_BASIC")
public class KrcmtAnnLeaBasicInfo extends ContractUkJpaEntity{
	
	@Id
    @Column(name = "SID")
    public String sid;
	
	@Column(name = "CID")
    public String cid;
	
	@Column(name = "WORK_DAYS_PER_YEAR")
    public Integer workDaysPerYear;
	
	@Column(name = "WORK_DAYS_BEFORE_INTRO")
    public Integer workDaysBeforeIntro;
	
	@Column(name = "GRANT_TABLE_CODE")
    public String grantTableCode;
	
	@Column(name = "GRANT_STANDARD_DATE")
    public GeneralDate grantStandardDate;

	/**
	 * @param employeeId
	 * @param workingDaysPerYear
	 */
	

	@Override
	protected Object getKey() {
		return sid;
	}

	/**
	 * @return
	 */
	public AnnualLeaveEmpBasicInfo toDomain() {
		return AnnualLeaveEmpBasicInfo.createFromJavaType(this.sid, this.workDaysPerYear, this.workDaysBeforeIntro, this.grantTableCode, this.grantStandardDate);
	}
	
	
}
