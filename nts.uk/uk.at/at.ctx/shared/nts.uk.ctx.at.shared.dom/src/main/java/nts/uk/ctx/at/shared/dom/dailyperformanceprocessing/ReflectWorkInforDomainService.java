package nts.uk.ctx.at.shared.dom.dailyperformanceprocessing;

import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.EmployeeGeneralInfoImport;
import nts.uk.ctx.at.shared.dom.calculationsetting.StampReflectionManagement;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.output.ClosureOfDailyPerOutPut;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.output.PeriodInMasterList;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.repository.RecreateFlag;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionType;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * 
 * @author nampt 日別実績処理 作成処理 ⑥１日の日別実績の作成処理 勤務情報を反映する
 * 
 *
 */
public interface ReflectWorkInforDomainService {
	//KIF use
	void reflectWorkInformation(String companyID, String employeeID, GeneralDate processingDate,
			String empCalAndSumExecLogID, ExecutionType reCreateAttr, boolean reCreateWorkType, boolean reCreateWorkPlace,
			EmployeeGeneralInfoImport employeeGeneralInfoImport,
			Optional<StampReflectionManagement> stampReflectionManagement,
			Map<String, Map<String, WorkingConditionItem>> mapWorkingConditionItem,
    		Map<String, Map<String, DateHistoryItem>> mapDateHistoryItem, PeriodInMasterList periodInMasterList,
    		RecreateFlag recreateFlag,Optional<WorkInfoOfDailyAttendance> optDaily);
	//KBT use
	void reflectWorkInformationWithNoInfoImport(String companyID, String employeeID, GeneralDate processingDate,
			String empCalAndSumExecLogID, ExecutionType reCreateAttr , boolean reCreateWorkType , boolean reCreateWorkPlace, 
			Optional<StampReflectionManagement> stampReflectionManagement,RecreateFlag recreateFlag,Optional<WorkInfoOfDailyAttendance> optDaily);

	AffiliationInforState createAffiliationInforOfDailyPerfor(String companyId, String employeeId, GeneralDate day,
			String empCalAndSumExecLogID);

	/**
	 * 特定日を日別実績に反映する
	 * 
	 * @param companyId
	 * @param employeeId
	 * @param day
	 * @param workPlaceID
	 * @return
	 */
	SpecificDateAttrOfDailyAttd reflectSpecificDate(String companyId, String employeeId, GeneralDate day,
			String workPlaceID, PeriodInMasterList periodInMasterList);

	/**
	 * 休業を日別実績に反映する
	 * 
	 * @param employeeId
	 * @param day
	 * @return
	 */
	ClosureOfDailyPerOutPut reflectHolidayOfDailyPerfor(String companyId, String employeeId, GeneralDate day,
			String empCalAndSumExecLogID, WorkInfoOfDailyAttendance workInfoOfDailyPerformance);

	/**
	 * 計算区分を日別実績に反映する
	 * 
	 * @param companyId
	 * @param employeeId
	 * @param day
	 * @return
	 */
	CalAttrOfDailyAttd reflectCalAttOfDaiPer(String companyId, String employeeId, GeneralDate day,
			AffiliationInforOfDailyAttd affiliationInforOfDailyPerfor, PeriodInMasterList periodInMasterList);
	/**
	 * 自動打刻セットする
	 * @param companyId
	 * @param workInfoOfDailyPerformanceUpdate
	 * @param workingConditionItem
	 * @param timeLeavingOptional
	 * @param employeeID
	 * @param day
	 * @param stampReflectionManagement
	 * @return
	 */
	TimeLeavingOfDailyAttd createStamp(String companyId,
			WorkInfoOfDailyAttendance workInfoOfDailyPerformanceUpdate,
			Optional<WorkingConditionItem> workingConditionItem, TimeLeavingOfDailyAttd timeLeavingOptional,
			String employeeID, GeneralDate day, Optional<StampReflectionManagement> stampReflectionManagement);
	
	/**
	 * 所属情報を反映する
	 * @param companyId
	 * @param employeeId
	 * @param day
	 * @param empCalAndSumExecLogID
	 * @param employeeGeneralInfoImport
	 * @return
	 */
	public AffiliationInforState createAffiliationInforState(String companyId, String employeeId,
			GeneralDate day, String empCalAndSumExecLogID, EmployeeGeneralInfoImport employeeGeneralInfoImport);
	
	/**
	 * 所属情報を反映する New
	 * @param companyId
	 * @param employeeId
	 * @param day
	 * @param dailyAttd
	 * @param generalInfoImport
	 * @return
	 */
	public AffiliationInforState createAffiliationInforState(String companyId, String employeeId,
			GeneralDate day, EmployeeGeneralInfoImport generalInfoImport);
	
	/**
	 * 勤務情報を反映する
	 * @return
	 */
	WorkInfoOfDailyAttendance reflect(String companyId, String employeeId, GeneralDate day, String empCalAndSumExecLogID,
			ExecutionType reCreateAttr, boolean reCreateWorkType, EmployeeGeneralInfoImport employeeGeneralInfoImport,
			Optional<StampReflectionManagement> stampReflectionManagement,
			Map<String, Map<String, WorkingConditionItem>> mapWorkingConditionItem,
			Map<String, Map<String, DateHistoryItem>> mapDateHistoryItem, PeriodInMasterList periodInMasterList , TimeLeavingOfDailyAttd timeLeavingOptional,
			RecreateFlag recreateFlag,Optional<WorkInfoOfDailyAttendance> optDaily);
	WorkInfoOfDailyAttendance reflectWithNoInfoImport(String companyId, String employeeId, GeneralDate day,
			String empCalAndSumExecLogID, ExecutionType reCreateAttr, boolean reCreateWorkType,
			Optional<StampReflectionManagement> stampReflectionManagement, TimeLeavingOfDailyAttd timeLeavingOptional,RecreateFlag recreateFlag,Optional<WorkInfoOfDailyAttendance> optDaily);
    
    public ExitStatus reCreateWorkType(String employeeId, GeneralDate day, String empCalAndSumExecLogID,
            boolean reCreateWorkType, boolean reCreateWorkPlace);
    
    @AllArgsConstructor
	/**
	 * 再作成しない : 0 再作成する : 1
	 */
	public enum ExitStatus {
		/* 再作成しない */
		DO_NOT_RECREATE(0),

		/* 再作成する */
		RECREATE(1);

		public final int value;

	}
    
    public Optional<BonusPaySetting> reflectBonusSettingDailyPer(String companyId, String employeeId, GeneralDate day,
			WorkInfoOfDailyAttendance workInfoOfDailyPerformanceUpdate,
			AffiliationInforOfDailyAttd affiliationInforOfDailyPerfor, PeriodInMasterList periodInMasterList);
}
