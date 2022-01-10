package nts.uk.ctx.at.request.dom.application.approvalstatus.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.gul.mail.send.MailContents;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.request.dom.application.applist.extractcondition.ApplicationListAtr;
import nts.uk.ctx.at.request.dom.application.applist.service.ApplicationTypeDisplay;
import nts.uk.ctx.at.request.dom.application.applist.service.content.AppContentService;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppContentDetailCMM045;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppHolidayWorkDataOutput;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppOvertimeDataOutput;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppStampDataOutput;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.CompLeaveAppDataOutput;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.ScreenAtr;
import nts.uk.ctx.at.request.dom.application.applist.service.param.AttendanceNameItem;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailTemp;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailTempRepository;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailType;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttComfirmSet;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttConfirmEmp;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttConfirmEmpMonthDay;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttContentPrepareOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttEmp;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttEmpDate;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttEmpDateContent;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttEmpDateSymbol;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttEmpMailOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttExecutionOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttSendMailInfoOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttWkpEmpMailOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprovalStatusEmployeeOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApproverSpecial;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ConfirmWorkplaceInfoOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.DailyConfirmOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.DisplayWorkplace;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.EmpPeriod;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.EmployeeEmailOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.MailTransmissionContentOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.PeriodOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.PhaseApproverStt;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.SendMailResultOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.SumCountOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.UnApprovalSendMail;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmploymentAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.SyEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.AffWorkplaceImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeEmailImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmploymentHisImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.actualsituation.confirmstatusmonthly.StatusConfirmMonthImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.sys.EnvAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.sys.dto.MailDestinationImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.AgentAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalStatusForEmployeeImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApproveRootStatusForEmpImPort;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AgentDataRequestPubImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AgentInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AppFrameInsImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AppPhaseInsImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AppRootInsImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AppRootInsPeriodImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalBehaviorAtrImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalFrameImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverApproveImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.EmpPerformMonthParamAt;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.Request533Import;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.EmployeeBasicInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.request.dom.setting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.approvallistsetting.ApprovalListDispSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.approvallistsetting.ApprovalListDisplaySetting;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.WorkplaceInforExport;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.mail.MailSender;
import nts.uk.shr.com.mail.SendMailFailedException;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.url.RegisterEmbededURL;
import nts.uk.shr.com.url.UrlParamAtr;
import nts.uk.shr.com.url.UrlTaskIncre;

@Stateless
public class ApprovalStatusServiceImpl implements ApprovalStatusService {
	@Inject
	private EmployeeRequestAdapter employeeRequestAdapter;

	@Inject
	private ApplicationRepository applicationRepository;

	@Inject
	private ApprovalStatusMailTempRepository approvalStatusMailTempRepo;

	@Inject
	private MailSender mailsender;

	@Inject
	private AgentAdapter agentApdater;

	@Inject
	private ApprovalRootStateAdapter approvalStateAdapter;

	@Inject
	private WorkplaceAdapter workplaceAdapter;

	@Inject
	private RegisterEmbededURL registerEmbededURL;

	@Inject
	private AtEmploymentAdapter atEmploymentAdapter;

	@Inject
	private EnvAdapter envAdapter;
	
	@Inject
	private AtEmployeeAdapter atEmployeeAdapter;
	
	@Inject
	private ApprovalSttScreenRepository approvalSttScreenRepository;
	
	@Inject
	private AppContentDetailCMM045 appContentDetailCMM045;
	
	@Inject
	private ApprovalListDispSetRepository approvalListDispSetRepository;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;
	
	@Inject
	private RecordWorkInfoAdapter recordWorkInfoAdapter;
	
	@Inject
	private SyEmployeeAdapter syEmployeeAdapter;
	
	@Inject
	private AppContentService appContentService;
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<ApprovalStatusEmployeeOutput> getApprovalStatusEmployee(String wkpId, GeneralDate closureStart,
			GeneralDate closureEnd, List<String> listEmpCd) {
		List<ApprovalStatusEmployeeOutput> listEmp = new ArrayList<>();
		// imported(申請承認)「社員ID（リスト）」を取得する
		// RequestList120-1
		List<AffWorkplaceImport> listEmpInOut = employeeRequestAdapter.getListSIdByWkpIdAndPeriod(wkpId, closureStart,
				closureEnd);
		// 社員ID(リスト)
		for (AffWorkplaceImport empInOut : listEmpInOut) {
			// imported(就業)「所属雇用履歴」より雇用コードを取得する
			DatePeriod datePeriod = new DatePeriod(closureStart, closureEnd);
			// RequestList264
			List<EmploymentHisImport> listEmpHist = atEmploymentAdapter.findByListSidAndPeriod(empInOut.getSId(),
					datePeriod);
			if (listEmpHist.isEmpty())
				continue;
			// 雇用（リスト）
			for (EmploymentHisImport empHist : listEmpHist) {

				// 取得した雇用コードが雇用コード(リスト)に存在する
				if (!listEmpCd.contains(empHist.getEmploymentCode())) {
					// 存在しない場合
					continue;
				}
				// 存在する場合
				// アルゴリズム「承認状況対象期間取得」を実行する
				PeriodOutput sttPeriod = this.getApprovalSttPeriod(empInOut.getSId(), empHist.getDatePeriod().start(),
						empHist.getDatePeriod().end(), closureStart, closureEnd, empInOut.getJobEntryDate(),
						empInOut.getRetirementDate());
				// 社員ID＜社員ID、期間＞(リスト)を追加する
				listEmp.add(new ApprovalStatusEmployeeOutput(empInOut.getSId(), sttPeriod.getStartDate(),
						sttPeriod.getEndDate()));
			}
		}
		return listEmp;
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
	 * 承認状況社員メールアドレス取得
	 * 
	 * @return ・取得社員ID（リスト）＜社員ID、社員名、メールアドレス、期間＞
	 */
	@Override
	public List<EmployeeEmailImport> findEmpMailAddr(List<String> listsId) {
		String cid = AppContexts.user().companyId();
		// imported（就業）「個人社員基本情報」を取得する
		// RequestList126
		List<EmployeeEmailImport> listEmployee = employeeRequestAdapter.getApprovalStatusEmpMailAddr(listsId);
		// Imported（申請承認）「社員メールアドレス」を取得する
		// RequestList419
		List<MailDestinationImport> listMailEmp = envAdapter.getEmpEmailAddress(cid, listsId, 6);
		for (EmployeeEmailImport emp : listEmployee) {
			Optional<MailDestinationImport> empMailOtp = listMailEmp.stream()
					.filter(x -> x.getEmployeeID().equals(emp.getSId())).findFirst();
			empMailOtp.ifPresent(empMail -> {
				if (empMail.getOutGoingMails().isEmpty())
					return;
				emp.setMailAddr(empMail.getOutGoingMails().get(0).getEmailAddress());
			});
		}
		return listEmployee;
	}

	@Override
	public ApprovalStatusMailTemp getApprovalStatusMailTemp(int type) {
		String cId = AppContexts.user().companyId();
		Optional<ApprovalStatusMailTemp> data = approvalStatusMailTempRepo.getApprovalStatusMailTempById(cId, type);
		return data.isPresent() ? data.get() : null;
	}

	@Override
	public SendMailResultOutput sendTestMail(int mailType, boolean screenUrlApprovalEmbed, boolean screenUrlDayEmbed, boolean screenUrlMonthEmbed) {
		// 会社ID
		String cid = AppContexts.user().companyId();
		// アルゴリズム「承認状況メール本文取得」を実行する
		ApprovalStatusMailTemp domain = approvalStatusMailTempRepo.getApprovalStatusMailTempById(cid, mailType).get();
		// 社員ID
		String sid = AppContexts.user().employeeId();
		// 社員名
		String sName = employeeRequestAdapter.getEmployeeName(sid);
		// メールアドレス
		String mailAddr = this.confirmApprovalStatusMailSender();
		// 件名
		String subject = domain.getMailSubject().v();
		// 送信本文
		String text = domain.getMailContent().v();

		// ログイン者よりメール送信内容を作成する(create nội dung send mail theo người login)
		List<MailTransmissionContentOutput> listMailContent = new ArrayList<MailTransmissionContentOutput>();
		listMailContent.add(new MailTransmissionContentOutput(sid, sName, mailAddr, subject, text));
		// 送信区分の編集
		ApprovalStatusMailType mailTypeEnum = EnumAdaptor.valueOf(mailType, ApprovalStatusMailType.class);
		boolean transmissionAtr = false;
		switch (mailTypeEnum) {
		case DAILY_UNCONFIRM_BY_PRINCIPAL:
			transmissionAtr = false;
			break;
		case DAILY_UNCONFIRM_BY_CONFIRMER:
			transmissionAtr = true;
			break;
		case MONTHLY_UNCONFIRM_BY_PRINCIPAL:
			transmissionAtr = false;
			break;
		case MONTHLY_UNCONFIRM_BY_CONFIRMER:
			transmissionAtr = true;
			break;
		case APP_APPROVAL_UNAPPROVED:
			transmissionAtr = true;
			break;
		case WORK_CONFIRMATION:
			transmissionAtr = true;
			break;
		default:
			break;
		}
		// アルゴリズム「承認状況メール送信実行」を実行する
		return this.exeApprovalStatusMailTransmission(listMailContent, domain, transmissionAtr, screenUrlApprovalEmbed, screenUrlDayEmbed, screenUrlMonthEmbed);
	}

	@Override
	public SendMailResultOutput exeApprovalStatusMailTransmission(List<MailTransmissionContentOutput> listMailInput, ApprovalStatusMailTemp domain, 
			boolean transmissionAtr, boolean screenUrlApprovalEmbed, boolean screenUrlDayEmbed, boolean screenUrlMonthEmbed) {
		//メール送信内容(リスト)
		List<String> listError = new ArrayList<>();
		//社員の名称（ビジネスネーム）、社員コードを取得する RQ228
		List<String> employeeIDs = listMailInput.stream().map(x-> x.getSId()).collect(Collectors.toList());
		List<EmployeeInfoImport> lstEmpInfor = this.atEmployeeAdapter.getByListSID(employeeIDs);
		//取得した「社員コード」を「メール送信内容リスト」に付与して、「社員コード順」に並べる
		lstEmpInfor = lstEmpInfor.stream().sorted(Comparator.comparing(EmployeeInfoImport::getScd))
				.collect(Collectors.toList());
		List<MailTransmissionContentOutput> listMailContent = new ArrayList<MailTransmissionContentOutput>();
		//map lại list sau khi sắp xếp
		lstEmpInfor.forEach(x -> {
			listMailInput.stream().filter(mail -> mail.getSId().equals(x.getSid())).findFirst().ifPresent(email -> {
				listMailContent.add(email);
			});
		});
		
		for (MailTransmissionContentOutput mailTransmission : listMailContent) {
			if(mailTransmission.getMailAddr() == null){
				// 送信エラー社員(リスト)と社員名、エラー内容を追加する
				listError.add(mailTransmission.getSName());
				continue;
			}
			// アルゴリズム「承認状況メール埋込URL取得」を実行する
			String embeddedURL = this.getEmbeddedURL(mailTransmission.getSId(), domain, transmissionAtr, screenUrlApprovalEmbed, screenUrlDayEmbed, screenUrlMonthEmbed);
			try {
				// アルゴリズム「メールを送信する」を実行する
				mailsender.sendFromAdmin(mailTransmission.getMailAddr(),
						new MailContents(mailTransmission.getSubject(), mailTransmission.getText() + embeddedURL));
			} catch (SendMailFailedException mailException) {
				if(Strings.isNotBlank(mailException.getMessageId())) {
					throw new BusinessException(mailException.getMessageId());
				}
				throw new BusinessException(new RawErrorMessage(mailException.getMessage()));
			} catch (Exception e) {
				throw new BusinessException(new RawErrorMessage(e.getMessage()));
			}
		}
		SendMailResultOutput result = new SendMailResultOutput();
		if (listError.size() > 0) {
			result.setOK(false);
			result.setListError(listError);
		} else {
			result.setOK(true);
		}
		return result;
	}

	/**
	 * 承認状況メール埋込URL取得
	 */
	private String getEmbeddedURL(String eid, ApprovalStatusMailTemp domain, boolean transmissionAtr, 
			boolean screenUrlApprovalEmbed, boolean screenUrlDayEmbed, boolean screenUrlMonthEmbed) {
		List<String> listUrl = new ArrayList<>();
		String contractCD = AppContexts.user().contractCode();
		String employeeCD = AppContexts.user().employeeCode();
		// 承認状況メールテンプレート.URL承認埋込
		if (screenUrlApprovalEmbed) {
			List<UrlTaskIncre> listTask = new ArrayList<>();
			listTask.add(new UrlTaskIncre("", "", "", "activeMode", "approval", UrlParamAtr.URL_PARAM));
			// アルゴリズム「埋込URL情報登録」を実行する
			String url1 = registerEmbededURL.embeddedUrlInfoRegis("CMM045", "A", 1, 1, eid, contractCD, "", employeeCD, 0, listTask);
			listUrl.add(url1);
		}
		// 承認状況メールテンプレート.URL日別埋込
		if (screenUrlDayEmbed) {
			List<UrlTaskIncre> listTask = new ArrayList<>();
			if (transmissionAtr) {
				// アルゴリズム「埋込URL情報登録」を実行する
				String url2 = registerEmbededURL.embeddedUrlInfoRegis("KDW004", "A", 1, 1, eid, contractCD, "", employeeCD, 0, listTask);
				listUrl.add(url2);
			} else {
				listTask.add(UrlTaskIncre.createFromJavaType("", "", "", "screenMode", "normal"));
				listTask.add(UrlTaskIncre.createFromJavaType("", "", "", "errorRef", "true"));
				listTask.add(UrlTaskIncre.createFromJavaType("", "", "", "changePeriod", "true"));
				// アルゴリズム「埋込URL情報登録」を実行する
				String url2 = registerEmbededURL.embeddedUrlInfoRegis("KDW003", "A", 1, 1, eid, contractCD, "", employeeCD, 0, listTask);
				listUrl.add(url2);
			}
		}
		// 承認状況メールテンプレート.URL月別埋込
		if (screenUrlMonthEmbed) {
			List<UrlTaskIncre> listTask = new ArrayList<>();
			if (transmissionAtr) {
				listTask.add(new UrlTaskIncre("", "", "", "activeMode", "approval", UrlParamAtr.URL_PARAM));
				// アルゴリズム「埋込URL情報登録」を実行する
				String url3 = registerEmbededURL.embeddedUrlInfoRegis("KMW003", "A", 1, 1, eid, contractCD, "", employeeCD, 0, listTask);
				listUrl.add(url3);
			} else {
				listTask.add(new UrlTaskIncre("", "", "", "activeMode", "normal", UrlParamAtr.URL_PARAM));
				// アルゴリズム「埋込URL情報登録」を実行する
				String url3 = registerEmbededURL.embeddedUrlInfoRegis("KMW003", "A", 1, 1, eid, contractCD, "", employeeCD, 0, listTask);
				listUrl.add(url3);
			}
		}
		if (listUrl.size() == 0) {
			return "";
		}
		String url = StringUtils.join(listUrl, System.lineSeparator() + TextResource.localize("KAF018_510") + System.lineSeparator());
		return System.lineSeparator() + TextResource.localize("KAF018_510") + System.lineSeparator() + url;
	}

	@Override
	public String confirmApprovalStatusMailSender() {
		String sId = AppContexts.user().employeeId();
		List<String> listSId = new ArrayList<>();
		listSId.add(sId);
		// アルゴリズム「承認状況社員メールアドレス取得」を実行する
		Optional<EmployeeEmailImport> emp = this.findEmpMailAddr(listSId).stream().findFirst();
		if (!emp.isPresent()) {
			throw new BusinessException("Msg_791");
		}
		EmployeeEmailImport empEmail = emp.get();
		if (Objects.isNull(empEmail.getMailAddr()) || empEmail.getMailAddr().isEmpty()) {
			throw new BusinessException("Msg_791");
		}
		return empEmail.getMailAddr();
	}

	/**
	 * アルゴリズム「承認状況社員メールアドレス取得」を実行する RequestList #126
	 * 
	 * @return 取得社員ID＜社員ID、社員名、メールアドレス＞
	 */
	@Override
	public EmployeeEmailOutput findEmpMailAddr() {
		String cId = AppContexts.user().employeeId();
		List<String> listCId = new ArrayList<String>();
		listCId.add(cId);
		Optional<EmployeeEmailImport> employee = employeeRequestAdapter.getApprovalStatusEmpMailAddr(listCId).stream()
				.findFirst();
		return employee.isPresent() ? EmployeeEmailOutput.fromImport(employee.get()) : null;
	}

	/**
	 * アルゴリズム「承認状況未承認メール送信」を実行する
	 */
	@Override
	public List<String> getAppSttSendingUnapprovedMail(List<UnApprovalSendMail> listAppSttApp) {
		List<String> listWorksp = new ArrayList<>();
		// EA修正履歴 2125
		// this.confirmApprovalStatusMailSender();
		//hoatt - 2018.10.24
//		2018/10/24　EA2865
//		#102263
		// アルゴリズム「承認状況メール本文取得」を実行する
		//input： ・メール種類　＝　日別未確認(本人)
		ApprovalStatusMailTemp domain = this.getApprovalStatusMailTemp(ApprovalStatusMailType.DAILY_UNCONFIRM_BY_PRINCIPAL.value);
		//対象が存在しない場合
		if(domain == null){
			//メッセージ（#Msg_1458）を表示する
			throw new BusinessException("Msg_1458");
		}
		// 職場一覧のメール送信欄のチェックがONの件数
		if (listAppSttApp.stream().filter(x -> x.isChecked()).count() == 0) {
			throw new BusinessException("Msg_794");
		}
		return listWorksp;
	}

	/**
	 * 承認状況未承認者取得代行優先
	 * 
	 * @param appDate
	 * @param confirmAtr
	 * @param listApproverState
	 * 
	 */
	private List<ApproverSpecial> getUnAppSubstitutePriority(List<ApproverStateImport_New> listApproverState,
			GeneralDate appDate, int confirmAtr) {
		List<ApproverSpecial> listEmpId = new ArrayList<>();
		String cId = AppContexts.user().companyId();
		for (ApproverStateImport_New approver : listApproverState) {
			String sID = approver.getApproverID();
			// ドメインモデル「代行者管理」を取得する
			List<AgentDataRequestPubImport> lstAgentData = agentApdater.lstAgentBySidData(cId, sID, appDate, appDate);
			Optional<AgentDataRequestPubImport> agent = Optional.empty();
			if (lstAgentData != null && !lstAgentData.isEmpty()) {
				agent = lstAgentData.stream().findFirst();
			}
			// 対象が存在する場合
			if (agent.isPresent()) {
				switch (agent.get().getAgentAppType1()) {
				// 0:代行者指定
				case SUBSTITUTE_DESIGNATION:
					// 代行者管理.承認代行者を社員IDにセットする
					listEmpId.add(new ApproverSpecial(agent.get().getAgentSid1(), confirmAtr));
					break;
				// 1:パス
				case PATH:
					break;
				// 2:設定なし
				case NO_SETTINGS:
					// 承認者IDを社員IDにセットする
					listEmpId.add(new ApproverSpecial(approver.getApproverID(), confirmAtr));
					break;
				default:
					break;
				}
			} else {
				// 承認者IDを社員IDにセットする
				listEmpId.add(new ApproverSpecial(approver.getApproverID(), confirmAtr));
			}
		}
		return listEmpId;
	}

	@Override
	public List<ApprSttExecutionOutput> getStatusExecution(ClosureId closureId, YearMonth processingYm, DatePeriod period, 
			InitDisplayOfApprovalStatus initDisplayOfApprovalStatus, List<DisplayWorkplace> displayWorkplaceLst, 
			List<String> employmentCDLst, ApprSttComfirmSet apprSttComfirmSet) {
		// アルゴリズム「状況取得_表示対象データの取得」を実行する
		return this.getStatusDisplayData(closureId, processingYm, period, initDisplayOfApprovalStatus, displayWorkplaceLst, employmentCDLst, apprSttComfirmSet);
	}

	@Override
	public List<ApprSttExecutionOutput> getStatusDisplayData(ClosureId closureId, YearMonth processingYm, DatePeriod period, 
			InitDisplayOfApprovalStatus initDisplayOfApprovalStatus, List<DisplayWorkplace> displayWorkplaceLst, 
			List<String> employmentCDLst, ApprSttComfirmSet apprSttComfirmSet) {
		// アルゴリズム「状況取得_共通処理」を実行する
		List<ApprSttExecutionOutput> result = this.getStatusCommonProcess(closureId, processingYm, period, displayWorkplaceLst, employmentCDLst);
		// 「申請の承認状況を表示する」を判定
		if(initDisplayOfApprovalStatus.isApplicationApprovalFlg()) {
			Map<String, Integer> mapUnApprAppCount = this.getStatusApplicationApproval(period, displayWorkplaceLst, employmentCDLst);
			mapUnApprAppCount.entrySet().stream().forEach(x -> {
				result.stream().filter(y -> y.getWkpID().equals(x.getKey())).findAny().ifPresent(z -> {
					z.setCountUnApprApp(x.getValue());
				});
			});
		}
		// アルゴリズム「状況取得_日別実績」を実行する
		if(initDisplayOfApprovalStatus.isConfirmAndApprovalDailyFlg()) {
			Map<String, Pair<Integer, Integer>> mapUnConfirmApprDayCount = this.getStatusDayConfirmApproval(period, displayWorkplaceLst, employmentCDLst, apprSttComfirmSet);
			mapUnConfirmApprDayCount.entrySet().stream().forEach(x -> {
				result.stream().filter(y -> y.getWkpID().equals(x.getKey())).findAny().ifPresent(z -> {
					z.setCountUnConfirmDay(x.getValue().getLeft());
					z.setCountUnApprDay(x.getValue().getRight());
				});
			});
		}
		// アルゴリズム「状況取得_月別実績」を実行する
		if(initDisplayOfApprovalStatus.isConfirmAndApprovalMonthFlg()) {
			Map<String, Pair<Integer, Integer>> mapUnConfirmApprMonthCount = this.getStatusMonthConfirmApproval(
					period, processingYm, displayWorkplaceLst, employmentCDLst, apprSttComfirmSet);
			mapUnConfirmApprMonthCount.entrySet().stream().forEach(x -> {
				result.stream().filter(y -> y.getWkpID().equals(x.getKey())).findAny().ifPresent(z -> {
					z.setCountUnConfirmMonth(x.getValue().getLeft());
					z.setCountUnApprMonth(x.getValue().getRight());
				});
			});
		}
		// アルゴリズム「状況取得_就業確定」を実行する
		if(initDisplayOfApprovalStatus.isConfirmEmploymentFlg()) {
			Map<String, Pair<String, GeneralDateTime>> mapUnConfirmEmploymentCount = this.getStatusEmploymentConfirm(closureId, processingYm, displayWorkplaceLst);
			mapUnConfirmEmploymentCount.entrySet().stream().forEach(x -> {
				result.stream().filter(y -> y.getWkpID().equals(x.getKey())).findAny().ifPresent(z -> {
					z.setDisplayConfirm(true);
					z.setConfirmPerson(x.getValue().getLeft());
					z.setDate(x.getValue().getRight());
				});
			});
		}
		return result;
	}

	@Override
	public List<ApprSttExecutionOutput> getStatusCommonProcess(ClosureId closureId, YearMonth processingYm,
			DatePeriod period, List<DisplayWorkplace> displayWorkplaceLst, List<String> employmentCDLst) {
		List<ApprSttExecutionOutput> result = displayWorkplaceLst.stream()
				.map(x -> new ApprSttExecutionOutput(x))
				.collect(Collectors.toList());
		String companyId = AppContexts.user().companyId();
		List<String> wkpIDLst = displayWorkplaceLst.stream().map(x -> x.getId()).collect(Collectors.toList());
		// クエリモデル「雇用に合致する社員を取得する」を実行し「対象社員情報<List>」を取得する
		List<EmpPeriod> empPeriodLst = approvalSttScreenRepository.getCountEmp(period.start(), period.end(), wkpIDLst, employmentCDLst);
		// 職場
		empPeriodLst.stream().collect(Collectors.groupingBy(obj -> obj.getWkpID())).entrySet().forEach(x -> {
			result.stream().filter(y -> y.getWkpID().equals(x.getKey())).findAny().ifPresent(z -> {
				z.setCountEmp(x.getValue().size());
				z.setEmpPeriodLst(x.getValue());
			});
		});
		// [No.560]職場IDから職場の情報を全て取得する
		GeneralDate baseDate = GeneralDate.today();
		List<WorkplaceInforExport> wkpInforLst = workplaceAdapter.getWorkplaceInforByWkpIds(companyId, wkpIDLst, baseDate);
		wkpInforLst.stream().forEach(x -> {
			result.stream().filter(y -> y.getWkpID().equals(x.getWorkplaceId())).findAny().ifPresent(z -> {
				z.setWkpName(x.getWorkplaceName());
			});
		});
		return result;
	}
	
	@Override
	public Map<String, Integer> getStatusApplicationApproval(DatePeriod period, List<DisplayWorkplace> displayWorkplaceLst, List<String> employmentCDLst) {
		// クエリモデル「期間中の「未承認の申請」の申請者、申請日から、未承認社員を職場別にカウントする」を実行する
		return approvalSttScreenRepository.getCountUnApprApp(
				period.start(), 
				period.end(), 
				displayWorkplaceLst.stream().map(x -> x.getId()).collect(Collectors.toList()), 
				employmentCDLst);
	}
	
	@Override
	public Map<String, Pair<Integer, Integer>> getStatusDayConfirmApproval(DatePeriod period, List<DisplayWorkplace> displayWorkplaceLst, List<String> employmentCDLst,
			ApprSttComfirmSet apprSttComfirmSet) {
		Map<String, Pair<Integer, Integer>> result = new HashMap<>();
		Map<String, Integer> mapCountUnConfirmDay = new HashMap<>();
		Map<String, Integer> mapCountUnApprDay = new HashMap<>();
		// 「日別実績の上長承認の状況を表示する」を判定する
		if(apprSttComfirmSet.isUseBossConfirm()) {
			// クエリモデル「日別上長承認の未承認者で対象の社員の期間内の人数をカウントする」を実行
			mapCountUnApprDay = approvalSttScreenRepository.getCountUnApprDay(
					period.start(), 
					period.end(), 
					displayWorkplaceLst.stream().map(x -> x.getId()).collect(Collectors.toList()), 
					employmentCDLst);
		}
		// 「日別実績の本人確認を表示する」を判定する
		if(apprSttComfirmSet.isUsePersonConfirm()) {
			// クエリモデル「日別の本人確認で未確認の社員」を実行して未確認社員のカウントを取る
			mapCountUnConfirmDay = approvalSttScreenRepository.getCountUnConfirmDay(
					period.start(), 
					period.end(), 
					displayWorkplaceLst.stream().map(x -> x.getId()).collect(Collectors.toList()), 
					employmentCDLst);
		}
		List<String> keyLst = new ArrayList<>();
		keyLst.addAll(mapCountUnConfirmDay.keySet());
		keyLst.addAll(mapCountUnApprDay.keySet());
		keyLst = keyLst.stream().distinct().collect(Collectors.toList());
		for(String key : keyLst) {
			result.put(key, Pair.of(mapCountUnConfirmDay.get(key), mapCountUnApprDay.get(key)));
		}
		return result;
	}

	@Override
	public Map<String, Pair<Integer, Integer>> getStatusMonthConfirmApproval(DatePeriod period, YearMonth processingYm, 
			List<DisplayWorkplace> displayWorkplaceLst, List<String> employmentCDLst, ApprSttComfirmSet apprSttComfirmSet) {
		Map<String, Pair<Integer, Integer>> result = new HashMap<>();
		Map<String, Integer> mapCountUnConfirmMonth = new HashMap<>();
		Map<String, Integer> mapCountUnApprMonth = new HashMap<>();
		// 「日別実績の上長承認の状況を表示する」を判定する
		if(apprSttComfirmSet.isMonthlyConfirm()) {
			// 月別上長承認で未承認の社員のカウント
			mapCountUnApprMonth = approvalSttScreenRepository.getCountUnApprMonth(
					period.end(), 
					processingYm, 
					displayWorkplaceLst.stream().map(x -> x.getId()).collect(Collectors.toList()), 
					employmentCDLst);
		}
		// 「月別実績の本人確認を表示する」を判別
		if(apprSttComfirmSet.isMonthlyIdentityConfirm()) {
			// クエリモデル「社員の月別の雇用が合致する社員の本人確認」を実行する
			mapCountUnConfirmMonth = approvalSttScreenRepository.getCountUnConfirmMonth(
					period.end(), 
					displayWorkplaceLst.stream().map(x -> x.getId()).collect(Collectors.toList()), 
					employmentCDLst);
		}
		List<String> keyLst = new ArrayList<>();
		keyLst.addAll(mapCountUnConfirmMonth.keySet());
		keyLst.addAll(mapCountUnApprMonth.keySet());
		keyLst = keyLst.stream().distinct().collect(Collectors.toList());
		for(String key : keyLst) {
			result.put(key, Pair.of(mapCountUnConfirmMonth.get(key), mapCountUnApprMonth.get(key)));
		}
		return result;
	}

	@Override
	public Map<String, Pair<String, GeneralDateTime>> getStatusEmploymentConfirm(ClosureId closureId, YearMonth yearMonth, List<DisplayWorkplace> displayWorkplaceLst) {
		String companyID = AppContexts.user().companyId();
		// クエリモデル「対象職場の締めの確定状況を取得する」を実行する
		Map<String, Pair<String, GeneralDateTime>> mapCountWorkConfirm = approvalSttScreenRepository.getCountWorkConfirm(
				closureId, 
				yearMonth, 
				companyID, 
				displayWorkplaceLst.stream().map(x -> x.getId()).collect(Collectors.toList()));
		// imported（就業）「個人社員基本情報」を取得する
		List<EmployeeEmailImport> listEmployee = employeeRequestAdapter.getApprovalStatusEmpMailAddr(
				mapCountWorkConfirm.entrySet().stream().map(x -> x.getValue().getLeft()).collect(Collectors.toList()));
		
		return mapCountWorkConfirm.entrySet().stream().collect(Collectors.toMap(x -> x.getKey(), x -> {
			String empName = listEmployee.stream().filter(y -> y.getSId().equals(x.getValue().getLeft())).findAny().map(y -> y.getSName()).orElse("");
			return Pair.of(empName, x.getValue().getRight());
		}));
	}

	@Override
	public List<ApprSttEmp> getApprSttStartByEmp(String wkpID, DatePeriod period, List<EmpPeriod> empPeriodLst) {
		// アルゴリズム「承認状況社員別一覧作成」を実行する(Thực hiện thuật toán 「承認状況社員別一覧作成」)
		return this.getAppSttCreateByEmpLst(wkpID, period, empPeriodLst).stream()
				.sorted(Comparator.comparing(ApprSttEmp::getEmpCD)).collect(Collectors.toList());
	}

	@Override
	public List<ApprSttEmp> getAppSttCreateByEmpLst(String wkpID, DatePeriod paramPeriod, List<EmpPeriod> empPeriodLst) {
		List<ApprSttEmp> apprSttEmpLst = new ArrayList<>();
		List<EmployeeBasicInfoImport> employeeBasicInfoImportLst = workplaceAdapter.findBySIds(empPeriodLst.stream().map(x -> x.getEmpID()).collect(Collectors.toList()));
		for(EmpPeriod empPeriod : empPeriodLst) {
			// アルゴリズム「承認状況対象期間取得」を実行する
			DatePeriod period = this.getApprSttTargetPeriod(
					empPeriod.getEmpID(), 
					new DatePeriod(empPeriod.getEmploymentStartDate(), empPeriod.getEmploymentEndDate()), 
					paramPeriod, 
					new DatePeriod(empPeriod.getCompanyInDate(), empPeriod.getCompanyOutDate()));
			// imported（就業）「個人社員基本情報」を取得する(Lấy imported（就業）「個人社員基本情報」)
			EmployeeBasicInfoImport employeeBasicInfoImport = employeeBasicInfoImportLst.stream().filter(x -> x.getEmployeeId().equals(empPeriod.getEmpID())).findAny().orElse(null);
			// アルゴリズム「承認状況取得申請」を実行する(Thực hiện thuật toán 「承認状況取得申請」)
			List<Pair<Application,List<ApprovalPhaseStateImport_New>>> appPairLst = this.getApprSttApplication(empPeriod.getEmpID(), period);
			// アルゴリズム「承認状況日別状態作成」を実行する(Thực hiện thuật toán 「承認状況日別状態作成」)
			List<ApprSttEmpDate> dateInfoLst = this.createApprSttByDate(empPeriod.getEmpID(), period, appPairLst);
			apprSttEmpLst.add(new ApprSttEmp(employeeBasicInfoImport.getEmployeeCode(), employeeBasicInfoImport.getPName(), empPeriod.getEmpID(), dateInfoLst));
		}
		return apprSttEmpLst;
	}

	@Override
	public DatePeriod getApprSttTargetPeriod(String employeeID, DatePeriod employmentPeriod, DatePeriod closurePeriod, DatePeriod inoutPeriod) {
		GeneralDate start = closurePeriod.start();
		GeneralDate end = closurePeriod.end();
		if(employmentPeriod.start()!=null) {
			// 雇用期間（開始日）≦締め期間（開始日）
			if(employmentPeriod.start().after(closurePeriod.start())) {
				// 対象期間.開始日＝雇用期間（開始日）
				start = employmentPeriod.start();
			} else {
				// 対象期間.開始日＝締め期間（開始日）
				start = closurePeriod.start();
			}
		}
		// 対象期間.開始日≦入退社期間（入社年月日）
		if(start.beforeOrEquals(inoutPeriod.start())) {
			// 対象期間.開始日＝入退社期間（入社年月日）
			start = inoutPeriod.start();
		}
		if(employmentPeriod.end()!=null) {
			// 雇用期間（終了日）≧締め期間（終了日）
			if(employmentPeriod.end().before(closurePeriod.end())) {
				// 対象期間.終了日＝雇用期間（終了日）
				end = employmentPeriod.end();
			} else {
				// 対象期間終了日＝締め期間（終了日）
				end = closurePeriod.end();
			}
		}
		// 対象期間.開始日≧入退社期間（退社年月日）
		if(start.afterOrEquals(inoutPeriod.end())) {
			// 対象期間.開始日＝入退社期間（退社年月日）
			start = inoutPeriod.end();
		}
		// 期間　＝　「対象期間開始日、対象期間終了日」をセットする
		return new DatePeriod(start, end);
	}

	@Override
	public List<Pair<Application,List<ApprovalPhaseStateImport_New>>> getApprSttApplication(String employeeID, DatePeriod period) {
		List<Pair<Application,List<ApprovalPhaseStateImport_New>>> appPairLst = new ArrayList<>();
		// ドメインモデル「申請」を取得する
		List<Application> appLst = applicationRepository.getApprSttByEmpPeriod(employeeID, period);
		// imported（申請承認）「承認ルートの内容」を取得する
		Map<String, List<ApprovalPhaseStateImport_New>> mapApproval = approvalStateAdapter.getApprovalPhaseByID(appLst.stream().map(x -> x.getAppID()).collect(Collectors.toList()));
		for(Application app : appLst) {
			appPairLst.add(Pair.of(app, mapApproval.entrySet().stream().filter(x -> x.getKey().equals(app.getAppID())).findAny().map(x -> x.getValue()).orElse(Collections.emptyList())));
		}
		return appPairLst;
	}

	@Override
	public List<ApprSttEmpDate> createApprSttByDate(String employeeID, DatePeriod period, List<Pair<Application,List<ApprovalPhaseStateImport_New>>> appPairLst) {
		Map<GeneralDate, List<ApprSttEmpDateSymbol>> dateSttMap = new HashMap<>();
		for(Pair<Application,List<ApprovalPhaseStateImport_New>> appPair : appPairLst) {
			Application app = appPair.getKey();
			ApprSttEmpDateSymbol symbol = null;
			// 申請.反映情報.実績反映状態
			// アルゴリズム「反映状態を取得する」を実行する
			ReflectedState reflectedState = app.getAppReflectedState();
			switch (reflectedState) {
			case REFLECTED:
				// 
				symbol = ApprSttEmpDateSymbol.REFLECTED;
				break;
			case WAITREFLECTION:
				symbol = ApprSttEmpDateSymbol.WAITREFLECTION;
				break;
			case DENIAL:
				symbol = ApprSttEmpDateSymbol.DENIAL;
				break;
			case NOTREFLECTED:
				symbol = ApprSttEmpDateSymbol.NOTREFLECTED_REMAND;
				break;
			case REMAND:
				symbol = ApprSttEmpDateSymbol.NOTREFLECTED_REMAND;
				break;
			default:
				break;
			}
			if(symbol==null) {
				continue;
			}
			
			List<GeneralDate> dateLst = period.datesBetween();
			boolean containDate = false;
			if(app.getOpAppStartDate().isPresent()) {
				if(dateLst.contains(app.getOpAppStartDate().get().getApplicationDate())) {
					containDate = true;
				}
			}
			Pair<GeneralDate, ApprSttEmpDateSymbol> pair = null;
			
			if(containDate) {
				pair = Pair.of(app.getOpAppStartDate().get().getApplicationDate(), symbol);
			} else {
				pair = Pair.of(period.start(), symbol);
			}
			
			if(dateSttMap.containsKey(pair.getKey())) {
				if(!dateSttMap.get(pair.getKey()).contains(pair.getValue())) {
					dateSttMap.get(pair.getKey()).add(pair.getValue());
				}
			} else {
				List<ApprSttEmpDateSymbol> symbolLst = new ArrayList<>();
				symbolLst.add(pair.getValue());
				dateSttMap.put(pair.getKey(), symbolLst);
			}
		}
		return dateSttMap.entrySet().stream().map(entry -> {
			return new ApprSttEmpDate(entry.getKey(), entry.getValue().stream().sorted(Comparator.comparing(x -> x.value)).map(x -> x.name).collect(Collectors.joining("")));
		}).collect(Collectors.toList());
	}

	@Override
	public List<ApprSttEmpDateContent> getApprSttAppContent(String employeeID, List<DatePeriod> periodLst) {
		List<Pair<Application,List<ApprovalPhaseStateImport_New>>> appPairLst = new ArrayList<>();
		for(DatePeriod period : periodLst) {
			appPairLst.addAll(this.getApprSttApplication(employeeID, period));
		}
		return this.getApprSttAppContentAdd(appPairLst);
	}

	@Override
	public List<ApprSttEmpDateContent> getApprSttAppContentAdd(List<Pair<Application,List<ApprovalPhaseStateImport_New>>> appPairLst) {
		String companyID = AppContexts.user().companyId();
		List<ApprSttEmpDateContent> result = new ArrayList<>();
		// アルゴリズム「申請内容編集準備情報の取得」を実行する
		ApprSttContentPrepareOutput apprSttContentPrepareOutput = this.getApprSttAppContentPrepare(companyID);
		// 申請承認内容(リスト)
		for(Pair<Application,List<ApprovalPhaseStateImport_New>> appPair : appPairLst) {
			Application application = appPair.getKey();
			// アルゴリズム「反映状態を取得する」を実行する
			ReflectedState reflectedState = application.getAppReflectedState();
			// アルゴリズム「承認状況申請承認者取得」を実行する
			List<PhaseApproverStt> phaseApproverSttLst = this.getApplicationApproverStt(appPair);
			Optional<ApplicationTypeDisplay> opAppTypeDisplay = Optional.empty();
			// ドメインモデル「申請」．申請種類をチェック
			String content = "";
			switch (application.getAppType()) {
			case COMPLEMENT_LEAVE_APPLICATION:
				// 振休振出申請データを作成( Tạo data application nghỉ bù làm bù)
				CompLeaveAppDataOutput compLeaveAppDataOutput = appContentDetailCMM045.getContentComplementLeave(
						application, 
						companyID, 
						apprSttContentPrepareOutput.getWorkTypeLst(), 
						DisplayAtr.NOT_DISPLAY, 
						ScreenAtr.KAF018);
				content = compLeaveAppDataOutput.getContent();
				break;
			case ABSENCE_APPLICATION:
				// 申請一覧リスト取得休暇 (Ngày nghỉ lấy  Application list)
				content = appContentDetailCMM045.getContentApplyForLeave(
						application, 
						companyID, 
						apprSttContentPrepareOutput.getWorkTypeLst(), 
						DisplayAtr.NOT_DISPLAY,
						ScreenAtr.KAF018);
				break;
			case GO_RETURN_DIRECTLY_APPLICATION:
				// 直行直帰申請データを作成 ( Tạo dữ liệu đơn xin đi làm, về nhà thẳng)
				content = appContentDetailCMM045.getContentGoBack(
						application, 
						DisplayAtr.NOT_DISPLAY, 
						apprSttContentPrepareOutput.getWorkTimeSettingLst(), 
						apprSttContentPrepareOutput.getWorkTypeLst(), 
						ScreenAtr.KAF018);
				break;
			case WORK_CHANGE_APPLICATION:
				// 勤務変更申請データを作成
				content = appContentDetailCMM045.getContentWorkChange(
						application, 
						DisplayAtr.NOT_DISPLAY, 
						apprSttContentPrepareOutput.getWorkTimeSettingLst(), 
						apprSttContentPrepareOutput.getWorkTypeLst(), 
						companyID);
				break;
			case OVER_TIME_APPLICATION: 
				// 残業申請データを作成
				AppOvertimeDataOutput appOvertimeDataOutput = appContentDetailCMM045.createOvertimeContent(
						application, 
						apprSttContentPrepareOutput.getWorkTypeLst(), 
						apprSttContentPrepareOutput.getWorkTimeSettingLst(), 
						apprSttContentPrepareOutput.getAttendanceNameItemLst(), 
						ApplicationListAtr.APPLICATION, 
						apprSttContentPrepareOutput.getApprovalListDisplaySetting(), 
						companyID, 
						Collections.emptyMap(),
						ScreenAtr.KAF018);
				content = appOvertimeDataOutput.getAppContent();
				opAppTypeDisplay = appOvertimeDataOutput.getOpAppTypeDisplay();
				break;
			case HOLIDAY_WORK_APPLICATION:
				// 休出時間申請データを作成
				AppHolidayWorkDataOutput appHolidayWorkDataOutput = appContentDetailCMM045.createHolidayWorkContent(
						application, 
						apprSttContentPrepareOutput.getWorkTypeLst(), 
						apprSttContentPrepareOutput.getWorkTimeSettingLst(), 
						apprSttContentPrepareOutput.getAttendanceNameItemLst(), 
						ApplicationListAtr.APPLICATION, 
						apprSttContentPrepareOutput.getApprovalListDisplaySetting(), 
						companyID,
						Collections.emptyMap(),
						ScreenAtr.KAF018);
				content = appHolidayWorkDataOutput.getAppContent();
				break;
			case BUSINESS_TRIP_APPLICATION:
				// 出張申請データを作成
				content = appContentDetailCMM045.createBusinessTripData(
						application, 
						DisplayAtr.NOT_DISPLAY, 
						ScreenAtr.KAF018, 
						companyID);
				break;
			case OPTIONAL_ITEM_APPLICATION:
				// 任意申請データを作成
				content = appContentDetailCMM045.createOptionalItemApp(
						application, 
						DisplayAtr.NOT_DISPLAY, 
						ScreenAtr.KAF018, 
						companyID);
				break;
			case STAMP_APPLICATION:
				// 打刻申請データを作成
				AppStampDataOutput appStampDataOutput = appContentDetailCMM045.createAppStampData(
						application, 
						DisplayAtr.NOT_DISPLAY, 
						ScreenAtr.KAF018, 
						companyID, 
						null);
				content = appStampDataOutput.getAppContent();
				opAppTypeDisplay = appStampDataOutput.getOpAppTypeDisplay();
				break;
			case ANNUAL_HOLIDAY_APPLICATION:
				// 時間休暇申請データを作成
				content = appContentDetailCMM045.createAnnualHolidayData(
						application, 
						DisplayAtr.NOT_DISPLAY, 
						ScreenAtr.KAF018, 
						companyID);
				break;
			case EARLY_LEAVE_CANCEL_APPLICATION:
				// 遅刻早退取消申請データを作成
				content = appContentDetailCMM045.createArrivedLateLeaveEarlyData(
						application, 
						DisplayAtr.NOT_DISPLAY, 
						ScreenAtr.KAF018, 
						companyID);
				break;
			default:
				break;
			}
			
			result.add(new ApprSttEmpDateContent(application, content, reflectedState, phaseApproverSttLst, opAppTypeDisplay));
		}
		return result.stream().sorted(Comparator.comparing((ApprSttEmpDateContent x) -> {
			return x.getApplication().getAppDate().getApplicationDate().toString() + x.getApplication().getAppType().value;
		})).collect(Collectors.toList());
	}
	
	@Override
	public ApprSttContentPrepareOutput getApprSttAppContentPrepare(String companyID) {
		// ドメインモデル「承認一覧表示設定」を取得する
		ApprovalListDisplaySetting approvalListDisplaySetting = approvalListDispSetRepository.findByCID(companyID).get();
		// ドメインモデル「就業時間帯」を取得 (Lấy WorkTime)
		List<WorkTimeSetting> workTimeSettingLst = workTimeSettingRepository.findByCId(companyID);
		// ドメインモデル「勤務種類」を取得(Lấy WorkType)
		List<WorkType> workTypeLst = workTypeRepository.findByCompanyId(companyID);
		// 勤怠名称を取得 ( Lấy tên working time)
		List<AttendanceNameItem> attendanceNameItemLst = appContentService.getAttendanceNameItemLst(companyID);
		return new ApprSttContentPrepareOutput(approvalListDisplaySetting, workTypeLst, workTimeSettingLst, attendanceNameItemLst);
	}

	@Override
	public List<PhaseApproverStt> getApplicationApproverStt(Pair<Application,List<ApprovalPhaseStateImport_New>> appPair) {
		List<PhaseApproverStt> result = new ArrayList<>();
		// クラス：承認フェーズ(class: approval pharse)
		for(ApprovalPhaseStateImport_New phase : appPair.getValue()) {
			List<ApproverSpecial> approverSpecialLst = new ArrayList<>();
			// クラス：承認枠(class: approval frame )
			for(ApprovalFrameImport_New frame : phase.getListApprovalFrame()) {
				// アルゴリズム「承認状況未承認者取得代行優先」を実行する
				approverSpecialLst.addAll(this.getUnAppSubstitutePriority(
						frame.getListApprover(),
						appPair.getKey().getAppDate().getApplicationDate(), 
						frame.getConfirmAtr()));
			}
			// 承認者（リスト）
			if(CollectionUtil.isEmpty(approverSpecialLst)) {
				// 承認者リストをセットする
				result.add(new PhaseApproverStt(phase.getPhaseOrder(), "", null, phase.getApprovalAtr().value));
				continue;
			}
			// 承認者（リスト）
			String approverName = "";
			int count = 0;
			for(int i = 0; i < approverSpecialLst.size(); i++) {
				// 1人目
				if(i==0) {
					String approverID = "";
					Optional<ApproverSpecial> opApproverSpecial = approverSpecialLst.stream().filter(x -> x.getConfirmAtr()==1).findFirst();
					if(opApproverSpecial.isPresent()) {
						approverID = opApproverSpecial.get().getApproverId();
					} else {
						approverID = approverSpecialLst.get(0).getApproverId();
					}
					// imported（就業）「個人社員基本情報」を取得する
					List<EmployeeEmailImport> listEmployee = employeeRequestAdapter.getApprovalStatusEmpMailAddr(Arrays.asList(approverID));
					if(!CollectionUtil.isEmpty(listEmployee)) {
						approverName = listEmployee.get(0).getSName();
					}
					continue;
				}
				// 人数をカウント（＋１）する
				count+=1;
			}
			result.add(new PhaseApproverStt(phase.getPhaseOrder(), approverName, count, phase.getApprovalAtr().value));
		}
		return result;
	}

	@Override
	public ApprSttSendMailInfoOutput getApprSttSendMailInfo(ApprovalStatusMailType mailType, ClosureId closureId, YearMonth processingYm,
			DatePeriod period, List<DisplayWorkplace> displayWorkplaceLst, List<String> employmentCDLst, ClosureDate closureDate) {
		String companyId = AppContexts.user().companyId();
		List<ApprSttWkpEmpMailOutput> wkpEmpMailLst = new ArrayList<>();
		// アルゴリズム「メール送信_メール本文取得」を実行する
		Optional<ApprovalStatusMailTemp> opApprovalStatusMailTemp = approvalStatusMailTempRepo.getApprovalStatusMailTempById(companyId, mailType.value);
		if(!opApprovalStatusMailTemp.isPresent()) {
			switch (mailType) {
			case APP_APPROVAL_UNAPPROVED:
				throw new BusinessException("Msg_2042");
			case DAILY_UNCONFIRM_BY_PRINCIPAL:
				throw new BusinessException("Msg_2043");
			case DAILY_UNCONFIRM_BY_CONFIRMER:
				throw new BusinessException("Msg_2044");
			case MONTHLY_UNCONFIRM_BY_PRINCIPAL:
				throw new BusinessException("Msg_2045");
			case MONTHLY_UNCONFIRM_BY_CONFIRMER:
				throw new BusinessException("Msg_2046");
			case WORK_CONFIRMATION:
				throw new BusinessException("Msg_2047");
			default:
				break;
			}
		}
		List<ApprSttExecutionOutput> apprSttExecutionOutputLst = displayWorkplaceLst.stream().map(x -> new ApprSttExecutionOutput(x)).collect(Collectors.toList());
		switch (mailType) {
		case APP_APPROVAL_UNAPPROVED:
			// アルゴリズム「メール送信_対象再取得_申請」を実行
			List<EmpPeriod> mailCountUnApprAppLst = this.getMailCountUnApprApp(period, displayWorkplaceLst, employmentCDLst);
			// 職場毎の社員IDをカウントする
			mailCountUnApprAppLst.stream().collect(Collectors.groupingBy(obj -> obj.getWkpID())).entrySet().stream().forEach(x -> {
				apprSttExecutionOutputLst.stream().filter(y -> y.getWkpID().equals(x.getKey())).findAny().ifPresent(z -> {
					z.setCountEmp(x.getValue().size());
					z.setEmpPeriodLst(x.getValue());
				});
			});
			// アルゴリズム「メール送信承認者を取得」を実行
			wkpEmpMailLst = this.getAppApproverToSendMail(
					apprSttExecutionOutputLst.stream().filter(x -> x.getCountEmp() > 0).collect(Collectors.toList()), 
					period);
			break;
		case DAILY_UNCONFIRM_BY_PRINCIPAL:
			// アルゴリズム「メール送信_対象再取得_日別本人」を実行
			List<Pair<String, String>> lstMailCountUnConfirmDay = this.getMailCountUnConfirmDay(period, displayWorkplaceLst, employmentCDLst);
			List<ApprSttWkpEmpMailOutput> wkpEmpMailDayLstQuery = apprSttExecutionOutputLst.stream().map(x -> new ApprSttWkpEmpMailOutput(
					x.getWkpID(), 
					x.getWkpCD(), 
					x.getWkpName(), 
					x.getHierarchyCode(), 
					0, 
					Collections.emptyList()))
					.collect(Collectors.toList());
			lstMailCountUnConfirmDay.stream().collect(Collectors.groupingBy(obj -> obj.getKey())).entrySet().stream().forEach(x -> {
				wkpEmpMailDayLstQuery.stream().filter(y -> y.getWkpID().equals(x.getKey())).findAny().ifPresent(z -> {
					z.setCountEmp(x.getValue().size());
					z.setEmpMailLst(x.getValue().stream().map(t -> new ApprSttEmpMailOutput(t.getValue(), "", "")).collect(Collectors.toList()));
				});
			});
			wkpEmpMailLst = wkpEmpMailDayLstQuery;
			break;
		case DAILY_UNCONFIRM_BY_CONFIRMER:
			// アルゴリズム「メール送信_対象再取得_日別上長」を実行
			List<Pair<String, Pair<String, GeneralDate>>> lstDayApproval = this.getMailCountUnApprDay(period, displayWorkplaceLst, employmentCDLst);
			// アルゴリズム「メール送信_日別上長の情報を取得」を実行
			wkpEmpMailLst = this.getDayApproverToSendMail(lstDayApproval).stream().map(x -> {
				Optional<ApprSttExecutionOutput> opApprSttExecutionOutput = apprSttExecutionOutputLst.stream().filter(y -> y.getWkpID().equals(x.getWkpID())).findAny();
				return new ApprSttWkpEmpMailOutput(
						x.getWkpID(), 
						opApprSttExecutionOutput.isPresent() ? opApprSttExecutionOutput.get().getWkpCD() : x.getWkpCD(), 
						opApprSttExecutionOutput.isPresent() ? opApprSttExecutionOutput.get().getWkpName() : x.getWkpName(), 
						opApprSttExecutionOutput.isPresent() ? opApprSttExecutionOutput.get().getHierarchyCode() : x.getHierarchyCode(), 
						x.getCountEmp(), 
						x.getEmpMailLst());
			}).collect(Collectors.toList());
			break;
		case MONTHLY_UNCONFIRM_BY_PRINCIPAL:
			// アルゴリズム「メール送信_対象再取得_月別本人」を実行
			List<Pair<String, String>> lstMailCountUnConfirmMonth = this.getMailCountUnConfirmMonth(period, displayWorkplaceLst, employmentCDLst);
			List<ApprSttWkpEmpMailOutput> wkpEmpMailMonthLstQuery = apprSttExecutionOutputLst.stream().map(x -> new ApprSttWkpEmpMailOutput(
					x.getWkpID(), 
					x.getWkpCD(), 
					x.getWkpName(), 
					x.getHierarchyCode(), 
					0, 
					Collections.emptyList()))
					.collect(Collectors.toList());
			lstMailCountUnConfirmMonth.stream().collect(Collectors.groupingBy(obj -> obj.getKey())).entrySet().stream().forEach(x -> {
				wkpEmpMailMonthLstQuery.stream().filter(y -> y.getWkpID().equals(x.getKey())).findAny().ifPresent(z -> {
					z.setCountEmp(x.getValue().size());
					z.setEmpMailLst(x.getValue().stream().map(t -> new ApprSttEmpMailOutput(t.getValue(), "", "")).collect(Collectors.toList()));
				});
			});
			wkpEmpMailLst = wkpEmpMailMonthLstQuery;
			break;
		case MONTHLY_UNCONFIRM_BY_CONFIRMER:
			// アルゴリズム「メール送信_対象再取得_月別上長」を実行
			List<Pair<String, String>> lstMonthApproval = this.getMailCountUnApprMonth(period, processingYm, displayWorkplaceLst, employmentCDLst);
			// アルゴリズム「メール送信_月別上長の情報を取得」を実行
			wkpEmpMailLst = this.getMonthApproverToSendMail(lstMonthApproval, period, closureId.value, processingYm, closureDate).stream().map(x -> {
				Optional<ApprSttExecutionOutput> opApprSttExecutionOutput = apprSttExecutionOutputLst.stream().filter(y -> y.getWkpID().equals(x.getWkpID())).findAny();
				return new ApprSttWkpEmpMailOutput(
						x.getWkpID(), 
						opApprSttExecutionOutput.isPresent() ? opApprSttExecutionOutput.get().getWkpCD() : x.getWkpCD(), 
						opApprSttExecutionOutput.isPresent() ? opApprSttExecutionOutput.get().getWkpName() : x.getWkpName(), 
						opApprSttExecutionOutput.isPresent() ? opApprSttExecutionOutput.get().getHierarchyCode() : x.getHierarchyCode(), 
						x.getCountEmp(), 
						x.getEmpMailLst());
			}).collect(Collectors.toList());
			break;
		case WORK_CONFIRMATION:
			// アルゴリズム「メール送信_対象再取得_就業確定」を実行
			Map<String, List<String>> mapWorkConfirm = this.getMailCountWorkConfirm(
					period, 
					closureId, 
					processingYm, 
					companyId, 
					displayWorkplaceLst.stream().map(x -> x.getId()).collect(Collectors.toList()), 
					employmentCDLst);
			List<ApprSttWkpEmpMailOutput> wkpEmpMailWorkLstQuery = apprSttExecutionOutputLst.stream().map(x -> new ApprSttWkpEmpMailOutput(
					x.getWkpID(), 
					x.getWkpCD(), 
					x.getWkpName(), 
					x.getHierarchyCode(), 
					0, 
					Collections.emptyList()))
					.collect(Collectors.toList());
			mapWorkConfirm.entrySet().stream().collect(Collectors.groupingBy(obj -> obj.getKey())).entrySet().stream().forEach(x -> {
				wkpEmpMailWorkLstQuery.stream().filter(y -> y.getWkpID().equals(x.getKey())).findAny().ifPresent(z -> {
					z.setCountEmp(x.getValue().size());
					z.setEmpMailLst(x.getValue().stream().map(t -> new ApprSttEmpMailOutput(t.getValue().isEmpty() ? "" : t.getValue().get(0), "", "")).collect(Collectors.toList()));
				});
			});
			wkpEmpMailLst = wkpEmpMailWorkLstQuery;
			break;
		default:
			break;
		}
		// アルゴリズム「メール送信_本人の情報を取得」を実行
		wkpEmpMailLst = this.getPersonInfo(wkpEmpMailLst);
		return new ApprSttSendMailInfoOutput(opApprovalStatusMailTemp.get(), wkpEmpMailLst);
	}
	
	@Override
	public List<EmpPeriod> getMailCountUnApprApp(DatePeriod period, List<DisplayWorkplace> displayWorkplaceLst, List<String> employmentCDLst) {
		// クエリモデル「職場別の未承認社員」を実行し「職場別の未承認社員」を取得とする
		return approvalSttScreenRepository.getMailCountUnApprApp(
				period.start(), 
				period.end(), 
				displayWorkplaceLst.stream().map(x -> x.getId()).collect(Collectors.toList()), 
				employmentCDLst);
	}
	
	@Override
	public List<Pair<String, String>> getMailCountUnConfirmDay(DatePeriod period, List<DisplayWorkplace> displayWorkplaceLst,
			List<String> employmentCDLst) {
		// クエリモデル「日別の本人確認で未確認の社員」を実行する
		return approvalSttScreenRepository.getMailCountUnConfirmDay(
				period.start(), 
				period.end(), 
				displayWorkplaceLst.stream().map(x -> x.getId()).collect(Collectors.toList()), 
				employmentCDLst);
	}

	@Override
	public List<Pair<String, Pair<String, GeneralDate>>> getMailCountUnApprDay(DatePeriod period, List<DisplayWorkplace> displayWorkplaceLst,
			List<String> employmentCDLst) {
		// クエリモデル「日別上長承認の未承認者で対象の期間内の社員」を実行する
		return approvalSttScreenRepository.getMailCountUnApprDay(
				period.start(), 
				period.end(), 
				displayWorkplaceLst.stream().map(x -> x.getId()).collect(Collectors.toList()), 
				employmentCDLst);
	}

	@Override
	public List<Pair<String, String>> getMailCountUnConfirmMonth(DatePeriod period, List<DisplayWorkplace> displayWorkplaceLst,
			List<String> employmentCDLst) {
		// クエリモデル「社員の月別の雇用が合致する本人未確認の社員」を実行する
		return approvalSttScreenRepository.getMailCountUnConfirmMonth(
				period.end(), 
				displayWorkplaceLst.stream().map(x -> x.getId()).collect(Collectors.toList()), 
				employmentCDLst);
	}

	@Override
	public List<Pair<String, String>> getMailCountUnApprMonth(DatePeriod period, YearMonth processingYm, List<DisplayWorkplace> displayWorkplaceLst,
			List<String> employmentCDLst) {
		// クエリモデル「月別上長承認で未承認の社員」を実行する
		return approvalSttScreenRepository.getMailCountUnApprMonth(
				period.end(), 
				processingYm, 
				displayWorkplaceLst.stream().map(x -> x.getId()).collect(Collectors.toList()), 
				employmentCDLst);
	}

	@Override
	public Map<String, List<String>> getMailCountWorkConfirm(DatePeriod period, ClosureId closureId, YearMonth yearMonth, 
			String companyID, List<String> wkpIDLst, List<String> employmentCDLst) {
		// クエリモデル「対象職場で締めの確定がなされていない職場を取得する」を実行する
		List<String> wkpIDLstQuery = approvalSttScreenRepository.getMailCountWorkConfirm(
				period.start(), 
				period.end(), 
				closureId, 
				yearMonth, 
				companyID, 
				wkpIDLst, 
				employmentCDLst);
		if(CollectionUtil.isEmpty(wkpIDLstQuery)) {
			return Collections.emptyMap();
		}
		// 職場の管理者を求める　　　　　　　　　　　リクエストリストNo.653
		Map<String, List<String>> infoMap = syEmployeeAdapter.getListEmpInfo(companyID, GeneralDate.today(), wkpIDLstQuery);
		return infoMap;
	}
	
	@Override
	public List<ApprSttWkpEmpMailOutput> getAppApproverToSendMail(List<ApprSttExecutionOutput> apprSttExecutionOutputLst, DatePeriod paramPeriod) {
		List<ApprSttWkpEmpMailOutput> result = new ArrayList<>();
		// 「職場別、雇用の合致する対象社員の、雇用の開始、終了」を変数として保持する
		// đã query
		// 職場単位でループする
		for(ApprSttExecutionOutput apprSttExecutionOutput : apprSttExecutionOutputLst) {
			List<ApprovalStatusEmployeeOutput> listEmpOutput = new ArrayList<>();
			// 社員単位でループする
			for(EmpPeriod empPeriod : apprSttExecutionOutput.getEmpPeriodLst()) {
				// アルゴリズム「承認状況対象期間取得」を実行する
				DatePeriod period = this.getApprSttTargetPeriod(
						empPeriod.getEmpID(), 
						new DatePeriod(empPeriod.getEmploymentStartDate(), empPeriod.getEmploymentEndDate()), 
						paramPeriod, 
						new DatePeriod(empPeriod.getCompanyInDate(), empPeriod.getCompanyOutDate()));
				listEmpOutput.add(new ApprovalStatusEmployeeOutput(empPeriod.getEmpID(), period.start(), period.end()));
				// アルゴリズム「承認状況未承認申請取得」を実行する
				// get theo list ở dưới
			}
			List<String> empIDLst = this.getApprSttUnapprovedApp(listEmpOutput).stream().distinct().collect(Collectors.toList());
			result.add(new ApprSttWkpEmpMailOutput(
					apprSttExecutionOutput.getWkpID(), 
					apprSttExecutionOutput.getWkpCD(), 
					apprSttExecutionOutput.getWkpName(), 
					apprSttExecutionOutput.getHierarchyCode(), 
					apprSttExecutionOutput.getCountEmp(), 
					empIDLst.stream().map(x -> new ApprSttEmpMailOutput(x, "", "")).collect(Collectors.toList())));
		}
		return result;
	}
	
	@Override
	public List<ApprSttWkpEmpMailOutput> getDayApproverToSendMail(List<Pair<String, Pair<String, GeneralDate>>> lstDayApproval) {
		List<ApprSttWkpEmpMailOutput> result = new ArrayList<>();
		lstDayApproval.stream().collect(Collectors.groupingBy(x -> x.getKey())).entrySet().forEach(entry -> {
			List<String> employeeIDLst = entry.getValue().stream().map(x -> x.getValue().getLeft()).distinct().collect(Collectors.toList());
			List<GeneralDate> dateLst = entry.getValue().stream().map(x -> x.getValue().getRight()).distinct().collect(Collectors.toList());
			List<ApproverApproveImport> approverApproveImportLst = approvalStateAdapter.getApproverByDateLst(employeeIDLst, dateLst, 1);
			List<String> approverEmpImporIDtLst = approverApproveImportLst.stream()
					.map(x -> x.getAuthorList()).flatMap(Collection::stream).map(x -> x.getEmployeeID()).distinct().collect(Collectors.toList());
			result.add(new ApprSttWkpEmpMailOutput(
					entry.getKey(), "", "", "", 
					employeeIDLst.size(), 
					approverEmpImporIDtLst.stream().map(x -> new ApprSttEmpMailOutput(x, "", "")).collect(Collectors.toList())));
		});
		return result;
	}

	@Override
	public List<ApprSttWkpEmpMailOutput> getMonthApproverToSendMail(List<Pair<String, String>> lstMonthApproval, DatePeriod paramPeriod,
			Integer closureID, YearMonth yearMonth, ClosureDate closureDate) {
		List<ApprSttWkpEmpMailOutput> result = new ArrayList<>();
		lstMonthApproval.stream().collect(Collectors.groupingBy(x -> x.getKey())).entrySet().forEach(entry -> {
			List<String> employeeIDLst = entry.getValue().stream().map(x -> x.getValue()).distinct().collect(Collectors.toList());
			List<ApproverApproveImport> approverApproveImportLst = new ArrayList<>();
			employeeIDLst.forEach(employeeID -> {
				ApproverApproveImport approverApproveImport = approvalStateAdapter
						.getApproverByPeriodMonth(employeeID, closureID, yearMonth, closureDate, paramPeriod.end());
				approverApproveImportLst.add(approverApproveImport);
			});
			List<String> approverEmpImporIDtLst = approverApproveImportLst.stream()
					.map(x -> x.getAuthorList()).flatMap(Collection::stream).map(x -> x.getEmployeeID()).distinct().collect(Collectors.toList());
			result.add(new ApprSttWkpEmpMailOutput(
					entry.getKey(), "", "", "", 
					employeeIDLst.size(), 
					approverEmpImporIDtLst.stream().map(x -> new ApprSttEmpMailOutput(x, "", ""))
					.collect(Collectors.toList())));
		});
		return result;
	}

	@Override
	public List<ApprSttWkpEmpMailOutput> getPersonInfo(List<ApprSttWkpEmpMailOutput> wkpEmpMailLst) {
		String companyID = AppContexts.user().companyId();
		for(ApprSttWkpEmpMailOutput wkpEmpMail : wkpEmpMailLst) {
			List<String> empIDLst = wkpEmpMail.getEmpMailLst().stream().map(x -> x.getEmpID()).collect(Collectors.toList());
			// 職場IDから職場の情報を全て取得する。　　　　RequestListNo.560
			// không cần
			// imported（就業）「個人社員基本情報」を取得する
			// RequestList126
			List<EmployeeEmailImport> listEmployee = employeeRequestAdapter.getApprovalStatusEmpMailAddr(empIDLst);
			// imported（申請承認）「社員メールアドレス」を取得する
			// RequestList419
			List<MailDestinationImport> listMailEmp = envAdapter.getEmpEmailAddress(companyID, empIDLst, 6);
			// 取得した情報を編集する
			for(ApprSttEmpMailOutput empMail : wkpEmpMail.getEmpMailLst()) {
				listEmployee.stream().filter(x -> x.getSId().equals(empMail.getEmpID())).findAny().ifPresent(x -> {
					empMail.setEmpName(x.getSName());
				});
				listMailEmp.stream().filter(x -> x.getEmployeeID().equals(empMail.getEmpID())).findAny().ifPresent(x -> {
					if(!CollectionUtil.isEmpty(x.getOutGoingMails())) {
						empMail.setEmpMail(x.getOutGoingMails().get(0).getEmailAddress());
					}
				});
			}
		}
		return wkpEmpMailLst;
	}

	@Override
	public SendMailResultOutput sendMailToDestination(ApprovalStatusMailTemp approvalStatusMailTemp, List<ApprSttWkpEmpMailOutput> wkpEmpMailLst,
			boolean screenUrlApprovalEmbed, boolean screenUrlDayEmbed, boolean screenUrlMonthEmbed) {
		List<MailTransmissionContentOutput> listMailInput = wkpEmpMailLst.stream().filter(x -> x.getCountEmp() > 0 && x.getEmpMailLst().size() > 0)
				.map(x -> x.getEmpMailLst()
						.stream().map(y -> new MailTransmissionContentOutput(
								y.getEmpID(), 
								y.getEmpName(), 
								y.getEmpMail(), 
								approvalStatusMailTemp.getMailSubject().v(), 
								approvalStatusMailTemp.getMailContent().v()))
						.collect(Collectors.toList()))
				.flatMap(List::stream)
		        .collect(Collectors.toList());
		List<MailTransmissionContentOutput> listMailInputHaveMail = listMailInput.stream()
				.filter(x -> Strings.isNotBlank(x.getMailAddr())).collect(Collectors.toList());
		List<String> empNotDuplicateLst = listMailInputHaveMail.stream().map(x -> x.getSId()).distinct().collect(Collectors.toList());
		List<MailTransmissionContentOutput> listMailInputNotDuplicate = new ArrayList<>();
		empNotDuplicateLst.forEach(emp -> {
			listMailInputHaveMail.stream().filter(x -> x.getSId().equals(emp)).findAny().ifPresent(item -> {
				listMailInputNotDuplicate.add(item);
			});
		});
		// 送信対象があるか判別
		if(CollectionUtil.isEmpty(listMailInputNotDuplicate)) {
			// メッセージ（Msg_787)を表示する
			throw new BusinessException("Msg_787");
		}
		// 起動区分から送信区分を編集する
		boolean transmissionAtr = false;
		switch (approvalStatusMailTemp.getMailType()) {
		case DAILY_UNCONFIRM_BY_PRINCIPAL:
			transmissionAtr = false;
			break;
		case DAILY_UNCONFIRM_BY_CONFIRMER:
			transmissionAtr = true;
			break;
		case MONTHLY_UNCONFIRM_BY_PRINCIPAL:
			transmissionAtr = false;
			break;
		case MONTHLY_UNCONFIRM_BY_CONFIRMER:
			transmissionAtr = true;
			break;
		case APP_APPROVAL_UNAPPROVED:
			transmissionAtr = true;
			break;
		case WORK_CONFIRMATION:
			transmissionAtr = true;
			break;
		default:
			break;
		}
		// アルゴリズム「承認状況メール送信実行」を実行する
		return this.exeApprovalStatusMailTransmission(listMailInputNotDuplicate, approvalStatusMailTemp, transmissionAtr, screenUrlApprovalEmbed, screenUrlDayEmbed, screenUrlMonthEmbed);
	}

	@Override
	public List<String> getApprSttUnapprovedApp(List<ApprovalStatusEmployeeOutput> approvalStatusEmployeeLst) {
		List<String> result = new ArrayList<>();
		// 社員ID（リスト）
		for(ApprovalStatusEmployeeOutput approvalStatusEmployee : approvalStatusEmployeeLst) {
			List<Pair<Application,List<ApprovalPhaseStateImport_New>>> appPairLst = this.getApprSttApplication(
					approvalStatusEmployee.getSid(), 
					new DatePeriod(approvalStatusEmployee.getStartDate(), approvalStatusEmployee.getEndDate()));
			
			for(Pair<Application,List<ApprovalPhaseStateImport_New>> appPair : appPairLst) {
				if(appPair.getKey().getAppReflectedState() != ReflectedState.NOTREFLECTED) {
					continue;
				}
				List<String> targetLst = this.getApprSttUnapprovedAppTarget(appPair.getValue(), appPair.getKey().getAppDate().getApplicationDate());
				result.addAll(targetLst.stream().distinct().collect(Collectors.toList()));
			}
		}
		return result;
	}

	@Override
	public List<String> getApprSttUnapprovedAppTarget(List<ApprovalPhaseStateImport_New> phaseLst, GeneralDate appDate) {
		List<String> result = new ArrayList<>();
		phaseLst.sort(Comparator.comparing(ApprovalPhaseStateImport_New::getPhaseOrder).reversed());
		for(ApprovalPhaseStateImport_New phase : phaseLst) {
			if(phase.getApprovalAtr()==ApprovalBehaviorAtrImport_New.APPROVED || phase.getApprovalAtr()==ApprovalBehaviorAtrImport_New.DENIAL) {
				continue;
			}
			boolean isBreak = false;
			for(ApprovalFrameImport_New frame : phase.getListApprovalFrame()) {
				for(ApproverStateImport_New state : frame.getListApprover()) {
					if(state.getApprovalAtr()==ApprovalBehaviorAtrImport_New.APPROVED || state.getApprovalAtr()==ApprovalBehaviorAtrImport_New.DENIAL) {
						continue;
					}
					result.addAll(this.getApprSttUnapprovedAppPerson(Arrays.asList(state.getApproverID()), appDate));
					if(!CollectionUtil.isEmpty(result)) {
						isBreak = true;
					}
				}
			}
			if(isBreak) {
				break;
			}
		}
		return result;
	}

	@Override
	public List<String> getApprSttUnapprovedAppPerson(List<String> approverIDLst, GeneralDate appDate) {
		List<String> result = new ArrayList<>();
		String companyID = AppContexts.user().companyId();
		for(String approverID : approverIDLst) {
			List<AgentInfoImport> listAgentInfor = agentApdater.findAgentByPeriod(companyID, Arrays.asList(approverID), appDate, appDate, 1);
			if(!CollectionUtil.isEmpty(listAgentInfor)) {
				result.add(listAgentInfor.get(0).getApproverID());
			} else {
				result.add(approverID);
			}
		}
		return result;
	}
	
	@Override
	public List<ApprSttConfirmEmp> getConfirmApprSttByEmp(String wkpID, DatePeriod paramPeriod,
			List<EmpPeriod> empPeriodLst, ApprSttComfirmSet apprSttComfirmSet, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		// アルゴリズム「承認状況取得職場社員実績」を実行する
		return this.getConfirmApprSttByEmpLst(wkpID, paramPeriod, empPeriodLst, apprSttComfirmSet, yearMonth, closureId, closureDate);
	}

	@Override
	public List<ApprSttConfirmEmp> getConfirmApprSttByEmpLst(String wkpID, DatePeriod paramPeriod, List<EmpPeriod> empPeriodLst, ApprSttComfirmSet apprSttComfirmSet,
			YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate) {
		String companyID = AppContexts.user().companyId();
		List<ApprSttConfirmEmp> apprSttConfirmEmpLst = new ArrayList<>();
		List<EmployeeBasicInfoImport> employeeBasicInfoImportLst = workplaceAdapter.findBySIds(empPeriodLst.stream().map(x -> x.getEmpID()).collect(Collectors.toList()));
		// 社員ID(リスト)
		for(EmpPeriod empPeriod : empPeriodLst) {
			// アルゴリズム「承認状況対象期間取得」を実行する
			DatePeriod period = this.getApprSttTargetPeriod(
					empPeriod.getEmpID(), 
					new DatePeriod(empPeriod.getEmploymentStartDate(), empPeriod.getEmploymentEndDate()), 
					paramPeriod, 
					new DatePeriod(empPeriod.getCompanyInDate(), empPeriod.getCompanyOutDate()));
			// imported（就業）「個人社員基本情報」を取得する(Lấy imported（就業）「個人社員基本情報」)
			// xử lý trên UI
			EmployeeBasicInfoImport employeeBasicInfoImport = employeeBasicInfoImportLst.stream().filter(x -> x.getEmployeeId().equals(empPeriod.getEmpID())).findAny().orElse(null);
			// 月別本人確認を利用する
			boolean monthConfirm = false;
			if(apprSttComfirmSet.isMonthlyIdentityConfirm()) {
				// 社員の現在の締めの「月別本人確認」の状態を取得する　　　　リクエストリスト　No.654
				Optional<StatusConfirmMonthImport> opStatusConfirmMonthImport = recordWorkInfoAdapter.getConfirmStatusMonthly(
						companyID, Arrays.asList(empPeriod.getEmpID()), yearMonth, closureId.value);
				if(opStatusConfirmMonthImport.isPresent()) {
					if(!CollectionUtil.isEmpty(opStatusConfirmMonthImport.get().getListConfirmStatus())) {
						monthConfirm = opStatusConfirmMonthImport.get().getListConfirmStatus().get(0).isConfirmStatus();
					}
				}
			}
			// 月別確認を利用する
			Integer monthApproval = null;
			if(apprSttComfirmSet.isMonthlyConfirm()) {
				// imported（ワークフロー）「承認ルート状況」を取得する
				Request533Import request533Import = approvalStateAdapter.getAppRootStatusByEmpsMonth(Arrays.asList(new EmpPerformMonthParamAt(
								yearMonth, 
								closureId.value, 
								closureDate, 
								paramPeriod.end(), 
								empPeriod.getEmpID())));
				if(!CollectionUtil.isEmpty(request533Import.getAppRootSttMonthImportLst())) {
					monthApproval = request533Import.getAppRootSttMonthImportLst().get(0).getDailyConfirmAtr();
				}
			}
			// アルゴリズム「承認状況取得日別確認状況」を実行する
			List<DailyConfirmOutput> dailyConfirmOutputLst = this.createConfirmApprSttByDate(wkpID, empPeriod.getEmpID(), period, apprSttComfirmSet);
			apprSttConfirmEmpLst.add(new ApprSttConfirmEmp(
					dailyConfirmOutputLst, 
					employeeBasicInfoImport.getEmployeeCode(), 
					employeeBasicInfoImport.getPName(), 
					monthConfirm, 
					monthApproval,
					employeeBasicInfoImport.getEmployeeId()));
		}
		return apprSttConfirmEmpLst.stream().sorted(Comparator.comparing(ApprSttConfirmEmp::getEmpCD)).collect(Collectors.toList());
	}

	@Override
	public List<DailyConfirmOutput> createConfirmApprSttByDate(String wkpID, String employeeID, DatePeriod period, ApprSttComfirmSet apprSttComfirmSet) {
		// 期間範囲分の日別確認（リスト）を作成する
		List<DailyConfirmOutput> listDailyConfirm = new ArrayList<DailyConfirmOutput>();
		SumCountOutput sumCount = new SumCountOutput();

		// 上司確認を利用する
		if (apprSttComfirmSet.isUseBossConfirm()) {
			// アルゴリズム「承認状況取得日別上司承認状況」を実行する
			this.getApprovalSttByDateOfBoss(employeeID, wkpID, period.start(), period.end(), listDailyConfirm, sumCount);
		}
		// 本人確認を利用する
		if (apprSttComfirmSet.isUsePersonConfirm()) {
			// アルゴリズム「承認状況取得日別本人確認状況」を実行する
			this.getApprovalSttByDateOfPerson(employeeID, wkpID, period.start(), period.end(), listDailyConfirm, sumCount);
		}
		return listDailyConfirm.stream().sorted(Comparator.comparing(DailyConfirmOutput::getTargetDate)).collect(Collectors.toList());
	}
	
	/**
	 * 承認状況取得日別上司承認状況
	 */
	private void getApprovalSttByDateOfBoss(String sId, String wkpId, GeneralDate startDate, GeneralDate endDate,
			List<DailyConfirmOutput> listDailyConfirm, SumCountOutput sumCount) {
		// 会社ID
		String cid = AppContexts.user().companyId();
		// // Request list 113
		// imported（ワークフロー）「承認ルート状況」を取得する
		List<ApproveRootStatusForEmpImPort> listApproval = approvalStateAdapter.getApprovalByEmplAndDate(startDate,
				endDate, sId, cid, 1);
		// 承認ルートの状況
		for (ApproveRootStatusForEmpImPort approval : listApproval) {
			DailyConfirmOutput dailyConfirm;
			// 日別確認（リスト）に同じ職場ID、社員ID、対象日が登録済
			Optional<DailyConfirmOutput> confirm = listDailyConfirm.stream().filter(x -> x.getWkpID().equals(wkpId)
					&& x.getEmpID().equals(sId) && x.getTargetDate().equals(approval.getAppDate())).findFirst();
			if (confirm.isPresent()) {
				// 対象の日別確認（リスト）の行を対象とする
				dailyConfirm = confirm.get();
			} else {
				// 日別確認（リスト）に追加する
				dailyConfirm = new DailyConfirmOutput(wkpId, sId, approval.getAppDate(), false, null);
				listDailyConfirm.add(dailyConfirm);
			}
			// 上司確認 ＝確認
			dailyConfirm.setBossConfirm(approval.getApprovalStatus().value);
			// 承認ルート状況.承認状況
			if (ApprovalStatusForEmployeeImport.APPROVED.equals(approval.getApprovalStatus())) {
				// 上司確認件数 ＝＋１
				sumCount.bossConfirm++;
			} else {
				// 上司未確認件数 ＝＋１
				sumCount.bossUnconfirm++;
			}
		}
	}
	
	/**
	 * 承認状況取得日別本人確認状況
	 */
	private void getApprovalSttByDateOfPerson(String sId, String wkpId, GeneralDate startDate, GeneralDate endDate,
			List<DailyConfirmOutput> listDailyConfirm, SumCountOutput sumCount) {
		// RequestList165
		// imported（KAF018承認状況の照会）本人確認済みの日付を取得
		List<GeneralDate> dateLst = recordWorkInfoAdapter.getResovleDateIdentify(sId, new DatePeriod(startDate, endDate));
		// 確認日付
		for (GeneralDate date : dateLst) {
			// 日別確認（リスト）に同じ職場ID、社員ID、対象日が登録済
			Optional<DailyConfirmOutput> confirm = listDailyConfirm.stream().filter(x -> x.getWkpID().equals(wkpId)
					&& x.getEmpID().equals(sId) && x.getTargetDate().equals(date))
					.findFirst();
			if (confirm.isPresent()) {
				// 対象の日別確認（リスト）の行を対象として内容を更新するする
				confirm.get().setPersonConfirm(true);
			} else {
				// 日別確認（リスト）に追加
				DailyConfirmOutput newConfirm = new DailyConfirmOutput(wkpId, sId, date, true, null);
				listDailyConfirm.add(newConfirm);
			}
			// 本人確認件数 ＝＋１
			sumCount.personConfirm++;
		}
		GeneralDate tempDate = startDate;
		while (tempDate.beforeOrEquals(endDate)) {
			GeneralDate date = tempDate;
			Optional<DailyConfirmOutput> confirmOtp = listDailyConfirm.stream()
					.filter(x -> x.getTargetDate().equals(date)).findFirst();
			if (!confirmOtp.isPresent()) {
				DailyConfirmOutput newConfirm = new DailyConfirmOutput(wkpId, sId, date, null, null);
				listDailyConfirm.add(newConfirm);
			}
			tempDate = tempDate.addDays(1);
		}
		// 本人未確認件数 ＝日別確認(リスト)で上司確認＝未確認、本人確認＝未確認の件数
		sumCount.personUnconfirm = (int) listDailyConfirm.stream()
				.filter(x -> x.getBossConfirm()!=null && x.getPersonConfirm()!=null)
				.filter(x -> x.getBossConfirm()==0 && !x.getPersonConfirm()).count();
	}

	@Override
	public ApprSttConfirmEmpMonthDay getConfirmApprSttContent(String wkpID, String employeeID, DatePeriod period, ApprSttComfirmSet apprSttComfirmSet,
			YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate) {
		String companyID = AppContexts.user().companyId();
		// アルゴリズム「月別本人確認を取得する」を実行する
		boolean monthConfirm = this.getMonthConfirm(companyID, employeeID, apprSttComfirmSet, yearMonth, closureId);
		// アルゴリズム「月別上長承認進捗状況を取得する」を実行する
		Integer monthApproval = this.getMonthApprovalTopStatus(employeeID, period, apprSttComfirmSet, yearMonth, closureId, closureDate);
		// アルゴリズム「月別承認の進捗過程を取得する」を実行する
		ApprovalRootStateImport_New approvalRootStateMonth = this.getMonthApprovalStatus(employeeID, period, yearMonth, closureId.value, 
				closureDate, period.end(), apprSttComfirmSet).orElse(null);
		// アルゴリズム「月別の承認者を取得する」を実行する
		List<PhaseApproverStt> monthApprovalLst = this.getMonthApproval(apprSttComfirmSet, employeeID, period);
		// アルゴリズム「日別本人確認と上長承認を取得する」を実行する
		List<DailyConfirmOutput> listDailyConfirm = this.createConfirmApprSttByDate(wkpID, employeeID, period, apprSttComfirmSet);
		// アルゴリズム「日別承認の進捗過程を取得する」を実行する
		List<ApprovalRootStateImport_New> approvalRootStateDayLst = this.getDayApprovalStatus(closureId, employeeID, period, apprSttComfirmSet);
		// アルゴリズム「日別の承認者を取得する」を実行する
		Map<GeneralDate, List<PhaseApproverStt>> dayApprovalMap = this.getDayApproval(apprSttComfirmSet, employeeID, period);
		// 画面表示する
		return new ApprSttConfirmEmpMonthDay(
				monthConfirm, 
				monthApproval, 
				approvalRootStateMonth,
				monthApprovalLst, 
				listDailyConfirm, 
				approvalRootStateDayLst, 
				dayApprovalMap);
	}

	@Override
	public boolean getMonthConfirm(String companyID, String employeeID, ApprSttComfirmSet apprSttComfirmSet, 
			YearMonth yearMonth, ClosureId closureId) {
		// 「月別本人確認を取得する」を判別する
		if(apprSttComfirmSet.isMonthlyIdentityConfirm()) {
			// 社員の現在の締めの「月別本人確認」の状態を取得する　　　　リクエストリスト　No.654
			Optional<StatusConfirmMonthImport> opStatusConfirmMonthImport = recordWorkInfoAdapter.getConfirmStatusMonthly(
					companyID, Arrays.asList(employeeID), yearMonth, closureId.value);
			if(opStatusConfirmMonthImport.isPresent()) {
				if(!CollectionUtil.isEmpty(opStatusConfirmMonthImport.get().getListConfirmStatus())) {
					return opStatusConfirmMonthImport.get().getListConfirmStatus().get(0).isConfirmStatus();
				}
			}
		}
		return false;
	}

	@Override
	public Integer getMonthApprovalTopStatus(String employeeID, DatePeriod paramPeriod, ApprSttComfirmSet apprSttComfirmSet,
			YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate) {
		// 月別確認を利用する
		if(apprSttComfirmSet.isMonthlyConfirm()) {
			// imported（ワークフロー）「承認ルート状況」を取得する
			Request533Import request533Import = approvalStateAdapter.getAppRootStatusByEmpsMonth(
					Arrays.asList(new EmpPerformMonthParamAt(
							yearMonth, 
							closureId.value, 
							closureDate, 
							paramPeriod.end(), 
							employeeID)));
			if(!CollectionUtil.isEmpty(request533Import.getAppRootSttMonthImportLst())) {
				return request533Import.getAppRootSttMonthImportLst().get(0).getDailyConfirmAtr();
			}
		}
		return null;
	}

	@Override
	public Optional<ApprovalRootStateImport_New> getMonthApprovalStatus(String employeeID, DatePeriod period, YearMonth yearMonth, Integer closureID,
			ClosureDate closureDate, GeneralDate baseDate, ApprSttComfirmSet apprSttComfirmSet) {
		// 「月別実績の上長承認の状況を表示する」を判別する
		if(apprSttComfirmSet.isMonthlyConfirm()) {
			// 社員の月別実績の進捗過程を得る。　　　　　　　リクエストリストNo.673
			return Optional.ofNullable(approvalStateAdapter.getAppRootInstanceMonthByEmpPeriod(employeeID, period, yearMonth, closureID,
					closureDate, baseDate));
		}
		return Optional.empty();
	}

	@Override
	public List<PhaseApproverStt> getMonthApproval(ApprSttComfirmSet apprSttComfirmSet, String employeeID, DatePeriod period) {
		// 「月別実績の上長承認の状況を表示する」を判別する
		if(apprSttComfirmSet.isMonthlyConfirm()) {
			// アルゴリズム「社員の月別実績の進捗状況を得る」を実行する
			return this.getMonthAchievementApprover(employeeID, period);
		}
		return Collections.emptyList();
	}

	@Override
	public List<PhaseApproverStt> getMonthAchievementApprover(String employeeID, DatePeriod period) {
		List<AppRootInsPeriodImport> appRootInsPeriodImportLst = approvalStateAdapter.getAppRootInstanceByEmpPeriod(Arrays.asList(employeeID), period, 2);
		if(CollectionUtil.isEmpty(appRootInsPeriodImportLst)) {
			return Collections.emptyList();
		}
		Optional<AppRootInsImport> opAppRootInsImport = appRootInsPeriodImportLst.get(0).getAppRootInstanceLst()
				.stream().filter(x -> x.getDatePeriod().contains(period.end())).findAny();
		if(!opAppRootInsImport.isPresent()) {
			return Collections.emptyList();
		}
		
		// アルゴリズム「実績承認者取得」を実行する
		return this.getAchievementApprover(opAppRootInsImport.get(), period.end());
	}

	@Override
	public List<PhaseApproverStt> getAchievementApprover(AppRootInsImport appRootInsImport, GeneralDate date) {
		List<PhaseApproverStt> result = new ArrayList<>();
		// クラス：承認フェーズ中間データ
		for(AppPhaseInsImport phase : appRootInsImport.getListAppPhase()) {
			List<ApproverSpecial> approverSpecialLst = new ArrayList<>();
			// クラス：承認枠中間データ
			for(AppFrameInsImport frame : phase.getListAppFrame()) {
				// アルゴリズム「承認状況未承認者取得代行優先」を実行する
				approverSpecialLst.addAll(this.getUnAppSubstitutePriority(
						frame.getListApprover().stream().map(x -> ApproverStateImport_New.createSimpleFromInstance(x)).collect(Collectors.toList()), 
						date, 
						frame.isConfirmAtr() ? 1 : 0));
			}
			// 承認者（リスト）
			if(CollectionUtil.isEmpty(approverSpecialLst)) {
				// 承認者リストをセットする
				result.add(new PhaseApproverStt(phase.getPhaseOrder(), "", null, null));
				continue;
			}
			// 承認者（リスト）
			String approverName = "";
			int count = 0;
			for(int i = 0; i < approverSpecialLst.size(); i++) {
				// 1人目
				if(i==0) {
					String approverID = "";
					Optional<ApproverSpecial> opApproverSpecial = approverSpecialLst.stream().filter(x -> x.getConfirmAtr()==1).findFirst();
					if(opApproverSpecial.isPresent()) {
						approverID = opApproverSpecial.get().getApproverId();
					} else {
						approverID = approverSpecialLst.get(0).getApproverId();
					}
					// imported（就業）「個人社員基本情報」を取得する
					List<EmployeeEmailImport> listEmployee = employeeRequestAdapter.getApprovalStatusEmpMailAddr(Arrays.asList(approverID));
					if(!CollectionUtil.isEmpty(listEmployee)) {
						approverName = listEmployee.get(0).getSName();
					}
					continue;
				}
				// 人数をカウント（＋１）する
				count+=1;
			}
			// 承認者リストをセットする
			result.add(new PhaseApproverStt(phase.getPhaseOrder(), approverName, count, null));
		}
		return result;
	}

	@Override
	public List<ApprovalRootStateImport_New> getDayApprovalStatus(ClosureId closureId, String employeeID, DatePeriod period, ApprSttComfirmSet apprSttComfirmSet) {
		List<ApprovalRootStateImport_New> result = new ArrayList<>();
		// 「日別実績の上長承認の状況を表示する」を判別する
		if(apprSttComfirmSet.isUseBossConfirm()) {
			// 社員の日別実績の進捗状況を得る。　　　　　　リクエストリストNo.672
			result = approvalStateAdapter.getAppRootInstanceDayByEmpPeriod(Arrays.asList(employeeID), period, 1);
		}
		return result;
	}

	@Override
	public Map<GeneralDate, List<PhaseApproverStt>> getDayApproval(ApprSttComfirmSet apprSttComfirmSet, String employeeID, DatePeriod period) {
		// 「日別実績の上長承認の状況を表示する」を判別する
		if(apprSttComfirmSet.isUseBossConfirm()) {
			// アルゴリズム「社員の日別実績の進捗状況を得る」を実行する
			return this.getDayAchievementApprover(employeeID, period);
		}
		return Collections.emptyMap();
	}

	@Override
	public Map<GeneralDate, List<PhaseApproverStt>> getDayAchievementApprover(String employeeID, DatePeriod period) {
		List<AppRootInsPeriodImport> appRootInsPeriodImportLst = approvalStateAdapter.getAppRootInstanceByEmpPeriod(Arrays.asList(employeeID), period, 1);
		if(CollectionUtil.isEmpty(appRootInsPeriodImportLst)) {
			return Collections.emptyMap();
		}
		Map<GeneralDate, List<PhaseApproverStt>> result = new HashMap<>();
		for(GeneralDate loopDate : period.datesBetween()) {
			Optional<AppRootInsImport> opAppRootInsImport = appRootInsPeriodImportLst.get(0).getAppRootInstanceLst()
					.stream().filter(x -> x.getDatePeriod().contains(loopDate)).findAny();
			if(!opAppRootInsImport.isPresent()) {
				continue;
			}
			List<PhaseApproverStt> phaseApproverSttLst = this.getAchievementApprover(opAppRootInsImport.get(), loopDate);
			result.put(loopDate, phaseApproverSttLst);
		}
		return result;
	}

	@Override
	public ConfirmWorkplaceInfoOutput getEmploymentConfirmInfo(String wkpID, String employeeID, String roleID) {
		String companyID = AppContexts.user().companyId();
		// 職場管理者を取得する リクエストリストNo.653
		Map<String, List<String>> infoMap = syEmployeeAdapter.getListEmpInfo(companyID, GeneralDate.today(), Arrays.asList(wkpID));

		List<String> sids = new ArrayList<>();
		for (Map.Entry<String, List<String>> entry : infoMap.entrySet()) {
			sids.addAll(entry.getValue());
		}

		// imported（就業）「個人社員基本情報」を取得する
		List<EmployeeEmailImport> authorLst = employeeRequestAdapter.getApprovalStatusEmpMailAddr(sids);
		// [RQ679]所属職場権限を取得する
		boolean authorUse = workplaceAdapter.getWorkPlaceAuthorityById(companyID, roleID, 2).map(x -> x.isAvailability()).orElse(false);
		// ログイン社員IDがList<社員ID>に含まれるかチェック
		boolean isAuthor = sids.contains(employeeID); 
		
		return new ConfirmWorkplaceInfoOutput(authorLst, isAuthor, authorUse);
	}
}
