package nts.uk.ctx.at.shared.app.command.specialholiday;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SphdLimit;
import nts.uk.ctx.at.shared.dom.specialholiday.SubCondition;
import nts.uk.ctx.at.shared.dom.specialholiday.grantday.GrantPeriodic;
import nts.uk.ctx.at.shared.dom.specialholiday.grantday.GrantRegular;
import nts.uk.ctx.at.shared.dom.specialholiday.grantday.GrantSingle;

@Data
@AllArgsConstructor
public class AddSpecialHolidayCommand {

	/*会社ID*/
	private String companyId;

	/*特別休暇コード*/
	private int specialHolidayCode;

	/*特別休暇名称*/
	private String specialHolidayName;

	/*定期付与*/
	private int grantPeriodicCls;

	/*メモ*/
	private String memo;
	
	private List<String> workTypeList;
	
	private GrantRegularCommand regularCommand;
	
	private GrantPeriodicCommand periodicCommant;
	
	private SphdLimitCommand limitCommand;
	
	private SubConditionCommand conditionCommand;
	
	private GrantSingleCommand singleCommand;
	
	public SpecialHoliday toDomain(String companyId) {
		return SpecialHoliday.createFromJavaType(
				companyId, 
				this.specialHolidayCode,
				this.specialHolidayName, 
				this.grantPeriodicCls,
				this.memo,
				this.workTypeList,
				this.toDomainGrantRegular(companyId),
				this.toDomainGrantPeriodic(companyId),
				this.toDomainSphdLimit(companyId),
				this.toDomainSubCondition(companyId),
				this.toDomainGrantSingle(companyId));
	}
	
	private GrantRegular toDomainGrantRegular(String companyId) {
		return GrantRegular.createFromJavaType(
				companyId, 
				specialHolidayCode, 
				this.regularCommand.getGrantStartDate(), 
				this.regularCommand.getMonths(), 
				this.regularCommand.getYears(), 
				this.regularCommand.getGrantRegularMethod());
	}
	
	private GrantPeriodic toDomainGrantPeriodic(String companyId){
		return GrantPeriodic.createFromJavaType(
				companyId,
				specialHolidayCode,
				this.periodicCommant.getGrantDay(), 
				this.periodicCommant.getSplitAcquisition(), 
				this.periodicCommant.getGrantPeriodicMethod());
	}
	
	private SphdLimit toDomainSphdLimit(String companyId){
		return SphdLimit.createFromJavaType( 
				companyId,
				specialHolidayCode,
				this.limitCommand.getSpecialVacationMonths(), 
				this.limitCommand.getSpecialVacationYears(), 
				this.limitCommand.getGrantCarryForward(), 
				this.limitCommand.getLimitCarryoverDays(), 
				this.limitCommand.getSpecialVacationMethod());
	}
	
	private SubCondition toDomainSubCondition(String companyId){
		return SubCondition.createFromJavaType(companyId,
				specialHolidayCode,
				this.conditionCommand.getUseGender(),
				this.conditionCommand.getUseEmployee(), 
				this.conditionCommand.getUseCls(), 
				this.conditionCommand.getUseAge(), 
				this.conditionCommand.getGenderAtr(), 
				this.conditionCommand.getLimitAgeFrom(), 
				this.conditionCommand.getLimitAgeTo(), 
				this.conditionCommand.getAgeCriteriaAtr(), 
				this.conditionCommand.getAgeBaseYearAtr(),
				this.conditionCommand.getAgeBaseDates());
	}
	
	private GrantSingle toDomainGrantSingle(String companyId){
		return GrantSingle.createSimpleFromJavaType(companyId,
				specialHolidayCode,
				this.singleCommand.getGrantDaySingleType(), 
				this.singleCommand.getFixNumberDays(), 
				this.singleCommand.getMakeInvitation(), 
				this.singleCommand.getHolidayExclusionAtr());
	}
}
