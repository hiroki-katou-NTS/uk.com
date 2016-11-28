package nts.uk.ctx.pr.proto.infra.entity.personalinfo.holiday;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.arc.layer.infra.data.entity.AggregateTableEntity;

@Entity
@Table(name="PHLDT_HOLIDAY_PAID")
public class PhldtHolidayPaid {
	
	@EmbeddedId
    public PhldtHolidayPaidPK phldtHolidayPaidPK;

	@Column(name = "BOY_DATE")
	public Date boyDate;
	
	@Column(name = "EXPIRE_DATE")
	public Date expireDate;
	
	@Column(name = "GRANT_DAYS")
	public BigDecimal grantDays;
	
	@Column(name = "GRANT_TIME")
	public BigDecimal grantTimes;
	
	@Column(name = "TAKE_DAYS")
	public BigDecimal takeDays;
	
	@Column(name = "TAKE_TIME")
	public BigDecimal takeTime;
	
	@Column(name = "DAYS_TO_TIME")
	public BigDecimal daysToTime;
	
	@Column(name = "REMAIN_DAYS")
	public BigDecimal remainDays;
	
	@Column(name = "REMAIN_TIME")
	public BigDecimal remainTime;
	
	@Column(name = "CUR_CARRY_DAYS")
	public BigDecimal curCarryDays;
	
	@Column(name = "CUR_CARRY_TIME")
	public BigDecimal curCarryTime;
	
	@Column(name = "CUR_TAKE_DAYS")
	public BigDecimal curTakeDays;
	
	@Column(name = "CUR_TAKE_TIME")
	public BigDecimal curTakeTime;
	
	@Column(name = "CUR_DAYS_TO_TIME")
	public BigDecimal curDaysToTime;
	
	@Column(name = "PAST_TAKE_DAYS")
	public BigDecimal pastTakeDays;
	
	@Column(name = "PAST_TAKE_TIME")
	public BigDecimal pastTakeTime;
	
	@Column(name = "PAST_DAYS_TO_TIME")
	public BigDecimal pastDaysToTime;
	
	@Column(name = "GRANT_ATTEND_RATE")
	public BigDecimal grantAttendRate;
	
	@Column(name = "GRANT_REGULAR_DAYS")
	public BigDecimal grantRegularDays;
	
	@Column(name = "GRANT_DEDUCT_DAYS")
	public BigDecimal grantDeductDays;
	
	@Column(name = "GRANT_WORK_DAYS")
	public BigDecimal grantWorkDays;
	
	@Column(name = "USAGE_RATE")
	public BigDecimal usageRate;
	
	@Column(name = "DISAPPEAR_FLG")
	public int disappearFlg;
}
