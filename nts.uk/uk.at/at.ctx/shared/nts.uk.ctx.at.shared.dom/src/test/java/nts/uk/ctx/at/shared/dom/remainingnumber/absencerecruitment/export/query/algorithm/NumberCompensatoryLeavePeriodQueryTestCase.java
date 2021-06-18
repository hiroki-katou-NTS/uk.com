package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.EmploymentHistShareImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.PauseError;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.AbsRecMngInPeriodRefactParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.CompenLeaveAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.HolidayAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.DaikyuFurikyuHelper;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.OccurrenceDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnUsedDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;

@RunWith(JMockit.class)
public class NumberCompensatoryLeavePeriodQueryTestCase {

	private static String CID = "000000000000-0117";

	private static String SID = "292ae91c-508c-4c6e-8fe8-3e72277dec16";

	@Injectable
	private NumberCompensatoryLeavePeriodQuery.Require require;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

	}

	// 1 暫定振休のみ4日取得する
	// 振休取得日 2019/11/4 2019/11/5 2019/11/14 2019/11/15 (日数 = 1)
	@Test
	public void testCase1() {

		List<InterimAbsMng> useAbsMng = Arrays.asList(
				DaikyuFurikyuHelper.createAbsMng("a1", 1.0),//必要日数
				DaikyuFurikyuHelper.createAbsMng("a2", 1.0),//必要日数
				DaikyuFurikyuHelper.createAbsMng("a3", 1.0),//必要日数
				DaikyuFurikyuHelper.createAbsMng("a4", 1.0));//必要日数

		List<InterimRemain> interimMng = Arrays.asList(
				DaikyuFurikyuHelper.createRemain("a1", 
						GeneralDate.ymd(2019, 11, 4),//対象日
						CreateAtr.SCHEDULE,//作成元区分
						RemainType.PAUSE),//残数種類
				DaikyuFurikyuHelper.createRemain("a2",
						GeneralDate.ymd(2019, 11, 5),
						CreateAtr.SCHEDULE, 
						RemainType.PAUSE),
				DaikyuFurikyuHelper.createRemain("a3", 
						GeneralDate.ymd(2019, 11, 14),
						CreateAtr.SCHEDULE, 
						RemainType.PAUSE),
				DaikyuFurikyuHelper.createRemain("a4", 
						GeneralDate.ymd(2019, 11, 15),
						CreateAtr.SCHEDULE, 
						RemainType.PAUSE));

		new Expectations() {
			{

			}
		};

		AbsRecMngInPeriodRefactParamInput inputParam = DaikyuFurikyuHelper.createAbsRecInput(
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)),//集計開始日, 集計終了日 
				GeneralDate.ymd(2019, 11, 30), //画面表示日
				true, //モード 
				true, // 上書きフラグ
				useAbsMng, interimMng, new ArrayList<>());//暫定管理データ
		
		/*CompenLeaveAggrResult resultActual = NumberCompensatoryLeavePeriodQuery.process(require, inputParam);

		
		CompenLeaveAggrResult resultExpected = new CompenLeaveAggrResult(
				new VacationDetails(new ArrayList<>()),// 振出振休明細
				new ReserveLeaveRemainingDayNumber(-4.0),// 残日数
				new ReserveLeaveRemainingDayNumber(0.0),// 未消化日数
				new ReserveLeaveRemainingDayNumber(0.0),// 発生日数
				new ReserveLeaveRemainingDayNumber(4.0),// 使用日数
				new ReserveLeaveRemainingDayNumber(0.0), // 繰越日数
				Finally.of(GeneralDate.ymd(2020, 11, 1)),// 前回集計期間の翌日
				new ArrayList<>(),
				Arrays.asList(PauseError.PAUSEREMAINNUMBER));

		NumberCompensatoryLeavePeriodQueryTest.assertData(resultActual, resultExpected);
		assertThat(resultActual.getLstSeqVacation()).isEqualTo(new ArrayList<>());*/
	}

	// 2 暫定休出のみ4日取得する
	// 休出日 2019/11/2 2019/11/3 2019/11/9 2019/11/10 (日数 = 1)
	@Test
	public void testCase2() {

		List<InterimRecMng> useRecMng = Arrays.asList(
				new InterimRecMng("a1",SID,
						GeneralDate.ymd(2019, 11, 2),//対象日
						CreateAtr.SCHEDULE, //作成元区分
						RemainType.PICKINGUP, GeneralDate.max(), new OccurrenceDay(1.0), new UnUsedDay(1.0)),
				
				new InterimRecMng("a2",SID, GeneralDate.ymd(2019, 11, 3),
						CreateAtr.SCHEDULE, RemainType.PICKINGUP, GeneralDate.max(), new OccurrenceDay(1.0), new UnUsedDay(1.0)),
				new InterimRecMng("a3",SID, GeneralDate.ymd(2019, 11, 9),
						CreateAtr.SCHEDULE, RemainType.PICKINGUP, GeneralDate.max(), new OccurrenceDay(1.0), new UnUsedDay(1.0)),
				new InterimRecMng("a4",SID, GeneralDate.ymd(2019, 11, 10),
						CreateAtr.SCHEDULE, RemainType.PICKINGUP, GeneralDate.max(), new OccurrenceDay(1.0), new UnUsedDay(1.0)));

		new Expectations() {
			{
				require.findByEmployeeIdOrderByStartDate(anyString);
				result = Arrays.asList(
						new EmploymentHistShareImport(SID, "02",
								new DatePeriod(GeneralDate.ymd(2019, 05, 02), GeneralDate.ymd(2019, 11, 02))),
						new EmploymentHistShareImport(SID, "00",
								new DatePeriod(GeneralDate.ymd(2019, 11, 03), GeneralDate.ymd(9999, 12, 31))));

				require.findEmploymentHistory(CID, SID, (GeneralDate) any);
				result = Optional.of(new BsEmploymentHistoryImport(SID, "00", "A",
						new DatePeriod(GeneralDate.min(), GeneralDate.max())));

//				require.getClosureDataByEmployee(SID, (GeneralDate) any);
//				result = NumberRemainVacationLeaveRangeQueryTest.createClosure();

//				require.getFirstMonth(CID);
//				result = new CompanyDto(11);
			}
		};

		AbsRecMngInPeriodRefactParamInput inputParam = DaikyuFurikyuHelper.createAbsRecInput(
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)),//集計開始日, 集計終了日 
				GeneralDate.ymd(2019, 11, 30), //画面表示日
				true, //モード 
				true, // 上書きフラグ
				new ArrayList<>(), new ArrayList<>(), useRecMng);//暫定管理データ

		CompenLeaveAggrResult resultActual = NumberCompensatoryLeavePeriodQuery.process(require, inputParam);

		CompenLeaveAggrResult resultExpected = new CompenLeaveAggrResult(
				new VacationDetails(new ArrayList<>()),// 振出振休明細
				new ReserveLeaveRemainingDayNumber(4.0),// 残日数
				new ReserveLeaveRemainingDayNumber(0.0),// 未消化日数
				new ReserveLeaveRemainingDayNumber(4.0),// 発生日数
				new ReserveLeaveRemainingDayNumber(0.0),// 使用日数
				new ReserveLeaveRemainingDayNumber(0.0),  // 繰越日数
				Finally.of(GeneralDate.ymd(2020, 11, 1)), // 未消化日数
				new ArrayList<>(),// 前回集計期間の翌日
				Arrays.asList());// 振休エラー
		
		//NumberCompensatoryLeavePeriodQueryTest.assertData(resultActual, resultExpected);
		//assertThat(resultActual.getLstSeqVacation()).isEqualTo(new ArrayList<>());
	}

	// 3 暫定振休、休出が同じ数取得する
	// 振休取得日 2019/11/4 2019/11/5 2019/11/14 2019/11/15 (日数 = 1)
	// 休出日 2019/11/2 2019/11/3 2019/11/9 2019/11/10(日数 = 1)
	@Test
	public void testCase3() {

		List<InterimAbsMng> useAbsMng = Arrays.asList(
				DaikyuFurikyuHelper.createAbsMng("a5", 1.0),//必要日数
				DaikyuFurikyuHelper.createAbsMng("a6", 1.0),//必要日数
				DaikyuFurikyuHelper.createAbsMng("a7", 1.0),//必要日数
				DaikyuFurikyuHelper.createAbsMng("a8", 1.0));//必要日数

		List<InterimRecMng> useRecMng = Arrays.asList(
				new InterimRecMng("a1",SID,
						GeneralDate.ymd(2019, 11, 2),//対象日
						CreateAtr.SCHEDULE,//作成元区分
						RemainType.PICKINGUP, GeneralDate.max(), new OccurrenceDay(1.0), new UnUsedDay(1.0)),
				new InterimRecMng("a2",SID, GeneralDate.ymd(2019, 11, 3),
						CreateAtr.SCHEDULE, RemainType.PICKINGUP, GeneralDate.max(), new OccurrenceDay(1.0), new UnUsedDay(1.0)),
				new InterimRecMng("a3",SID, GeneralDate.ymd(2019, 11, 9),
						CreateAtr.SCHEDULE, RemainType.PICKINGUP, GeneralDate.max(), new OccurrenceDay(1.0), new UnUsedDay(1.0)),
				new InterimRecMng("a4",SID, GeneralDate.ymd(2019, 11, 10),
						CreateAtr.SCHEDULE, RemainType.PICKINGUP, GeneralDate.max(), new OccurrenceDay(1.0), new UnUsedDay(1.0)));

//		new Expectations() {
//			{
//				require.findByEmployeeIdOrderByStartDate(anyString);
//				result = Arrays.asList(
//						new EmploymentHistShareImport(SID, "02",
//								new DatePeriod(GeneralDate.ymd(2019, 05, 02), GeneralDate.ymd(2019, 11, 02))),
//						new EmploymentHistShareImport(SID, "00",
//								new DatePeriod(GeneralDate.ymd(2019, 11, 03), GeneralDate.ymd(9999, 12, 31))));
//
//				require.findEmploymentHistory(CID, SID, (GeneralDate) any);
//				result = Optional.of(new BsEmploymentHistoryImport(SID, "00", "A",
//						new DatePeriod(GeneralDate.min(), GeneralDate.max())));
//
////				require.getClosureDataByEmployee(SID, (GeneralDate) any);
////				result = NumberRemainVacationLeaveRangeQueryTest.createClosure();
//
////				require.getFirstMonth(CID);
////				result = new CompanyDto(11);
//			}
//		};
//
//		AbsRecMngInPeriodRefactParamInput inputParam = DaikyuFurikyuHelper.createAbsRecInput(
//				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)),//集計開始日, 集計終了日 
//				GeneralDate.ymd(2019, 11, 30), //画面表示日
//				true, //モード 
//				true, // 上書きフラグ
//				useAbsMng,new ArrayList<>() , useRecMng);//暫定管理データ

//		CompenLeaveAggrResult resultActual = NumberCompensatoryLeavePeriodQuery.process(require, inputParam);
//
//	
//		CompenLeaveAggrResult resultExpected = new CompenLeaveAggrResult
//				(new VacationDetails(new ArrayList<>()),// 振出振休明細
//				new ReserveLeaveRemainingDayNumber(0.0), // 残日数
//				new ReserveLeaveRemainingDayNumber(0.0),// 未消化日数
//				new ReserveLeaveRemainingDayNumber(4.0), // 発生日数
//				new ReserveLeaveRemainingDayNumber(4.0),// 使用日数
//				new ReserveLeaveRemainingDayNumber(0.0), // 繰越日数
//				Finally.of(GeneralDate.ymd(2020, 11, 1)), // 前回集計期間の翌日
//				new ArrayList<>(),// 逐次休暇の紐付け情報
//				Arrays.asList());// 振休エラー
//
//		NumberCompensatoryLeavePeriodQueryTest.assertData(resultActual, resultExpected);
//		assertThat(resultActual.getLstSeqVacation()).isEqualTo(new ArrayList<>());
	}

	// 4 暫定振休、休出を取得 但し振休が半日
	// 振休取得日 2019/11/4 2019/11/5(日数 = 0.5)
	// 休出日 2019/11/2 2019/11/3(日数 = 1)
	@Test
	public void testCase4() {

		List<InterimAbsMng> useAbsMng = Arrays.asList(
				DaikyuFurikyuHelper.createAbsMng("a5", 0.5),//必要日数
				DaikyuFurikyuHelper.createAbsMng("a6", 0.5));//必要日数

		List<InterimRecMng> useRecMng = Arrays.asList(
				new InterimRecMng("a1", SID,
						GeneralDate.ymd(2019, 11, 2),//対象日
						CreateAtr.SCHEDULE, //作成元区分
						RemainType.PICKINGUP, GeneralDate.max(), new OccurrenceDay(1.0), new UnUsedDay(1.0)),
				new InterimRecMng("a2",SID, GeneralDate.ymd(2019, 11, 3),
						CreateAtr.SCHEDULE, RemainType.PICKINGUP, GeneralDate.max(), new OccurrenceDay(1.0), new UnUsedDay(1.0)));

		
//		new Expectations() {
//			{
//				require.findByEmployeeIdOrderByStartDate(anyString);
//				result = Arrays.asList(
//						new EmploymentHistShareImport(SID, "02",
//								new DatePeriod(GeneralDate.ymd(2019, 05, 02), GeneralDate.ymd(2019, 11, 02))),
//						new EmploymentHistShareImport(SID, "00",
//								new DatePeriod(GeneralDate.ymd(2019, 11, 03), GeneralDate.ymd(9999, 12, 31))));
//
//				require.findEmploymentHistory(CID, SID, (GeneralDate) any);
//				result = Optional.of(new BsEmploymentHistoryImport(SID, "00", "A",
//						new DatePeriod(GeneralDate.min(), GeneralDate.max())));
//
////				require.getClosureDataByEmployee(SID, (GeneralDate) any);
////				result = NumberRemainVacationLeaveRangeQueryTest.createClosure();
//
////				require.getFirstMonth(CID);
////				result = new CompanyDto(11);
//			}
//		};
//
//		AbsRecMngInPeriodRefactParamInput inputParam = DaikyuFurikyuHelper.createAbsRecInput(
//				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)),//集計開始日, 集計終了日 
//				GeneralDate.ymd(2019, 11, 30), //画面表示日
//				true, //モード 
//				true, // 上書きフラグ
//				useAbsMng, new ArrayList<>(), useRecMng);//暫定管理データ

//		CompenLeaveAggrResult resultActual = NumberCompensatoryLeavePeriodQuery.process(require, inputParam);
//
//		CompenLeaveAggrResult resultExpected = new CompenLeaveAggrResult(
//				new VacationDetails(new ArrayList<>()),// 振出振休明細
//				new ReserveLeaveRemainingDayNumber(1.0), // 残日数
//				new ReserveLeaveRemainingDayNumber(0.0),// 未消化日数
//				new ReserveLeaveRemainingDayNumber(2.0), // 発生日数
//				new ReserveLeaveRemainingDayNumber(1.0),// 使用日数
//				new ReserveLeaveRemainingDayNumber(0.0), // 繰越日数
//				Finally.of(GeneralDate.ymd(2020, 11, 1)), //前回集計期間の翌日
//				new ArrayList<>(),//逐次休暇の紐付け情報
//				Arrays.asList());//振休エラー
//
//		NumberCompensatoryLeavePeriodQueryTest.assertData(resultActual, resultExpected);
//		assertThat(resultActual.getLstSeqVacation()).isEqualTo(new ArrayList<>());
	}

	// 5 暫定振休、休出を取得 但し休出が半日
	// 振休取得日 2019/11/4 2019/11/5(日数 = 1)
	// 休出日 2019/11/2 2019/11/3(日数 = 0.5)

	@Test
	public void testCase5() {
		List<InterimAbsMng> useAbsMng = Arrays.asList(
				DaikyuFurikyuHelper.createAbsMng("a5", 1.0),//必要日数
				DaikyuFurikyuHelper.createAbsMng("a6", 1.0));//必要日数

		List<InterimRecMng> useRecMng = Arrays.asList(
				new InterimRecMng("a1",SID,
						GeneralDate.ymd(2019, 11, 2),//対象日
						CreateAtr.SCHEDULE, //作成元区分
						RemainType.PICKINGUP, GeneralDate.max(), new OccurrenceDay(0.5), new UnUsedDay(0.5)),
				new InterimRecMng("a2",SID, GeneralDate.ymd(2019, 11, 3),
						CreateAtr.SCHEDULE, RemainType.PICKINGUP, GeneralDate.max(), new OccurrenceDay(0.5), new UnUsedDay(0.5)));

//		new Expectations() {
//			{
//				require.findByEmployeeIdOrderByStartDate(anyString);
//				result = Arrays.asList(
//						new EmploymentHistShareImport(SID, "02",
//								new DatePeriod(GeneralDate.ymd(2019, 05, 02), GeneralDate.ymd(2019, 11, 02))),
//						new EmploymentHistShareImport(SID, "00",
//								new DatePeriod(GeneralDate.ymd(2019, 11, 03), GeneralDate.ymd(9999, 12, 31))));
//
//				require.findEmploymentHistory(CID, SID, (GeneralDate) any);
//				result = Optional.of(new BsEmploymentHistoryImport(SID, "00", "A",
//						new DatePeriod(GeneralDate.min(), GeneralDate.max())));
//
////				require.getClosureDataByEmployee(SID, (GeneralDate) any);
////				result = NumberRemainVacationLeaveRangeQueryTest.createClosure();
//
////				require.getFirstMonth(CID);
////				result = new CompanyDto(11);
//			}
//		};
//
//		AbsRecMngInPeriodRefactParamInput inputParam = DaikyuFurikyuHelper.createAbsRecInput(
//				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)),//集計開始日, 集計終了日 
//				GeneralDate.ymd(2019, 11, 30), //画面表示日
//				true, //モード 
//				true, // 上書きフラグ
//				useAbsMng, new ArrayList<>(), useRecMng);//暫定管理データ

//		CompenLeaveAggrResult resultActual = NumberCompensatoryLeavePeriodQuery.process(require, inputParam);
//
//		CompenLeaveAggrResult resultExpected = new CompenLeaveAggrResult(
//				new VacationDetails(new ArrayList<>()),// 振出振休明細
//				new ReserveLeaveRemainingDayNumber(-1.0), // 残日数
//				new ReserveLeaveRemainingDayNumber(0.0),// 未消化日数
//				new ReserveLeaveRemainingDayNumber(1.0), // 発生日数
//				new ReserveLeaveRemainingDayNumber(2.0),// 使用日数
//				new ReserveLeaveRemainingDayNumber(0.0), // 繰越日数
//				Finally.of(GeneralDate.ymd(2020, 11, 1)),// 前回集計期間の翌日 
//				new ArrayList<>(),// 逐次休暇の紐付け情報
//				Arrays.asList(PauseError.PAUSEREMAINNUMBER));// 振休エラー
//		
//		NumberCompensatoryLeavePeriodQueryTest.assertData(resultActual, resultExpected);
//		assertThat(resultActual.getLstSeqVacation()).isEqualTo(new ArrayList<>());
	}

	// 6 暫定半日振休取得、確定休出1日残っているが2019/11/14に期限切れになる
	// 振休取得日 2019/11/14 2019/11/15(日数 = 0.5)
	// 確定休出 2019/8/14 (日数 = 1, 期限日 = 2019/11/14 )
//	@Test
//	public void testCase6() {
//		List<InterimAbsMng> useAbsMng = Arrays.asList(
//				new InterimAbsMng("bdda6a46-2cbe-48c8-85f8-c04ca554e132", new RequiredDay(0.5), new UnOffsetDay(0.5)),
//				new InterimAbsMng("bdda6a46-2cbe-48c8-85f8-c04ca554e133", new RequiredDay(0.5), new UnOffsetDay(0.5)));
//
////		List<InterimRecMng> useRecMng = Arrays.asList(new InterimRecMng("adda6a46-2cbe-48c8-85f8-c04ca554e132",
////				GeneralDate.ymd(2019, 11, 14), new OccurrenceDay(1.0), StatutoryAtr.PUBLIC, new UnUsedDay(1.0)));
//
//		List<InterimRemain> interimMng = Arrays.asList(
////				new InterimRemain("adda6a46-2cbe-48c8-85f8-c04ca554e132", SID, GeneralDate.ymd(2019, 8, 14),
////						CreateAtr.SCHEDULE, RemainType.PICKINGUP, RemainAtr.SINGLE),
//
//				new InterimRemain("bdda6a46-2cbe-48c8-85f8-c04ca554e132", SID, GeneralDate.ymd(2019, 11, 14),
//						CreateAtr.SCHEDULE, RemainType.PAUSE, RemainAtr.SINGLE),
//				new InterimRemain("bdda6a46-2cbe-48c8-85f8-c04ca554e133", SID, GeneralDate.ymd(2019, 11, 15),
//						CreateAtr.SCHEDULE, RemainType.PAUSE, RemainAtr.SINGLE));
//
//		CompenLeaveAggrResult compenLeaveAggrResult = new CompenLeaveAggrResult(
//				new VacationDetails(Collections.emptyList()), new ReserveLeaveRemainingDayNumber(0.0),
//				new ReserveLeaveRemainingDayNumber(0.0), new ReserveLeaveRemainingDayNumber(0.0),
//				new ReserveLeaveRemainingDayNumber(0.0), new ReserveLeaveRemainingDayNumber(0.0),
//				Finally.of(GeneralDate.ymd(2019, 12, 21)), Collections.emptyList(), Collections.emptyList());
//
//		new Expectations() {
//			{
//				
//				//List<PayoutManagementData> 
//				require.getByUnUseState(CID, SID, (GeneralDate) any, 0, DigestionAtr.UNUSED);
//				result = Arrays.asList(new PayoutManagementData("adda6a46-2cbe-48c8-85f8-c04ca554e132", CID, SID, false,
//						GeneralDate.ymd(2019, 8, 14), GeneralDate.ymd(2019, 11, 14), HolidayAtr.PUBLIC_HOLIDAY.value, 1.0, 1.0, 0));
//				
//				require.findByEmployeeIdOrderByStartDate(anyString);
//				result = Arrays.asList(new EmploymentHistShareImport(SID, "00",
//						new DatePeriod(GeneralDate.ymd(2000, 11, 03), GeneralDate.ymd(9999, 12, 31))));
//
//				require.findEmploymentHistory(CID, SID, (GeneralDate) any);
//				result = Optional.of(new BsEmploymentHistoryImport(SID, "00", "A",
//						new DatePeriod(GeneralDate.min(), GeneralDate.max())));
//
//				require.findEmpById(anyString, anyString);
//				result = Optional.of(new EmpSubstVacation(CID, "00", ManageDistinct.YES));
//			}
//		};
//
//		AbsRecMngInPeriodRefactParamInput inputParam = new AbsRecMngInPeriodRefactParamInput(CID, SID,
//				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)),
//				GeneralDate.ymd(2019, 11, 30), false, true, useAbsMng, interimMng, new ArrayList<>(),
//				Optional.of(compenLeaveAggrResult), Optional.empty(), Optional.empty(),
//				new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));
//
//		CompenLeaveAggrResult resultActual = NumberCompensatoryLeavePeriodQuery.process(require, inputParam);
//
//		CompenLeaveAggrResult resultExpected = new CompenLeaveAggrResult(new VacationDetails(new ArrayList<>()),
//				new ReserveLeaveRemainingDayNumber(-0.5), new ReserveLeaveRemainingDayNumber(0.5),
//				new ReserveLeaveRemainingDayNumber(0.0), new ReserveLeaveRemainingDayNumber(1.0),
//				new ReserveLeaveRemainingDayNumber(1.0), Finally.of(GeneralDate.ymd(2020, 11, 1)), new ArrayList<>(),
//				Arrays.asList(PauseError.PAUSEREMAINNUMBER));
//
//		NumberCompensatoryLeavePeriodQueryTest.assertData(resultActual, resultExpected);
//		assertThat(resultActual.getLstSeqVacation())
//				.extracting(x -> x.getOutbreakDay(), x -> x.getDateOfUse(), x -> x.getDayNumberUsed(),
//						x -> x.getTargetSelectionAtr())
//				.containsExactly(
//						Tuple.tuple(GeneralDate.ymd(2019, 8, 14), GeneralDate.ymd(2019, 11, 14),
//								new ReserveLeaveRemainingDayNumber(0.5), TargetSelectionAtr.AUTOMATIC));
//	}
//
//	// 7暫定1日休出し、暫定1日振休取得 確定休出0.5日（0.5日紐づけ済み）が残っている
//	// 振休取得日 2019/11/15
//	// 休出日 2019/11/10
//	// 確定休出 2019/10/14 (日数 = 0.5, 期限日 = 2020/1/14 )
//
//	@Test
//	public void testCase7() {
//		List<InterimAbsMng> useAbsMng = Arrays.asList(
//				new InterimAbsMng("bdda6a46-2cbe-48c8-85f8-c04ca554e133", new RequiredDay(1.0), new UnOffsetDay(1.0)));
//
//		List<InterimRecMng> useRecMng = Arrays.asList(
//				new InterimRecMng("adda6a46-2cbe-48c8-85f8-c04ca554e132", GeneralDate.max(), new OccurrenceDay(1.0),
//						StatutoryAtr.PUBLIC, new UnUsedDay(1.0))
////				new InterimRecMng("adda6a46-2cbe-48c8-85f8-c04ca554e134", GeneralDate.ymd(2020, 1, 14),
////						new OccurrenceDay(0.5), StatutoryAtr.PUBLIC, new UnUsedDay(0.5))
//				);
//
//		List<InterimRemain> interimMng = Arrays.asList(
//				new InterimRemain("adda6a46-2cbe-48c8-85f8-c04ca554e132", SID, GeneralDate.ymd(2019, 11, 10),
//						CreateAtr.SCHEDULE, RemainType.PICKINGUP, RemainAtr.SINGLE),
////				new InterimRemain("adda6a46-2cbe-48c8-85f8-c04ca554e134", SID, GeneralDate.ymd(2019, 10, 14),
////						CreateAtr.SCHEDULE, RemainType.PICKINGUP, RemainAtr.SINGLE),
//
//				new InterimRemain("bdda6a46-2cbe-48c8-85f8-c04ca554e133", SID, GeneralDate.ymd(2019, 11, 15),
//						CreateAtr.SCHEDULE, RemainType.PAUSE, RemainAtr.SINGLE));
//
//		CompenLeaveAggrResult compenLeaveAggrResult = new CompenLeaveAggrResult(
//				new VacationDetails(Collections.emptyList()), new ReserveLeaveRemainingDayNumber(0.0),
//				new ReserveLeaveRemainingDayNumber(0.0), new ReserveLeaveRemainingDayNumber(0.0),
//				new ReserveLeaveRemainingDayNumber(0.0), new ReserveLeaveRemainingDayNumber(0.0),
//				Finally.of(GeneralDate.ymd(2019, 12, 21)), Collections.emptyList(), Collections.emptyList());
//
//		new Expectations() {
//			{
//				
//				require.getByUnUseState(CID, SID, (GeneralDate) any, 0, DigestionAtr.UNUSED);
//				result = Arrays.asList(new PayoutManagementData("adda6a46-2cbe-48c8-85f8-c04ca554e134", CID, SID, false,
//						GeneralDate.ymd(2019, 10, 14), GeneralDate.ymd(2020, 1, 14), HolidayAtr.PUBLIC_HOLIDAY.value, 0.5, 0.5, 0));
//				
//				require.findByEmployeeIdOrderByStartDate(anyString);
//				result = Arrays.asList(new EmploymentHistShareImport(SID, "00",
//						new DatePeriod(GeneralDate.ymd(2000, 11, 03), GeneralDate.ymd(9999, 12, 31))));
//
//				require.findEmploymentHistory(CID, SID, (GeneralDate) any);
//				result = Optional.of(new BsEmploymentHistoryImport(SID, "00", "A",
//						new DatePeriod(GeneralDate.min(), GeneralDate.max())));
//
//				require.findEmpById(anyString, anyString);
//				result = Optional.of(new EmpSubstVacation(CID, "00", ManageDistinct.YES
//						));
//			}
//		};
//
//		AbsRecMngInPeriodRefactParamInput inputParam = new AbsRecMngInPeriodRefactParamInput(CID, SID,
//				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)),
//				GeneralDate.ymd(2019, 11, 30), false, true, useAbsMng, interimMng, useRecMng,
//				Optional.of(compenLeaveAggrResult), Optional.empty(), Optional.empty(),
//				new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));
//
//		CompenLeaveAggrResult resultActual = NumberCompensatoryLeavePeriodQuery.process(require, inputParam);
//
//		CompenLeaveAggrResult resultExpected = new CompenLeaveAggrResult(new VacationDetails(new ArrayList<>()),
//				new ReserveLeaveRemainingDayNumber(0.5), new ReserveLeaveRemainingDayNumber(0.0),
//				new ReserveLeaveRemainingDayNumber(1.0), new ReserveLeaveRemainingDayNumber(1.0),
//				new ReserveLeaveRemainingDayNumber(0.5), Finally.of(GeneralDate.ymd(2020, 11, 1)), new ArrayList<>(),
//				Arrays.asList());
//
//		NumberCompensatoryLeavePeriodQueryTest.assertData(resultActual, resultExpected);
//		assertThat(resultActual.getLstSeqVacation())
//				.extracting(x -> x.getOutbreakDay(), x -> x.getDateOfUse(), x -> x.getDayNumberUsed(),
//						x -> x.getTargetSelectionAtr())
//				.containsExactly(
//						Tuple.tuple(GeneralDate.ymd(2019, 10, 14), GeneralDate.ymd(2019, 11, 15),
//								new ReserveLeaveRemainingDayNumber(1.0), TargetSelectionAtr.AUTOMATIC),
//						Tuple.tuple(GeneralDate.ymd(2019, 11, 10), GeneralDate.ymd(2019, 11, 15),
//								new ReserveLeaveRemainingDayNumber(0.5), TargetSelectionAtr.AUTOMATIC));
//	}
}
