package nts.uk.ctx.at.request.dom.application.common.service.setting;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.appabsence.apptimedigest.TimeDigestApplication;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.SpecAbsenceDispInfo;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementDetail;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoNoDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoRelatedDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.ApplyWorkTypeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.InitWkTypeWkTimeOutput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.setting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.OTAppBeforeAccepRestric;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.PrePostInitAtr;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApprovalFunctionSet;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;

public interface CommonAlgorithm {
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.申請表示情報(基準日関係なし)を取得する.申請表示情報(基準日関係なし)を取得する
	 * @param companyID 会社ID
	 * @param applicantLst 申請者リスト<Optional>
	 * @param appType 申請種類
	 * @param opHolidayAppType 休暇申請の種類＜Optional＞
	 * @param opOvertimeAppAtr Optional＜残業申請区分＞
	 * @return
	 */
	public AppDispInfoNoDateOutput getAppDispInfo(String companyID, List<String> applicantLst, ApplicationType appType, 
			Optional<HolidayAppType> opHolidayAppType, Optional<OvertimeAppAtr> opOvertimeAppAtr);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.10_申請者を作成.10_申請者を作成
	 * @param applicantLst 申請者リスト
	 * @return
	 */
	public List<EmployeeInfoImport> getEmployeeInfoLst(List<String> applicantLst);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.申請表示情報(基準日関係あり)を取得する.申請表示情報(基準日関係あり)を取得する
	 * @param companyID 会社ID
	 * @param appType 申請種類
	 * @param dateLst 申請対象日リスト
	 * @param appDispInfoNoDateOutput 申請表示情報(基準日関係なし)
	 * @param mode 新規詳細モード(新規モード/詳細モード) 新規モード: true/詳細モード: false
	 * @return
	 */
	public AppDispInfoWithDateOutput getAppDispInfoWithDate(String companyID, ApplicationType appType, List<GeneralDate> dateLst,
			AppDispInfoNoDateOutput appDispInfoNoDateOutput, boolean mode, Optional<OvertimeAppAtr> opOvertimeAppAtr);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.7.就業の申請承認設定を取得.2.社員IDから申請承認設定情報の取得.社員IDから申請承認設定情報の取得
	 * @param companyID 会社ID
	 * @param employeeID 社員ID
	 * @param date 基準日
	 * @param targetApp 申請種類
	 * @return
	 */
	public ApprovalFunctionSet getApprovalFunctionSet(String companyID, String employeeID, GeneralDate date, ApplicationType targetApp);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.申請表示情報(基準日関係あり)を取得する.12_承認ルートを取得.12_承認ルートを取得
	 * @param companyID 会社ID
	 * @param employeeID 申請者ID
	 * @param rootAtr 就業ルート区分
	 * @param appType 申請種類
	 * @param appDate 基準日
	 * @return
	 */
	public ApprovalRootContentImport_New getApprovalRoot(String companyID, String employeeID, 
			EmploymentRootAtr rootAtr, ApplicationType appType, GeneralDate appDate);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.申請表示情報(申請対象日関係あり)を取得する.申請表示情報(申請対象日関係あり)を取得する
	 * @param companyID 会社ID
	 * @param employeeID 社員ID
	 * @param dateLst 申請対象日リスト<Optional>
	 * @param appType 申請種類
	 * @param prePostAtrDisp 事前事後区分表示
	 * @param initValueAtr 事前事後区分の初期表示
	 * @param opOvertimeAppAtr 残業区分<Optional>
	 * @param otAppBeforeAccepRestric 残業申請事前の受付制限
	 * @return
	 */
	public AppDispInfoRelatedDateOutput getAppDispInfoRelatedDate(String companyID, String employeeID, List<GeneralDate> dateLst, 
			ApplicationType appType, DisplayAtr prePostAtrDisp, PrePostInitAtr initValueAtr, Optional<OvertimeAppAtr> opOvertimeAppAtr,
			OTAppBeforeAccepRestric otAppBeforeAccepRestric);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.起動時の申請表示情報を取得する.起動時の申請表示情報を取得する
	 * @param companyID 会社ID
	 * @param appType 申請種類
	 * @param applicantLst 申請者リスト
	 * @param dateLst 申請対象日リスト
	 * @param mode 新規詳細モード(新規モード/詳細モード) 新規モード: true/詳細モード: false
	 * @param opHolidayAppType 休暇申請の種類<Optional>
	 * @param opOvertimeAppAtr Optional＜残業申請区分＞
	 * @return
	 */
	public AppDispInfoStartupOutput getAppDispInfoStart(String companyID, ApplicationType appType, List<String> applicantLst, 
			List<GeneralDate> dateLst, boolean mode, Optional<HolidayAppType> opHolidayAppType, Optional<OvertimeAppAtr> opOvertimeAppAtr);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.申請日を変更する処理.申請日を変更する処理
	 * @param companyID 会社ID
	 * @param dateLst 申請対象日リスト
	 * @param appType 申請種類
	 * @param appDispInfoNoDateOutput 申請表示情報(基準日関係なし)
	 * @param appDispInfoWithDateOutput 申請表示情報(基準日関係あり)
	 * @param opOvertimeAppAtr 残業区分<Optional>
	 * @return
	 */
	public AppDispInfoWithDateOutput changeAppDateProcess(String companyID, List<GeneralDate> dateLst, ApplicationType appType, 
			AppDispInfoNoDateOutput appDispInfoNoDateOutput, AppDispInfoWithDateOutput appDispInfoWithDateOutput, Optional<OvertimeAppAtr> opOvertimeAppAtr);
	
	/**
	 * 申請済み勤務種類の存在判定と取得
	 * @param companyID
	 * @param wkTypes 勤務種類(List) //workType(list)
	 * @param wkTypeCD 選択済勤務種類コード//selectedWorkTypeCode
	 * @return
	 */
	public ApplyWorkTypeOutput appliedWorkType(String companyID, List<WorkType> wkTypes, String wkTypeCD);
	
	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.入力者の社員情報を取得する.入力者の社員情報を取得する
	 * @param employeeID
	 * @param enterPersonID
	 * @return
	 */
	public Optional<EmployeeInfoImport> getEnterPersonInfor(String employeeID, String enterPersonID);
	
	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.申請の矛盾チェック.申請の矛盾チェック
	 * @param companyID 会社ID
	 * @param employeeInfo 対象者の社員情報
	 * @param dateLst 対象日リスト
	 * @param workTypeLst 申請する勤務種類リスト
	 * @param actualContentDisplayLst 表示する実績内容
	 */
	public void appConflictCheck(String companyID, EmployeeInfoImport employeeInfo, List<GeneralDate> dateLst,
			List<String> workTypeLst, List<ActualContentDisplay> actualContentDisplayLst);
	
	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.09_勤務種類就業時間帯の初期選択をセットする.09_勤務種類就業時間帯の初期選択をセットする
	 * @param employeeID 社員ID
	 * @param date 基準日
	 * @param workTypeLst 勤務種類リスト
	 * @param workTimeLst 就業時間帯リスト
	 * @param achievementDetail 実績詳細
	 * @return
	 */
	public InitWkTypeWkTimeOutput initWorkTypeWorkTime(
			String employeeID,
			GeneralDate date,
			GeneralDate inputDate,
			List<WorkType> workTypeLst,
			List<WorkTimeSetting> workTimeLst,
			AchievementDetail achievementDetail);
	
	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.申請の矛盾チェック.日ごとに申請の矛盾チェック.日ごとに申請の矛盾チェック
	 * @param companyID 会社ID
	 * @param employeeInfo 対象者の社員情報：社員情報
	 * @param date 対象日
	 * @param workTypeApp 申請する「勤務種類」
	 * @param workTypeActual 変更元の「勤務種類」
	 */
	public void inconsistencyCheckApplication(String companyID, EmployeeInfoImport employeeInfo, GeneralDate date,
			WorkType workTypeApp, WorkType workTypeActual);
	
	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.申請の矛盾チェック.勤務種類の分類の矛盾ルール.勤務種類の分類の矛盾ルール
	 * @param workTypeAppAtr 申請する勤務種類の分類
	 * @param workTypeActualAtr 変更元の勤務種類の分類
	 * @return チェック結果（矛盾、矛盾ではない） true: 矛盾, false: 矛盾ではない
	 */
	public boolean conflictRuleOfWorkTypeAtr(WorkTypeClassification workTypeAppAtr, WorkTypeClassification workTypeActualAtr);
	
	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.申請の矛盾チェック.日ごとに休日区分の矛盾チェック.日ごとに休日区分の矛盾チェック
	 * @param companyID 会社ID
	 * @param employeeInfo 対象者の社員情報：社員情報
	 * @param date 対象日
	 * @param workTypeApp 申請する「勤務種類」
	 * @param workTypeActual 変更元の「勤務種類」
	 */
	public void inconsistencyCheckHoliday(String companyID, EmployeeInfoImport employeeInfo, GeneralDate date,
			WorkType workTypeApp, WorkType workTypeActual);
	
	/**
	 * 11.時間消化登録時のエラーチェック
	 * @param specAbsenceDispInfo 時間消化申請
	 * @param superHolidayUnit 60H超休消化単位
	 * @param substituteHoliday 時間代休消化単位
	 * @param annualLeaveUnit 時間年休消化単位
	 * @param childNursingUnit 時間子の看護の消化単位
	 * @param nursingUnit 時間介護の消化単位 
	 * @param pendingUnit 時間特別休暇の消化単位
	 */
	public void vacationDigestionUnitCheck(TimeDigestApplication timeDigestApplication
			, TimeDigestiveUnit superHolidayUnit, TimeDigestiveUnit substituteHoliday
			, TimeDigestiveUnit annualLeaveUnit, TimeDigestiveUnit childNursingUnit
			, TimeDigestiveUnit nursingUnit, TimeDigestiveUnit pendingUnit);
}
