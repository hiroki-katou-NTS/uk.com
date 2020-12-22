package nts.uk.ctx.at.request.dom.application.gobackdirectly.service;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.ScBasicScheduleImport_Old;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly_Old;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.InforGoBackCommonDirectOutput;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSetting;

public interface GoBackDirectlyRegisterService {
	/**
	 * 直行直帰登録
	 * 
	 * @param employeeID
	 * @param application
	 * @param goBackDirectly
	 */
	public ProcessResult register(GoBackDirectly_Old goBackDirectly, Application application);
	
	/**
	 * 
	 * @param goBackDirectly
	 * @param application
	 * @param appApprovalPhases
	 * @return 
	 */
	public List<ConfirmMsgOutput> checkBeforRegister(GoBackDirectly_Old goBackDirectly, Application application, boolean checkOver1Year);

	/**
	 * アルゴリズム「直行直帰するチェック」を実行する
	 * 
	 * @param goBackDirectly
	 * @param goBackAtr
	 */
	public GoBackDirectAtr goBackDirectCheck(GoBackDirectly_Old goBackDirectly);

	/**
	 * 直行直帰遅刻早退のチェック
	 * 
	 * @param goBackDirectly
	 * @return
	 */
	public GoBackDirectLateEarlyOuput goBackDirectLateEarlyCheck(GoBackDirectly_Old goBackDirectly, Application application);

	/**
	 * Check Validity
	 * 
	 */
	public CheckValidOutput goBackLateEarlyCheckValidity(GoBackDirectly_Old goBackDirectly,
			GoBackDirectlyCommonSetting goBackCommonSet, int line, ScBasicScheduleImport_Old scBasicScheduleImport);
	
	public void createThrowMsg(String msgConfirm, List<String> msgLst);
	
	/**
	 * 直行直帰申請日の矛盾チェック
	 * @param companyID
	 * @param employeeID
	 * @param appDate
	 * @return
	 */
	public List<String> inconsistencyCheck(String companyID, String employeeID, GeneralDate appDate);
	/**
	 * 
	 * @param companyId
	 * @param agentAtr
	 * @param application
	 * @param goBackDirectly
	 * @param inforGoBackCommonDirectOutput
	 * @param mode
	 * @return
	 */
	public List<ConfirmMsgOutput> checkBeforRegisterNew(String companyId, boolean agentAtr, Application application,  GoBackDirectly goBackDirectly, InforGoBackCommonDirectOutput inforGoBackCommonDirectOutput, boolean mode);
	/**
	 * 「直行直帰登録」処理を実行する
	 * @param companyId 会社ID
	 * @param application_New 申請
	 * @param goBackDirectly 直行直帰申請
	 * @param inforGoBackCommonDirectOutput 直行直帰申請起動時の表示情報
	 * @return メール送信の結果
	 */
	public ProcessResult registerNew(String companyId, Application application_New, GoBackDirectly_Old goBackDirectly, InforGoBackCommonDirectOutput_Old inforGoBackCommonDirectOutput);
	/**
	 * Refactor4 
	 * UKDesign.UniversalK.就業.KAF_申請.KAF009_直行直帰の申請.A:直行直帰の申請（新規）.アルゴリズム.直行直帰するチェック
	 * 直行直帰するチェック
	 * @param goBackDirectly
	 * @return
	 */
	public GoBackDirectAtr goBackDirectCheckNew(GoBackDirectly goBackDirectly);
	
	/**refactor4 
	 * 直行直帰登録
	 * @param goBackDirectly
	 * @param application
	 * @return
	 */
	public ProcessResult register(GoBackDirectly goBackDirectly, Application application, InforGoBackCommonDirectOutput inforGoBackCommonDirectOutput); 
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF009_直行直帰の申請.A:直行直帰の申請（新規）.アルゴリズム.直行直帰登録
	 * @param companyId
	 * @param application
	 * @param goBackDirectly
	 * @param inforGoBackCommonDirectOutput
	 * @return
	 */
	public ProcessResult update(String companyId, Application application, GoBackDirectly goBackDirectly, InforGoBackCommonDirectOutput inforGoBackCommonDirectOutput); 
	
	
}
