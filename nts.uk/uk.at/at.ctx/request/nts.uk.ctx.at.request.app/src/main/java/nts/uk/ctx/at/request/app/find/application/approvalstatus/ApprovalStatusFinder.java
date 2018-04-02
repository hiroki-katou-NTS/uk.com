package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.gul.mail.send.MailContents;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailTemp;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailTempRepository;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeEmailImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalStatusForEmployee_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproveRootStatusForEmpImport;
import nts.uk.ctx.at.shared.app.find.pattern.monthly.setting.Period;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ApprovalComfirmDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureHistoryForComDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosuresDto;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.mail.MailSender;
import nts.uk.shr.com.mail.SendMailFailedException;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
/**
 * 承認状況メールテンプレート
 * 
 * @author dat.lh
 */
public class ApprovalStatusFinder {

	@Inject
	private ApprovalStatusMailTempRepository finder;

	@Inject
	private EmployeeRequestAdapter employeeRequestAdapter;
	
	@Inject 
	private ApprovalRootStateAdapter approvalRootStateAdapter;

	@Inject
	private MailSender mailsender;
	
	@Inject
	private ClosureService closureService;
	
	/** The repository. */
	@Inject
	private ClosureRepository repository;
	
	@Inject
	ClosureEmploymentRepository closureEmpRepo;

	public ApprovalStatusMailTempDto findByType(int mailType) {
		// 会社ID
		String cid = AppContexts.user().companyId();
		// ドメインモデル「承認状況メールテンプレート」を取得する
		Optional<ApprovalStatusMailTemp> domain = finder.getApprovalStatusMailTempById(cid, mailType);
		return domain.isPresent() ? ApprovalStatusMailTempDto.fromDomain(domain.get()) : null;
	}

	/**
	 * アルゴリズム「承認状況本文起動」を実行する
	 * 
	 * @return
	 */
	public List<ApprovalStatusMailTempDto> findBySetting() {
		// 会社ID
		String cid = AppContexts.user().companyId();
		List<ApprovalStatusMailTempDto> listMail = new ArrayList<ApprovalStatusMailTempDto>();

		UseSetingDto transmissionAttr = this.getUseSeting();

		listMail.add(this.getApprovalStatusMailTemp(cid, 0));
		if (transmissionAttr.isUsePersonConfirm()) {
			listMail.add(this.getApprovalStatusMailTemp(cid, 1));
		}
		if (transmissionAttr.isUseBossConfirm()) {
			listMail.add(this.getApprovalStatusMailTemp(cid, 2));
		}
		if (transmissionAttr.isMonthlyConfirm()) {
			listMail.add(this.getApprovalStatusMailTemp(cid, 3));
		}
		listMail.add(this.getApprovalStatusMailTemp(cid, 4));

		return listMail;
	}

	/**
	 * アルゴリズム「承認状況メール本文取得」を実行する
	 * 
	 * @param cid
	 *            会社ID
	 * @param mailType
	 *            メール種類
	 * @return ドメイン：承認状況メールテンプレート
	 */
	private ApprovalStatusMailTempDto getApprovalStatusMailTemp(String cid, int mailType) {
		Optional<ApprovalStatusMailTemp> domain = finder.getApprovalStatusMailTempById(cid, mailType);
		return domain.isPresent() ? ApprovalStatusMailTempDto.fromDomain(domain.get())
				: new ApprovalStatusMailTempDto(mailType, 1, 1, 1, "", "", 0);
	}

	/**
	 * アルゴリズム「承認状況社員メールアドレス取得」を実行する RequestList #126
	 * 
	 * @return 取得社員ID＜社員ID、社員名、メールアドレス＞
	 */
	public EmployeeEmailDto findEmpMailAddr() {
		String cId = AppContexts.user().employeeId();
		List<String> listCId = new ArrayList<String>();
		listCId.add(cId);
		Optional<EmployeeEmailImport> employee = employeeRequestAdapter.getApprovalStatusEmpMailAddr(listCId).stream()
				.findFirst();
		return employee.isPresent() ? EmployeeEmailDto.fromImport(employee.get()) : null;
	}

	/**
	 * アルゴリズム「承認状況メールテスト送信実行」を実行する
	 * 
	 * @param mailType
	 *            対象メール
	 * @return 結果
	 */
	public boolean sendTestMail(int mailType) {
		// 会社ID
		String cid = AppContexts.user().companyId();
		// ドメインモデル「承認状況メールテンプレート」を取得する
		ApprovalStatusMailTemp domain = finder.getApprovalStatusMailTempById(cid, mailType).get();
		// 社員ID
		String sid = AppContexts.user().employeeId();
		// 社員名
		String sName = employeeRequestAdapter.getEmployeeName(sid);
		// メールアドレス
		String mailAddr = employeeRequestAdapter.empEmail(sid);
		// 件名
		String subject = domain.getMailSubject().v();
		// 送信本文
		String text = domain.getMailContent().v();

		// ログイン者よりメール送信内容を作成する(create nội dung send mail theo người login)
		List<MailTransmissionContentDto> listMailContent = new ArrayList<MailTransmissionContentDto>();
		listMailContent.add(new MailTransmissionContentDto(sid, sName, mailAddr, subject, text));
		UseSetingDto transmissionAttr = this.getUseSeting();
		// アルゴリズム「承認状況メール送信実行」を実行する
		this.exeApprovalStatusMailTransmission(listMailContent, domain, transmissionAttr);
		return false;
	}

	/**
	 * アルゴリズム「承認状況メール送信実行」を実行する
	 * 
	 * @param listMailContent
	 *            メール送信内容＜社員ID、社員名、メールアドレス、件名、送信本文＞(リスト)
	 * @param domain
	 *            ドメイン：承認状況メールテンプレート
	 * @param transmissionAttr
	 *            送信区分（本人/日次/月次）
	 */
	private void exeApprovalStatusMailTransmission(List<MailTransmissionContentDto> listMailContent,
			ApprovalStatusMailTemp domain, UseSetingDto transmissionAttr) {
		for (MailTransmissionContentDto mailTransmission : listMailContent) {
			// アルゴリズム「承認状況メール埋込URL取得」を実行する

			EmbeddedUrlDto embeddedURL = this.getEmbeddedURL(mailTransmission.getSId(), domain, transmissionAttr);
			String s1 = embeddedURL.getURL();
			String s2 = embeddedURL.getURL2();
			try {
				mailsender.send("nts", mailTransmission.getMailAddr(),
						new MailContents(mailTransmission.getSubject(), mailTransmission.getText()));
			} catch (SendMailFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * アルゴリズム「承認状況メール埋込URL取得」を実行する
	 * 
	 * @param eid
	 * @param domain
	 * @param transmissionAttr
	 */
	private EmbeddedUrlDto getEmbeddedURL(String eid, ApprovalStatusMailTemp domain, UseSetingDto transmissionAttr) {
		String url1 = "123123123";
		String url2 = "1231ád23123";
		return new EmbeddedUrlDto(url1, url2);
	}

	/**
	 * アルゴリズム「承認状況取得実績使用設定」を実行する
	 * 
	 * @return
	 */
	private UseSetingDto getUseSeting() {
		// 月別確認を利用する ← 承認処理の利用設定.月の承認者確認を利用する
		boolean monthlyConfirm = true;
		// 上司確認を利用する ← 承認処理の利用設定.日の承認者確認を利用する
		boolean useBossConfirm = true;
		// 本人確認を利用する ← 本人確認処理の利用設定.日の本人確認を利用する
		boolean usePersonConfirm = true;
		// Waiting for Q&A
		return new UseSetingDto(monthlyConfirm, useBossConfirm, usePersonConfirm);
	}

	public List<ApprovalStatusActivityDto> getStatusActivity(ApprovalStatusActivityData wkpInfoDto) {
		UseSetingDto useSeting = this.getUseSeting();
		List<String> listWorkplaceId = wkpInfoDto.getListWorkplaceId();
		for (String wkpId : listWorkplaceId) {
			GeneralDate startDate = GeneralDate.fromString(wkpInfoDto.getStartDate(), "yyyy/MM/dd");
			GeneralDate endDate = GeneralDate.fromString(wkpInfoDto.getEndDate(), "yyyy/MM/dd");
			// アルゴリズム「承認状況取得社員」を実行する
			List<ApprovalStatusEmpDto> listStatusEmp = this.getApprovalSttEmp(wkpId, new Period(startDate, endDate),
					wkpInfoDto.getListEmpCd());
			// アルゴリズム「承認状況取得職場実績確認」を実行する
			this.getApprovalSttConfirmWkpResults(listStatusEmp, wkpId, useSeting);
		}
		return null;
	}

	/**
	 * アルゴリズム「承認状況取得社員」を実行する
	 * 
	 * @param wkpId
	 *            職場ID
	 * @param closurePeriod
	 *            期間(開始日～終了日)
	 * @param listEmpCd
	 *            雇用コード(リスト)
	 * @return 社員ID＜社員ID、期間＞(リスト)
	 */
	private List<ApprovalStatusEmpDto> getApprovalSttEmp(String wkpId, Period closurePeriod, List<String> listEmpCd) {
		List<ApprovalStatusEmpDto> listSttEmp = new ArrayList<ApprovalStatusEmpDto>();
		// imported(申請承認)「社員ID（リスト）」を取得する
		List<String> listSId = employeeRequestAdapter.getListSIdByWkpIdAndPeriod(wkpId, closurePeriod.getStartDate(),
				closurePeriod.getEndDate());
		// Waiting for Q&A
		Period entryLeavePeriod = new Period(GeneralDate.fromString("2018/01/01", "yyyy/MM/dd"),
				GeneralDate.fromString("2018/05/02", "yyyy/MM/dd"));
		// 社員ID(リスト)
		for (String sId : listSId) {
			// imported(就業)「所属雇用履歴」より雇用コードを取得する
			List<EmploymentDto> listEmpHist = new ArrayList<EmploymentDto>();
			// Waiting for Q&A
			Period empPeriod = new Period(GeneralDate.fromString("2018/01/02", "yyyy/MM/dd"),
					GeneralDate.fromString("2018/02/02", "yyyy/MM/dd"));

			// 雇用（リスト）
			for (EmploymentDto sttEmp : listEmpHist) {
				// 存在しない場合
				if (listEmpCd.contains(sttEmp.getEmpCd())) {
					continue;
				}
				// 存在する場合
				// アルゴリズム「承認状況対象期間取得」を実行する
				Period sttPeriod = this.getApprovalSttPeriod(sId, empPeriod, closurePeriod, entryLeavePeriod);
				listSttEmp.add(new ApprovalStatusEmpDto(sId, sttPeriod));
			}
		}
		return listSttEmp;
	}

	/**
	 * アルゴリズム「承認状況対象期間取得」を実行する
	 * 
	 * @param sId
	 *            社員ID
	 * @param empPeriod
	 *            雇用期間（開始日、終了日）
	 * @param closurePeriod
	 *            締め期間（開始日、終了日)
	 * @param inOutPeriod
	 *            入退社期間（入社年月日、退社年月日
	 * @return
	 */
	private Period getApprovalSttPeriod(String sId, Period empPeriod, Period closurePeriod, Period inOutPeriod) {
		GeneralDate startDate;
		GeneralDate endDate;
		// 雇用期間（開始日）≦締め期間（開始日）
		if (empPeriod.getStartDate().beforeOrEquals(closurePeriod.getStartDate())) {
			// 対象期間.開始日＝締め期間（開始日）
			startDate = closurePeriod.getStartDate();
		} else {
			// 対象期間.開始日＝雇用期間（開始日）
			startDate = empPeriod.getStartDate();
		}
		// 対象期間.開始日≦入退社期間（入社年月日）
		if (startDate.beforeOrEquals(inOutPeriod.getStartDate())) {
			// 対象期間.開始日＝入退社期間（入社年月日）
			startDate = inOutPeriod.getStartDate();
		}
		// 雇用期間（終了日）≧締め期間（終了日）
		if (empPeriod.getEndDate().afterOrEquals(closurePeriod.getEndDate())) {
			// 対象期間終了日＝締め期間（終了日）
			endDate = closurePeriod.getEndDate();
		} else {
			// 対象期間.終了日＝雇用期間（終了日）
			endDate = empPeriod.getEndDate();
		}
		// 対象期間.開始日≧入退社期間（退社年月日）
		if (endDate.afterOrEquals(inOutPeriod.getEndDate())) {
			// 対象期間.開始日＝入退社期間（退社年月日）
			endDate = inOutPeriod.getEndDate();
		}
		return new Period(startDate, endDate);
	}

	/**
	 * アルゴリズム「承認状況取得職場実績確認」を実行する
	 * 
	 * @param listEmp
	 *            社員ID＜社員ID、期間＞(リスト)
	 * @param wkpId
	 *            職場ID
	 * @param useSeting
	 *            月別確認を利用する/上司確認を利用する/本人確認を利用する
	 * @return
	 */
	private SumCountDto getApprovalSttConfirmWkpResults(List<ApprovalStatusEmpDto> listEmp,
			String wkpId, UseSetingDto useSeting) {
		SumCountDto sumCount = new SumCountDto();
		// 会社ID
		String cid = AppContexts.user().companyId();
		
		//社員ID(リスト)
		for (ApprovalStatusEmpDto emp : listEmp) {
			// 月別確認を利用する
			if (useSeting.isMonthlyConfirm()) {
				// imported(申請承認）「上司確認の状況」を取得する
				List<ApproveRootStatusForEmpImport> listApproval = approvalRootStateAdapter.getApprovalByEmplAndDate(
						emp.getClosurePeriod().getStartDate(),
						emp.getClosurePeriod().getEndDate(),
						emp.getSId(), cid, 2);
				ApproveRootStatusForEmpImport approval = listApproval.stream().findFirst().get();
				//承認状況＝「承認済」の場合(trạng thái approval = 「承認済」)
				if (ApprovalStatusForEmployee_New.APPROVED.equals( approval.getApprovalStatus())) {
					//月別確認件数　＝　＋１
					sumCount.monthConfirm ++;
				}
				//承認状況＝「未承認 」又は「承認中」の場合
				else{
					//月別未確認件数　＝　＋１
					sumCount.monthUnconfirm ++;
				}
			}

			// アルゴリズム「承認状況取得日別確認状況」を実行する
			this.getApprovalSttByDate(emp.getSId(), wkpId, emp.getClosurePeriod(), useSeting.isUseBossConfirm(),
					useSeting.isUsePersonConfirm(), sumCount);
			// 各件数を合算する
		}
		return sumCount;
	}
	
	/**
	 * アルゴリズム「承認状況取得日別確認状況」を実行する
	 * @param sId 社員ID
	 * @param wkpId 職場ID
	 * @param period 期間(開始日～終了日)
	 * @param useBossConfirm 上司確認を利用する
	 * @param usePersonConfirm 本人確認を利用する
	 * @param sumCount output
	 */
	private void getApprovalSttByDate(String sId, String wkpId, Period period, boolean useBossConfirm,
			boolean usePersonConfirm, SumCountDto sumCount) {
		// 期間範囲分の日別確認（リスト）を作成する(Tạo list confirm hàng ngày)
		List<DailyConfirmDto> listDailyConfirm = new ArrayList<DailyConfirmDto>();
		
		// 利用するの場合(use)
		if (useBossConfirm) {
			// アルゴリズム「承認状況取得日別上司承認状況」を実行する
			this.getApprovalSttByDateOfBoss(sId, wkpId, period, listDailyConfirm, sumCount);
		}
		// 利用しないの場合

		// 利用するの場合(use)
		if (usePersonConfirm) {
			// アルゴリズム「承認状況取得日別本人確認状況」を実行する
			this.getApprovalSttByDateOfPerson(sId, wkpId, period, listDailyConfirm, sumCount);
		}
		// 利用しないの場合
	}

	/**
	 * アルゴリズム「承認状況取得日別上司承認状況」を実行する
	 * @param sId 社員ID
	 * @param wkpId 職場ID
	 * @param period 期間(開始日～終了日)
	 * @param listDailyConfirm output 日別確認（リスト）＜職場ID、社員ID、対象日、本人確認、上司確認＞
	 * @param sumCount output 上司確認件数,上司未確認件数
	 */
	private void getApprovalSttByDateOfBoss(String sId, String wkpId, Period period,
			List<DailyConfirmDto> listDailyConfirm, SumCountDto sumCount) {
		// 会社ID
		String cid = AppContexts.user().companyId();
		// imported（ワークフロー）「承認ルート状況」を取得する
		List<ApproveRootStatusForEmpImport> listApproval = approvalRootStateAdapter
				.getApprovalByEmplAndDate(period.getStartDate(), period.getEndDate(), sId, cid, 0);
		//承認ルートの状況
		for (ApproveRootStatusForEmpImport approval : listApproval) {
			//日別確認（リスト）に同じ職場ID、社員ID、対象日が登録済
			Optional<DailyConfirmDto> confirm = listDailyConfirm.stream().filter(
					x -> x.getWkpId().equals(wkpId) && x.getSId().equals(sId) && x.getTargetDate().equals(period))
					.findFirst();
			//登録されていない場合(Chưa đăng ký
			if (!confirm.isPresent()) {
				// 日別確認（リスト）に追加する
				DailyConfirmDto newDailyConfirm = new DailyConfirmDto(wkpId, sId, period, false, false);
				listDailyConfirm.add(newDailyConfirm);
			}

			// 承認ルート状況.承認状況
			if (ApprovalStatusForEmployee_New.APPROVED.equals(approval.getApprovalStatus())) {
				// 「承認済」の場合
				// 上司確認件数 ＝＋１
				sumCount.bossConfirm ++;
			}
			else {
				// 「未承認」又は「承認中」の場合
				// 上司未確認件数 ＝＋１
				sumCount.bossUnconfirm ++;
			}
		}
	}

	/**
	 * アルゴリズム「承認状況取得日別本人確認状況」を実行する
	 * @param sId 社員ID
	 * @param wkpId 職場ID
	 * @param period 期間(開始日～終了日)
	 * @param listDailyConfirm output 日別確認（リスト）＜職場ID、社員ID、対象日、本人確認、上司確認＞
	 * @param sumCount output
	 */
	private void getApprovalSttByDateOfPerson(String sId, String wkpId, Period period,
			List<DailyConfirmDto> listDailyConfirm, SumCountDto sumCount) {
	}
	
	/**
	 * アルゴリズム「承認状況指定締め日取得」を実行する
	 * Acquire approval situation designated closing date
	 * @return approval situation
	 */
	public ApprovalComfirmDto findAllClosure() {
		// Get companyID.
		String companyId = AppContexts.user().companyId();
		GeneralDate startDate = null;
		GeneralDate endDate = null;
		int processingYm = 0;
		List<ClosureEmployment> listEmployeeCode = new ArrayList<>();
		//ドメインモデル「就業締め日」を取得する　<shared>
		List<Closure> closureList = this.repository.findAllUse(companyId);
		int selectedClosureId = 0;
		List<ClosuresDto> closureDto = closureList.stream().map(x -> {
			int closureId = x.getClosureId().value;
			List<ClosureHistoryForComDto> closureHistoriesList = x.getClosureHistories().stream().map(x1 -> {
				return new ClosureHistoryForComDto( x1.getClosureName().v(), x1.getClosureId().value, x1.getEndYearMonth().v().intValue(), x1.getClosureDate().getClosureDay().v().intValue(), x1.getStartYearMonth().v().intValue());
			}).collect(Collectors.toList());
			ClosureHistoryForComDto closureHistories = closureHistoriesList.stream()
					.filter(x2 -> x2.getClosureId() == closureId).findFirst().orElse(null);
			return new ClosuresDto(closureId, closureHistories.getCloseName(), closureHistories.getClosureDate());
		}).collect(Collectors.toList());
		
		//ユーザー固有情報「選択中の就業締め」を取得する
		//TODO neeed to get closureId init
		
		
		//就業締め日（リスト）の先頭の締めIDを選択
		Optional<ClosuresDto> closure = closureDto.stream().findFirst();
		if (closure.isPresent()) {
			val closureId = closure.get().getClosureId();
			selectedClosureId = closureId;
			val closureOpt = this.repository.findById(companyId, closureId);
			if (closureOpt.isPresent()) {
				val closureItem = closureOpt.get();
				// 当月の期間を算出する
				val yearMonth = closureItem.getClosureMonth().getProcessingYm();
				processingYm = yearMonth.v();
				//アルゴリズム「承認状況指定締め期間設定」を実行する
				//アルゴリズム「当月の期間を算出する」を実行する
				DatePeriod closurePeriod = this.closureService.getClosurePeriod(closureId, yearMonth);
				startDate = closurePeriod.start();
				endDate = closurePeriod.end();
				//ドメインモデル「雇用に紐づく就業締め」より、雇用コードと締めIDを取得する
				listEmployeeCode = closureEmpRepo.findByClosureId(companyId, closureId);
			} else {
				throw new RuntimeException("Could not find closure");
			}
		}
		return new ApprovalComfirmDto(selectedClosureId, closureDto, startDate, endDate, processingYm, listEmployeeCode);
	}
	
	/**
	 * アルゴリズム「承認状況指定締め期間設定」を実行する
	 * @param closureId
	 * @param closureDate
	 * @return
	 */
	public ApprovalStatusPeriorDto getApprovalStatusPerior(int closureId, int closureDate) {
		// Get companyID.
		String companyId = AppContexts.user().companyId();
		GeneralDate startDate = null;
		GeneralDate endDate = null;
		int processingYmNew = 0;
		// 当月の期間を算出する
		YearMonth processingYm = new YearMonth(closureDate);
		
		List<ClosureEmployment> listEmployeeCode = new ArrayList<>();
		Optional<Closure> closure = repository.findById(companyId, closureId);
		if(!closure.isPresent()){
			throw new RuntimeException("Could not find closure");
		}
		
		val yearMonth = closure.get().getClosureMonth().getProcessingYm();
		processingYmNew = yearMonth.v();
		//アルゴリズム「当月の期間を算出する」を実行する
		DatePeriod closurePeriod = this.closureService.getClosurePeriod(closureId, processingYm);
		startDate = closurePeriod.start();
		endDate = closurePeriod.end();
		//ドメインモデル「雇用に紐づく就業締め」より、雇用コードと締めIDを取得する
		listEmployeeCode = closureEmpRepo.findByClosureId(companyId, closureId);
		return new ApprovalStatusPeriorDto(startDate, endDate, listEmployeeCode, processingYmNew);
	}
}
