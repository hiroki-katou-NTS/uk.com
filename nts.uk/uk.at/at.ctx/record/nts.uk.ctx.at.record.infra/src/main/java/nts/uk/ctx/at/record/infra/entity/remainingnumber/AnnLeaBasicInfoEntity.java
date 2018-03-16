package nts.uk.ctx.at.record.infra.entity.remainingnumber;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Table(name = "KRCMT_ANNLEA_BASIC_INFO")
public class AnnLeaBasicInfoEntity extends UkJpaEntity{
	
	@Id
    @Column(name = "EMPLOYEE_ID")
    public String employeeId;
	
	@Column(name = "WORK_DAYS_PER_YEAR")
    public double workDaysPerYear;
	
	@Column(name = "WORK_DAYS_BEFORE_INTRO")
    public double workDaysBeforeIntro;
	
	@Column(name = "GRANT_TABLE_CODE")
    public String grantTableCode;
	
	@Column(name = "GRANT_STANDARD_DATE")
    public GeneralDate grantStandardDate;

	@Override
	protected Object getKey() {
		return employeeId;
	}
}
