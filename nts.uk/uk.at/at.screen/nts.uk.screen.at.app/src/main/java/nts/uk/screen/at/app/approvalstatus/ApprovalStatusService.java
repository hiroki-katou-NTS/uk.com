package nts.uk.screen.at.app.approvalstatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.gul.mail.send.MailContents;
import nts.uk.ctx.at.request.app.find.application.approvalstatus.ApprSttSpecDeadlineDto;
import nts.uk.ctx.at.request.app.find.application.approvalstatus.ApprSttSpecDeadlineSetDto;
import nts.uk.ctx.at.request.app.find.application.approvalstatus.ApprovalStatusMailTempDto;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppContentDetailCMM045;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.AppStampDataOutput;
import nts.uk.ctx.at.request.dom.application.applist.service.detail.ScreenAtr;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailTemp;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailTempRepository;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailType;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.InitDisplayOfApprovalStatus;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttComfirmSet;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprSttConfirmEmp;
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
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.DisplayWorkplace;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.EmpPeriod;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.MailTransmissionContentOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.PhaseApproverStt;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.SendMailResultOutput;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeEmailImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.sys.EnvAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.sys.dto.MailDestinationImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.AgentAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AgentDataRequestPubImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AgentInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalBehaviorAtrImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalFrameImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.EmployeeBasicInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.approvallistsetting.ApprovalListDispSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.approvallistsetting.ApprovalListDisplaySetting;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureDto;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.WorkplaceInforExport;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.mail.MailSender;
import nts.uk.shr.com.mail.SendMailFailedException;
import nts.uk.shr.com.url.RegisterEmbededURL;
import nts.uk.shr.com.url.UrlParamAtr;
import nts.uk.shr.com.url.UrlTaskIncre;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@Stateless
public class ApprovalStatusService {
	
	@Inject
	private ClosureRepository closureRepository;
	
	@Inject
	private ClosureEmploymentRepository closureEmpRepo;

	@Inject
	private MailSender mailsender;
	
	@Inject
	private RegisterEmbededURL registerEmbededURL;
	
	@Inject
	private AtEmployeeAdapter atEmployeeAdapter;
	
	@Inject
	private EmployeeRequestAdapter employeeRequestAdapter;

	@Inject
	private ApplicationRepository applicationRepository;

	@Inject
	private ApprovalStatusMailTempRepository approvalStatusMailTempRepo;

	@Inject
	private AgentAdapter agentApdater;

	@Inject
	private ApprovalRootStateAdapter approvalStateAdapter;

	@Inject
	private WorkplaceAdapter workplaceAdapter;

	@Inject
	private EnvAdapter envAdapter;
	
	@Inject
	private AppContentDetailCMM045 appContentDetailCMM045;
	
	@Inject
	private ApprovalListDispSetRepository approvalListDispSetRepository;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;
	
	@Inject
	private AppSttScreenRepository approvalSttScreenRepository;
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.A:状況照会条件入力.アルゴリズム.A:承認状況起動.A:承認状況起動
	 */
	public ApprSttSpecDeadlineDto getApprovalStatusActivation(Integer selectClosureId) {
		String companyID = AppContexts.user().companyId();
		// 「新規起動」か「戻り起動」か判別
		// xử lý trên UI
		// ドメインモデル「就業締め日」を取得する　<shared>(Lấy domain 「就業締め日」)
		List<Closure> closureList = this.closureRepository.findAllActive(companyID, UseClassification.UseClass_Use);
		// アルゴリズム「承認状況指定締め日取得」を実行する(Thực hiện thuật toán[lấy ngày chốt chỉ định trạng thái approval])
		ApprSttSpecDeadlineDto apprSttSpecDeadlineDto = this.getApprovalStatusSpecDeadline(selectClosureId, closureList);
		// アルゴリズム「承認状況日別実績利用確認」を実行する(Thực hiện[confirm sử dụng 日別実績 trạng thái approval ])
		// context khác, xử lý trên UI
		// ユーザ固有情報「承認状況照会の初期表示」をチェックする
		// xử lý trên UI
		return apprSttSpecDeadlineDto;
	}
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.A:状況照会条件入力.ユースケース.締め区分を変更する
	 * @param selectClosureId
	 * @return
	 */
	public ApprSttSpecDeadlineSetDto changeClosure(Integer selectClosureId) {
		String companyId = AppContexts.user().companyId();
		// ドメインモデル「締め」を取得する(get domain[closure])
		Closure closure = closureRepository.findClosureHistory(companyId, selectClosureId, UseClassification.UseClass_Use.value).get();
		// アルゴリズム「承認状況指定締め期間設定」を実行する(Thực hiện thuật toán 「承認状況指定締め期間設定」)
		return this.getApprovalStatusSpecDeadlineSet(selectClosureId, closure);
	}
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.A:状況照会条件入力.アルゴリズム.A:承認状況指定締め日取得.A:承認状況指定締め日取得
	 * @return
	 */
	public ApprSttSpecDeadlineDto getApprovalStatusSpecDeadline(Integer selectClosureId, List<Closure> closureList) {
		// ユーザー固有情報「選択中の就業締め」を取得する(lấy thông tin user đã có 「選択中の就業締め」)
		// 締めIDが取得ができた(Lấy được closeID)
		Closure closure = null;
		if(selectClosureId!=null) {
			// 就業締め日（リスト）より取得した締めIDと同じ締めID内容を選択(Chọn nội dung closeID ứng với closeID đã lấy từ list close Date)
			closure = closureList.stream().filter(x -> x.getClosureId().value==selectClosureId).findAny().orElse(closureList.get(0));
		} else {
			// 就業締め日（リスト）の先頭の締めIDを選択(Chọn closeID trên cùng trong list CloseDate)
			closure = closureList.get(0);
		}
		// アルゴリズム「承認状況指定締め期間設定」を実行する(Thực hiện thuật toán[setting thời gian close chỉ định trạng thái approval])
		ApprSttSpecDeadlineSetDto apprSttSpecDeadlineSetDto = this.getApprovalStatusSpecDeadlineSet(closure.getClosureId().value, closure);
		return new ApprSttSpecDeadlineDto(
				closureList.stream().map(x -> ClosureDto.fromDomain(x)).collect(Collectors.toList()), 
				apprSttSpecDeadlineSetDto.getStartDate(),
				apprSttSpecDeadlineSetDto.getEndDate(),
				apprSttSpecDeadlineSetDto.getListEmploymentCD());
	}
	
	/**
	 * refactor 5
	 * UKDesign.UniversalK.就業.KAF_申請.KAF018_承認状況の照会.A:状況照会条件入力.アルゴリズム.A:承認状況指定締め期間設定.A:承認状況指定締め期間設定
	 * @param closureId 締めID
	 * @param closure 就業締め日（ドメインモデル）
	 * @return
	 */
	public ApprSttSpecDeadlineSetDto getApprovalStatusSpecDeadlineSet(int closureId, Closure closure) {
		String companyId = AppContexts.user().companyId();
		// アルゴリズム「当月の期間を算出する」を実行する(Thực hiện[tính thời gian this month])
		DatePeriod datePeriodClosure = ClosureService.getClosurePeriod(
				ClosureService.createRequireM1(closureRepository, closureEmpRepo),
				closure.getClosureId().value, closure.getClosureMonth().getProcessingYm());
		// ドメインモデル「雇用に紐づく就業締め」より、雇用コードと締めIDを取得する
		List<String> listEmploymentCD = closureEmpRepo.findByClosureId(companyId, closureId)
				.stream().map(x -> x.getEmploymentCD()).collect(Collectors.toList());
		return new ApprSttSpecDeadlineSetDto(
				datePeriodClosure.start().toString(),
				datePeriodClosure.end().toString(),
				listEmploymentCD);
	}
	
	public SendMailResultOutput exeApprovalStatusMailTransmission(List<MailTransmissionContentOutput> listMailInput,
			ApprovalStatusMailTemp domain, ApprovalStatusMailType mailType) {
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
			String embeddedURL = this.getEmbeddedURL(mailTransmission.getSId(), domain, mailType);
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
	
	public List<ApprSttExecutionOutput> getStatusExecution(ClosureId closureId, YearMonth processingYm, DatePeriod period, 
			InitDisplayOfApprovalStatus initDisplayOfApprovalStatus, List<DisplayWorkplace> displayWorkplaceLst, 
			List<String> employmentCDLst, ApprSttComfirmSet apprSttComfirmSet) {
		// アルゴリズム「状況取得_表示対象データの取得」を実行する
		return this.getStatusDisplayData(closureId, processingYm, period, initDisplayOfApprovalStatus, displayWorkplaceLst, employmentCDLst, apprSttComfirmSet);
	}

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
			Map<String, Pair<String, GeneralDate>> mapUnConfirmEmploymentCount = this.getStatusEmploymentConfirm(closureId, processingYm, displayWorkplaceLst);
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
	
	public Map<String, Integer> getStatusApplicationApproval(DatePeriod period, List<DisplayWorkplace> displayWorkplaceLst, List<String> employmentCDLst) {
		// クエリモデル「期間中の「未承認の申請」の申請者、申請日から、未承認社員を職場別にカウントする」を実行する
		return approvalSttScreenRepository.getCountUnApprApp(
				period.start(), 
				period.end(), 
				displayWorkplaceLst.stream().map(x -> x.getId()).collect(Collectors.toList()), 
				employmentCDLst);
	}
	
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

	public Map<String, Pair<String, GeneralDate>> getStatusEmploymentConfirm(ClosureId closureId, YearMonth yearMonth, List<DisplayWorkplace> displayWorkplaceLst) {
		String companyID = AppContexts.user().companyId();
		// クエリモデル「対象職場の締めの確定状況を取得する」を実行する
		Map<String, Pair<String, GeneralDate>> mapCountWorkConfirm = approvalSttScreenRepository.getCountWorkConfirm(
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

	public List<ApprSttEmp> getApprSttStartByEmp(String wkpID, DatePeriod period, List<EmpPeriod> empPeriodLst) {
		// アルゴリズム「承認状況社員別一覧作成」を実行する(Thực hiện thuật toán 「承認状況社員別一覧作成」)
		return this.getAppSttCreateByEmpLst(wkpID, period, empPeriodLst).stream()
				.sorted(Comparator.comparing(ApprSttEmp::getEmpCD)).collect(Collectors.toList());
	}

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

	public List<ApprSttEmpDateContent> getApprSttAppContent(String employeeID, List<DatePeriod> periodLst) {
		List<Pair<Application,List<ApprovalPhaseStateImport_New>>> appPairLst = new ArrayList<>();
		for(DatePeriod period : periodLst) {
			appPairLst.addAll(this.getApprSttApplication(employeeID, period));
		}
		return this.getApprSttAppContentAdd(appPairLst);
	}

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
			// ドメインモデル「申請」．申請種類をチェック
			String content = "";
			switch (application.getAppType()) {
			case COMPLEMENT_LEAVE_APPLICATION:
				break;
			case ABSENCE_APPLICATION:
				break;
			case GO_RETURN_DIRECTLY_APPLICATION:
				// 直行直帰申請データを作成 ( Tạo dữ liệu đơn xin đi làm, về nhà thẳng)
				content = appContentDetailCMM045.getContentGoBack(
						application, 
						apprSttContentPrepareOutput.getApprovalListDisplaySetting().getAppReasonDisAtr(), 
						apprSttContentPrepareOutput.getWorkTimeSettingLst(), 
						apprSttContentPrepareOutput.getWorkTypeLst(), 
						ScreenAtr.CMM045);
				break;
			case WORK_CHANGE_APPLICATION:
				// 勤務変更申請データを作成
				content = appContentDetailCMM045.getContentWorkChange(
						application, 
						apprSttContentPrepareOutput.getApprovalListDisplaySetting().getAppReasonDisAtr(), 
						apprSttContentPrepareOutput.getWorkTimeSettingLst(), 
						apprSttContentPrepareOutput.getWorkTypeLst(), 
						companyID);
				break;
			case OVER_TIME_APPLICATION: 
				break;
			case HOLIDAY_WORK_APPLICATION:
				break;
			case BUSINESS_TRIP_APPLICATION:
				// 出張申請データを作成
				content = appContentDetailCMM045.createBusinessTripData(
						application, 
						apprSttContentPrepareOutput.getApprovalListDisplaySetting().getAppReasonDisAtr(), 
						ScreenAtr.CMM045, 
						companyID);
				break;
			case OPTIONAL_ITEM_APPLICATION:
				break;
			case STAMP_APPLICATION:
				// 打刻申請データを作成
				AppStampDataOutput appStampDataOutput = appContentDetailCMM045.createAppStampData(
						application, 
						apprSttContentPrepareOutput.getApprovalListDisplaySetting().getAppReasonDisAtr(), 
						ScreenAtr.CMM045, 
						companyID, 
						null);
				content = appStampDataOutput.getAppContent();
				break;
			case ANNUAL_HOLIDAY_APPLICATION:
				break;
			case EARLY_LEAVE_CANCEL_APPLICATION:
				// 遅刻早退取消申請データを作成
				content = appContentDetailCMM045.createArrivedLateLeaveEarlyData(
						application, 
						apprSttContentPrepareOutput.getApprovalListDisplaySetting().getAppReasonDisAtr(), 
						ScreenAtr.CMM045, 
						companyID);
				break;
			default:
				break;
			}
			
			result.add(new ApprSttEmpDateContent(application, content, reflectedState, phaseApproverSttLst));
		}
		return result.stream().sorted(Comparator.comparing((ApprSttEmpDateContent x) -> {
			return x.getApplication().getAppDate().getApplicationDate().toString() + x.getApplication().getAppType().value;
		})).collect(Collectors.toList());
	}
	
	public ApprSttContentPrepareOutput getApprSttAppContentPrepare(String companyID) {
		// ドメインモデル「承認一覧表示設定」を取得する
		ApprovalListDisplaySetting approvalListDisplaySetting = approvalListDispSetRepository.findByCID(companyID).get();
		// ドメインモデル「就業時間帯」を取得 (Lấy WorkTime)
		List<WorkTimeSetting> workTimeSettingLst = workTimeSettingRepository.findByCId(companyID);
		// ドメインモデル「勤務種類」を取得(Lấy WorkType)
		List<WorkType> workTypeLst = workTypeRepository.findByCompanyId(companyID);
		// 勤怠名称を取得 ( Lấy tên working time)
		// chưa đối ứng
		return new ApprSttContentPrepareOutput(approvalListDisplaySetting, workTypeLst, workTimeSettingLst);
	}

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
	
	/**
	 * refactor 5
	 * アルゴリズム「承認状況本文起動」を実行する
	 */
	public List<ApprovalStatusMailTempDto> getMailTemp() {
		// 会社ID
		String cid = AppContexts.user().companyId();
		List<ApprovalStatusMailTempDto> listMail = new ArrayList<ApprovalStatusMailTempDto>();

		listMail.add(this.getApprovalStatusMailTemp(cid, ApprovalStatusMailType.APP_APPROVAL_UNAPPROVED.value));
		listMail.add(this.getApprovalStatusMailTemp(cid, ApprovalStatusMailType.DAILY_UNCONFIRM_BY_PRINCIPAL.value));
		listMail.add(this.getApprovalStatusMailTemp(cid, ApprovalStatusMailType.DAILY_UNCONFIRM_BY_CONFIRMER.value));
		listMail.add(this.getApprovalStatusMailTemp(cid, ApprovalStatusMailType.MONTHLY_UNCONFIRM_BY_PRINCIPAL.value));
		listMail.add(this.getApprovalStatusMailTemp(cid, ApprovalStatusMailType.MONTHLY_UNCONFIRM_BY_CONFIRMER.value));
		listMail.add(this.getApprovalStatusMailTemp(cid, ApprovalStatusMailType.WORK_CONFIRMATION.value));
		return listMail;
	}
	
	/**
	 * refactor 5
	 * @param cid
	 * @param mailType
	 * @return
	 */
	private ApprovalStatusMailTempDto getApprovalStatusMailTemp(String cid, int mailType) {
		// アルゴリズム「承認状況メール本文取得」を実行する
		ApprovalStatusMailTemp domain = this.getApprovalStatusMailTemp(mailType);
		// ドメインの取得
		if (Objects.isNull(domain)) {
			// ドメインが取得できなかった場合
			// 画面モード ＝ 新規
			return new ApprovalStatusMailTempDto(mailType, null, null, null, "", "", 0);
		}
		// ドメインが取得できた場合(lấy được)
		// 画面モード ＝ 更新
		return ApprovalStatusMailTempDto.fromDomain(domain, mailType);
	}
	
	public ApprovalStatusMailTemp getApprovalStatusMailTemp(int type) {
		String cId = AppContexts.user().companyId();
		Optional<ApprovalStatusMailTemp> data = approvalStatusMailTempRepo.getApprovalStatusMailTempById(cId, type);
		return data.isPresent() ? data.get() : null;
	}
	
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
	 * 承認状況社員メールアドレス取得
	 * 
	 * @return ・取得社員ID（リスト）＜社員ID、社員名、メールアドレス、期間＞
	 */
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

	public SendMailResultOutput sendTestMail(int mailType) {
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
		// アルゴリズム「承認状況メール送信実行」を実行する
		return this.exeApprovalStatusMailTransmission(listMailContent, domain,
				EnumAdaptor.valueOf(mailType, ApprovalStatusMailType.class));
	}

	public ApprSttSendMailInfoOutput getApprSttSendMailInfo(ApprovalStatusMailType mailType, ClosureId closureId, YearMonth processingYm,
			DatePeriod period, List<DisplayWorkplace> displayWorkplaceLst, List<String> employmentCDLst) {
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
			Map<String, String> mapMailCountUnConfirmDay = this.getMailCountUnConfirmDay(period, displayWorkplaceLst, employmentCDLst);
			List<ApprSttWkpEmpMailOutput> wkpEmpMailDayLstQuery = apprSttExecutionOutputLst.stream().map(x -> new ApprSttWkpEmpMailOutput(
					x.getWkpID(), 
					x.getWkpCD(), 
					x.getWkpName(), 
					x.getHierarchyCode(), 
					0, 
					Collections.emptyList()))
					.collect(Collectors.toList());
			mapMailCountUnConfirmDay.entrySet().stream().collect(Collectors.groupingBy(obj -> obj.getKey())).entrySet().stream().forEach(x -> {
				wkpEmpMailDayLstQuery.stream().filter(y -> y.getWkpID().equals(x.getKey())).findAny().ifPresent(z -> {
					z.setCountEmp(x.getValue().size());
					z.setEmpMailLst(x.getValue().stream().map(t -> new ApprSttEmpMailOutput(t.getValue(), "", "")).collect(Collectors.toList()));
				});
			});
			wkpEmpMailLst = wkpEmpMailDayLstQuery;
			break;
		case DAILY_UNCONFIRM_BY_CONFIRMER:
			// 
			wkpEmpMailLst = this.getDayApproverToSendMail(apprSttExecutionOutputLst, period);
			break;
		case MONTHLY_UNCONFIRM_BY_PRINCIPAL:
			// アルゴリズム「メール送信_対象再取得_月別本人」を実行
			Map<String, String> mapMailCountUnConfirmMonth = this.getMailCountUnConfirmMonth(period, displayWorkplaceLst, employmentCDLst);
			List<ApprSttWkpEmpMailOutput> wkpEmpMailMonthLstQuery = apprSttExecutionOutputLst.stream().map(x -> new ApprSttWkpEmpMailOutput(
					x.getWkpID(), 
					x.getWkpCD(), 
					x.getWkpName(), 
					x.getHierarchyCode(), 
					0, 
					Collections.emptyList()))
					.collect(Collectors.toList());
			mapMailCountUnConfirmMonth.entrySet().stream().collect(Collectors.groupingBy(obj -> obj.getKey())).entrySet().stream().forEach(x -> {
				wkpEmpMailMonthLstQuery.stream().filter(y -> y.getWkpID().equals(x.getKey())).findAny().ifPresent(z -> {
					z.setCountEmp(x.getValue().size());
					z.setEmpMailLst(x.getValue().stream().map(t -> new ApprSttEmpMailOutput(t.getValue(), "", "")).collect(Collectors.toList()));
				});
			});
			wkpEmpMailLst = wkpEmpMailMonthLstQuery;
			break;
		case MONTHLY_UNCONFIRM_BY_CONFIRMER:
			// 
			wkpEmpMailLst = this.getMonthApproverToSendMail(apprSttExecutionOutputLst, period);
			break;
		case WORK_CONFIRMATION:
			break;
		default:
			break;
		}
		// アルゴリズム「メール送信_本人の情報を取得」を実行
		wkpEmpMailLst = this.getPersonInfo(wkpEmpMailLst);
		return new ApprSttSendMailInfoOutput(opApprovalStatusMailTemp.get(), wkpEmpMailLst);
	}
	
	public List<EmpPeriod> getMailCountUnApprApp(DatePeriod period, List<DisplayWorkplace> displayWorkplaceLst, List<String> employmentCDLst) {
		// クエリモデル「職場別の未承認社員」を実行し「職場別の未承認社員」を取得とする
		return approvalSttScreenRepository.getMailCountUnApprApp(
				period.start(), 
				period.end(), 
				displayWorkplaceLst.stream().map(x -> x.getId()).collect(Collectors.toList()), 
				employmentCDLst);
	}
	
	public Map<String, String> getMailCountUnConfirmDay(DatePeriod period, List<DisplayWorkplace> displayWorkplaceLst,
			List<String> employmentCDLst) {
		// クエリモデル「日別の本人確認で未確認の社員」を実行する
		return approvalSttScreenRepository.getMailCountUnConfirmDay(
				period.start(), 
				period.end(), 
				displayWorkplaceLst.stream().map(x -> x.getId()).collect(Collectors.toList()), 
				employmentCDLst);
	}

	public Map<String, String> getMailCountUnApprDay(DatePeriod period, List<DisplayWorkplace> displayWorkplaceLst,
			List<String> employmentCDLst) {
		// クエリモデル「日別上長承認の未承認者で対象の期間内の社員」を実行する
		return approvalSttScreenRepository.getMailCountUnApprDay(
				period.start(), 
				period.end(), 
				displayWorkplaceLst.stream().map(x -> x.getId()).collect(Collectors.toList()), 
				employmentCDLst);
	}

	public Map<String, String> getMailCountUnConfirmMonth(DatePeriod period, List<DisplayWorkplace> displayWorkplaceLst,
			List<String> employmentCDLst) {
		// クエリモデル「社員の月別の雇用が合致する本人未確認の社員」を実行する
		return approvalSttScreenRepository.getMailCountUnConfirmMonth(
				period.end(), 
				displayWorkplaceLst.stream().map(x -> x.getId()).collect(Collectors.toList()), 
				employmentCDLst);
	}

	public Map<String, String> getMailCountUnApprMonth(DatePeriod period, YearMonth processingYm, List<DisplayWorkplace> displayWorkplaceLst,
			List<String> employmentCDLst) {
		// クエリモデル「月別上長承認で未承認の社員」を実行する
		return approvalSttScreenRepository.getMailCountUnApprMonth(
				period.end(), 
				processingYm, 
				displayWorkplaceLst.stream().map(x -> x.getId()).collect(Collectors.toList()), 
				employmentCDLst);
	}

	public Map<String, String> getMailCountWorkConfirm(DatePeriod period, ClosureId closureId, YearMonth yearMonth, 
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
		return null;
	}
	
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
	
	public List<ApprSttWkpEmpMailOutput> getDayApproverToSendMail(List<ApprSttExecutionOutput> apprSttExecutionOutputLst, DatePeriod paramPeriod) {
		// approvalStateAdapter.getApproverByDateLst(employeeIDLst, dateLst, rootType);
		return null;
	}

	public List<ApprSttWkpEmpMailOutput> getMonthApproverToSendMail(List<ApprSttExecutionOutput> apprSttExecutionOutputLst, DatePeriod paramPeriod) {
		// approvalStateAdapter.getApproverByPeriodMonth(employeeID, closureID, yearMonth, closureDate, date);
		return null;
	}

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

	public SendMailResultOutput sendMailToDestination(ApprovalStatusMailTemp approvalStatusMailTemp, List<ApprSttWkpEmpMailOutput> wkpEmpMailLst) {
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
		// 送信対象があるか判別
		if(CollectionUtil.isEmpty(listMailInput)) {
			// メッセージ（Msg_787)を表示する
			throw new BusinessException("Msg_787");
		}
		// アルゴリズム「承認状況メール送信実行」を実行する
		return this.exeApprovalStatusMailTransmission(listMailInput, approvalStatusMailTemp, approvalStatusMailTemp.getMailType());
	}

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

	public List<String> getApprSttUnapprovedAppTarget(List<ApprovalPhaseStateImport_New> phaseLst, GeneralDate appDate) {
		List<String> result = new ArrayList<>();
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
					List<String> targetLst = this.getApprSttUnapprovedAppPerson(Arrays.asList(state.getApproverID()), appDate);
					if(!CollectionUtil.isEmpty(targetLst)) {
						result = targetLst;
						isBreak = true;
					}
				}
				if(isBreak) {
					break;
				}
			}
		}
		return result;
	}

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

	public List<ApprSttConfirmEmp> getConfirmSttByEmpLst(String wkpID, DatePeriod paramPeriod, List<EmpPeriod> empPeriodLst) {
		List<ApprSttConfirmEmp> apprSttConfirmEmpLst = new ArrayList<>();
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
			// apprSttEmpLst.add(new ApprSttEmp(employeeBasicInfoImport.getEmployeeCode(), employeeBasicInfoImport.getPName(), empPeriod.getEmpID(), dateInfoLst));
		}
		return apprSttConfirmEmpLst;
	}
	
	/**
	 * 承認状況未承認者取得代行優先
	 * 
	 * @param appDate
	 * @param confirmAtr
	 * @param listApprovalFrame
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
	
	/**
	 * 承認状況メール埋込URL取得
	 */
	private String getEmbeddedURL(String eid, ApprovalStatusMailTemp domain, ApprovalStatusMailType mailType) {
		List<String> listUrl = new ArrayList<>();
		String contractCD = AppContexts.user().contractCode();
		String employeeCD = AppContexts.user().employeeCode();
		// 承認状況メールテンプレート.URL承認埋込
		if (NotUseAtr.USE.equals(domain.getUrlApprovalEmbed())) {
			List<UrlTaskIncre> listTask = new ArrayList<>();
			listTask.add(new UrlTaskIncre("", "", "", "activeMode", "approval", UrlParamAtr.URL_PARAM));
			// アルゴリズム「埋込URL情報登録」を実行する
			String url1 = registerEmbededURL.embeddedUrlInfoRegis("CMM045", "A", 1, 1, eid, contractCD, "", employeeCD, 0, listTask);
			listUrl.add(url1);
		}
		// 承認状況メールテンプレート.URL日別埋込
		if (NotUseAtr.USE.equals(domain.getUrlDayEmbed())) {
			List<UrlTaskIncre> listTask = new ArrayList<>();
			if (ApprovalStatusMailType.DAILY_UNCONFIRM_BY_CONFIRMER.equals(mailType)) {
				// アルゴリズム「埋込URL情報登録」を実行する
				String url2 = registerEmbededURL.embeddedUrlInfoRegis("KDW004", "A", 1, 1, eid, contractCD, "", employeeCD, 0, listTask);
				listUrl.add(url2);
			} else if (ApprovalStatusMailType.DAILY_UNCONFIRM_BY_PRINCIPAL.equals(mailType)){
				listTask.add(UrlTaskIncre.createFromJavaType("", "", "", "screenMode", "normal"));
				listTask.add(UrlTaskIncre.createFromJavaType("", "", "", "errorRef", "true"));
				listTask.add(UrlTaskIncre.createFromJavaType("", "", "", "changePeriod", "true"));
				// アルゴリズム「埋込URL情報登録」を実行する
				String url2 = registerEmbededURL.embeddedUrlInfoRegis("KDW003", "A", 1, 1, eid, contractCD, "", employeeCD, 0, listTask);
				listUrl.add(url2);
			}
		}
		// 承認状況メールテンプレート.URL月別埋込
		if (NotUseAtr.USE.equals(domain.getUrlMonthEmbed())) {
			List<UrlTaskIncre> listTask = new ArrayList<>();
			if (ApprovalStatusMailType.MONTHLY_UNCONFIRM_BY_CONFIRMER==mailType) {
				listTask.add(new UrlTaskIncre("", "", "", "activeMode", "approval", UrlParamAtr.URL_PARAM));
				// アルゴリズム「埋込URL情報登録」を実行する
				String url3 = registerEmbededURL.embeddedUrlInfoRegis("KMW003", "A", 1, 1, eid, contractCD, "", employeeCD, 0, listTask);
				listUrl.add(url3);
			} else if (ApprovalStatusMailType.MONTHLY_UNCONFIRM_BY_PRINCIPAL==mailType) {
				listTask.add(new UrlTaskIncre("", "", "", "activeMode", "normal", UrlParamAtr.URL_PARAM));
				// アルゴリズム「埋込URL情報登録」を実行する
				String url3 = registerEmbededURL.embeddedUrlInfoRegis("KMW003", "A", 1, 1, eid, contractCD, "", employeeCD, 0, listTask);
				listUrl.add(url3);
			}
		}
		if (listUrl.size() == 0) {
			return "";
		}
		String url = StringUtils.join(listUrl, System.lineSeparator());
		return System.lineSeparator() + TextResource.localize("KAF018_190") + System.lineSeparator() + url;
	}
}
