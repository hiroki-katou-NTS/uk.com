package nts.uk.ctx.at.request.dom.application.approvalstatus.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailTemp;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailType;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttComfirmSet;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttConfirmEmp;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttConfirmEmpMonthDay;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttContentPrepareOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttEmp;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttEmpDate;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttEmpDateContent;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttExecutionOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttSendMailInfoOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttWkpEmpMailOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprovalStatusEmployeeOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ConfirmWorkplaceInfoOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.DailyConfirmOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.DisplayWorkplace;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.EmpPeriod;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.EmployeeEmailOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.MailTransmissionContentOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.PhaseApproverStt;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.SendMailResultOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.UnApprovalSendMail;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeEmailImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AppRootInsImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootStateImport_New;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

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
	SendMailResultOutput sendTestMail(int mailType, boolean screenUrlApprovalEmbed, boolean screenUrlDayEmbed, boolean screenUrlMonthEmbed);

	/**
	 * 承認状況メール送信実行
	 * 
	 * @param listMailContent
	 *            メール送信内容＜社員ID、社員名、メールアドレス、件名、送信本文＞(リスト)
	 *        transmissionAtr 送信区分: false = 本人, true = 上長
	 * @param domain
	 *            承認状況メールテンプレート
	 */
	SendMailResultOutput exeApprovalStatusMailTransmission(List<MailTransmissionContentOutput> listMailContent, ApprovalStatusMailTemp domain,
			boolean transmissionAtr, boolean screenUrlApprovalEmbed, boolean screenUrlDayEmbed, boolean screenUrlMonthEmbed);

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
	
	// refactor 5
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.B:承認・確認状況の照会.アルゴリズム.B:状況取得_実行.B:状況取得_実行
	 * @param param
	 * @param wkpIDLst
	 */
	public List<ApprSttExecutionOutput> getStatusExecution(ClosureId closureId, YearMonth processingYm, DatePeriod period, 
			InitDisplayOfApprovalStatus initDisplayOfApprovalStatus, List<DisplayWorkplace> displayWorkplaceLst, 
			List<String> employmentCDLst, ApprSttComfirmSet apprSttComfirmSet);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.B:承認・確認状況の照会.アルゴリズム.B:状況取得_表示対象データの取得.B:状況取得_表示対象データの取得
	 * @param param
	 * @param wkpIDLst
	 */
	public List<ApprSttExecutionOutput> getStatusDisplayData(ClosureId closureId, YearMonth processingYm, DatePeriod period, 
			InitDisplayOfApprovalStatus initDisplayOfApprovalStatus, List<DisplayWorkplace> displayWorkplaceLst, 
			List<String> employmentCDLst, ApprSttComfirmSet apprSttComfirmSet);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.B:承認・確認状況の照会.アルゴリズム.B:状況取得_共通処理.B:状況取得_共通処理
	 * @param param
	 * @param wkpIDLst
	 */
	public List<ApprSttExecutionOutput> getStatusCommonProcess(ClosureId closureId, YearMonth processingYm,
			DatePeriod period, List<DisplayWorkplace> displayWorkplaceLst, List<String> employmentCDLst);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.B:承認・確認状況の照会.アルゴリズム.B:状況取得_申請承認.B:状況取得_申請承認
	 * @param start
	 * @param end
	 * @return
	 */
	public Map<String, Integer> getStatusApplicationApproval(DatePeriod period, List<DisplayWorkplace> displayWorkplaceLst, List<String> employmentCDLst);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.B:承認・確認状況の照会.アルゴリズム.B:状況取得_日別実績.B:状況取得_日別実績
	 * @param period
	 * @param displayWorkplaceLst
	 * @param employmentCDLst
	 * @param apprSttComfirmSet
	 * @return
	 */
	public Map<String, Pair<Integer, Integer>> getStatusDayConfirmApproval(DatePeriod period, List<DisplayWorkplace> displayWorkplaceLst, List<String> employmentCDLst,
			ApprSttComfirmSet apprSttComfirmSet);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.B:承認・確認状況の照会.アルゴリズム.B:状況取得_月別実績.B:状況取得_月別実績
	 * @param period
	 * @param displayWorkplaceLst
	 * @param employmentCDLst
	 * @param apprSttComfirmSet
	 * @return
	 */
	public Map<String, Pair<Integer, Integer>> getStatusMonthConfirmApproval(DatePeriod period, YearMonth processingYm, 
			List<DisplayWorkplace> displayWorkplaceLst, List<String> employmentCDLst, ApprSttComfirmSet apprSttComfirmSet);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.B:承認・確認状況の照会.アルゴリズム.B:状況取得_就業確定.B:状況取得_就業確定
	 * @param period
	 * @param displayWorkplaceLst
	 * @param employmentCDLst
	 * @param apprSttComfirmSet
	 * @return
	 */
	public Map<String, Pair<String, GeneralDateTime>> getStatusEmploymentConfirm(ClosureId closureId, YearMonth yearMonth, List<DisplayWorkplace> displayWorkplaceLst);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.D:社員別申請承認状況ダイアログ.アルゴリズム.D:承認状況社員別起動.D:承認状況社員別起動
	 * @param wkpID
	 * @param period
	 * @param empPeriodLst
	 * @return
	 */
	public List<ApprSttEmp> getApprSttStartByEmp(String wkpID, DatePeriod period, List<EmpPeriod> empPeriodLst);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.D:社員別申請承認状況ダイアログ.アルゴリズム.D:承認状況社員別一覧作成.D:承認状況社員別一覧作成
	 * @param wkpID
	 * @param paramPeriod
	 * @param empPeriodLst
	 * @return
	 */
	public List<ApprSttEmp> getAppSttCreateByEmpLst(String wkpID, DatePeriod paramPeriod, List<EmpPeriod> empPeriodLst);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.D:社員別申請承認状況ダイアログ.アルゴリズム.D:承認状況対象期間取得.D:承認状況対象期間取得
	 * @param employeeID 社員ID
	 * @param employmentPeriod 雇用期間（開始日、終了日）
	 * @param closurePeriod 締め期間（開始日、終了日)
	 * @param inoutPeriod 入退社期間（入社年月日、退社年月日）
	 * @return
	 */
	public DatePeriod getApprSttTargetPeriod(String employeeID, DatePeriod employmentPeriod, DatePeriod closurePeriod, DatePeriod inoutPeriod);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.D:社員別申請承認状況ダイアログ.アルゴリズム.D:承認状況取得申請.D:承認状況取得申請
	 * @param employeeID 社員ID
	 * @param period 期間
	 * @return
	 */
	public List<Pair<Application,List<ApprovalPhaseStateImport_New>>> getApprSttApplication(String employeeID, DatePeriod period);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.D:社員別申請承認状況ダイアログ.アルゴリズム.D:承認状況日別状態作成.D:承認状況日別状態作成
	 * @param employeeID
	 * @param period
	 * @param appPairLst
	 * @return
	 */
	public List<ApprSttEmpDate> createApprSttByDate(String employeeID, DatePeriod period, List<Pair<Application,List<ApprovalPhaseStateImport_New>>> appPairLst);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.E:申請承認内容ダイアログ.アルゴリズム.E:承認状況申請内容表示.E:承認状況申請内容表示
	 * @param employeeID
	 * @param periodLst
	 * @return
	 */
	public List<ApprSttEmpDateContent> getApprSttAppContent(String employeeID, List<DatePeriod> periodLst);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.E:申請承認内容ダイアログ.アルゴリズム.E:承認状況申請内容追加.E:承認状況申請内容追加
	 * @param mapAppLst
	 */
	public List<ApprSttEmpDateContent> getApprSttAppContentAdd(List<Pair<Application,List<ApprovalPhaseStateImport_New>>> appPairLst);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.E:申請承認内容ダイアログ.アルゴリズム.E:申請内容編集準備情報の取得.E:申請内容編集準備情報の取得
	 * @param companyID
	 * @return
	 */
	public ApprSttContentPrepareOutput getApprSttAppContentPrepare(String companyID);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.E:申請承認内容ダイアログ.アルゴリズム.E:承認状況申請承認者取得.E:承認状況申請承認者取得
	 * @param appPair 申請承認内容
	 * @return
	 */
	public List<PhaseApproverStt> getApplicationApproverStt(Pair<Application,List<ApprovalPhaseStateImport_New>> appPair);
	
	/**
	 * C:メール送信_画面表示処理
	 * @param mailType
	 * @param apprSttExecutionOutputLst
	 */
	public ApprSttSendMailInfoOutput getApprSttSendMailInfo(ApprovalStatusMailType mailType, ClosureId closureId, YearMonth processingYm,
			DatePeriod period, List<DisplayWorkplace> displayWorkplaceLst, List<String> employmentCDLst, ClosureDate closureDate);
	
	/**
	 * C:メール送信_対象再取得_申請
	 * @return
	 */
	public List<EmpPeriod> getMailCountUnApprApp(DatePeriod period, List<DisplayWorkplace> displayWorkplaceLst, List<String> employmentCDLst);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.C:メール送信ダイアログ.アルゴリズム.C:メール送信_対象再取得_日別本人.C:メール送信_対象再取得_日別本人
	 * @param period
	 * @param displayWorkplaceLst
	 * @param employmentCDLst
	 * @return
	 */
	public List<Pair<String, String>> getMailCountUnConfirmDay(DatePeriod period, List<DisplayWorkplace> displayWorkplaceLst, List<String> employmentCDLst);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.C:メール送信ダイアログ.アルゴリズム.C:メール送信_対象再取得_日別上長.C:メール送信_対象再取得_日別上長
	 * @param period
	 * @param displayWorkplaceLst
	 * @param employmentCDLst
	 * @return
	 */
	public List<Pair<String, Pair<String, GeneralDate>>> getMailCountUnApprDay(DatePeriod period, List<DisplayWorkplace> displayWorkplaceLst, List<String> employmentCDLst);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.C:メール送信ダイアログ.アルゴリズム.C:メール送信_対象再取得_月別本人.C:メール送信_対象再取得_月別本人
	 * @param period
	 * @param displayWorkplaceLst
	 * @param employmentCDLst
	 * @return
	 */
	public List<Pair<String, String>> getMailCountUnConfirmMonth(DatePeriod period, List<DisplayWorkplace> displayWorkplaceLst, List<String> employmentCDLst);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.C:メール送信ダイアログ.アルゴリズム.C:メール送信_対象再取得_月別上長.C:メール送信_対象再取得_月別上長
	 * @param period
	 * @param displayWorkplaceLst
	 * @param employmentCDLst
	 * @return
	 */
	public List<Pair<String, String>> getMailCountUnApprMonth(DatePeriod period, YearMonth processingYm, List<DisplayWorkplace> displayWorkplaceLst, List<String> employmentCDLst);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.C:メール送信ダイアログ.アルゴリズム.C:メール送信_対象再取得_就業確定.C:メール送信_対象再取得_就業確定
	 * @param period
	 * @param closureId
	 * @param yearMonth
	 * @param companyID
	 * @param wkpIDLst
	 * @param employmentCDLst
	 * @return
	 */
	public Map<String, List<String>> getMailCountWorkConfirm(DatePeriod period, ClosureId closureId, YearMonth yearMonth, 
			String companyID, List<String> wkpIDLst, List<String> employmentCDLst);
	
	/**
	 * C:メール送信承認者を取得
	 */
	public List<ApprSttWkpEmpMailOutput> getAppApproverToSendMail(List<ApprSttExecutionOutput> apprSttExecutionOutputLst, DatePeriod paramPeriod);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.C:メール送信ダイアログ.アルゴリズム.C:メール送信_日別上長の情報を取得.C:メール送信_日別上長の情報を取得
	 * @param apprSttExecutionOutputLst
	 * @param paramPeriod
	 * @return
	 */
	public List<ApprSttWkpEmpMailOutput> getDayApproverToSendMail(List<Pair<String, Pair<String, GeneralDate>>> lstDayApproval);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.C:メール送信ダイアログ.アルゴリズム.C:メール送信_月別上長の情報を取得.C:メール送信_月別上長の情報を取得
	 * @param apprSttExecutionOutputLst
	 * @param paramPeriod
	 * @return
	 */
	public List<ApprSttWkpEmpMailOutput> getMonthApproverToSendMail(List<Pair<String, String>> lstMonthApproval, DatePeriod paramPeriod,
			Integer closureID, YearMonth yearMonth, ClosureDate closureDate);
	
	/**
	 * C:メール送信_本人の情報を取得
	 * @param wkpID
	 * @param employeeID
	 */
	public List<ApprSttWkpEmpMailOutput> getPersonInfo(List<ApprSttWkpEmpMailOutput> wkpEmpMailLst);
	
	/**
	 * C:メール送信_対象者へメール送信
	 */
	public SendMailResultOutput sendMailToDestination(ApprovalStatusMailTemp approvalStatusMailTemp, List<ApprSttWkpEmpMailOutput> wkpEmpMailLst,
			boolean screenUrlApprovalEmbed, boolean screenUrlDayEmbed, boolean screenUrlMonthEmbed);
	
	/**
	 * 承認状況未承認申請取得
	 * @param empPeriodLst
	 * @return
	 */
	public List<String> getApprSttUnapprovedApp(List<ApprovalStatusEmployeeOutput> approvalStatusEmployeeLst);
	
	/**
	 * 承認状況未承認メール対象者取得
	 * @param phaseLst
	 * @return
	 */
	public List<String> getApprSttUnapprovedAppTarget(List<ApprovalPhaseStateImport_New> phaseLst, GeneralDate appDate);
	
	/**
	 * 承認状況未承認メール未承認者取得
	 * @param approverIDLst
	 * @param appDate
	 * @return
	 */
	public List<String> getApprSttUnapprovedAppPerson(List<String> approverIDLst, GeneralDate appDate);
	
	/**
	 * F:承認状況社員実績起動
	 * @param wkpID
	 * @param paramPeriod
	 * @param empPeriodLst
	 * @param apprSttComfirmSet
	 * @param yearMonth
	 * @param closureId
	 * @param closureDate
	 * @return
	 */
	public List<ApprSttConfirmEmp> getConfirmApprSttByEmp(String wkpID, DatePeriod paramPeriod, List<EmpPeriod> empPeriodLst, ApprSttComfirmSet apprSttComfirmSet,
			YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate);
	
	/**
	 * F:承認状況取得職場社員実績一覧
	 * @param wkpID
	 * @param paramPeriod
	 * @param empPeriodLst
	 * @return
	 */
	public List<ApprSttConfirmEmp> getConfirmApprSttByEmpLst(String wkpID, DatePeriod paramPeriod, List<EmpPeriod> empPeriodLst, ApprSttComfirmSet apprSttComfirmSet,
			YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate);
	
	/**
	 * F:承認状況取得日別確認状況
	 * @return
	 */
	public List<DailyConfirmOutput> createConfirmApprSttByDate(String wkpID, String employeeID, DatePeriod period, ApprSttComfirmSet apprSttComfirmSet);
	
	/**
	 * G:実績承認詳細情報取得
	 * @param employeeID
	 * @param periodLst
	 */
	public ApprSttConfirmEmpMonthDay getConfirmApprSttContent(String wkpID, String employeeID, DatePeriod period, ApprSttComfirmSet apprSttComfirmSet,
			YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate);
	
	/**
	 * G:月別本人確認を取得する
	 */
	public boolean getMonthConfirm(String companyID, String employeeID, ApprSttComfirmSet apprSttComfirmSet, 
			YearMonth yearMonth, ClosureId closureId);
	
	/**
	 * G:月別上長承認進捗状況を取得する
	 */
	public Integer getMonthApprovalTopStatus(String employeeID, DatePeriod paramPeriod, ApprSttComfirmSet apprSttComfirmSet,
			YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate);
	
	/**
	 * G:月別承認の進捗を取得する
	 */
	public Optional<ApprovalRootStateImport_New> getMonthApprovalStatus(String employeeID, DatePeriod period, YearMonth yearMonth, Integer closureID,
			ClosureDate closureDate, GeneralDate baseDate, ApprSttComfirmSet apprSttComfirmSet);
	
	/**
	 * G:月別の承認者を取得する
	 */
	public List<PhaseApproverStt> getMonthApproval(ApprSttComfirmSet apprSttComfirmSet, String employeeID, DatePeriod period);
	
	/**
	 * G:社員の月別実績の進捗状況を得る
	 */
	public List<PhaseApproverStt> getMonthAchievementApprover(String employeeID, DatePeriod period);
	
	/**
	 * G:実績承認者取得
	 */
	public List<PhaseApproverStt> getAchievementApprover(AppRootInsImport appRootInsImport, GeneralDate date);
	
	/**
	 * G:日別承認の進捗を取得する
	 */
	public List<ApprovalRootStateImport_New> getDayApprovalStatus(ClosureId closureId, String employeeID, DatePeriod period, ApprSttComfirmSet apprSttComfirmSet);
	
	/**
	 * G:日別の承認者を取得する
	 */
	public Map<GeneralDate, List<PhaseApproverStt>> getDayApproval(ApprSttComfirmSet apprSttComfirmSet, String employeeID, DatePeriod period);
	
	/**
	 * G:社員の日別実績の進捗状況を得る
	 */
	public Map<GeneralDate, List<PhaseApproverStt>> getDayAchievementApprover(String employeeID, DatePeriod period);
	
	/**
	 * H:就業確定ダイアログ表示
	 * @param wkpID
	 * @return
	 */
	public ConfirmWorkplaceInfoOutput getEmploymentConfirmInfo(String wkpID, String employeeID, String roleID);
}
