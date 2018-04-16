package nts.uk.ctx.at.request.dom.application.approvalstatus.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.mail.send.MailContents;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailTemp;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailTempRepository;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailType;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprovalStatusEmployeeOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprovalSttAppOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.EmbeddedUrlOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.EmploymentOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.MailTransmissionContentOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.PeriodOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.SendMailResultOutput;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeEmailImport;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailholidayinstruction.Content;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailholidayinstruction.Subject;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.mail.MailSender;
import nts.uk.shr.com.mail.SendMailFailedException;

@Stateless
public class ApprovalStatusServiceImpl implements ApprovalStatusService {
	@Inject
	private EmployeeRequestAdapter employeeRequestAdapter;

	@Inject
	private ApplicationRepository_New appRepoNew;
	
	@Inject 
	private ApprovalStatusMailTempRepository approvalStatusMailTempRepo;
	
	@Inject
	private MailSender mailsender;
	
	@Override
	public List<ApprovalStatusEmployeeOutput> getApprovalStatusEmployee(String wkpId, GeneralDate closureStart,
			GeneralDate closureEnd, List<String> listEmpCd) {

		List<ApprovalStatusEmployeeOutput> listSttEmp = new ArrayList<>();
		// imported(申請承認)「社員ID（リスト）」を取得する
		// TODO Requestlist 120-1
		// Waiting for Q&A
		List<ApprovalStatusEmployeeOutput> listEmpInOut = new ArrayList<>();
		// TODO List<String> listSId =
		// employeeRequestAdapter.getListSIdByWkpIdAndPeriod(wkpId,
		// closureStart, closureEnd);
		// 社員ID(リスト)
		for (ApprovalStatusEmployeeOutput empInOut : listEmpInOut) {
			// imported(就業)「所属雇用履歴」より雇用コードを取得する
			// Waiting for Q&A
			List<EmploymentOutput> listEmpHist = new ArrayList<>();
			// 雇用（リスト）
			for (EmploymentOutput empHist : listEmpHist) {
				// 存在しない場合
				if (listEmpCd.contains(empHist.getEmpCd())) {
					continue;
				}
				// 存在する場合
				// アルゴリズム「承認状況対象期間取得」を実行する
				PeriodOutput sttPeriod = this.getApprovalSttPeriod(empInOut.getSId(), empHist.getStartDate(),
						empHist.getEndDate(), closureStart, closureEnd, empInOut.getStartDate(), empInOut.getEndDate());
				listSttEmp.add(new ApprovalStatusEmployeeOutput(empInOut.getSId(), sttPeriod.getStartDate(),
						sttPeriod.getStartDate()));
			}
		}
		return listSttEmp;
	}

	/**
	 * アルゴリズム「承認状況対象期間取得」を実行する
	 * 
	 * @param sId
	 *            社員ID
	 * @param empStartDate
	 *            雇用期間（開始日）
	 * @param empEndDate
	 *            雇用期間（終了日）
	 * @param closureStartDate
	 *            締め期間（開始日）
	 * @param closureEndDate
	 *            期間（終了日）
	 * @param entryDate
	 *            入退社期間（入社年月日）
	 * @param leaveDate
	 *            入退社期間（退社年月日）
	 * @return 期間（開始日～終了日）
	 */
	private PeriodOutput getApprovalSttPeriod(String sId, GeneralDate empStartDate, GeneralDate empEndDate,
			GeneralDate closureStartDate, GeneralDate closureEndDate, GeneralDate entryDate, GeneralDate leaveDate) {
		GeneralDate startDate;
		GeneralDate endDate;
		// 雇用期間（開始日）≦締め期間（開始日）
		if (empStartDate.beforeOrEquals(closureStartDate)) {
			// 対象期間.開始日＝締め期間（開始日）
			startDate = closureStartDate;
		} else {
			// 対象期間.開始日＝雇用期間（開始日）
			startDate = empStartDate;
		}
		// 対象期間.開始日≦入退社期間（入社年月日）
		if (startDate.beforeOrEquals(entryDate)) {
			// 対象期間.開始日＝入退社期間（入社年月日）
			startDate = entryDate;
		}
		// 雇用期間（終了日）≧締め期間（終了日）
		if (empEndDate.afterOrEquals(closureEndDate)) {
			// 対象期間終了日＝締め期間（終了日）
			endDate = closureEndDate;
		} else {
			// 対象期間.終了日＝雇用期間（終了日）
			endDate = empEndDate;
		}
		// 対象期間.開始日≧入退社期間（退社年月日）
		if (endDate.afterOrEquals(leaveDate)) {
			// 対象期間.開始日＝入退社期間（退社年月日）
			endDate = leaveDate;
		}
		return new PeriodOutput(startDate, endDate);
	}
	
	/**
	 * アルゴリズム「承認状況取得申請承認」を実行する
	 * @param wkpInfoDto
	 * @return ApprovalSttAppDto
	 */
	@Override
	public ApprovalSttAppOutput getApprovalSttApp(String wkpId, List<ApprovalStatusEmployeeOutput> listAppStatusEmp) {
		List<ApprovalSttAppOutput> appSttAppliStateList = new ArrayList<>();
		ApprovalSttAppOutput approvalSttApp = null;
		for (ApprovalStatusEmployeeOutput approvalStt : listAppStatusEmp) {
			List<Application_New> getAppSttAcquisitionAppl = this.getAppSttAcquisitionAppl(approvalStt);
			appSttAppliStateList.add(this.getCountAppSttAppliState(wkpId, getAppSttAcquisitionAppl));
		}

		if (appSttAppliStateList.isEmpty())
			return null;
		int numOfApp = appSttAppliStateList.stream().mapToInt(ApprovalSttAppOutput::getNumOfApp).sum();
		int appNumOfCase = appSttAppliStateList.stream().mapToInt(ApprovalSttAppOutput::getApprovedNumOfCase).sum();
		int numOfUnreflected = appSttAppliStateList.stream().mapToInt(ApprovalSttAppOutput::getNumOfUnreflected).sum();
		int numOfUnapproval = appSttAppliStateList.stream().mapToInt(ApprovalSttAppOutput::getNumOfUnapproval).sum();
		int numOfDenials = appSttAppliStateList.stream().mapToInt(ApprovalSttAppOutput::getNumOfDenials).sum();
		Integer numOfAppDisp = numOfApp == 0 ? null : numOfApp;
		Integer appNumOfCaseDisp = appNumOfCase == 0 ? null : appNumOfCase;
		Integer numOfUnreflectedDisp = numOfUnreflected == 0 ? null : numOfUnreflected;
		Integer numOfUnapprovalDisp = numOfUnapproval == 0 ? null : numOfUnapproval;
		Integer numOfDenialsDisp = numOfDenials == 0 ? null : numOfDenials;
		boolean isEnable = true;
		if(Objects.isNull(numOfUnapprovalDisp)){
			isEnable = false;
		}
		approvalSttApp = new ApprovalSttAppOutput(wkpId, isEnable, false, numOfAppDisp, appNumOfCaseDisp, numOfUnreflectedDisp, numOfUnapprovalDisp, numOfDenialsDisp);
		return approvalSttApp;
	}

	/**
	 * アルゴリズム「承認状況取得申請」を実行する
	 */
	private List<Application_New> getAppSttAcquisitionAppl(ApprovalStatusEmployeeOutput approvalStt) {
		List<Application_New> getAppSttAcquisitionAppl = null;
		String sId = AppContexts.user().companyId();
		String empId = approvalStt.getSId();
		GeneralDate startDate = approvalStt.getStartDate();
		GeneralDate endDate = approvalStt.getEndDate();
		//申請承認内容(リスト）
		getAppSttAcquisitionAppl = appRepoNew.getListAppById(sId, empId, startDate, endDate);
		return getAppSttAcquisitionAppl;
	}
	
	/**
	 * アルゴリズム「承認状況取得申請状態カウント」を実行する
	 */
	private ApprovalSttAppOutput getCountAppSttAppliState(String wkpId, List<Application_New> listApp) {
		int numOfApp = 0;
		int numOfUnapproval = 0;
		int numOfUnreflected = 0;
		int approvedNumOfCase = 0;
		int numOfDenials = 0;
		for(Application_New app: listApp) {
			//アルゴリズム「承認状況申請内容取得出張」を実行する
			int valueState = app.getReflectionInformation().getStateReflectionReal().value;
			if(valueState != 3 || valueState !=4) {
				numOfApp ++;
				if(valueState == 0 || valueState == 5){
					numOfUnapproval ++;
					numOfUnreflected ++;
				} else if(valueState == 1) {
					approvedNumOfCase ++;
					numOfUnreflected ++;
				} else if(valueState == 6) {
					numOfDenials ++;
				} else if(valueState == 2) {
					numOfApp ++;
				}
			}
		}
		return new ApprovalSttAppOutput(wkpId, false, false, numOfApp, approvedNumOfCase, numOfUnreflected, numOfUnapproval, numOfDenials);
	}

	/**
	 * 
	 * @param listAppSttDadta
	 * @return
	 */
/*	public List<ApprovalSttAppOutput> getConfirmStatus(List<ApprovalSttAppData> listAppSttDadta) {
		List<ApprovalSttAppOutput> listAppDto = new ArrayList<>();
		for(ApprovalSttAppData appData: listAppSttDadta) {
			ApprovalSttAppOutput appSttDto = new ApprovalSttAppOutput(appData.getWorkplaceId(), appData.isEnabled(), appData.isChecked() ,appData.getNumOfApp(), appData.getApprovedNumOfCase(), appData.getNumOfUnreflected(), appData.getNumOfUnapproval(), appData.getNumOfDenials());
			listAppDto.add(appSttDto);
		}
		return listAppDto;
	}*/
	
	/**
	 * アルゴリズム「承認状況社員メールアドレス取得」を実行する RequestList #126
	 * 
	 * @return 取得社員ID＜社員ID、社員名、メールアドレス＞
	 */
	@Override
	public List<EmployeeEmailImport> findEmpMailAddr(List<String> listsId) {
		List<EmployeeEmailImport> employee = employeeRequestAdapter.getApprovalStatusEmpMailAddr(listsId);
		return employee;
	}

	@Override
	public ApprovalStatusMailTemp getApprovalStatusMailTemp(int type) {
		String cId = AppContexts.user().companyId();
		Optional<ApprovalStatusMailTemp> data = approvalStatusMailTempRepo.getApprovalStatusMailTempById(cId, type);
		return data.isPresent() ? data.get() : null;
	}

	@Override
	public SendMailResultOutput sendTestMail(int mailType) {
		// 会社ID
		String cid = AppContexts.user().companyId();
		// ドメインモデル「承認状況メールテンプレート」を取得する
		ApprovalStatusMailTemp domain = approvalStatusMailTempRepo.getApprovalStatusMailTempById(cid, mailType).get();
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
		List<MailTransmissionContentOutput> listMailContent = new ArrayList<MailTransmissionContentOutput>();
		listMailContent.add(new MailTransmissionContentOutput(sid, sName, mailAddr, subject, text));
		//UseSetingDto transmissionAttr = this.getUseSeting();
		// アルゴリズム「承認状況メール送信実行」を実行する
		return this.exeApprovalStatusMailTransmission(listMailContent, domain);
	}

	@Override
	public SendMailResultOutput exeApprovalStatusMailTransmission(List<MailTransmissionContentOutput> listMailContent,
			ApprovalStatusMailTemp domain) {
		List<String> listError = new ArrayList<>();
		for (MailTransmissionContentOutput mailTransmission : listMailContent) {
			// アルゴリズム「承認状況メール埋込URL取得」を実行する
			EmbeddedUrlOutput embeddedURL = this.getEmbeddedURL(mailTransmission.getSId(), domain);
			try {
				// アルゴリズム「メールを送信する」を実行する
				mailsender.send("nts", mailTransmission.getMailAddr(),
						new MailContents(mailTransmission.getSubject(), mailTransmission.getText()));
			} catch (SendMailFailedException e) {
				// 送信エラー社員(リスト)と社員名、エラー内容を追加する
				listError.add(e.getMessage());
			}
		}
		SendMailResultOutput result = new SendMailResultOutput();
		if (listError.size() == 0) {
			result.setOK(false);
			result.setListError(listError);
		}
		result.setOK(true);
		return result;
	}

	/**
	 * アルゴリズム「承認状況メール埋込URL取得」を実行する
	 * 
	 * @param eid
	 * @param domain
	 * @param transmissionAttr
	 */
	private EmbeddedUrlOutput getEmbeddedURL(String eid, ApprovalStatusMailTemp domain) {
		// TODO waiting for Hiệp
		String url1 = "123123123";
		String url2 = "1231ád23123";
		return new EmbeddedUrlOutput(url1, url2);
	}
}
