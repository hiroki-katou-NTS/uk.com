package nts.uk.screen.at.app.monthlyperformance.correction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
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
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.pub.workrule.closure.PresentClosingPeriodExport;
import nts.uk.ctx.at.shared.pub.workrule.closure.ShClosurePub;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyPerformanceEmployeeDto;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.ActualTime;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.CorrectionOfDailyPerformance;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MonthlyPerformanceCorrectionDto;
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
	private IdentityProcessRepository identityProcessRepository;
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
	/**
	 * @return TODO
	 */
	public MonthlyPerformanceCorrectionDto generateData(int initMode, List<DailyPerformanceEmployeeDto> lstEmployees,  List<String> formatCodes, CorrectionOfDailyPerformance correctionOfDaily) {
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		AppContexts.user().roles();
		MonthlyPerformanceCorrectionDto screenDto = new MonthlyPerformanceCorrectionDto();
		// ドメインモデル「実績修正画面で利用するフォーマット」を取得する		
		Optional<FormatPerformance> formatPerformance = formatPerformanceRepository.getFormatPerformanceById(companyId);
		// ドメインモデル「月別実績の修正の機能」を取得する
		Optional<MonPerformanceFun> monPerformanceFun = monPerformanceFunRepository.getMonPerformanceFunById(companyId);
		// ドメインモデル「承認処理の利用設定」を取得する
		Optional<ApprovalProcess> approvalProcess = approvalProcessRepository.getApprovalProcessById(companyId);
		// ドメインモデル「本人確認処理の利用設定」を取得する
		Optional<IdentityProcess> identityProcess = identityProcessRepository.getIdentityProcessById(companyId);
		
		//ドメインモデル「勤務実績の権限」を取得する
		//TODO 勤務実績の権限 Authority of the work record
		boolean isExistAuthorityWorkRecord = true;
		//存在しない場合
		if(!isExistAuthorityWorkRecord)
		{
			//エラーメッセージ（Msg_914）を表示する
			throw new BusinessException("Msg_914");
		}
		
		//アルゴリズム「ログイン社員の締めを取得する」を実行する(Lấy thông tin close của thằng login)
		Integer closureId = getClosureId(companyId, employeeId, GeneralDate.today());
		
		//アルゴリズム「処理年月の取得」を実行する
		//Láy ngày tháng năm xử lý
		Optional<PresentClosingPeriodExport> presentClosingPeriodExport = this.shClosurePub.find(companyId, closureId);
		screenDto.setPresentClosingPeriodExport(presentClosingPeriodExport.orElse(null));
		
		//アルゴリズム「締め情報の表示」を実行する
		Integer yearMonth = 0;
		if (screenDto.getPresentClosingPeriodExport() != null) {
			yearMonth = screenDto.getPresentClosingPeriodExport().getProcessingYm().v();
		}
		this.displayClosure(screenDto, companyId, closureId, yearMonth);
		
		//どのメニューから起動したのかをチェックする
		//(Check xem khởi động từ menu nào)
		//「月別実績の修正」からの場合
		if (initMode == 0) {
			//アルゴリズム「通常モードで起動する」を実行する
			monthlyDisplay.getDisplayFormat(lstEmployees, formatCodes, correctionOfDaily, formatPerformance.get().getSettingUnitType());
		}
		//「月別実績の承認」からの場合
		else{
			//TODO 対象外
		}
		
		/*// 承認処理の利用設定
		Optional<ApprovalProcessingUseSetting> approvalProcessingUseSetting
				= this.approvalProcessingUseSettingRepository.findByCompanyId(companyId);
		// 本人確認処理の利用設定
		Optional<IdentityProcessUseSet> identityProcessUseSet
				= this.identityProcessUseSetRepository.findByKey(companyId);
		

		screenDto.setApprovalProcessingUseSetting(approvalProcessingUseSetting.orElse(null));
		screenDto.setIdentityProcessUseSet(identityProcessUseSet.orElse(null));*/
		
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
		
		//アルゴリズム「実績期間の取得」を実行する
		//Lấy thời gian thực tế
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
			screenDto.setSelectedActualTime(datePeriod);
		}		
	}
}
