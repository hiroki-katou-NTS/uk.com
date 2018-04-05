package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.gul.mail.send.MailContents;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailTemp;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailTempRepository;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.ApprovalStatusService;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprovalStatusEmployeeOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprovalSttAppOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.EmployeeEmailOutput;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
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
	
	@Inject
	private ApprovalStatusService appSttService;

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
		return null;
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
		List<String> listEmpCode = new ArrayList<>();
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
				List<ClosureEmployment> listEmployee = closureEmpRepo.findByClosureId(companyId, closureId);
				for(ClosureEmployment emp: listEmployee) {
					listEmpCode.add(emp.getEmploymentCD());
				}
			} else {
				throw new RuntimeException("Could not find closure");
			}
		}
		return new ApprovalComfirmDto(selectedClosureId, closureDto, startDate, endDate, processingYm, listEmpCode);
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
		
		List<ClosureEmployment> listEmployee = new ArrayList<>();
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
		listEmployee = closureEmpRepo.findByClosureId(companyId, closureId);
		List<String> listEmpCode = new ArrayList<>();
		for(ClosureEmployment emp: listEmployee) {
			listEmpCode.add(emp.getEmploymentCD());
		}
		return new ApprovalStatusPeriorDto(startDate, endDate, listEmpCode, processingYmNew);
	}
	
	/**
	 * アルゴリズム「承認状況職場別起動」を実行する
	 * @param appStatus
	 */
	public List<ApprovalSttAppOutput> getAppSttByWorkpace(ApprovalStatusActivityData appStatus) {
		List<ApprovalSttAppOutput> listAppSttApp = new ArrayList<>();
		ApprovalSttAppOutput approvalSttApp = null;
		GeneralDate startDate = GeneralDate.fromString(appStatus.getStartDate(), "yyyy/MM/dd");
		GeneralDate endDate = GeneralDate.fromString(appStatus.getEndDate(), "yyyy/MM/dd");
		for (String wkpId : appStatus.getListWorkplaceId()) {
			List<ApprovalStatusEmployeeOutput> listAppStatusEmp = appSttService.getApprovalStatusEmployee(wkpId, startDate, endDate, appStatus.getListEmpCd());
			 approvalSttApp = appSttService.getApprovalSttApp(wkpId, listAppStatusEmp);
			 listAppSttApp.add(approvalSttApp);
		}
		return listAppSttApp;
	}
	
	/**
	 * アルゴリズム「承認状況未承認メール送信」を実行する
	 */
	public List<String> getAppSttSendingUnapprovedMail(List<ApprovalSttAppOutput> listAppSttApp) {
		List<String> listWorksp = new ArrayList<>();
		if(this.IsAppSttSenderEmailConfirm()) {
			//職場一覧のメール送信欄のチェックがONの件数
			int countOnChecked = 0;
			for(ApprovalSttAppOutput app: listAppSttApp){
				if(app.isChecked()) countOnChecked ++;
				listWorksp.add(app.getWorkplaceId());
			}
			if(countOnChecked <= 0) {
				throw new BusinessException("Msg_794");
			}
		} else {
			
		}
		return listWorksp;
	}
	
	/**
	 * アルゴリズム「承認状況送信者メール確認」を実行する
	 */
	private boolean IsAppSttSenderEmailConfirm() {
		EmployeeEmailOutput empEmail =  appSttService.findEmpMailAddr();
		if(!Objects.isNull(empEmail)) {
			if(Objects.isNull(empEmail.getMailAddr()) || empEmail.getMailAddr().isEmpty()){
				throw new BusinessException("Msg_791");
			}
		}
		return true;
	}
}
