package nts.uk.ctx.at.request.dom.application.appabsence.service;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsence;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.AbsenceCheckRegisterOutput;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.AppAbsenceStartInfoOutput;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.SpecAbsenceDispInfo;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.AppliedDate;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSet;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

public interface AbsenceServiceProcess {
	/**
	 * @param workTypeCode
	 * @return
	 */
	public SpecialLeaveInfor getSpecialLeaveInfor(String workTypeCode);
	
	void createAbsence(AppAbsence domain, Application newApp, ApprovalRootStateImport_New approvalRootState);
	/**
	 * @author hoatt
	 * 13.計画年休上限チェック
	 * @param cID: 会社ID
	 * @param sID: 社員ID
	 * @param workTypeCD: 勤務種類コード
	 * @param sDate: 申請開始日
	 * @param eDate: 申請終了日
	 * @param lstDateIsHoliday: 休日の申請日
	 */
	void checkLimitAbsencePlan(String cID, String sID, String workTypeCD, 
			GeneralDate sDate, GeneralDate eDate, List<GeneralDate> lstDateIsHoliday);
	/**
	 * @author hoatt
	 * 14.休暇種類表示チェック
	 * @param companyID 会社ID
	 * @param sID 社員ID
	 * @param baseDate 基準日
	 * @return
	 */
	public CheckDispHolidayType checkDisplayAppHdType(String companyID, String sID, GeneralDate baseDate);
	
	/**
	 * 代休振休優先消化チェック
	 * @param mode 画面モード  新規モード: true/更新モード: false
	 * @param hdAppSet 休暇申請設定
	 * @param employmentSet 雇用別申請承認設定
	 * @param subVacaManage 振休管理区分
	 * @param subHdManage 代休管理区分
	 * @param subVacaRemain 振休残数
	 * @param subHdRemain 代休残数
	 * @return
	 */
	public List<ConfirmMsgOutput> checkDigestPriorityHd(boolean mode, HdAppSet hdAppSet, AppEmploymentSetting employmentSet, boolean subVacaManage,
			boolean subHdManage, Double subVacaRemain, Double subHdRemain);
	/**
	 * @author hoatt
	 * 振休代休優先チェック
	 * @param pridigCheck - 休暇申請設定．年休より優先消化チェック区分 - HdAppSet
	 * @param isSubVacaManage - 振休管理設定．管理区分
	 * @param subVacaTypeUseFlg - 休暇申請対象勤務種類．休暇種類を利用しない（振休） - AppEmploymentSetting
	 * @param isSubHdManage - 代休管理設定．管理区分
	 * @param subHdTypeUseFlg - 休暇申請対象勤務種類．休暇種類を利用しない（代休） - AppEmploymentSetting
	 * @param numberSubHd - 代休残数
	 * @param numberSubVaca - 振休残数
	 * @return エラーメッセージ - 確認メッセージ
	 */
	public List<ConfirmMsgOutput> checkPriorityHoliday(AppliedDate pridigCheck,
			boolean isSubVacaManage, boolean subVacaTypeUseFlg, boolean isSubHdManage, boolean subHdTypeUseFlg,
			int numberSubHd, int numberSubVaca);
	
	/**
	 * 残数取得する
	 * @param companyID 会社ID
	 * @param employeeID 社員ID
	 * @param baseDate 基準日
	 * @param yearManage 年休管理区分
	 * @param subHdManage 代休管理区分
	 * @param subVacaManage 積休管理区分
	 * @param retentionManage 振休管理区分
	 * @return
	 */
	public NumberOfRemainOutput getNumberOfRemaining(String companyID, String employeeID, GeneralDate baseDate,
            ManageDistinct annualLeaveManageDistinct, ManageDistinct accumulatedManage, ManageDistinct substituteLeaveManagement, 
            ManageDistinct holidayManagement, ManageDistinct overrest60HManagement, ManageDistinct childNursingManagement, 
            ManageDistinct longTermCareManagement);
	
	/**
	 * 休暇申請設定を取得する
	 * @param companyID 会社ID
	 * @return
	 */
	public HolidayRequestSetOutput getHolidayRequestSet(String companyID);
	
	/**
	 * 休暇残数情報を取得する
	 * @param companyID 会社ID
	 * @param employeeID 社員ID
	 * @param date 基準日
	 * @return
	 */
	public RemainVacationInfo getRemainVacationInfo(String companyID, String employeeID, GeneralDate date);
	
	/**
	 * 特別休暇の上限情報取得する
	 * @param companyID 会社ID
	 * @param appAbsenceStartInfoOutput 休暇申請起動時の表示情報
	 * @param workTypeCD 勤務種類コード<Optional>
	 * @return
	 */
	public AppAbsenceStartInfoOutput getSpecAbsenceUpperLimit(String companyID, AppAbsenceStartInfoOutput appAbsenceStartInfoOutput, Optional<String> workTypeCD);
	
	/**
	 * 就業時間帯変更時処理
	 * @param companyID 会社ID
	 * @param appAbsenceStartInfoOutput 休暇申請起動時の表示情報
	 * @param workTypeCD 勤務種類コード
	 * @param workTimeCD 就業時間帯コード<Optional>
	 * @return
	 */
	public AppAbsenceStartInfoOutput workTimesChangeProcess(String companyID, AppAbsenceStartInfoOutput appAbsenceStartInfoOutput, String workTypeCD, 
			Optional<String> workTimeCD, HolidayAppType holidayType);
	
	/**
	 * 勤務種類変更時処理
	 * @param companyID 会社ID
	 * @param appAbsenceStartInfoOutput 休暇申請起動時の表示情報
	 * @param holidayType 休暇種類
	 * @param workTypeCD 勤務種類コード<Optional>
	 * @return
	 */
	public AppAbsenceStartInfoOutput workTypeChangeProcess(String companyID, AppAbsenceStartInfoOutput appAbsenceStartInfoOutput, HolidayAppType holidayType, 
			Optional<String> workTypeCD);
	
	/**
	 * 休暇種類変更時処理
	 * @param companyID 会社ID
	 * @param appAbsenceStartInfoOutput 休暇申請起動時の表示情報
	 * @param displayHalfDayValue 勤務種類組み合わせ全表示チェック
	 * @param alldayHalfDay 終日半日休暇区分 
	 * @param holidayType 休暇種類
	 * @return
	 */
	public AppAbsenceStartInfoOutput holidayTypeChangeProcess(String companyID, AppAbsenceStartInfoOutput appAbsenceStartInfoOutput, 
			boolean displayHalfDayValue, Integer alldayHalfDay, HolidayAppType holidayType);
	
	/**
	 * 終日半日休暇変更時処理
	 * @param companyID 会社ID
	 * @param appAbsenceStartInfoOutput 休暇申請起動時の表示情報
	 * @param displayHalfDayValue 勤務種類組み合わせ全表示チェック
	 * @param alldayHalfDay 終日半日休暇区分 
	 * @param holidayType 休暇種類<Optional>
	 * @return
	 */
	public AppAbsenceStartInfoOutput allHalfDayChangeProcess(String companyID, AppAbsenceStartInfoOutput appAbsenceStartInfoOutput, 
			boolean displayHalfDayValue, Integer alldayHalfDay, Optional<HolidayAppType> holidayType);
	
	/**
	 * 登録前のエラーチェック処理
	 * @param companyID 会社ID
	 * @param appAbsenceStartInfoOutput 休暇申請起動時の表示情報
	 * @param application 申請
	 * @param appAbsence 休暇申請
	 * @param alldayHalfDay 終日半日休暇区分
	 * @param agentAtr 代行申請区分
	 * @param mourningAtr 喪主区分<Optional>
	 * @return
	 */
	public AbsenceCheckRegisterOutput checkBeforeRegister(String companyID, AppAbsenceStartInfoOutput appAbsenceStartInfoOutput, Application application,
			AppAbsence appAbsence, Integer alldayHalfDay, boolean agentAtr, Optional<Boolean> mourningAtr);
	
	/**
	 * 申請日の矛盾チェック
	 * @param companyID 会社ID
	 * @param employeeID 社員ID
	 * @param startDate 申請日開始日
	 * @param endDate 申請日終了日
	 * @param alldayHalfDay 終日半日休暇区分
	 * @param hdAppSet 休暇申請設定
	 * @return
	 */
	public List<ConfirmMsgOutput> inconsistencyCheck(String companyID, String employeeID, GeneralDate startDate, GeneralDate endDate, 
			Integer alldayHalfDay, HdAppSet hdAppSet, boolean mode);
	
	/**
	 * 休暇残数チェック
	 * @param companyID 会社ID
	 * @param appAbsence 申請
	 * @param closureStartDate 締め開始日
	 * @param hdAppSet 休暇申請設定
	 * @param holidayType 休暇種類 
	 */
	public void checkRemainVacation(String companyID, AppAbsence appAbsence, GeneralDate closureStartDate,
			HdAppSet hdAppSet, HolidayAppType holidayType);
	
	/**
	 * 休暇種類共通エラーチェック
	 * @param companyID 会社ID
	 * @param appAbsence 申請
	 * @param closureStartDate 締め開始日
	 * @param hdAppSet 休暇申請設定
	 * @param holidayType 休暇種類
	 * @param alldayHalfDay 終日半日休暇区分
	 */
	public List<ConfirmMsgOutput> holidayCommonCheck(String companyID, AppAbsence appAbsence, GeneralDate closureStartDate,
			HdAppSet hdAppSet, HolidayAppType holidayType, Integer alldayHalfDay, boolean mode);
	
	/**
	 * 年休のチェック処理
	 * @param mode 画面モード
	 * @param companyID 会社ID
	 * @param employeeID 社員ID
	 * @param startDate 申請開始日
	 * @param endDate 申請終了日
	 * @param workTypeCD 勤務種類コード
	 * @param lstDateIsHoliday 休日の申請日<List>
	 * @param appAbsenceStartInfoOutput 休暇申請起動時の表示情報
	 * @return
	 */
	public List<ConfirmMsgOutput> annualLeaveCheck(boolean mode, String companyID, String employeeID, GeneralDate startDate, GeneralDate endDate,
			String workTypeCD, List<GeneralDate> lstDateIsHoliday, AppAbsenceStartInfoOutput appAbsenceStartInfoOutput);
	
	/**
	 * 特別休暇の上限チェック
	 * @param companyID 会社ID
	 * @param startDate 申請開始日
	 * @param endDate  申請終了日
	 * @param mournerAtr 上限日数
	 * @param specAbsenceDispInfo 特別休暇表示情報
	 * @param lstDateIsHoliday 休日の申請日<List>
	 */
	public void checkSpecHoliday(String companyID, GeneralDate startDate, GeneralDate endDate, Boolean mournerAtr, 
			SpecAbsenceDispInfo specAbsenceDispInfo, List<GeneralDate> lstDateIsHoliday);
	
	/**
	 * 特別休暇のチェック処理
	 * @param companyID 会社ID
	 * @param startDate 申請開始日
	 * @param endDate 申請終了日
	 * @param holidayDateLst 休日の申請日<List>
	 * @param mournerAtr 喪主区分
	 */
	/**
	 * 特別休暇のチェック処理
	 * @param companyID 会社ID
	 * @param startDate 申請開始日
	 * @param endDate 申請終了日
	 * @param holidayDateLst 休日の申請日<List>
	 * @param mournerAtr 喪主区分
	 * @param specAbsenceDispInfo 特別休暇表示情報
	 */
	public void checkSpecLeaveProcess(String companyID, GeneralDate startDate, GeneralDate endDate, List<GeneralDate> holidayDateLst, 
			Boolean mournerAtr, SpecAbsenceDispInfo specAbsenceDispInfo);
	
	/**
	 * 時間消化のチェック処理
	 * @param startDate
	 * @param endDate
	 */
	public void checkTimeDigestProcess(GeneralDate startDate, GeneralDate endDate);
	
	/**
	 * 休暇種類別エラーチェック
	 * @param mode 画面モード
	 * @param companyID 会社ID
	 * @param employeeID 社員ID
	 * @param startDate 申請開始日
	 * @param endDate 申請終了日
	 * @param holidayType 休暇種類
	 * @param workTypeCD 勤務種類コード
	 * @param holidayDateLst 休日の申請日<List>
	 * @param appAbsenceStartInfoOutput 休暇申請起動時の表示情報
	 * @param mournerAtr 喪主区分<Optional>
	 * @return
	 */
	public List<ConfirmMsgOutput> errorCheckByHolidayType(boolean mode, String companyID, String employeeID, GeneralDate startDate, GeneralDate endDate,
			HolidayAppType holidayType, String workTypeCD, List<GeneralDate> holidayDateLst, AppAbsenceStartInfoOutput appAbsenceStartInfoOutput, Optional<Boolean> mournerAtr);
	
	/**
	 * 休暇申請登録時チェック処理
	 * @param mode 画面モード
	 * @param companyID 会社ID
	 * @param appAbsence 申請
	 * @param appAbsenceStartInfoOutput 休暇申請起動時の表示情報
	 * @param holidayType 休暇種類
	 * @param workTypeCD 勤務種類コード
	 * @param alldayHalfDay 終日半日休暇区分
	 * @param holidayDateLst 休日の申請日<List>
	 * @param mournerAtr 喪主区分<Optional>
	 * @return
	 */
	public List<ConfirmMsgOutput> checkAppAbsenceRegister(boolean mode, String companyID, AppAbsence appAbsence, AppAbsenceStartInfoOutput appAbsenceStartInfoOutput,
			HolidayAppType holidayType, String workTypeCD, Integer alldayHalfDay, List<GeneralDate> holidayDateLst, Optional<Boolean> mournerAtr);
	
	/**
	 * 勤務種類・就業時間帯情報を取得する
	 * @param companyID 会社ID
	 * @param appAbsence 休暇申請
	 * @param appAbsenceStartInfoOutput 休暇申請起動時の表示情報
	 * @return
	 */
	public AppAbsenceStartInfoOutput getWorkTypeWorkTimeInfo(String companyID, AppAbsence appAbsence, AppAbsenceStartInfoOutput appAbsenceStartInfoOutput);
	
	/**
	 * 更新前のエラーチェック処理
	 * @param companyID 会社ID
	 * @param appAbsenceStartInfoOutput 休暇申請起動時の表示情報
	 * @param application 申請
	 * @param appAbsence 休暇申請
	 * @param alldayHalfDay 終日半日休暇区分
	 * @param agentAtr 代行申請区分
	 * @param mourningAtr 喪主区分<Optional>
	 * @return
	 */
	public AbsenceCheckRegisterOutput checkBeforeUpdate(String companyID, AppAbsenceStartInfoOutput appAbsenceStartInfoOutput, Application application,
			AppAbsence appAbsence, Integer alldayHalfDay, boolean agentAtr, Optional<Boolean> mourningAtr);
	
	/**
	 * 1.休暇申請（新規）起動処理
	 * @param String companyID 会社ID
	 * @param AppDispInfoStartupOutput appDispInfoStartupOutput 申請表示情報
	 * @return AppAbsenceStartInfoOutput 休暇申請起動時の表示情報
	 */
	public AppAbsenceStartInfoOutput getVacationActivation(String companyID, AppDispInfoStartupOutput appDispInfoStartupOutput);
}
