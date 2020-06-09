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
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnualLeave;
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
	private Map<Integer, List<TmpAnnualLeaveMngWork>> testDataForOverWriteListMap;
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
				
				val testDataForOverWriteList // 上書き用の暫定年休管理データ
					= testDataForOverWriteListMap.get(caseNo);
				
				// Requireクラス
				GetAnnLeaRemNumWithinPeriodRequire t
					= TestGetAnnLeaRemNumWithinPeriodRequireFactory.create("1");
				
				// テストしたい処理を実行	
				GetAnnLeaRemNumWithinPeriodProc proc = new GetAnnLeaRemNumWithinPeriodProc(
					t.getEmpEmployee(),
					t.getAnnLeaEmpBasicInfoRepo(),
					t.getYearHolidayRepo(),
					t.getLengthServiceRepo(),
					t.getAnnualPaidLeaveSet(),
					t.getAnnLeaGrantRemDataRepo(),
					t.getAnnLeaMaxDataRepo(),
					t.getGetClosureStartForEmployee(),
					t.getCalcNextAnnualLeaveGrantDate(),
					t.getInterimRemOffMonth(),
					t.getCreateInterimAnnual(),
					t.getInterimRemainRepo(),
					t.getTmpAnnualLeaveMng(),
					t.getAttendanceTimeOfMonthlyRepo(),
					t.getGetAnnLeaRemNumWithinPeriod(),
					t.getClosureSttMngRepo(),
					t.getCalcAnnLeaAttendanceRate(),
					t.getGrantYearHolidayRepo(),
					t.getOperationStartSetRepo(),
					t.getAnnualLeaveRemainHistRepo());
				
				String companyId = "1";
				
				// 社員
				String employeeId = annualleaveTestCase.getEmployee();
				
				// 期間
				String s_aggr_period = annualleaveTestCase.getAggrPeriod();
				DatePeriod aggrPeriod = TestData.strToDatePeriod(s_aggr_period);
				
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
				
				boolean isGetNextMonthData = true;
				
				boolean noCheckStartDate = true;
				
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
						Optional.empty(),
						Optional.empty(),
						noCheckStartDate,
						Optional.empty(),
						Optional.empty(),
						Optional.empty(),
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
