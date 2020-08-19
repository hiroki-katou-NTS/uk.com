package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveRemainingNumberInfo;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveUsedInfo;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveUsedNumber;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.HalfDayAnnLeaRemainingNum;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.HalfDayAnnLeaUsedNum;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveRemainingNumber;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnualLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AnnualLeaveInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.testdata.TestData;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.testdata.TestDataForOverWriteList;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.testdata.TestOutputAggrResultOfAnnualLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.GetHolidayOver60hRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.GetHolidayOver60hRemNumWithinPeriodImpl;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param.AggrResultOfHolidayOver60h;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualLeaveMngWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.holidayover60h.interim.TmpHolidayOver60hMngWork;

/**
 * 年休　テストコード
 * @author masaaki_jinno
 *
 */
public class TestAnnualLeave {

	// Requireクラス　（バイナリファイルからデータを読み込むテストクラス）
	GetAnnLeaRemNumWithinPeriodProc.RequireM3 ｒequireM3;
	
	/** 上書き用の暫定年休管理データ */
	private Map<String, List<TmpAnnualLeaveMngWork>> testDataForOverWriteListMap;
//	private Map<int, inputObject2> inputList2;
//	private Map<int, expectedValue> expectedValueList;
	
	/** 期待値 */
	private List<TestOutputAggrResultOfAnnualLeave> testOutputAggrResultOfAnnualLeaveList; 
	private Map<String, TestOutputAggrResultOfAnnualLeave> testOutputAggrResultOfAnnualLeaveListMap;
	
	/** テストケース */
	private List<AnnualleaveTestCase> caseList;
	
	
	/*検証用のドメイン取得処理*/
	@Before
	public void initialize(){

		// Requireクラス　バイナリファイルからデータを読み込む
		ｒequireM3 = new CalcAnnLeaAttendanceRateRequireM3Test();
		
		// 上書き用の暫定年休管理データ 
		this.testDataForOverWriteListMap = TestDataForOverWriteList.build();
		
		// 期待値のリスト
		testOutputAggrResultOfAnnualLeaveList = TestOutputAggrResultOfAnnualLeave.build();
		
		// 期待値のリストを、テストケースＮｏをキーに、MapListへ変換
		testOutputAggrResultOfAnnualLeaveListMap = new HashMap<String, TestOutputAggrResultOfAnnualLeave>();
		for( TestOutputAggrResultOfAnnualLeave t : testOutputAggrResultOfAnnualLeaveList ){
			testOutputAggrResultOfAnnualLeaveListMap.put(t.getCaseNo(), t);
		}
		
		// テストケース一覧読み込み
		this.caseList = AnnualleaveTestCase.build();
	}

	/*検証method*/
	@Test
	public void assert1(){
		try{
			for(AnnualleaveTestCase annualleaveTestCase : this.caseList){ // テストケースをループ
				
				// テストケースＮｏ
				String caseNo = annualleaveTestCase.getCaseNo();
				
//				// Requireクラス
//				GetAnnLeaRemNumWithinPeriodRequire g_require
//					= TestGetAnnLeaRemNumWithinPeriodRequireFactory.create("1");
				
				// テストしたい処理を実行	
//				GetAnnLeaRemNumWithinPeriodProc proc = new GetAnnLeaRemNumWithinPeriodProc();
//					g_require.getEmpEmployee(),
//					g_require.getAnnLeaEmpBasicInfoRepo(),
//					g_require.getYearHolidayRepo(),
//					g_require.getLengthServiceRepo(),
//					g_require.getAnnualPaidLeaveSet(),
//					g_require.getAnnLeaGrantRemDataRepo(),
//					g_require.getAnnLeaMaxDataRepo(),
//					g_require.getGetClosureStartForEmployee(),
//					g_require.getCalcNextAnnualLeaveGrantDate(),
//					g_require.getInterimRemOffMonth(),
//					g_require.getCreateInterimAnnual(),
//					g_require.getInterimRemainRepo(),
//					g_require.getTmpAnnualLeaveMng(),
//					g_require.getAttendanceTimeOfMonthlyRepo(),
//					g_require.getGetAnnLeaRemNumWithinPeriod(),
//					g_require.getClosureSttMngRepo(),
//					g_require.getCalcAnnLeaAttendanceRate(),
//					g_require.getGrantYearHolidayRepo(),
//					g_require.getOperationStartSetRepo(),
//					g_require.getAnnualLeaveRemainHistRepo());
				
				// テスト用会社ID ooooo 要修正
				String companyId = "000000000000-0101";
				
				// 社員 ooooo 要修正　CSVファイルで社員コードを取得して、DBより社員IDに変換する
				//String employeeId = annualleaveTestCase.getEmployee();
				String employeeId = "ca294040-910f-4a42-8d90-2bd02772697c";
				
				// 期間
				String s_aggr_period = annualleaveTestCase.getAggrPeriod();
				DatePeriod aggrPeriod = TestData.strToDatePeriod(s_aggr_period);
				
//				// 期間の開始日を「社員に対応する締め開始日」として処理を行う。
//				val testGetClosureStartForEmployee 
//					= (TestGetClosureStartForEmployee)g_require.getGetClosureStartForEmployee();
//				testGetClosureStartForEmployee.startDate 
//					= GeneralDate.ymd(aggrPeriod.start().year(), aggrPeriod.start().month(), aggrPeriod.start().day());
				
				// モード
				InterimRemainMngMode mode;
				String s_mode = annualleaveTestCase.getMode();
				if ( s_mode.equals("月次") || s_mode.equals("1") ){
					mode = InterimRemainMngMode.MONTHLY;
				}
				else{
					mode = InterimRemainMngMode.OTHER;
				}
				
				// 基準日
				String s_criterial_date = annualleaveTestCase.getCriteriaDate();
				GeneralDate criterialDate = TestData.stringyyyyMMddToGeneralDate(s_criterial_date);
				
				// 出勤率フラグ
				boolean isCalcAttendanceRate = true;
				String s_isCalcAttendanceRate = annualleaveTestCase.getIsCalcAttendanceRate();
				if (s_isCalcAttendanceRate.equals("TRUE")){
					isCalcAttendanceRate = true;
				} else if (s_isCalcAttendanceRate.equals("FALSE")){
					isCalcAttendanceRate = false;
				}

				// 上書きフラグ
				boolean isOverWrite = false;
				String s_isOverWriteOpt = annualleaveTestCase.getIsOverWrite();
				if (s_isOverWriteOpt.equals("TRUE")){
					isOverWrite = true;
				} else if ( s_isOverWriteOpt.equals("FALSE")){
					isOverWrite = false;
				}
				
				// 上書き用の暫定年休管理データ
				String s_forOverWriteListMap = annualleaveTestCase.getForOverWriteList();
				val testDataForOverWriteList
					= testDataForOverWriteListMap.get(s_forOverWriteListMap);
				
				boolean isGetNextMonthData = true;
				
				// 集計開始日を締め開始日とする　（締め開始日を確認しない）
				boolean noCheckStartDate = true; // 今回から未使用
				
				// 不足分付与残数データ出力区分
				boolean isOutShortRemain = true; // ooooo
				
				// 過去月集計モード
				boolean aggrPastMonthMode = true; // ooooo
				
				// キャッシュ
				CacheCarrier cacheCarrier = new CacheCarrier();
				
//				// 年月
//				YearMonth yearMonth = YearMonth.of("202003"); // ooooo
				
				// 月別集計で必要な会社別設定
				//companySets 
					
//				// テストしたい処理を実行
				Optional<AggrResultOfAnnualLeave> aggrResultOfAnnualLeaveOpt
					= GetAnnLeaRemNumWithinPeriodProc.algorithm(
						ｒequireM3,
						cacheCarrier,
						companyId,
						employeeId,
						aggrPeriod,
						mode,
						criterialDate,
						isGetNextMonthData,
						isCalcAttendanceRate,
						Optional.of(isOverWrite),
						Optional.of(testDataForOverWriteList),
						Optional.empty(), 	// 前回の年休の集計結果
						noCheckStartDate,	// 集計開始日を締め開始日とする　（締め開始日を確認しない）
						Optional.of(isOutShortRemain), // 不足分付与残数データ出力区分
						Optional.of(aggrPastMonthMode), // 過去月集計モード
						Optional.empty(),	// 年月
						Optional.empty(),
						Optional.empty(),
						Optional.empty());
				
				if ( aggrResultOfAnnualLeaveOpt.isPresent() ){
					
					// 検証			
					assertProcedure(
							caseNo, 
							aggrResultOfAnnualLeaveOpt.get(), 
							testOutputAggrResultOfAnnualLeaveListMap);
				}
				
				String ss = "";
			}
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
	}

	/**
	 * 計算結果と期待値を照合
	 * @param testCase
	 * @param aggrResultOfAnnualLeave
	 * @param testOutputAggrResultOfAnnualLeaveListMap
	 */
	private void assertProcedure(
			String testCase,
			AggrResultOfAnnualLeave aggrResultOfAnnualLeave,
			Map<String, TestOutputAggrResultOfAnnualLeave> testOutputAggrResultOfAnnualLeaveListMap
			){
		
		TestOutputAggrResultOfAnnualLeave t_result
			= testOutputAggrResultOfAnnualLeaveListMap.get(testCase);
		
		if (t_result == null){
			assertThat(t_result == null, is(false));
			return;
		}
		
		
		/** 年休情報（期間終了日時点） */
		
		// 残数年休マイナスあり使用数
		{
			// 付与前
			double result_usedDaysBeforeGrant = 0.0;
			int result_usedTimeBeforeGrant = 0;
			// 合計
			double result_usedDaysTotal = 0.0;
			int result_usedTimeTotal = 0;
			// 付与後
			double result_usedDaysAfterGrant = 0.0;
			int result_usedTimeAfterGrant = 0;
			
			// 年休情報
			AnnualLeaveInfo asOfPeriodEnd = aggrResultOfAnnualLeave.getAsOfPeriodEnd();
			if ( asOfPeriodEnd != null ){
				
				// 年休情報残数
				nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AnnualLeaveRemainingNumber remainingNumber 
					= asOfPeriodEnd.getRemainingNumber();
				if ( remainingNumber != null ){
					
					// 年休（マイナスあり）
					nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeave annualLeave
						= remainingNumber.getAnnualLeaveWithMinus();
					
					if ( annualLeave != null ){
						
						// 年休使用
						AnnualLeaveUsedInfo usedInfo = annualLeave.getUsedNumberInfo();
						
						// 残数付与前
						AnnualLeaveUsedNumber usedNumberBeforeGrant = usedInfo.getUsedNumberBeforeGrant();
						
						if ( usedNumberBeforeGrant != null ){
							// 使用日数
							result_usedDaysBeforeGrant = usedNumberBeforeGrant.getUsedDays().getUsedDayNumber().v();
							
							// 使用時間
							if ( usedNumberBeforeGrant.getUsedTime().isPresent() ){
								result_usedTimeBeforeGrant = usedNumberBeforeGrant.getUsedTime().get().getUsedTime().v();
							}
						}
						
						// 合計
						AnnualLeaveUsedNumber usedNumberTotal = usedInfo.getUsedNumber();
						
						if ( usedNumberTotal != null ){
							// 使用日数
							result_usedDaysTotal = usedNumberTotal.getUsedDays().getUsedDayNumber().v();
							
							// 使用時間
							if ( usedNumberTotal.getUsedTime().isPresent() ){
								result_usedTimeTotal = usedNumberTotal.getUsedTime().get().getUsedTime().v();
							}
						}
						
						// 残数付与後
						if ( usedInfo.getUsedNumberAfterGrantOpt().isPresent() ){
							AnnualLeaveUsedNumber usedNumberAfterGrant = usedInfo.getUsedNumberAfterGrantOpt().get();
											
							// 使用日数
							result_usedDaysAfterGrant = usedNumberAfterGrant.getUsedDays().getUsedDayNumber().v();
							
							// 使用時間
							if ( usedNumberAfterGrant.getUsedTime().isPresent() ){
								result_usedTimeAfterGrant = usedNumberAfterGrant.getUsedTime().get().getUsedTime().v();
							}
						}
					}
				}
			}
			
//			残数年休マイナスあり使用数付与前使用日数
//			残数年休マイナスあり使用数付与前使用時間
//			残数年休マイナスあり使用数合計使用日数
//			残数年休マイナスあり使用数合計使用時間
//			残数年休マイナスあり使用数使用回数
//			残数年休マイナスあり使用数付与後使用日数
//			残数年休マイナスあり使用数付与後使用時間
			
			assert_double(testCase, "残数年休マイナスあり使用数付与前使用日数", result_usedDaysBeforeGrant, t_result);
			assert_integer(testCase, "残数年休マイナスあり使用数付与前使用時間", result_usedTimeBeforeGrant, t_result);
			
			assert_double(testCase, "残数年休マイナスあり使用数合計使用日数", result_usedDaysTotal, t_result);
			assert_integer(testCase, "残数年休マイナスあり使用数合計使用時間", result_usedTimeTotal, t_result);
	
			assert_double(testCase, "残数年休マイナスあり使用数付与後使用日数", result_usedDaysAfterGrant, t_result);
			assert_integer(testCase, "残数年休マイナスあり使用数付与後使用時間", result_usedTimeAfterGrant, t_result);
		}
	
		
		// 残数年休マイナスあり残数		
		{
			// 付与前
			double result_remainingDaysBeforeGrant = 0.0;
			int result_remainingTimeBeforeGrant = 0;
			// 合計
			double result_remainingDaysTotal = 0.0;
			int result_remainingTimeTotal = 0;
			// 付与後
			double result_remainingDaysAfterGrant = 0.0;
			int result_remainingTimeAfterGrant = 0;
			
			// 年休情報
			AnnualLeaveInfo asOfPeriodEnd = aggrResultOfAnnualLeave.getAsOfPeriodEnd();
			if ( asOfPeriodEnd != null ){
				
				// 年休情報残数
				nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AnnualLeaveRemainingNumber remainingNumber 
					= asOfPeriodEnd.getRemainingNumber();
				if ( remainingNumber != null ){
					
					// 年休（マイナスあり）
					nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeave annualLeave
						= remainingNumber.getAnnualLeaveWithMinus();
					
					if ( annualLeave != null ){
						
						// 年休残数
						AnnualLeaveRemainingNumberInfo remainingInfo = annualLeave.getRemainingNumberInfo();
						
						// 残数付与前
						AnnualLeaveRemainingNumber remainingNumberBeforeGrant = remainingInfo.getRemainingNumberBeforeGrant();
						
						if ( remainingNumberBeforeGrant != null ){
							// 使用日数
							result_remainingDaysBeforeGrant = remainingNumberBeforeGrant.getTotalRemainingDays().v();
							
							// 使用時間
							if ( remainingNumberBeforeGrant.getTotalRemainingTime().isPresent() ){
								result_remainingTimeBeforeGrant = remainingNumberBeforeGrant.getTotalRemainingTime().get().v();
							}
						}
						
						// 合計
						AnnualLeaveRemainingNumber remainingNumberTotal = remainingInfo.getRemainingNumber();
						
						if ( remainingNumberTotal != null ){
							// 使用日数
							result_remainingDaysTotal = remainingNumberTotal.getTotalRemainingDays().v();
							
							// 使用時間
							if ( remainingNumberTotal.getTotalRemainingTime().isPresent() ){
								result_remainingTimeTotal = remainingNumberTotal.getTotalRemainingTime().get().v();
							}
						}
						
						// 残数付与後
						if ( remainingInfo.getRemainingNumberAfterGrantOpt().isPresent() ){
							AnnualLeaveRemainingNumber remainingNumberAfterGrant = remainingInfo.getRemainingNumberAfterGrantOpt().get();
											
							// 使用日数
							result_remainingDaysAfterGrant = remainingNumberAfterGrant.getTotalRemainingDays().v();
							
							// 使用時間
							if ( remainingNumberAfterGrant.getTotalRemainingTime().isPresent() ){
								result_remainingTimeAfterGrant = remainingNumberAfterGrant.getTotalRemainingTime().get().v();
							}
						}
					}
				}
			}

//			残数年休マイナスあり残数合計合計残日数
//			残数年休マイナスあり残数合計明細
//			残数年休マイナスあり残数合計合計残時間
//			残数年休マイナスあり残数付与前合計残日数
//			残数年休マイナスあり残数付与前明細
//			残数年休マイナスあり残数付与前合計残時間
//			残数年休マイナスあり残数付与後合計残日数
//			残数年休マイナスあり残数付与後明細
//			残数年休マイナスあり残数付与後合計残時間

			assert_double(testCase, "残数年休マイナスあり残数合計合計残日数", result_remainingDaysBeforeGrant, t_result);
			assert_integer(testCase, "残数年休マイナスあり残数合計合計残時間", result_remainingTimeBeforeGrant, t_result);
			
			assert_double(testCase, "残数年休マイナスあり残数付与前合計残日数", result_remainingDaysTotal, t_result);
			assert_integer(testCase, "残数年休マイナスあり残数付与前合計残時間", result_remainingTimeTotal, t_result);
	
			assert_double(testCase, "残数年休マイナスあり残数付与後明細", result_remainingDaysAfterGrant, t_result);
			assert_integer(testCase, "残数年休マイナスあり残数付与後合計残時間", result_remainingTimeAfterGrant, t_result);
		}			
		
		
		// 残数年休マイナスなし使用数
		{
			// 付与前
			double result_usedDaysBeforeGrant = 0.0;
			int result_usedTimeBeforeGrant = 0;
			// 合計
			double result_usedDaysTotal = 0.0;
			int result_usedTimeTotal = 0;
			// 付与後
			double result_usedDaysAfterGrant = 0.0;
			int result_usedTimeAfterGrant = 0;
			
			// 年休情報
			AnnualLeaveInfo asOfPeriodEnd = aggrResultOfAnnualLeave.getAsOfPeriodEnd();
			if ( asOfPeriodEnd != null ){
				
				// 年休情報残数
				nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AnnualLeaveRemainingNumber remainingNumber 
					= asOfPeriodEnd.getRemainingNumber();
				if ( remainingNumber != null ){
					
					// 年休（マイナスなし）
					nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeave annualLeave
						= remainingNumber.getAnnualLeaveNoMinus();
					
					if ( annualLeave != null ){
						
						// 年休使用
						AnnualLeaveUsedInfo usedInfo = annualLeave.getUsedNumberInfo();
						
						// 残数付与前
						AnnualLeaveUsedNumber usedNumberBeforeGrant = usedInfo.getUsedNumberBeforeGrant();
						
						if ( usedNumberBeforeGrant != null ){
							// 使用日数
							result_usedDaysBeforeGrant = usedNumberBeforeGrant.getUsedDays().getUsedDayNumber().v();
							
							// 使用時間
							if ( usedNumberBeforeGrant.getUsedTime().isPresent() ){
								result_usedTimeBeforeGrant = usedNumberBeforeGrant.getUsedTime().get().getUsedTime().v();
							}
						}
						
						// 合計
						AnnualLeaveUsedNumber usedNumberTotal = usedInfo.getUsedNumber();
						
						if ( usedNumberTotal != null ){
							// 使用日数
							result_usedDaysTotal = usedNumberTotal.getUsedDays().getUsedDayNumber().v();
							
							// 使用時間
							if ( usedNumberTotal.getUsedTime().isPresent() ){
								result_usedTimeTotal = usedNumberTotal.getUsedTime().get().getUsedTime().v();
							}
						}
						
						// 残数付与後
						if ( usedInfo.getUsedNumberAfterGrantOpt().isPresent() ){
							AnnualLeaveUsedNumber usedNumberAfterGrant = usedInfo.getUsedNumberAfterGrantOpt().get();
											
							// 使用日数
							result_usedDaysAfterGrant = usedNumberAfterGrant.getUsedDays().getUsedDayNumber().v();
							
							// 使用時間
							if ( usedNumberAfterGrant.getUsedTime().isPresent() ){
								result_usedTimeAfterGrant = usedNumberAfterGrant.getUsedTime().get().getUsedTime().v();
							}
						}
					}
				}
			}
		
	//		ss = t_result.getMapStringData().get("残数年休マイナスなし使用数付与前使用日数");
	//		ss = t_result.getMapStringData().get("残数年休マイナスなし使用数付与前使用時間");
	//		ss = t_result.getMapStringData().get("残数年休マイナスなし合計使用日数");
	//		ss = t_result.getMapStringData().get("残数年休マイナスなし合計使用時間");
	//		ss = t_result.getMapStringData().get("残数年休マイナスなし使用回数");
	//		ss = t_result.getMapStringData().get("残数年休マイナスなし付与後使用日数");
	//		ss = t_result.getMapStringData().get("残数年休マイナスなし付与後使用時間");
			
			assert_double(testCase, "残数年休マイナスなし使用数付与前使用日数", result_usedDaysBeforeGrant, t_result);
			assert_integer(testCase, "残数年休マイナスなし使用数付与前使用時間", result_usedTimeBeforeGrant, t_result);
			
			assert_double(testCase, "残数年休マイナスなし使用数合計使用日数", result_usedDaysTotal, t_result);
			assert_integer(testCase, "残数年休マイナスなし使用数合計使用時間", result_usedTimeTotal, t_result);
	
			assert_double(testCase, "残数年休マイナスなし使用数付与後使用日数", result_usedDaysAfterGrant, t_result);
			assert_integer(testCase, "残数年休マイナスなし使用数付与後使用時間", result_usedTimeAfterGrant, t_result);

		}
		
		
		// 残数年休マイナスなし残数		
		{
			// 付与前
			double result_remainingDaysBeforeGrant = 0.0;
			int result_remainingTimeBeforeGrant = 0;
			// 合計
			double result_remainingDaysTotal = 0.0;
			int result_remainingTimeTotal = 0;
			// 付与後
			double result_remainingDaysAfterGrant = 0.0;
			int result_remainingTimeAfterGrant = 0;
			
			// 年休情報
			AnnualLeaveInfo asOfPeriodEnd = aggrResultOfAnnualLeave.getAsOfPeriodEnd();
			if ( asOfPeriodEnd != null ){
				
				// 年休情報残数
				nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AnnualLeaveRemainingNumber remainingNumber 
					= asOfPeriodEnd.getRemainingNumber();
				if ( remainingNumber != null ){
					
					// 年休（マイナスなし）
					nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeave annualLeave
						= remainingNumber.getAnnualLeaveNoMinus();
					
					if ( annualLeave != null ){
						
						// 年休残数
						AnnualLeaveRemainingNumberInfo remainingInfo = annualLeave.getRemainingNumberInfo();
						
						// 残数付与前
						AnnualLeaveRemainingNumber remainingNumberBeforeGrant = remainingInfo.getRemainingNumberBeforeGrant();
						
						if ( remainingNumberBeforeGrant != null ){
							// 使用日数
							result_remainingDaysBeforeGrant = remainingNumberBeforeGrant.getTotalRemainingDays().v();
							
							// 使用時間
							if ( remainingNumberBeforeGrant.getTotalRemainingTime().isPresent() ){
								result_remainingTimeBeforeGrant = remainingNumberBeforeGrant.getTotalRemainingTime().get().v();
							}
						}
						
						// 合計
						AnnualLeaveRemainingNumber remainingNumberTotal = remainingInfo.getRemainingNumber();
						
						if ( remainingNumberTotal != null ){
							// 使用日数
							result_remainingDaysTotal = remainingNumberTotal.getTotalRemainingDays().v();
							
							// 使用時間
							if ( remainingNumberTotal.getTotalRemainingTime().isPresent() ){
								result_remainingTimeTotal = remainingNumberTotal.getTotalRemainingTime().get().v();
							}
						}
						
						// 残数付与後
						if ( remainingInfo.getRemainingNumberAfterGrantOpt().isPresent() ){
							AnnualLeaveRemainingNumber remainingNumberAfterGrant = remainingInfo.getRemainingNumberAfterGrantOpt().get();
											
							// 使用日数
							result_remainingDaysAfterGrant = remainingNumberAfterGrant.getTotalRemainingDays().v();
							
							// 使用時間
							if ( remainingNumberAfterGrant.getTotalRemainingTime().isPresent() ){
								result_remainingTimeAfterGrant = remainingNumberAfterGrant.getTotalRemainingTime().get().v();
							}
						}
					}
				}
			}

//					ss = t_result.getMapStringData().get("残数年休マイナスなし残数合計合計残日数");
//					ss = t_result.getMapStringData().get("残数年休マイナスなし残数合計明細");
//					ss = t_result.getMapStringData().get("残数年休マイナスなし残数合計合計残時間");
//					ss = t_result.getMapStringData().get("残数年休マイナスなし残数付与前合計残日数");
//					ss = t_result.getMapStringData().get("残数年休マイナスなし残数付与前明細");
//					ss = t_result.getMapStringData().get("残数年休マイナスなし残数付与前合計残時間");
//					ss = t_result.getMapStringData().get("残数年休マイナスなし残数付与後合計残日数");
//					ss = t_result.getMapStringData().get("残数年休マイナスなし残数付与後明細");
//					ss = t_result.getMapStringData().get("残数年休マイナスなし残数付与後合計残時間");

			assert_double(testCase, "残数年休マイナスなし残数合計合計残日数", result_remainingDaysBeforeGrant, t_result);
			assert_integer(testCase, "残数年休マイナスなし残数合計合計残時間", result_remainingTimeBeforeGrant, t_result);
			
			assert_double(testCase, "残数年休マイナスなし残数付与前合計残日数", result_remainingDaysTotal, t_result);
			assert_integer(testCase, "残数年休マイナスなし残数付与前合計残時間", result_remainingTimeTotal, t_result);
	
			assert_double(testCase, "残数年休マイナスなし残数付与後明細", result_remainingDaysAfterGrant, t_result);
			assert_integer(testCase, "残数年休マイナスなし残数付与後合計残時間", result_remainingTimeAfterGrant, t_result);
		}	
		
		
		// 使用数半日年休（マイナスあり）
		{
			// 付与前
			int result_usedDaysBeforeGrant = 0;
			//int result_usedTimeBeforeGrant = 0;
			// 合計
			int result_usedDaysTotal = 0;
			//int result_usedTimeTotal = 0;
			// 付与後
			int result_usedDaysAfterGrant = 0;
			//int result_usedTimeAfterGrant = 0;
			
			// 年休情報
			AnnualLeaveInfo asOfPeriodEnd = aggrResultOfAnnualLeave.getAsOfPeriodEnd();
			if ( asOfPeriodEnd != null ){
				
				// 半日年休情報残数
				nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AnnualLeaveRemainingNumber remainingNumber 
					= asOfPeriodEnd.getRemainingNumber();
				if ( remainingNumber != null ){
					
					// 半日年休（マイナスあり）
					if ( remainingNumber.getHalfDayAnnualLeaveWithMinus().isPresent()){
						nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.HalfDayAnnualLeave annualLeave
							= remainingNumber.getHalfDayAnnualLeaveWithMinus().get();
						
						if ( annualLeave != null ){
							
							// 年休使用
							HalfDayAnnLeaUsedNum usedInfo = annualLeave.getUsedNum();
							
							// 残数付与前
							UsedTimes usedNumberBeforeGrant = usedInfo.getTimesBeforeGrant();
							
							if ( usedNumberBeforeGrant != null ){
								// 使用日数
								result_usedDaysBeforeGrant = usedNumberBeforeGrant.v();
								
//								// 使用時間
//								if ( usedNumberBeforeGrant.getUsedTime().isPresent() ){
//									result_usedTimeBeforeGrant = usedNumberBeforeGrant.getUsedTime().get().getUsedTime().v();
//								}
							}
							
							// 合計
							//AnnualLeaveUsedNumber usedNumberTotal = usedInfo.getUsedNumber();
							UsedTimes usedNumberTotal = usedInfo.getTimes();
							
							if ( usedNumberTotal != null ){
								// 使用日数
								//result_usedDaysTotal = usedNumberTotal.getUsedDays().getUsedDayNumber().v();
								result_usedDaysTotal = usedNumberTotal.v();
								
//								// 使用時間
//								if ( usedNumberTotal.getUsedTime().isPresent() ){
//									result_usedTimeTotal = usedNumberTotal.getUsedTime().get().getUsedTime().v();
//								}
							}
							
							// 残数付与後
//							if ( usedInfo.getUsedNumberAfterGrantOpt().isPresent() ){
							if ( usedInfo.getTimesAfterGrant().isPresent() ){
								UsedTimes usedNumberAfterGrant = usedInfo.getTimesAfterGrant().get();
												
								// 使用日数
								result_usedDaysAfterGrant = usedNumberAfterGrant.v();
								
//								// 使用時間
//								if ( usedNumberAfterGrant.getUsedTime().isPresent() ){
//									result_usedTimeAfterGrant = usedNumberAfterGrant.getUsedTime().get().getUsedTime().v();
//								}
							}
						}
					}
				}
			}
			
//			ss = t_result.getMapStringData().get("残数半日年休（マイナスあり）使用数回数");
//			ss = t_result.getMapStringData().get("残数半日年休（マイナスあり）使用数回数付与前");
//			ss = t_result.getMapStringData().get("残数半日年休（マイナスあり）使用数回数付与後");
			
			assert_integer(testCase, "残数半日年休（マイナスあり）使用数回数", result_usedDaysBeforeGrant, t_result);
//			assert_integer(testCase, "残数年休マイナスあり使用数付与前使用時間", result_usedTimeBeforeGrant, t_result);
			
			assert_integer(testCase, "残数半日年休（マイナスあり）使用数回数付与前", result_usedDaysTotal, t_result);
//			assert_integer(testCase, "残数年休マイナスあり使用数合計使用時間", result_usedTimeTotal, t_result);
	
			assert_integer(testCase, "残数半日年休（マイナスあり）使用数回数付与後", result_usedDaysAfterGrant, t_result);
//			assert_integer(testCase, "残数年休マイナスあり使用数付与後使用時間", result_usedTimeAfterGrant, t_result);
		}
		
		
		// 残数半日年休（マイナスあり）
		{
			// 付与前
			int result_usedDaysBeforeGrant = 0;
			//int result_usedTimeBeforeGrant = 0;
			// 合計
			int result_usedDaysTotal = 0;
			//int result_usedTimeTotal = 0;
			// 付与後
			int result_usedDaysAfterGrant = 0;
			//int result_usedTimeAfterGrant = 0;
			
			// 年休情報
			AnnualLeaveInfo asOfPeriodEnd = aggrResultOfAnnualLeave.getAsOfPeriodEnd();
			if ( asOfPeriodEnd != null ){
				
				// 半日年休情報残数
				nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AnnualLeaveRemainingNumber remainingNumber 
					= asOfPeriodEnd.getRemainingNumber();
				if ( remainingNumber != null ){
					
					// 半日年休（マイナスあり）
					if ( remainingNumber.getHalfDayAnnualLeaveWithMinus().isPresent()){
						nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.HalfDayAnnualLeave annualLeave
							= remainingNumber.getHalfDayAnnualLeaveWithMinus().get();
						
						if ( annualLeave != null ){
							
							// 年休残数
							HalfDayAnnLeaRemainingNum usedInfo = annualLeave.getRemainingNum();
							
							// 残数付与前
							RemainingTimes usedNumberBeforeGrant = usedInfo.getTimesBeforeGrant();
							
							if ( usedNumberBeforeGrant != null ){
								// 使用日数
								result_usedDaysBeforeGrant = usedNumberBeforeGrant.v();
								
//										// 使用時間
//										if ( usedNumberBeforeGrant.getUsedTime().isPresent() ){
//											result_usedTimeBeforeGrant = usedNumberBeforeGrant.getUsedTime().get().getUsedTime().v();
//										}
							}
							
							// 合計
							//AnnualLeaveUsedNumber usedNumberTotal = usedInfo.getUsedNumber();
							RemainingTimes usedNumberTotal = usedInfo.getTimes();
							
							if ( usedNumberTotal != null ){
								// 使用日数
								//result_usedDaysTotal = usedNumberTotal.getUsedDays().getUsedDayNumber().v();
								result_usedDaysTotal = usedNumberTotal.v();
								
//										// 使用時間
//										if ( usedNumberTotal.getUsedTime().isPresent() ){
//											result_usedTimeTotal = usedNumberTotal.getUsedTime().get().getUsedTime().v();
//										}
							}
							
							// 残数付与後
//									if ( usedInfo.getUsedNumberAfterGrantOpt().isPresent() ){
							if ( usedInfo.getTimesAfterGrant().isPresent() ){
								RemainingTimes usedNumberAfterGrant = usedInfo.getTimesAfterGrant().get();
												
								// 使用日数
								result_usedDaysAfterGrant = usedNumberAfterGrant.v();
								
//										// 使用時間
//										if ( usedNumberAfterGrant.getUsedTime().isPresent() ){
//											result_usedTimeAfterGrant = usedNumberAfterGrant.getUsedTime().get().getUsedTime().v();
//										}
							}
						}
					}
				}
			}

//					ss = t_result.getMapStringData().get("残数半日年休（マイナスあり）残数回数");
//					ss = t_result.getMapStringData().get("残数半日年休（マイナスあり）残数回数付与前");
//					ss = t_result.getMapStringData().get("残数半日年休（マイナスあり）残数回数付与後");
			
			assert_integer(testCase, "残数半日年休（マイナスあり）使用数回数", result_usedDaysBeforeGrant, t_result);
//					assert_integer(testCase, "残数年休マイナスあり使用数付与前使用時間", result_usedTimeBeforeGrant, t_result);
			
			assert_integer(testCase, "残数半日年休（マイナスあり）使用数回数付与前", result_usedDaysTotal, t_result);
//					assert_integer(testCase, "残数年休マイナスあり使用数合計使用時間", result_usedTimeTotal, t_result);
	
			assert_integer(testCase, "残数半日年休（マイナスあり）使用数回数付与後", result_usedDaysAfterGrant, t_result);
//					assert_integer(testCase, "残数年休マイナスあり使用数付与後使用時間", result_usedTimeAfterGrant, t_result);
		}

		
		
//		ss = t_result.getMapStringData().get("残数未消化未消化日数");
//		ss = t_result.getMapStringData().get("残数未消化未消化時間");
		
//		ss = t_result.getMapStringData().get("残数半日年休（マイナスなし）使用数回数");
//		ss = t_result.getMapStringData().get("残数半日年休（マイナスなし）使用数回数付与前");
//		ss = t_result.getMapStringData().get("残数半日年休（マイナスなし）使用数回数付与後");
//		ss = t_result.getMapStringData().get("残数半日年休（マイナスなし）残数回数");
//		ss = t_result.getMapStringData().get("残数半日年休（マイナスなし）残数回数付与前");
//		ss = t_result.getMapStringData().get("残数半日年休（マイナスなし）残数回数付与後");
		
//		ss = t_result.getMapStringData().get("残数時間年休（マイナスあり）時間");
//		ss = t_result.getMapStringData().get("残数時間年休（マイナスあり）時間付与前");
//		ss = t_result.getMapStringData().get("残数時間年休（マイナスあり）時間付与後");
//		ss = t_result.getMapStringData().get("残数時間年休（マイナスなし）時間");
//		ss = t_result.getMapStringData().get("残数時間年休（マイナスなし）時間付与前");
//		ss = t_result.getMapStringData().get("残数時間年休（マイナスなし）時間付与後");
		
//		ss = t_result.getMapStringData().get("付与残数データ");
//		ss = t_result.getMapStringData().get("上限データ社員ID");
//		ss = t_result.getMapStringData().get("上限データ半日年休上限上限回数");
//		ss = t_result.getMapStringData().get("上限データ半日年休上限使用回数");
//		ss = t_result.getMapStringData().get("上限データ半日年休上限残回数");
//		ss = t_result.getMapStringData().get("上限データ時間年休上限上限時間");
//		ss = t_result.getMapStringData().get("上限データ時間年休上限使用時間");
//		ss = t_result.getMapStringData().get("上限データ時間年休上限残時間");
		
//		ss = t_result.getMapStringData().get("付与情報付与日数");
//		ss = t_result.getMapStringData().get("付与情報付与所定日数");
//		ss = t_result.getMapStringData().get("付与情報付与労働日数");
//		ss = t_result.getMapStringData().get("付与情報付与控除日数");
//		ss = t_result.getMapStringData().get("付与情報控除日数付与前");
//		ss = t_result.getMapStringData().get("付与情報控除日数付与後");
//		ss = t_result.getMapStringData().get("付与情報出勤率");
//		ss = t_result.getMapStringData().get("使用日数");
//		ss = t_result.getMapStringData().get("使用時間");

				
		
//		assertThat(result.attr2.isEqualTo(exp.attr2).as(case.asStr("no").String() + "attr2");			
//		assertThat(result.attr3.isEqualTo(exp.attr3).as(case.asStr("no").String() + "attr3");			
		
	}				


	/**
	 * Integer項目のテスト　assertThat
	 * @param testCase テストケース名
	 * @param itemName　テスト項目名
	 * @param result　結果
	 * @param testOutputAggrResultOfAnnualLeaveList　期待値
	 */
	private void assert_integer(
			String testCase,
			String itemName,
			int result,
			TestOutputAggrResultOfAnnualLeave testOutputAggrResultOfAnnualLeaveList
		){
		
		int expected = 7777;
		String ss = testOutputAggrResultOfAnnualLeaveList.getMapStringData().get(itemName);
		if (0 < ss.trim().length() ){
			expected = Integer.valueOf(ss);
		}
		assertThat(result).as(testCase + " " +  itemName).isEqualTo(expected);
	}
	
	/**
	 * Double項目のテスト　assertThat
	 * @param testCase テストケース名
	 * @param itemName　テスト項目名
	 * @param result　結果
	 * @param testOutputAggrResultOfAnnualLeaveList　期待値
	 */
	private void assert_double(
			String testCase,
			String itemName,
			double result,
			TestOutputAggrResultOfAnnualLeave testOutputAggrResultOfAnnualLeaveList
		){
		
		double expected = 0.7777;
		String ss = testOutputAggrResultOfAnnualLeaveList.getMapStringData().get(itemName);
		if (0 < ss.trim().length() ){
			expected = Double.valueOf(ss);
		}
		assertThat(result).as(testCase + " " +  itemName).isEqualTo(expected);
	}
	
}
