package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnualLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.testdata.TestDataForOverWriteList;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.testdata.TestOutputAggrResultOfAnnualLeave;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.ClosureStatusManagementRepository;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainOffMonthProcess;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.CalcNextAnnualLeaveGrantDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveRemainHistRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnLeaMaxDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualLeaveMngWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemainRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerformRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantYearHolidayRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;

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

				EmpEmployeeAdapter empEmployee;
				AnnLeaEmpBasicInfoRepository annLeaEmpBasicInfoRepo;
				YearHolidayRepository yearHolidayRepo;
				LengthServiceRepository lengthServiceRepo;
				AnnualPaidLeaveSettingRepository annualPaidLeaveSet;
				AnnLeaGrantRemDataRepository annLeaGrantRemDataRepo;
				AnnLeaMaxDataRepository annLeaMaxDataRepo;
				GetClosureStartForEmployee getClosureStartForEmployee;
				CalcNextAnnualLeaveGrantDate calcNextAnnualLeaveGrantDate;
				InterimRemainOffMonthProcess interimRemOffMonth;
				CreateInterimAnnualMngData createInterimAnnual;
				InterimRemainRepository interimRemainRepo;
				TmpAnnualHolidayMngRepository tmpAnnualLeaveMng;
				AttendanceTimeOfMonthlyRepository attendanceTimeOfMonthlyRepo;
				GetAnnLeaRemNumWithinPeriod getAnnLeaRemNumWithinPeriod;
				ClosureStatusManagementRepository closureSttMngRepo;
				CalcAnnLeaAttendanceRate calcAnnLeaAttendanceRate;
				GrantYearHolidayRepository grantYearHolidayRepo;
				OperationStartSetDailyPerformRepository operationStartSetRepo;
				AnnualLeaveRemainHistRepository annualLeaveRemainHistRepo;
				
//				// テストしたい処理を実行	
//				GetAnnLeaRemNumWithinPeriodProc proc = new GetAnnLeaRemNumWithinPeriodProc(
//						empEmployee,
//						 annLeaEmpBasicInfoRepo,
//						 yearHolidayRepo,
//						 lengthServiceRepo,
//						 annualPaidLeaveSet,
//						 annLeaGrantRemDataRepo,
//						 annLeaMaxDataRepo,
//						 getClosureStartForEmployee,
//						 calcNextAnnualLeaveGrantDate,
//						 interimRemOffMonth,
//						 createInterimAnnual,
//						 interimRemainRepo,
//						 tmpAnnualLeaveMng,
//						 attendanceTimeOfMonthlyRepo,
//						 getAnnLeaRemNumWithinPeriod,
//						 closureSttMngRepo,
//						 calcAnnLeaAttendanceRate,
//						 grantYearHolidayRepo,
//						 operationStartSetRepo,
//						 annualLeaveRemainHistRepo);
				
				/*
				 * 期間中の年休残数を取得
				 * @param companyId 会社ID
				 * @param employeeId 社員ID
				 * @param aggrPeriod 集計期間
				 * @param mode モード
				 * @param criteriaDate 基準日
				 * @param isGetNextMonthData 翌月管理データ取得フラグ
				 * @param isCalcAttendanceRate 出勤率計算フラグ
				 * @param isOverWriteOpt 上書きフラグ
				 * @param forOverWriteListOpt 上書き用の暫定年休管理データ
				 * @param prevAnnualLeaveOpt 前回の年休の集計結果
				 * @param noCheckStartDate 集計開始日を締め開始日とする　（締め開始日を確認しない）
				 * @return 年休の集計結果
				 */
				
//				// テストしたい処理を実行		
//				Optional<AggrResultOfAnnualLeave> aggrResultOfAnnualLeave
//					= proc.algorithm(companyId, employeeId, aggrPeriod, mode, criteriaDate,
//						isGetNextMonthData, isCalcAttendanceRate,
//						isOverWriteOpt, forOverWriteListOpt, prevAnnualLeaveOpt, noCheckStartDate);
				
				
				
				
				
				
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
