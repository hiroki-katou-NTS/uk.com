package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnualLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository.TestGetClosureStartForEmployee;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository.TestGetClosureStartForEmployeeFactory;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.testdata.TestData;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.testdata.TestDataForOverWriteList;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.testdata.TestOutputAggrResultOfAnnualLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualLeaveMngWork;

/**
 * 年休　テストコード
 * @author masaaki_jinno
 *
 */
public class TestAnnualLeave {

	/** 上書き用の暫定年休管理データ */
	private Map<String, List<TmpAnnualLeaveMngWork>> testDataForOverWriteListMap;
//	private Map<int, inputObject2> inputList2;
//	private Map<int, expectedValue> expectedValueList;
	
	/** 期待値 */
	private List<TestOutputAggrResultOfAnnualLeave> testOutputAggrResultOfAnnualLeaveList; 
	
	/** テストケース */
	private List<AnnualleaveTestCase> caseList;
	
	/*検証用のドメイン取得処理*/
	@Before
	public void initialize(){

		// 上書き用の暫定年休管理データ 
		this.testDataForOverWriteListMap = TestDataForOverWriteList.build();
//		TestDataForOverWriteList t = new TestDataForOverWriteList();
		
//		this.inputList = inputObject2.build();
//		this.expectedValueList = expectedVaue.build();
		
		// 期待値
		testOutputAggrResultOfAnnualLeaveList = TestOutputAggrResultOfAnnualLeave.build();
		
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
				
				// Requireクラス
				GetAnnLeaRemNumWithinPeriodRequire g_require
					= TestGetAnnLeaRemNumWithinPeriodRequireFactory.create("1");
				
				// テストしたい処理を実行	
				GetAnnLeaRemNumWithinPeriodProc proc = new GetAnnLeaRemNumWithinPeriodProc(
					g_require.getEmpEmployee(),
					g_require.getAnnLeaEmpBasicInfoRepo(),
					g_require.getYearHolidayRepo(),
					g_require.getLengthServiceRepo(),
					g_require.getAnnualPaidLeaveSet(),
					g_require.getAnnLeaGrantRemDataRepo(),
					g_require.getAnnLeaMaxDataRepo(),
					g_require.getGetClosureStartForEmployee(),
					g_require.getCalcNextAnnualLeaveGrantDate(),
					g_require.getInterimRemOffMonth(),
					g_require.getCreateInterimAnnual(),
					g_require.getInterimRemainRepo(),
					g_require.getTmpAnnualLeaveMng(),
					g_require.getAttendanceTimeOfMonthlyRepo(),
					g_require.getGetAnnLeaRemNumWithinPeriod(),
					g_require.getClosureSttMngRepo(),
					g_require.getCalcAnnLeaAttendanceRate(),
					g_require.getGrantYearHolidayRepo(),
					g_require.getOperationStartSetRepo(),
					g_require.getAnnualLeaveRemainHistRepo());
				
				String companyId = "1";
				
				// 社員
				String employeeId = annualleaveTestCase.getEmployee();
				
				// 期間
				String s_aggr_period = annualleaveTestCase.getAggrPeriod();
				DatePeriod aggrPeriod = TestData.strToDatePeriod(s_aggr_period);
				
				// 期間の開始日を「社員に対応する締め開始日」として処理を行う。
				val testGetClosureStartForEmployee = (TestGetClosureStartForEmployee)g_require.getGetClosureStartForEmployee();
				testGetClosureStartForEmployee.startDate 
					= GeneralDate.ymd(aggrPeriod.start().year(), aggrPeriod.start().month(), aggrPeriod.start().day());
				
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
				
//				// 年月
//				YearMonth yearMonth = YearMonth.of("202003"); // ooooo
				
				// 月別集計で必要な会社別設定
				//companySets 
				
//				// テストしたい処理を実行
				Optional<AggrResultOfAnnualLeave> aggrResultOfAnnualLeave
					= proc.algorithm(
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
					
	//			// 検証			
	//			assertProcedure(result, exp, case);
				
				String ss = "";
			}
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
	}

	private void assertProcedure(List<TmpAnnualLeaveMngWork> tmpAnnualLeaveMngWorkList){
		
		TmpAnnualLeaveMngWork t = tmpAnnualLeaveMngWorkList.get(0);
		
		assertThat(t.getUseDays().v().equals(Double.valueOf(1.0)), is(true));			
		
//		assertThat(result.attr2.isEqualTo(exp.attr2).as(case.asStr("no").String() + "attr2");			
//		assertThat(result.attr3.isEqualTo(exp.attr3).as(case.asStr("no").String() + "attr3");			
	}				

	
}
