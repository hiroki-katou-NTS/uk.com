package nts.uk.ctx.at.shared.app.command.specialholiday;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.app.command.specialholiday.grantcondition.SpecialLeaveRestrictionCommand;
import nts.uk.ctx.at.shared.app.command.specialholiday.grantinformation.GrantRegularCommand;
import nts.uk.ctx.at.shared.app.command.specialholiday.periodinformation.AvailabilityPeriodCommand;
import nts.uk.ctx.at.shared.app.command.specialholiday.periodinformation.GrantPeriodicCommand;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.TargetItem;
import nts.uk.ctx.at.shared.dom.specialholiday.grantcondition.AgeRange;
import nts.uk.ctx.at.shared.dom.specialholiday.grantcondition.AgeStandard;
import nts.uk.ctx.at.shared.dom.specialholiday.grantcondition.SpecialLeaveRestriction;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.FixGrantDate;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantRegular;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantTime;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.GrantPeriodic;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.SpecialVacationDeadline;
import nts.uk.shr.com.time.calendar.MonthDay;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Value
public class SpecialHolidayCommand {
	/** 会社ID */
	private String companyId;

	/** 特別休暇コード */
	private int specialHolidayCode;

	/** 特別休暇名称 */
	private String specialHolidayName;
	
	/** 付与情報 */
	private GrantRegularCommand regularCommand;
	
	/** 期限情報 */
	private GrantPeriodicCommand periodicCommand;
	
	/** 特別休暇利用条件 */
	private SpecialLeaveRestrictionCommand leaveResCommand;

	/** 対象項目 */
	private TargetItemCommand targetItemCommand;
	
	/** メモ */
	private String memo;

	public SpecialHoliday toDomain(String companyId) {
		return  SpecialHoliday.createFromJavaType(companyId, 
				this.specialHolidayCode, 
				this.specialHolidayName, 
				this.toDomainGrantRegular(companyId),
				this.toDomainGrantPeriodic(companyId),
				this.toDomainSpecLeaveRest(companyId),
				this.toDomainTargetItem(companyId),
				this.memo);
	}

	private TargetItem toDomainTargetItem(String companyId2) {
		if(this.targetItemCommand == null) {
			return null;
		}
		
		return TargetItem.createFromJavaType(this.targetItemCommand.getAbsenceFrameNo(), this.targetItemCommand.getFrameNo());
	}

	private SpecialLeaveRestriction toDomainSpecLeaveRest(String companyId2) {
		if(this.leaveResCommand == null) {
			return null;
		}
		
		return SpecialLeaveRestriction.createFromJavaType(companyId, this.specialHolidayCode,
				this.leaveResCommand.getRestrictionCls(),
				this.leaveResCommand.getAgeLimit(),
				this.leaveResCommand.getGenderRest(),
				this.leaveResCommand.getRestEmp(),
				this.leaveResCommand.getListCls(),
				this.toDomainAgeStandard(),
				this.toDomainAgeRange(),
				this.leaveResCommand.getGender(),
				this.leaveResCommand.getListEmp());
	}

	private AgeRange toDomainAgeRange() {
		if(this.leaveResCommand.getAgeRange().getAgeHigherLimit() == null || this.leaveResCommand.getAgeRange().getAgeLowerLimit() == null) {
			return null;
		}
		
		return AgeRange.createFromJavaType(this.leaveResCommand.getAgeRange().getAgeLowerLimit(), this.leaveResCommand.getAgeRange().getAgeHigherLimit());
	}

	private AgeStandard toDomainAgeStandard() {
		if(this.leaveResCommand.getAgeStandard() == null) {
			return null;
		}
		
		MonthDay ageBaseDate = new MonthDay(this.leaveResCommand.getAgeStandard().getAgeBaseDate() / 100, this.leaveResCommand.getAgeStandard().getAgeBaseDate() % 100);
		
		return AgeStandard.createFromJavaType(this.leaveResCommand.getAgeStandard().getAgeCriteriaCls(), ageBaseDate);
	}

	private GrantPeriodic toDomainGrantPeriodic(String companyId) {
		if(this.periodicCommand == null) {
			return null;
		}
		
		DatePeriod availabilityPeriod = createAvailabilityPeriod(this.periodicCommand.getAvailabilityPeriod());
		
		return GrantPeriodic.createFromJavaType(companyId, this.specialHolidayCode,
				this.periodicCommand.getTimeSpecifyMethod(),
				availabilityPeriod,
				this.toDomainSpecialVacationDeadline(),
				this.periodicCommand.getLimitCarryoverDays());
	}

	private DatePeriod createAvailabilityPeriod(AvailabilityPeriodCommand availabilityPeriod) {
		GeneralDate startDate = null;
		GeneralDate endDate = null;
		String startInput = availabilityPeriod.getStartDate();
		String endInput = availabilityPeriod.getEndDate();
		if (startInput != null) {
			startDate = GeneralDate.fromString(startInput, "yyyy/MM/dd");
		}
		if (endInput != null) {
			endDate = GeneralDate.fromString(endInput, "yyyy/MM/dd");
		}

		return new DatePeriod(startDate, endDate);
	}

	private SpecialVacationDeadline toDomainSpecialVacationDeadline() {
		if(this.periodicCommand.getExpirationDate() == null) {
			return null;
		}
		
		return SpecialVacationDeadline.createFromJavaType(this.periodicCommand.getExpirationDate().getMonths(), this.periodicCommand.getExpirationDate().getYears());
	}

	private GrantRegular toDomainGrantRegular(String companyId) {
		if(this.regularCommand == null) {
			return null;
		}
		
		return GrantRegular.createFromJavaType(companyId, this.specialHolidayCode, 
				regularCommand.getTypeTime(), 
				regularCommand.getGrantDate(), 
				regularCommand.isAllowDisappear(),
				this.toDomainGrantTime());
	}

	private GrantTime toDomainGrantTime() {
		if(this.regularCommand.getGrantTime() == null) {
			return null;
		}
		
		return GrantTime.createFromJavaType(this.toDomainFixGrantDate(), null);
	}

	private FixGrantDate toDomainFixGrantDate() {
		if(this.regularCommand.getGrantTime().getFixGrantDate() == null) {
			return null;
		}
		
		return FixGrantDate.createFromJavaType(this.regularCommand.getGrantTime().getFixGrantDate().getInterval(), 
				this.regularCommand.getGrantTime().getFixGrantDate().getGrantDays());
	}
}
