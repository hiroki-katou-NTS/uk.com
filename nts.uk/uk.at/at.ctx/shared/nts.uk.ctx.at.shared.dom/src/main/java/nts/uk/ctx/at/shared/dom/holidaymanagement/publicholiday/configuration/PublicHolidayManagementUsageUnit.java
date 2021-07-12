package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employment.SharedSidPeriodDateEmploymentImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.SharedAffWorkPlaceHisImport;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.PublicHolidayMonthSetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.company.CompanyMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.EmployeeMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employment.EmploymentMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.workplace.WorkplaceMonthDaySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;

/**
 * The Class PublicHolidayManagementUsageUnit.
 */
// 利用単位の設定
@Getter
@Setter
public class PublicHolidayManagementUsageUnit extends AggregateRoot{
	
	/** The is manage employee public hd. */
	// 社員の公休管理をする
	private int isManageEmployeePublicHd;
	
	/** The is manage wkp public hd. */
	// 職場の公休管理をする
	private int isManageWkpPublicHd;
	
	/** The is manage emp public hd. */
	// 雇用の公休管理をする
	private int isManageEmpPublicHd;
	
	/**
	 * Instantiates a new public holiday management usage unit.
	 *
	 * @param isManageEmployeePublicHd the is manage employee public hd
	 * @param isManageWkpPublicHd the is manage wkp public hd
	 * @param isManageEmpPublicHd the is manage emp public hd
	 */
	public PublicHolidayManagementUsageUnit(int isManageEmployeePublicHd, int isManageWkpPublicHd, int isManageEmpPublicHd){
		this.isManageEmployeePublicHd = isManageEmployeePublicHd;
		this.isManageWkpPublicHd = isManageWkpPublicHd;
		this.isManageEmpPublicHd = isManageEmpPublicHd;
	}
	

	/**
	 * 公休日数取得
	 * @param require
	 * @param companyID 会社ID
	 * @param employeeId　社員ID
	 * @param periodList　年月(List)
	 * @param criteriaDate　基準日
	 * @return List<PublicHolidayMonthSetting>
	 */
	public List<PublicHolidayMonthSetting> GetNumberofPublicHoliday(
			RequireM1 require, String companyID, String employeeId, List<AggregationPeriod> periodList, GeneralDate criteriaDate){
		
		List<PublicHolidayMonthSetting> publicHolidayMonthSetting = new ArrayList<>();
		List<PublicHolidayMonthSetting> empPublicHolidayMonthSetting= new ArrayList<>();
		List<PublicHolidayMonthSetting> wkpPublicHolidayMonthSetting = new ArrayList<>();
		
		//公休管理使用単位を確認する
		//雇用の公休管理するか
		if(isEmpPublicHd()){
			//雇用月間日数設定を取得する
			empPublicHolidayMonthSetting = getEmploymentMonthDaySetting(require, companyID,employeeId, periodList,criteriaDate);
		}
		//職場の公休管理するか
		if(isWkpPublicHd()){
			//職場月間日数設定を取得する
			wkpPublicHolidayMonthSetting = getWkpPublicHolidayMonthSetting(require, companyID,employeeId, periodList,criteriaDate);
		}
		
		
		//Require．会社月間日数設定を取得する
		CompanyMonthDaySetting companyMonthDaySetting = require.companyMonthDaySetting(companyID) ;
		//会社月間日数設定を取得する
		publicHolidayMonthSetting = 
				companyMonthDaySetting.getPublicHolidayMonthSetting(periodList);
	
		//会社月間日数設定を雇用月間日数設定で上書きする
		if(isEmpPublicHd()){
				updatesetting(publicHolidayMonthSetting, empPublicHolidayMonthSetting);
		}
		//会社月間日数設定を職場月間日数設定で上書きする
		if(isWkpPublicHd()){
			updatesetting(publicHolidayMonthSetting, wkpPublicHolidayMonthSetting);
		}
		
		//社員の公休管理する ＝ true
		if(isEmployeePublicHd()){
			//社員月間日数設定を取得する
			EmployeeMonthDaySetting employeeMonthDaySetting = require.employeeMonthDaySetting(companyID, employeeId) ;
			
			List<PublicHolidayMonthSetting> employeePublicHolidayMonthSetting = 
					employeeMonthDaySetting.getPublicHolidayMonthSetting(periodList);
			
			//社員の設定で上書きする
			updatesetting(publicHolidayMonthSetting, employeePublicHolidayMonthSetting);
		}
		
		return publicHolidayMonthSetting;
	}
	
	
	//雇用の公休管理をするがtureか
	private boolean isEmpPublicHd(){
		return this.isManageEmpPublicHd == 1;
	}
	//職場の公休管理をするがtureか
	private boolean isWkpPublicHd(){
		return this.isManageWkpPublicHd == 1;
	}
	// 社員の公休管理をするがtureか
	private boolean isEmployeePublicHd(){
		return this.isManageEmployeePublicHd == 1;
	}
	
	
	//雇用月間日数設定を取得する
	private List<PublicHolidayMonthSetting> getEmploymentMonthDaySetting(
			RequireM1 require, String companyID, String employeeId,	List<AggregationPeriod> periodList, GeneralDate criteriaDate){
		
		//社員と基準日から雇用履歴項目を取得する
		List<String> employeeIds = new ArrayList<>();
		employeeIds.add(employeeId);
		Optional<SharedSidPeriodDateEmploymentImport> employmentHist =
				require.getEmpHistBySidAndPeriod(employeeIds,  new DatePeriod(criteriaDate, criteriaDate))
				.stream().findFirst().map(c ->c);
		
		if(employmentHist.isPresent()){
			return new ArrayList<>();
		}
		if(employmentHist.get().getAffPeriodEmpCodeExports().isEmpty()){
			return new ArrayList<>();
		}
		
		EmploymentMonthDaySetting employmentMonthDaySetting = require.employmentMonthDaySetting(
				new CompanyId(companyID),employmentHist.get().getAffPeriodEmpCodeExports().get(0).getEmploymentCode());
		
		//期間から雇用月間日数設定を取得する
		return employmentMonthDaySetting.getPublicHolidayMonthSetting(periodList);
	}
	
	//職場月間日数設定を取得する
	private List<PublicHolidayMonthSetting> getWkpPublicHolidayMonthSetting(
			RequireM1 require, String companyID, String employeeId,	List<AggregationPeriod> periodList, GeneralDate criteriaDate){
		
		//社員と基準日から所属職場履歴項目を取得する
		Optional<SharedAffWorkPlaceHisImport> affWorkplaceHistoryItem = require.getAffWorkPlaceHis(employeeId, criteriaDate);
		
		if(!affWorkplaceHistoryItem.isPresent()){
			return new ArrayList<>();
		}
		
		WorkplaceMonthDaySetting workplaceMonthDaySetting =
				require.workplaceMonthDaySetting(new CompanyId(companyID), affWorkplaceHistoryItem.get().getWorkplaceId());
		//期間から職場月間日数設定を取得する
		return  workplaceMonthDaySetting.getPublicHolidayMonthSetting(periodList);
	}
	
	//設定の上書き
	private List<PublicHolidayMonthSetting> updatesetting(List<PublicHolidayMonthSetting> publicHolidayMonthSetting, 
			List<PublicHolidayMonthSetting> updateSettings){
		
		for(PublicHolidayMonthSetting updateSetting : updateSettings){
			publicHolidayMonthSetting
				.removeIf(x -> x.getPublicHdManagementYear().equals(updateSetting.getPublicHdManagementYear())
					&& x.getMonth().equals(updateSetting.getMonth()));
				
			publicHolidayMonthSetting.add(updateSetting);
		}
		
		return publicHolidayMonthSetting;
	}
	
	public static interface RequireM1 extends AffiliationInforOfDailyAttd.Require{
		//会社月間日数設定
		CompanyMonthDaySetting companyMonthDaySetting(String companyId);
		
		//雇用月間日数設定
		EmploymentMonthDaySetting employmentMonthDaySetting(CompanyId companyId, String employmentCode);
		
		// 職場月間日数設定
		WorkplaceMonthDaySetting workplaceMonthDaySetting(CompanyId companyId, String workplaceId);
		
		//社員月間日数設定
		EmployeeMonthDaySetting employeeMonthDaySetting(String companyId, String employeeId);

		//社員と基準日から所属職場履歴項目を取得する
		
		Optional<SharedAffWorkPlaceHisImport> getAffWorkPlaceHis(String employeeId, GeneralDate processingDate);
		
		//社員（List）と期間から雇用履歴を取得する
		List<SharedSidPeriodDateEmploymentImport> getEmpHistBySidAndPeriod(List<String> employeeID, DatePeriod Period);
	}
	
}
