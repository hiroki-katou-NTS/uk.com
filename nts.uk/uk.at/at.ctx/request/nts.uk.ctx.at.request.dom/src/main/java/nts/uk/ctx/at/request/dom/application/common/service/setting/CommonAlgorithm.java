package nts.uk.ctx.at.request.dom.application.common.service.setting;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoNoDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.PrePostInitialAtr;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting;

public interface CommonAlgorithm {
	
	/**
	 * 申請表示情報(基準日関係なし)を取得する
	 * @param companyID 会社ID
	 * @param applicantLst 申請者リスト<Optional>
	 * @param appType 申請種類
	 * @return
	 */
	public AppDispInfoNoDateOutput getAppDispInfo(String companyID, List<String> applicantLst, ApplicationType appType);
	
	/**
	 * 10_申請者を作成
	 * @param applicantLst 申請者リスト
	 * @return
	 */
	public List<EmployeeInfoImport> getEmployeeInfoLst(List<String> applicantLst);
	
	/**
	 * 申請表示情報(基準日関係あり)を取得する
	 * @param companyID 会社ID
	 * @param appType 申請種類
	 * @param dateLst 申請対象日リスト
	 * @param appDispInfoNoDateOutput 申請表示情報(基準日関係なし)
	 * @param mode 新規詳細モード(新規モード/詳細モード) 新規モード: true/詳細モード: false
	 * @return
	 */
	public AppDispInfoWithDateOutput getAppDispInfoWithDate(String companyID, ApplicationType appType, List<GeneralDate> dateLst,
			AppDispInfoNoDateOutput appDispInfoNoDateOutput, boolean mode);
	
	/**
	 * 社員IDから申請承認設定情報の取得
	 * @param companyID 会社ID
	 * @param employeeID 社員ID
	 * @param date 基準日
	 * @return
	 */
	public ApprovalFunctionSetting getApprovalFunctionSet(String companyID, String employeeID, GeneralDate date);
	
	/**
	 * 12_承認ルートを取得
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
	 * 申請表示情報(申請対象日関係あり)を取得する
	 * @param companyID 会社ID
	 * @param employeeID 社員ID
	 * @param dateLst 申請対象日リスト<Optional>
	 * @param appType 申請種類
	 * @param prePostAtrDisp 事前事後区分表示
	 * @param initValueAtr 事前事後区分の初期表示
	 * @return
	 */
	public AppDispInfoWithDateOutput getAppDispInfoRelatedDate(String companyID, String employeeID, List<GeneralDate> dateLst, 
			ApplicationType appType, DisplayAtr prePostAtrDisp, PrePostInitialAtr initValueAtr);
	
	/**
	 * 起動時の申請表示情報を取得する
	 * @param companyID 会社ID
	 * @param appType 申請種類
	 * @param applicantLst 申請者リスト
	 * @param dateLst 申請対象日リスト
	 * @param mode 新規詳細モード(新規モード/詳細モード) 新規モード: true/詳細モード: false
	 * @return
	 */
	public AppDispInfoStartupOutput getAppDispInfoStart(String companyID, ApplicationType appType, List<String> applicantLst, 
			List<GeneralDate> dateLst, boolean mode);
	
}
