package nts.uk.screen.at.app.monthlyperformance.correction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.workrecord.operationsetting.FormatPerformanceDto;
import nts.uk.ctx.at.record.app.find.workrecord.operationsetting.IdentityProcessDto;
import nts.uk.ctx.at.record.app.find.workrecord.operationsetting.IdentityProcessFinder;
import nts.uk.ctx.at.record.dom.approvalmanagement.ApprovalProcessingUseSetting;
import nts.uk.ctx.at.record.dom.approvalmanagement.repository.ApprovalProcessingUseSettingRepository;
import nts.uk.ctx.at.record.dom.organization.EmploymentHistoryImported;
import nts.uk.ctx.at.record.dom.organization.adapter.EmploymentAdapter;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.IdentityProcessUseSet;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentityProcessUseSetRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcess;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcessRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.FormatPerformance;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.FormatPerformanceRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.IdentityProcess;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.IdentityProcessRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.MonPerformanceFun;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.MonPerformanceFunRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.SettingUnitType;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.pub.workrule.closure.PresentClosingPeriodExport;
import nts.uk.ctx.at.shared.pub.workrule.closure.ShClosurePub;
import nts.uk.screen.at.app.dailyperformance.correction.DailyPerformanceScreenRepo;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPCellDataDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyPerformanceEmployeeDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.ActualTime;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.ActualTimeState;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.CorrectionOfDailyPerformance;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.DisplayItem;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MPCellDataDto;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MPCellStateDto;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MPControlDisplayItem;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MPDataDto;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MPHeaderDto;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MonthlyPerformanceAuthorityDto;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MonthlyPerformanceCorrectionDto;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MonthlyPerformanceEmployeeDto;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.OperationOfMonthlyPerformanceDto;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * TODO
 */
@Stateless
public class MonthlyPerformanceCorrectionProcessor {

	@Inject
	private FormatPerformanceRepository formatPerformanceRepository;
	@Inject
	private MonPerformanceFunRepository monPerformanceFunRepository;
	@Inject
	private ApprovalProcessRepository approvalProcessRepository;
	@Inject
	private IdentityProcessFinder identityProcessFinder;
	@Inject
	private ApprovalProcessingUseSettingRepository approvalProcessingUseSettingRepository;
	@Inject
	private IdentityProcessUseSetRepository identityProcessUseSetRepository;
	@Inject
	private EmploymentAdapter employmentAdapter;
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;
	@Inject
	private ShClosurePub shClosurePub;
	
	@Inject
	private ClosureRepository closureRepository;

	@Inject
	private ClosureService closureService;
	@Inject 
	private MonthlyPerformanceDisplay monthlyDisplay;
	@Inject 
	private MonthlyPerformanceCheck monthlyCheck;
	@Inject
	private DailyPerformanceScreenRepo repo;
	/**
	 * @return TODO
	 */
	public MonthlyPerformanceCorrectionDto initScreen(int initMode, List<DailyPerformanceEmployeeDto> lstEmployees,  List<String> formatCodes, CorrectionOfDailyPerformance correctionOfDaily) {
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		String rollId = AppContexts.user().roles().forAttendance();
		AppContexts.user().roles();
		MonthlyPerformanceCorrectionDto screenDto = new MonthlyPerformanceCorrectionDto();
		//1. 起動に必要な情報の取得
		// ドメインモデル「実績修正画面で利用するフォーマット」を取得する		
		Optional<FormatPerformance> formatPerformance = formatPerformanceRepository.getFormatPerformanceById(companyId);
		// ドメインモデル「月別実績の修正の機能」を取得する
		Optional<MonPerformanceFun> monPerformanceFun = monPerformanceFunRepository.getMonPerformanceFunById(companyId);
		// ドメインモデル「本人確認処理の利用設定」を取得する
		IdentityProcessDto identityProcess = identityProcessFinder.getAllIdentityProcessById(companyId);
		// ドメインモデル「承認処理の利用設定」を取得する
		Optional<ApprovalProcess> approvalProcess = approvalProcessRepository.getApprovalProcessById(companyId);
		// 承認処理の利用設定
		Optional<ApprovalProcessingUseSetting> approvalProcessingUseSetting = this.approvalProcessingUseSettingRepository.findByCompanyId(companyId);
		//Comment
		if(monPerformanceFun.isPresent()){
			screenDto.setComment(monPerformanceFun.get().getComment().v());
		}
		//本人確認処理の利用設定
		screenDto.setIdentityProcess(identityProcess);
		
		//2. アルゴリズム「ログイン社員の月別実績の権限を取得する」を実行する(Lấy quyền thực hiện monthly result của thằng login)
		//TODO 勤務実績の権限 Authority of the work record
		boolean isExistAuthorityWorkRecord = true;
		//存在しない場合
		if(!isExistAuthorityWorkRecord)
		{
			//エラーメッセージ（Msg_914）を表示する
			throw new BusinessException("Msg_914");
		}		
		//3. アルゴリズム「ログイン社員の締めを取得する」を実行する(Lấy thông tin close của thằng login
		Integer closureId = getClosureId(companyId, employeeId, GeneralDate.today());
		
		//4.アルゴリズム「処理年月の取得」を実行する Láy ngày tháng năm xử lý
		Optional<PresentClosingPeriodExport> presentClosingPeriodExport = this.shClosurePub.find(companyId, closureId);
		
		//アルゴリズム「締め情報の表示」を実行する
		Integer yearMonth = 0;
		DateRange dateRange;
		if (presentClosingPeriodExport.isPresent()) {
			yearMonth = presentClosingPeriodExport.get().getProcessingYm().v();
			screenDto.setProcessDate(yearMonth);
			dateRange = new DateRange(presentClosingPeriodExport.get().getClosureStartDate(),
					presentClosingPeriodExport.get().getClosureEndDate());
		}else{
			//TODO confirm
			dateRange = new DateRange(GeneralDate.legacyDate(new Date()).addMonths(-1).addDays(+1), GeneralDate.legacyDate(new Date()));
		}
		//5. アルゴリズム「締め情報の表示」を実行する HIền thị thông tin Close締め情報の表示
		this.displayClosure(screenDto, companyId, closureId, yearMonth);
		
		//6. どのメニューから起動したのかをチェックする (Check xem khởi động từ menu nào)
		//「月別実績の修正」からの場合
		if (initMode == 0) {
			//7. アルゴリズム「通常モードで起動する」を実行する khởi động ở mode thông thường
			//アルゴリズム「<<Public>> 就業条件で社員を検索して並び替える」を実行する
			/*
			 * TODO code dang sua.
			 */
			 screenDto.setLstEmployee(extractEmployeeList(lstEmployees, employeeId, dateRange));
			List<DailyPerformanceEmployeeDto> lstEmployeeData = extractEmployeeData(initMode, employeeId,
					screenDto.getLstEmployee());
			List<String> employeeIds = lstEmployeeData.stream().map(e -> e.getId()).collect(Collectors.toList());
			
			//List<String> employeeIds = new ArrayList<>();
			//アルゴリズム「表示フォーマットの取得」を実行する(Thực hiện 「Lấy format hiển thị」)
			DisplayItem dispItem = monthlyDisplay.getDisplayFormat(employeeIds, dateRange, formatCodes, correctionOfDaily, formatPerformance.get().getSettingUnitType(), screenDto);
			
			//TODO アルゴリズム「月別実績を表示する」を実行する Hiển thị monthly result
			
			//TODO dummy data
			screenDto.setSelectedActualTime(new ActualTime(GeneralDate.today(), GeneralDate.today()));
			//TODO end
			//アルゴリズム「実績の時系列をチェックする」を実行する (Check actual time)
			screenDto.setActualTimeState(monthlyCheck.checkActualTime(closureId, yearMonth, screenDto.getSelectedActualTime()).value);
			
			//TODO Dummy data
			//screenDto.setLstControlDisplayItem(new MPControlDisplayItem());
			//author
			List<MonthlyPerformanceAuthorityDto> listAuthor = new ArrayList<>();
			listAuthor.add(new MonthlyPerformanceAuthorityDto(rollId, BigDecimal.valueOf(32), true));
			listAuthor.add(new MonthlyPerformanceAuthorityDto(rollId, BigDecimal.valueOf(33), true));
			listAuthor.add(new MonthlyPerformanceAuthorityDto(rollId, BigDecimal.valueOf(34), true));
			listAuthor.add(new MonthlyPerformanceAuthorityDto(rollId, BigDecimal.valueOf(12), true));
			listAuthor.add(new MonthlyPerformanceAuthorityDto(rollId, BigDecimal.valueOf(11), true));
			screenDto.setAuthorityDto(listAuthor);
			//format type			
			screenDto.setFormatPerformance(new FormatPerformanceDto(companyId, SettingUnitType.AUTHORITY.value));
			//Add dummy header
			screenDto.getLstControlDisplayItem().getLstHeader().addAll(MPHeaderDto.GenerateFixedHeader());
			screenDto.getLstControlDisplayItem().getLstHeader().add(new MPHeaderDto("Inbound time", "time", "String", "140px", "", false, "Label", true, true));
			screenDto.getLstControlDisplayItem().getLstHeader().add(new MPHeaderDto("Button", "alert", "String", "140px", "", false, "Button", true, true));
			screenDto.setLstFixedHeader(MPHeaderDto.GenerateFixedHeader());
			screenDto.setProcessDate(201803);
			//Cellstate
			List<MPCellStateDto> lstCellState = new ArrayList<>();
			//
			//setting data
			List<MPDataDto> lstData = new ArrayList<>();
			for (int i = 1; i < 100; i++) {				
				MPDataDto mpdata = new MPDataDto("id"+i, 
						false, "",
						"employeeId" + i, "employeeCode" + i, 
						"employeeName" + i, 
						"", "", "", true, true, true, "");
				mpdata.addCellData(new MPCellDataDto("time", "11:" + i, "String", ""));
				mpdata.addCellData(new MPCellDataDto("alert", "", "String", ""));
				lstData.add(mpdata);
				
				lstCellState.add(new MPCellStateDto("id"+i, "time", Arrays.asList("ntsgrid-alarm")));
			}			
			screenDto.setLstCellState(lstCellState);
			screenDto.setLstData(lstData);
			//
			List<ActualTime> actualTimes = new ArrayList<>();
			actualTimes.add(new ActualTime(dateRange.getStartDate(), dateRange.getEndDate()));
			actualTimes.add(new ActualTime(GeneralDate.today(), GeneralDate.today()));
			screenDto.setLstActualTimes(actualTimes);
			//comment
			screenDto.setComment("生産性を上げて残業時間を減らしましょう！");
			//A4_2：対象締め日
			screenDto.setClosureName("末締め");
			//Enable check
			screenDto.setActualTimeState(ActualTimeState.CurrentMonth.value);
			//End dummy data
			
			
		}
		//「月別実績の承認」からの場合
		else{
			//TODO 対象外
		}
		
		
		return screenDto;
	}
	
	/**
	 * アルゴリズム「ログイン社員の締めを取得する」を実行する
	 * @return 対象締め：締めID
	 */
	private Integer getClosureId(String cId, String employeeId, GeneralDate sysDate) {
		// アルゴリズム「社員IDと基準日から社員の雇用コードを取得」を実行する
		// Thực hiện thuật toán「Lấy employment từ employee ID và base date」
		// 社員IDと基準日から社員の雇用コードを取得
		Optional<EmploymentHistoryImported> empHistory = this.employmentAdapter.getEmpHistBySid(cId, employeeId, sysDate);
		// 雇用に紐づく締めを取得する
		String employmentCode = empHistory.orElseThrow(() -> new BusinessException("Msg_1143")).getEmploymentCode();
		Optional<ClosureEmployment> closureEmployment = this.closureEmploymentRepository.findByEmploymentCD(cId, employmentCode);
		// アルゴリズム「処理年月と締め期間を取得する」を実行する
		Integer closureId = closureEmployment.orElseThrow(() -> new BusinessException("Msg_1143")).getClosureId();
		
		//対象締め：締めID
		return closureId;
	}
	private List<DailyPerformanceEmployeeDto> extractEmployeeData(Integer initScreen, String sId,
			List<DailyPerformanceEmployeeDto> emps) {
		if (initScreen == 0) {
			return emps.stream().filter(x -> x.getId().equals(sId)).collect(Collectors.toList());
		}
		return emps;

	}
	/**
	 * Get id of employee list.
	 */
	private List<DailyPerformanceEmployeeDto> extractEmployeeList(List<DailyPerformanceEmployeeDto> lstEmployee,
			String sId, DateRange range) {
		if (!lstEmployee.isEmpty()) {
			return lstEmployee;
		} else {
			return getListEmployee(sId, range);
		}
	}
	/** アルゴリズム「対象者を抽出する」を実行する */
	private List<DailyPerformanceEmployeeDto> getListEmployee(String sId, DateRange dateRange) {
		// アルゴリズム「自職場を取得する」を実行する
		// List<String> lstJobTitle = this.repo.getListJobTitle(dateRange);
		// List<String> lstEmployment = this.repo.getListEmployment();
		/// 対応するドメインモデル「所属職場」を取得する + 対応するドメインモデル「職場」を取得する
		Map<String, String> lstWorkplace = this.repo.getListWorkplace(sId, dateRange);
		// List<String> lstClassification = this.repo.getListClassification();
		// 取得したドメインモデル「所属職場．社員ID」に対応するImported「（就業）社員」を取得する
		if (lstWorkplace.isEmpty()) {
			return new ArrayList<>();
		}
		return this.repo.getListEmployee(null, null, lstWorkplace, null);
	}
	/**
	 * 締め情報の表示
	 */
	private void displayClosure(MonthlyPerformanceCorrectionDto screenDto, String companyId, Integer closureId, Integer startYM){
		//アルゴリズム「締めの名称を取得する」を実行する
		//Thực hiện thuật toán 「Get close Name」
		Optional<ClosureHistory> closureHis = closureRepository.findById(companyId, closureId, startYM);
		if(closureHis.isPresent()){
			//締め名称　→　画面項目「A4_2：対象締め日」			
			screenDto.setClosureName(closureHis.get().getClosureName().v());
		}
		
		//アルゴリズム「実績期間の取得」を実行する Lấy thời gian thực tế
		//TODO リクエスト待ち：NO245
		//(Wait request: NO245)
		/**
		 * 【Input】
		 * ・処理年月：パラメータ「処理年月」に一致する
		 * ・締めID：パラメータ「締めID」に一致する
		 */
		List<ActualTime> actualTimes = new ArrayList();
		//実績期間　→　画面項目「A4_5：実績期間選択肢」
		screenDto.setLstActualTimes(actualTimes);
		//実績期間の件数をチェックする
		//Check số thời gian thực
		if (actualTimes.size() == 2) {
			//当月の期間を算出する
			//Tính toán thời gian của tháng này
			DatePeriod datePeriod = closureService.getClosurePeriod(closureId, new YearMonth(startYM));
			//画面項目「A4_4：実績期間選択」の選択状態を変更する
			screenDto.setSelectedActualTime(new ActualTime(datePeriod.start(), datePeriod.end()));
		}		
	}
	/**
	 * 月別実績を表示する
	 */
	private void displayMonthlyResult(MonthlyPerformanceCorrectionDto screenDto){
		//社員ID（List）から社員コードと表示名を取得
		//Lấy employee code và tên hiển thị từ list employeeID
		//TODO Get data from 社員データ管理情報(Employee data management information) 
		//SyEmployeePub 

		//アルゴリズム「対象年月に対応する月別実績を取得する」を実行する Lấy monthly result ứng với năm tháng
		//ドメインモデル「月別実績の勤怠時間」の取得
		
		//TODO ドメインモデル「月別実績の編集状態」すべて取得する
		
	}
}
