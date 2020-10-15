package nts.uk.ctx.at.request.dom.application.common.service.print;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.印刷内容を取得する
 * @author Doan Duy Hung
 *
 */
public interface CommonAppPrint {
	
	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.印刷内容を取得する.印刷内容を取得する
	 * @param companyID 会社ID
	 * @param appID 申請ID
	 * @param appDispInfoStartupOutput 申請表示情報
	 * @param opPrintContentOfEachApp 各申請の印刷内容＜Optional＞
	 * @return
	 */
	public PrintContentOfApp print(String companyID, String appID, AppDispInfoStartupOutput appDispInfoStartupOutput,
			Optional<PrintContentOfEachApp> opPrintContentOfEachApp);
	
	/**
	 * refactor 4
	 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.印刷内容を取得する.印字する承認者を取得する.印字する承認者を取得する
	 * @param approvalLst 承認ルートインスタンス
	 * @param date 基準日
	 * @return
	 */
	public ApproverColumnContents getApproverToPrint(List<ApprovalPhaseStateImport_New> approvalLst, GeneralDate date);
}
