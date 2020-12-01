package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten;

import java.util.Optional;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.MaxDayReference;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmptYearlyRetentionSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacation;

public class AbsenceTenProcess {
	
	/**
	 * 10-1.年休の設定を取得する
	 * @param companyID
	 * @return
	 */
	public static AnnualHolidaySetOutput getSettingForAnnualHoliday(RequireM1 require, String companyID){
		AnnualHolidaySetOutput annualHoliday = new AnnualHolidaySetOutput();
		// ドメインモデル「年休設定」を取得する(lấy dữ liệu domain 「年休設定」)
		AnnualPaidLeaveSetting annualPaidLeave = require.annualPaidLeaveSetting(companyID);
		if(annualPaidLeave == null){
			return new AnnualHolidaySetOutput(false,false,0,false,false,0,0);
		}
		if(annualPaidLeave.getYearManageType() != null && annualPaidLeave.getYearManageType().equals(ManageDistinct.NO)){
			// ドメインモデル「年休設定」．年休管理区分 = 管理しない
						annualHoliday.setYearHolidayManagerFlg(false);
						annualHoliday.setHalfManageCategoryFlg(false);
						annualHoliday.setHalfHolidayLitmit(0);
						annualHoliday.setSuspensionTimeYearFlg(false);
						annualHoliday.setHoursHolidayType(false);
						annualHoliday.setHoursHolidayLimit(0);
						annualHoliday.setTimeYearRest(1);
						return annualHoliday;
		}
		if(annualPaidLeave.getYearManageType() != null && annualPaidLeave.getYearManageType().equals(ManageDistinct.YES)){
			// ドメインモデル「年休設定」．年休管理区分 = 管理する
			annualHoliday.setYearHolidayManagerFlg(true);
			// ドメインモデル「半日年休管理」．管理区分をチェックする(kiểm tra domain 「半日年休管理」．管理区分)
			if(annualPaidLeave.getManageAnnualSetting().getHalfDayManage().manageType.equals(ManageDistinct.YES)){
				annualHoliday.setHalfManageCategoryFlg(true);
				// ドメインモデル「半日年休管理」．参照先をチェックする(kiểm tra domain 「半日年休管理」．参照先)
				if(annualPaidLeave.getManageAnnualSetting().getHalfDayManage().reference.equals(MaxDayReference.CompanyUniform)){
					// ドメインモデル「半日年休管理」．参照先 = 会社一律
					if(annualPaidLeave.getManageAnnualSetting().getHalfDayManage().maxNumberUniformCompany != null){
						annualHoliday.setHalfHolidayLitmit(annualPaidLeave.getManageAnnualSetting().getHalfDayManage().maxNumberUniformCompany.v());
					}
				}else{
					// ドメインモデル「半日年休管理」．参照先 = 年休付与テーブルを参照 : TODO
				}
			}else{
				annualHoliday.setHalfManageCategoryFlg(false);
				annualHoliday.setHalfHolidayLitmit(0);
			}
			// ドメインモデル「時間年休管理設定」．時間年休管理区分をチェックする(kiểm tra domain「時間年休管理設定」．時間年休管理区分)
			if(annualPaidLeave.getTimeSetting().getTimeManageType().equals(ManageDistinct.NO)){
				// ドメインモデル「時間年休管理設定」．時間年休管理区分 = 管理しない
				annualHoliday.setSuspensionTimeYearFlg(false);
				annualHoliday.setHoursHolidayLimit(0);
				annualHoliday.setTimeYearRest(1);
			}else{
				// ドメインモデル「時間年休管理設定」．時間年休管理区分 = 管理する
				annualHoliday.setSuspensionTimeYearFlg(true);
				annualHoliday.setTimeYearRest(annualPaidLeave.getTimeSetting().getTimeUnit().value);
				// ドメインモデル「時間年休の上限日数」．参照先をチェックする(kiêm tra domain 「時間年休の上限日数」．参照先)
				if(annualPaidLeave.getTimeSetting().getMaxYearDayLeave().reference.equals(MaxDayReference.CompanyUniform)){
					// ドメインモデル「時間年休の上限日数」．参照先 = 会社一律
					annualHoliday.setHoursHolidayLimit(annualPaidLeave.getTimeSetting().getMaxYearDayLeave().maxNumberUniformCompany.v());
				}else{
					// ドメインモデル「時間年休の上限日数」．参照先 = 年休付与テーブルを参照: TODO
				}
				
			}
		}
		return annualHoliday;
	}

	/**
	 * 10-2.代休の設定を取得する
	 * @param companyID
	 * @param employeeID
	 * @param baseDate
	 * @return
	 */
	public static SubstitutionHolidayOutput getSettingForSubstituteHoliday(RequireM4 require, CacheCarrier cacheCarrier, 
			String companyID, String employeeID, GeneralDate baseDate) {
		SubstitutionHolidayOutput result = new SubstitutionHolidayOutput();
		// アルゴリズム「社員所属雇用履歴を取得」を実行する(thực hiện xử lý 「社員所属雇用履歴を取得」)
		Optional<BsEmploymentHistoryImport> empHistImport = require.employmentHistory(cacheCarrier, companyID, employeeID, baseDate);
		if(!empHistImport.isPresent() || empHistImport.get().getEmploymentCode()==null){
			return null;
		}
		int isManageByTime = 0;
		int digestiveUnit = 0;
		// ドメインモデル「雇用の代休管理設定」を取得する(lấy domain 「雇用の代休管理設定」)
		CompensatoryLeaveEmSetting compensatoryLeaveEmSet = require.compensatoryLeaveEmSetting(companyID, empHistImport.get().getEmploymentCode());
		if(compensatoryLeaveEmSet != null){
			if (compensatoryLeaveEmSet.getIsManaged() != null) {
				// １件以上取得できた(lấy được 1 data trở lên)
				if (compensatoryLeaveEmSet.getIsManaged().equals(ManageDistinct.YES)) {
					// ドメインモデル「雇用の代休管理設定」．管理区分 = 管理する
					result.setSubstitutionFlg(true);
					result.setExpirationOfsubstiHoliday(
							compensatoryLeaveEmSet.getCompensatoryAcquisitionUse().getExpirationTime().value);
					// add refactor RequestList203
					result.setAdvancePayment(compensatoryLeaveEmSet.getCompensatoryAcquisitionUse().getPreemptionPermit().value);
					isManageByTime = compensatoryLeaveEmSet.getCompensatoryDigestiveTimeUnit()
							.getIsManageByTime().value;
					digestiveUnit = compensatoryLeaveEmSet.getCompensatoryDigestiveTimeUnit().getDigestiveUnit().value;
				} else {
					// ドメインモデル「雇用の代休管理設定」．管理区分 = 管理しない
					result.setSubstitutionFlg(false);
					result.setTimeOfPeriodFlg(false);
				}
			}
		}else{
			// ０件(0 data), ドメインモデル「代休管理設定」を取得する(lấy dữ liệu domain 「代休管理設定」)
			CompensatoryLeaveComSetting compensatoryLeaveComSet = require.compensatoryLeaveComSetting(companyID);
			if(compensatoryLeaveComSet != null){
				if (compensatoryLeaveComSet.isManaged()) {
					// ドメインモデル「代休管理設定」．管理区分 = 管理する
					result.setSubstitutionFlg(true);
					result.setExpirationOfsubstiHoliday(
							compensatoryLeaveComSet.getCompensatoryAcquisitionUse().getExpirationTime().value);
					// add refactor RequestList203
					result.setAdvancePayment(compensatoryLeaveComSet.getCompensatoryAcquisitionUse().getPreemptionPermit().value);
					isManageByTime = compensatoryLeaveComSet.getCompensatoryDigestiveTimeUnit()
							.getIsManageByTime().value;
					digestiveUnit = compensatoryLeaveComSet.getCompensatoryDigestiveTimeUnit().getDigestiveUnit().value;
				}
			}else{
				//ドメインモデル「代休管理設定」．管理区分 = 管理しない
				result.setSubstitutionFlg(false);
				result.setTimeOfPeriodFlg(false);
			}
		}
		// 代休管理区分をチェックする(kiểm tra 代休管理区分)
		if(result.isSubstitutionFlg()){
			// ドメインモデル「時間代休の消化単位」．管理区分をチェックする(kiểm tra domain 「時間代休の消化単位」．管理区分)
			if(isManageByTime == 1){
				result.setTimeOfPeriodFlg(true);
				result.setDigestiveUnit(digestiveUnit);
			}else{
				result.setTimeOfPeriodFlg(false);
			}
		}
		return result;
	}
	
	/**
	 * @author hoatt
	 * 10-3.振休の設定を取得する
	 * @param companyID
	 * @param employeeID
	 * @param baseDate
	 * @return
	 */
	public static LeaveSetOutput getSetForLeave(RequireM3 require, CacheCarrier cacheCarrier,
			String companyID, String employeeID, GeneralDate baseDate) {
		boolean subManageFlag = false;
		int expirationOfLeave = 0;
		ApplyPermission applyPermission = null;
		// アルゴリズム「社員所属雇用履歴を取得」を実行する
		Optional<BsEmploymentHistoryImport> empHistImport = require.employmentHistory(cacheCarrier, companyID, employeeID, baseDate);
		if(empHistImport.isPresent()){
			//ドメインモデル「雇用振休管理設定」を取得する(láy dữ liệu domain 「雇用振休管理設定」)
			Optional<EmpSubstVacation> optEmpSubData = require.empSubstVacation(companyID, empHistImport.get().getEmploymentCode());
			if(optEmpSubData.isPresent()){//１件以上取得できた(1data trở lên)
				//ドメインモデル「雇用振休管理設定」．管理区分をチェックする(kiểm tra domain 「雇用振休管理設定」．管理区分)
				EmpSubstVacation empSubData = optEmpSubData.get();
			/*	if(empSubData.getSetting().getIsManage().equals(ManageDistinct.YES)){
					subManageFlag = true;
					//振休使用期限=ドメインモデル「振休管理設定」．「振休取得・使用方法」．休暇使用期限
					expirationOfLeave = empSubData.getSetting().getExpirationDate().value;
					//refactor RQ204
					applyPermission = empSubData.getSetting().getAllowPrepaidLeave();
				}*/
			}else{//０件(0 data)
				//ドメインモデル「振休管理設定」を取得する(lấy dữ liệu domain 「振休管理設定」)
				Optional<ComSubstVacation>  comSub = require.comSubstVacation(companyID);
			/*	if(comSub.isPresent()){
					ComSubstVacation comSubSet = comSub.get();
					if(comSubSet.isManaged()){
						subManageFlag = true;
						//振休使用期限=ドメインモデル「振休管理設定」．「振休取得・使用方法」．休暇使用期限
						expirationOfLeave = comSubSet.getSetting().getExpirationDate().value;
						//refactor RQ204
						applyPermission = comSubSet.getSetting().getAllowPrepaidLeave();
					}
				}*/
			}
		}
		return new LeaveSetOutput(subManageFlag, expirationOfLeave, applyPermission);
	}
	
	/**
	 * @author hoatt
	 * 10-4.積立年休の設定を取得する
	 * @param companyID
	 * @param employeeID
	 * @param baseDate
	 * @return
	 */
	public static boolean getSetForYearlyReserved(RequireM2 require, CacheCarrier cacheCarrier, 
			String companyID, String employeeID, GeneralDate baseDate) {
		// アルゴリズム「社員所属雇用履歴を取得」を実行する
		Optional<BsEmploymentHistoryImport> empHist = require.employmentHistory(cacheCarrier, companyID, employeeID, baseDate);
		if(empHist.isPresent()){
			BsEmploymentHistoryImport emp = empHist.get();
			//ドメインモデル「雇用積立年休設定」を取得する(lấy dữ liệu domain 「雇用積立年休設定」)
			Optional<EmptYearlyRetentionSetting> empRet = require.employmentYearlyRetentionSetting(companyID, emp.getEmploymentCode());
			if(empRet.isPresent()){//１件以上取得できた(dữ liệu lấy được từ 1 trở lên)
				//ドメインモデル「雇用積立年休設定」．管理区分をチェックする(check domain 「雇用積立年休設定」．管理区分)
				return empRet.get().getManagementCategory().equals(ManageDistinct.YES) ? true : false;
			}
			//０件(0 dữ liệu)
			//RedMine#98195　EA修正履歴No.2406
			//ドメインモデル「積立年休設定」を取得する(lấy domain 「積立年休設定」)
			Optional<RetentionYearlySetting> retYearSet = require.retentionYearlySetting(companyID);
			if(retYearSet.isPresent()){
				return retYearSet.get().getManagementCategory().value == 1 ? true : false;
			}
		}
		return false;
	}

	public static interface RequireM0 {
		
		Optional<BsEmploymentHistoryImport> employmentHistory(CacheCarrier cacheCarrier, 
				String companyId, String employeeId, GeneralDate baseDate);
	}
	
	public static interface RequireM4 extends RequireM0 {

		CompensatoryLeaveEmSetting compensatoryLeaveEmSetting(String companyId, String employmentCode);

		CompensatoryLeaveComSetting compensatoryLeaveComSetting(String companyId);
	}
	
	public static interface RequireM3 extends RequireM0 {
		
		Optional<EmpSubstVacation> empSubstVacation(String companyId, String contractTypeCode);

		Optional<ComSubstVacation> comSubstVacation(String companyId);
	}
	
	public static interface RequireM2 extends RequireM0 {
		
		Optional<EmptYearlyRetentionSetting> employmentYearlyRetentionSetting(String companyId, String employmentCode);
		
		Optional<RetentionYearlySetting> retentionYearlySetting(String companyId);
	}
	
	public static interface RequireM1 {
		
		AnnualPaidLeaveSetting annualPaidLeaveSetting(String companyId);
	}
	
}
