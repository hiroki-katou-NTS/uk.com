package nts.uk.screen.at.app.ktgwidget;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workrule.closure.PresentClosingPeriodImport;
import nts.uk.ctx.at.record.dom.approvalmanagement.dailyperformance.algorithm.closure.ClosureHistPeriod;
import nts.uk.ctx.at.record.dom.approvalmanagement.dailyperformance.algorithm.closure.GetSpecifyPeriod;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.GetExcessTimesYear;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.AgreementTimeDetail;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.GetAgreementTime;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.standardtime.AgreementOperationSetting;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.screen.at.app.ktgwidget.find.dto.EmployeesOvertimeDisplayDto;
import nts.uk.screen.at.app.ktgwidget.find.dto.YearAndEmpOtHoursDto;
import nts.uk.screen.at.app.mobi.AgreementTimeToppage;
import nts.uk.ctx.at.record.dom.standardtime.export.GetAgreementPeriodFromYear;
import nts.uk.ctx.at.record.dom.standardtime.export.GetAgreementTimeOfMngPeriod;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementOperationSettingRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class KTG026QueryProcessor {
	
	// The record dom require service
	@Inject 
	private RecordDomRequireService requireService;
	
	// The agreement operation setting repository
	@Inject
	AgreementOperationSettingRepository agreementOperationSettingRepository;
	
	// The get specify period
	@Inject
	private GetSpecifyPeriod getSpecifyPeriod;
	
	// Get excess times year
	@Inject
	private GetExcessTimesYear getExcessTimesYear;
	
	// 36協定運用設定の取得 
	@Inject
	private AgreementOperationSettingRepository agreementOpeSetRepo;
	
	// 管理期間の36協定時間を取得
	@Inject
	private GetAgreementTimeOfMngPeriod getAgreementTimeOfMngPeriod;
	
	/**
	 * UKDesign.UniversalK.就業.KTG_ウィジェット.KTG026_時間外労働時間の表示(従業員用).アルゴリズム.起動する.起動する
	 * @param employeeId
	 */
	public void startScreenKtg026(String employeeId, Integer targetDate, Integer targetYear, String referenceMode, int currentOrNextMonth) {
		// 従業員用の時間外時間表示
		EmployeesOvertimeDisplayDto result = EmployeesOvertimeDisplayDto.builder().build();

		val require = requireService.createRequire();
		CacheCarrier cacheCarrier = new CacheCarrier();
		// システム日付
		GeneralDate systemDate = GeneralDate.today();

		// 社員に対応する処理締めを取得する
		Closure closure = ClosureService.getClosureDataByEmployee(require, cacheCarrier, employeeId, systemDate);
		
		// 従業員用の時間外時間表示．ログイン者の締めID＝取得したドメインモデル「締め」．締めID
		result.setClosureID(closure.getClosureId().value);

		YearMonth yearMonth = closure.getClosureMonth().getProcessingYm();

		// 指定した年月の締め期間を取得する
		List<ClosureHistPeriod> lstClosureHist = getSpecifyPeriod.getSpecifyPeriod(yearMonth);

		// 従業員用の時間外時間表示．当月の締め情報．処理年月＝取得したドメインモデル「締め」．当月
		// 従業員用の時間外時間表示．当月の締め情報．締め開始日＝取得した締め期間．開始日
		// 従業員用の時間外時間表示．当月の締め情報．締め終了日＝取得した締め期間．終了日
		if (!lstClosureHist.isEmpty()) {
			PresentClosingPeriodImport closingPeriod = new PresentClosingPeriodImport(
				closure.getClosureMonth().getProcessingYm(),
				lstClosureHist.get(0).getPeriod().start(),
				lstClosureHist.get(0).getPeriod().end());
			result.setClosingPeriod(closingPeriod);
		}

		// アルゴリズム「年月を指定して、36協定期間の年度を取得する」を実行する
		Year year = this.getYearAgreementPeriod(closure.getClosureMonth().getProcessingYm());
		
		// 従業員用の時間外時間表示．当月含む年＝取得した年度
		result.setYear(year.v());
		// 従業員用の時間外時間表示．表示する年＝取得した年度
		result.setDisplayYear(year.v());

		// INPUT．対象年をチェックする
		if (targetYear != null) { // INPUT．対象年 != NULL
			// 従業員用の時間外時間表示．表示する年＝INPUT．対象年
			result.setDisplayYear(targetYear);
		} else { // INPUT．対象年 = NULL
			
			// INPUT．対象年月をチェックする
			if (targetDate != null) { // INPUT．対象年月 != NULL
				
				// アルゴリズム「年月を指定して、36協定期間の年度を取得する」を実行する
				// 従業員用の時間外時間表示．表示する年＝取得した年度
				result.setDisplayYear(this.getYearAgreementPeriod(YearMonth.of(targetDate)).v());

			} else {
				if (currentOrNextMonth == 2) {
					Year year2 = this.getYearAgreementPeriod(result.getClosingPeriod().getProcessingYm().addMonths(1));
					result.setYear2(year2.v());
					result.setDisplayYear(year2.v());
				}
			}

		}
	}
	
	/**
	 * 	年月を指定して、36協定期間の年度を取得する
	 * @param yearMonth 年月
	 * @return year 年度
	 */
	public Year getYearAgreementPeriod(YearMonth yearMonth) {
		// 会社ID
		String companyID = AppContexts.user().companyId();
		// 起算月を取得 Get the starting month
		AgreementOperationSetting agreeOpSet = agreementOperationSettingRepository.find(companyID).get();
		Year year = new Year(yearMonth.year());
		
		// パラメータ「年月」の月と起算月を比較 The month of the parameter "year-month" and the starting month are compared
		if (yearMonth.month() < (agreeOpSet.getStartingMonth().value + 1)) { // パラメータ「年月」の月　＜　起算月　の場合
			// パラメータ「年月」の年を-1年する Make the year of the parameter "year-month" -1 year
			year = new Year(yearMonth.year() - 1);
		}
		
		return year;
	}
	
	/**
	 * UKDesign.UniversalK.就業.KTG_ウィジェット.KTG026_時間外労働時間の表示(従業員用).アルゴリズム.指定する年と指定する社員の時間外時間と超過回数の取得.指定する年と指定する社員の時間外時間と超過回数の取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param closingId 締めID
	 * @param targetYear 対象年
	 * @param processingDate 当月の年月
	 * @return year and employee overtime hours
	 */
	public YearAndEmpOtHoursDto getYearAndEmployeeOTHours(String companyId, String employeeId, String closingId, Year targetYear, YearMonth processingDate) {
		YearAndEmpOtHoursDto result = YearAndEmpOtHoursDto.builder().build();
		val require = requireService.createRequire();
		CacheCarrier cacheCarrier = new CacheCarrier();
		GeneralDate systemDate = GeneralDate.today();
		// [No.458]年間超過回数の取得
		// OUTPUT．「36協定超過情報」＝取得した「36協定超過情報」
		result.setAgreeInfo(getExcessTimesYear.algorithm(employeeId, targetYear));
		
		// 年度から36集計期間を計算
		// ○社員に対応する処理締めを取得する (Lấy closure xử lý ứng với employee)
		Closure closure = ClosureService.getClosureDataByEmployee(require, cacheCarrier, employeeId, systemDate);
		
		// ○３６協定運用設定を取得 (Lấy 36AgreementOperationSetting)
		Optional<AgreementOperationSetting> agreeOperation = agreementOpeSetRepo.find(companyId);
		
		// ○年度から集計期間を取得 (Lấy thời gian tính toán từ niên độ )
		Optional<DatePeriod> datePeriod = GetAgreementPeriodFromYear.algorithm(targetYear, closure, agreeOperation);
		
		if (!datePeriod.isPresent()) {
			return result;
		}
		
		// 取得した期間の年月をループする
		for (int i = datePeriod.get().start().yearMonth().month(); i <= 12; i++) {
			// ループする年月とINPUT．当月の年月の大小比較
			if (processingDate.addMonths(2).lessThanOrEqualTo(datePeriod.get(). start().yearMonth())) { // [INPUT．当月の年月．AddMonth(2)<=ループする年月]がtrue
				break;
				
			} else if (processingDate.lessThanOrEqualTo(datePeriod.get(). start().yearMonth())) { // [INPUT．当月の年月<=ループする年月]がtrue
				// 【NO.333】36協定時間の取得
				List<AgreementTimeDetail> listAgreementTimeDetail = GetAgreementTime.get(
					require, cacheCarrier, companyId, Arrays.asList(employeeId), datePeriod.get().end().yearMonth(), ClosureId.valueOf(closingId));
				
			} else { // [INPUT．当月の年月<=ループする年月]がfalse
				// [NO.612]年月期間を指定して管理期間の36協定時間を取得する
				List<AgreementTimeToppage> agreementTimeToppageLst = 
						getAgreementTimeOfMngPeriod.getAgreementTimeByMonths(Arrays.asList(employeeId), datePeriod.get().start().yearMonth());
				
			}
			
			datePeriod.get().start().yearMonth().addMonths(1);
		}
		
		return result;
		
		
	}
	
}
