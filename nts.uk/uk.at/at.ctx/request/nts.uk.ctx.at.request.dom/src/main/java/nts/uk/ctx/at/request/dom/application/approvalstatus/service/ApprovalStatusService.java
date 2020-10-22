package nts.uk.ctx.at.request.dom.application.approvalstatus.service;

import java.util.List;
import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailTemp;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailType;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApplicationsListOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttExecutionOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprovalStatusEmployeeOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprovalSttAppOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprovalSttByEmpListOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.DisplayWorkplace;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.EmployeeEmailOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.MailTransmissionContentOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.SendMailResultOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.UnApprovalSendMail;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.WorkplaceInfor;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeEmailImport;

public interface ApprovalStatusService {
	/**
	 * 承認状況取得職場社員
	 * 
	 * @param wkpId
	 *            職場ID
	 * @param closurePeriod
	 *            期間(開始日～終了日)
	 * @param listEmpCd
	 *            雇用コード(リスト)
	 * @return 社員ID＜社員ID、期間＞(リスト)
	 */
	List<ApprovalStatusEmployeeOutput> getApprovalStatusEmployee(String wkpId, GeneralDate closureStart,
			GeneralDate closureEnd, List<String> listEmpCd);

	/**
	 * アルゴリズム「承認状況取得申請承認」を実行する
	 * 
	 * @param wkpInfoDto
	 * @return ApprovalSttAppDto
	 */
	ApprovalSttAppOutput getApprovalSttApp(WorkplaceInfor wkpInfor,
			List<ApprovalStatusEmployeeOutput> listAppStatusEmp);

	/**
	 * 承認状況社員メールアドレス取得
	 * 
	 * @return 取得社員ID＜社員ID、社員名、メールアドレス＞
	 */
	List<EmployeeEmailImport> findEmpMailAddr(List<String> listsId);

	/**
	 * 承認状況メール本文取得
	 * 
	 * @param type
	 *            メール種類
	 * @return ドメイン：承認状況メールテンプレート
	 */
	ApprovalStatusMailTemp getApprovalStatusMailTemp(int type);

	/**
	 * 承認状況メールテスト送信実行
	 * 
	 * @param mailType
	 *            対象メール
	 */
	SendMailResultOutput sendTestMail(int mailType);

	/**
	 * 承認状況メール送信実行
	 * 
	 * @param listMailContent
	 *            メール送信内容＜社員ID、社員名、メールアドレス、件名、送信本文＞(リスト)
	 * @param domain
	 *            承認状況メールテンプレート
	 */
	SendMailResultOutput exeApprovalStatusMailTransmission(List<MailTransmissionContentOutput> listMailContent,
			ApprovalStatusMailTemp domain, ApprovalStatusMailType mailType);

	/**
	 * 承認状況送信者メール確認
	 */
	String confirmApprovalStatusMailSender();

	/**
	 * アルゴリズム「承認状況未承認メール送信」を実行する
	 */
	List<String> getAppSttSendingUnapprovedMail(List<UnApprovalSendMail> listAppSttApp);

	/**
	 * アルゴリズム「承認状況社員メールアドレス取得」を実行する RequestList #126
	 * 
	 * @return 取得社員ID＜社員ID、社員名、メールアドレス＞
	 */
	EmployeeEmailOutput findEmpMailAddr();

	/**
	 * アルゴリズム「承認状況未承認メール送信実行」を実行する
	 * 
	 * @param unAppMailTransmis
	 * @return 
	 */
	SendMailResultOutput exeSendUnconfirmedMail(List<String> listWkpId, GeneralDate closureStart, GeneralDate closureEnd, List<String> listEmpCd);

	/**
	 * アルゴリズム「承認状況社員別一覧作成」を実行する
	 * @return 
	 */
	List<ApprovalSttByEmpListOutput> getApprovalSttById(String selectedWkpId, List<String> listWkpId,
			GeneralDate startDate, GeneralDate endDate, List<String> listEmpCode);

	/**
	 * アルゴリズム「承認状況申請内容表示」を実行する
	 */
	ApplicationsListOutput initApprovalSttRequestContentDis(List<ApprovalStatusEmployeeOutput> listStatusEmp);
	
	// refactor 5
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.B:承認・確認状況の照会.アルゴリズム.B:状況取得_実行.B:状況取得_実行
	 * @param param
	 * @param wkpIDLst
	 */
	public List<ApprSttExecutionOutput> getStatusExecution(List<DisplayWorkplace> wkpInfoLst);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.B:承認・確認状況の照会.アルゴリズム.B:状況取得_表示対象データの取得.B:状況取得_表示対象データの取得
	 * @param param
	 * @param wkpIDLst
	 */
	public List<ApprSttExecutionOutput> getStatusDisplayData(List<DisplayWorkplace> wkpInfoLst);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.B:承認・確認状況の照会.アルゴリズム.B:状況取得_共通処理.B:状況取得_共通処理
	 * @param param
	 * @param wkpIDLst
	 */
	public List<ApprSttExecutionOutput> getStatusCommonProcess(List<DisplayWorkplace> displayWorkplaceLst);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.B:承認・確認状況の照会.アルゴリズム.B:状況取得_申請承認.B:状況取得_申請承認
	 * @param start
	 * @param end
	 * @return
	 */
	public Map<String, Integer> getStatusApplicationApproval(DatePeriod period);
	
}
