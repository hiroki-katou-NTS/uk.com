package nts.uk.ctx.at.record.infra.entity.remainingnumber;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Table;

import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Table(name = "KRCMT_SPECIAL_LEAVE_REAM")
public class KrcmtSpecialLeaveReam extends UkJpaEntity{
	
	@Embedded
	public KrcmtSpecialLeaveReamPK key;
	
	@Column(name = "SID")
	public String employeeId;

	@Column(name = "SPECIAL_LEAVE_CD")
	public int specialLeaCode;
	
	@Column(name = "GRANT_DATE")
    public GeneralDate grantDate;
	
	@Column(name = "DEADLINE_DATE")
    public GeneralDate deadlineDate;

	@Column(name = "EXPIRED_STATE")
    public int expStatus;
	
	@Column(name = "REGISTRATION_TYPE")
    public int registerType;
	
	@Column(name = "NUMBER_OF_DAYS_GRANT")
    public int numberOfDayGrant;
	
	@Column(name = "TIME_GRANT")
    public int timeGrant;
	
	@Column(name = "NUMBER_OF_DAYS_REMAIN")
    public double numberOfDayRemain;
	
	@Column(name = "TIME_REMAIN")
    public int timeRemain;
	
	@Column(name = "NUMBER_OF_DAYS_USE")
    public double numberOfDayUse;

	@Column(name = "TIME_USE")
    public int timeUse;
	
	@Column(name = "NUMBER_OF_DAYS_USE_LOSE")
    public double numberOfDayUseToLose;
	
	@Column(name = "NUMBER_OF_EXCEEDED_DAYS")
    public int numberOfExceededDays;

	@Column(name = "TIME_EXCEEDED")
    public int timeExceeded;
	
	@Override
	protected Object getKey() {
		return key;
	}
	
	

}
