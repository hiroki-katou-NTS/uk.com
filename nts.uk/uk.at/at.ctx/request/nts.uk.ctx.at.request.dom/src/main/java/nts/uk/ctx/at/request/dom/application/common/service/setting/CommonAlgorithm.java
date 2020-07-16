package nts.uk.ctx.at.request.dom.application.common.service.setting;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoNoDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoRelatedDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.ApplyWorkTypeOutput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.setting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.PrePostInitAtr;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApprovalFunctionSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

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
	 * @return
	 */
	public AppDispInfoRelatedDateOutput getAppDispInfoRelatedDate(String companyID, String employeeID, List<GeneralDate> dateLst, 
			ApplicationType appType, DisplayAtr prePostAtrDisp, PrePostInitAtr initValueAtr, Optional<OvertimeAppAtr> opOvertimeAppAtr);
	
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
	 * 申請日を変更する処理
	 * @param companyID 会社ID
	 * @param dateLst 申請対象日リスト
	 * @param appType 申請種類
	 * @param appDispInfoNoDateOutput 申請表示情報(基準日関係なし)
	 * @param appDispInfoWithDateOutput 申請表示情報(基準日関係あり)
	 * @return
	 */
	public AppDispInfoWithDateOutput changeAppDateProcess(String companyID, List<GeneralDate> dateLst,
			ApplicationType appType, AppDispInfoNoDateOutput appDispInfoNoDateOutput, AppDispInfoWithDateOutput appDispInfoWithDateOutput);
	
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
}
