package nts.uk.ctx.at.request.dom.application.appabsence.service;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsence;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.AppAbsenceStartInfoOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.AppliedDate;

public interface AbsenceServiceProcess {
	/**
	 * @param workTypeCode
	 * @return
	 */
	public SpecialLeaveInfor getSpecialLeaveInfor(String workTypeCode);
	
	void createAbsence(AppAbsence domain, Application_New newApp);
	/**
	 * @author hoatt
	 * 13.計画年休上限チェック
	 * @param cID: 会社ID
	 * @param sID: 社員ID
	 * @param workTypeCD: 勤務種類コード
	 * @param sDate: 申請開始日
	 * @param eDate: 申請終了日
	 * @param hdAppType: 休暇種類
	 * @param lstDateIsHoliday: 休日の申請日
	 */
	void checkLimitAbsencePlan(String cID, String sID, String workTypeCD, 
			GeneralDate sDate, GeneralDate eDate, HolidayAppType hdAppType, List<GeneralDate> lstDateIsHoliday);
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
	 * @author hoatt
	 * 代休振休優先消化チェック
	 * @param pridigCheck - 休暇申請設定．年休より優先消化チェック区分 - HdAppSet
	 * @param isSubVacaManage - 振休管理設定．管理区分
	 * @param subVacaTypeUseFlg - 休暇申請対象勤務種類．休暇種類を利用しない（振休） - AppEmploymentSetting
	 * @param isSubHdManage - 代休管理設定．管理区分
	 * @param subHdTypeUseFlg - 休暇申請対象勤務種類．休暇種類を利用しない（代休） - AppEmploymentSetting
	 * @param numberSubHd - 代休残数
	 * @param numberSubVaca - 振休残数
	 * @return エラーメッセージ - 確認メッセージ
	 */
	public void checkDigestPriorityHd(AppliedDate pridigCheck, boolean isSubVacaManage, boolean subVacaTypeUseFlg,
			boolean isSubHdManage, boolean subHdTypeUseFlg, int numberSubHd, int numberSubVaca);
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
	public void checkPriorityHoliday(AppliedDate pridigCheck,
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
			boolean yearManage, boolean subHdManage, boolean subVacaManage, boolean retentionManage);
	
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
			Optional<String> workTimeCD, Integer holidayType);
	
	/**
	 * 勤務種類変更時処理
	 * @param companyID 会社ID
	 * @param appAbsenceStartInfoOutput 休暇申請起動時の表示情報
	 * @param holidayType 休暇種類
	 * @param workTypeCD 勤務種類コード<Optional>
	 * @return
	 */
	public AppAbsenceStartInfoOutput workTypeChangeProcess(String companyID, AppAbsenceStartInfoOutput appAbsenceStartInfoOutput, Integer holidayType, 
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
			boolean displayHalfDayValue, Integer alldayHalfDay, Integer holidayType);
	
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
			boolean displayHalfDayValue, Integer alldayHalfDay, Optional<Integer> holidayType);
	
}
