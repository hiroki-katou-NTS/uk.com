package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.assertj.core.groups.Tuple;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.DayOffError;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.BreakDayOffRemainMngRefactParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SubstituteHolidayAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryAcquisitionUse;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryAcquisitionUseGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryDigestiveTimeUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryDigestiveTimeUnitGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSettingGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.DeadlCheckMonth;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureGetMemento;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistoryGetMemento;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureName;
import nts.uk.ctx.at.shared.dom.workrule.closure.CompanyId;
import nts.uk.ctx.at.shared.dom.workrule.closure.CurrentMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@RunWith(JMockit.class)
public class NumberRemainVacationLeaveRangeQueryTest {

	@Injectable
	private NumberRemainVacationLeaveRangeQuery.Require require;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	/**
	 * テストしたい内容
	 * 
	 * 前回代休の集計結果がない
	 * 
	 * 準備するデータ
	 * 
	 * 暫定残数管理データがある
	 * 
	 * モードがその他の月次モード
	 * 
	 * →代休の集計結果 (残日数, 使用日数)
	 */
	@Test
	public void testOptBeforeResultNoPresent() {
		List<InterimDayOffMng> dayOffMng = new ArrayList<InterimDayOffMng>();
		List<InterimBreakMng> breakMng = new ArrayList<>();
		List<InterimRemain> interimMng = new ArrayList<>();
		
		// 代休の集計結果
		Optional<SubstituteHolidayAggrResult> holidayAggrResult = Optional
				.of(DaikyuFurikyuHelper.createDefaultResult(new ArrayList<>(), 12.0, // 休出代休明細
						GeneralDate.ymd(2019, 10, 01)));// 前回集計期間の翌日

		BreakDayOffRemainMngRefactParam inputParam = DaikyuFurikyuHelper.inputParamDaikyu(
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)), false,
				GeneralDate.ymd(2019, 11, 30), false, interimMng, null, null, breakMng, dayOffMng,
				holidayAggrResult, new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));

		new Expectations() {
			{
				require.getBySidYmd(anyString, anyString, (GeneralDate) any);
				result = new ArrayList<>();

				require.getBySidYmd(anyString, anyString, (GeneralDate) any, (DigestionAtr) any);
				result = new ArrayList<>();

				//暫定残数管理データ
				require.getRemainBySidPriod(anyString, (DatePeriod) any, (RemainType) any);
				result = Arrays.asList(
						new InterimRemain("daikyu1", "", GeneralDate.ymd(2019, 11, 4), CreateAtr.RECORD,
								RemainType.SUBHOLIDAY, RemainAtr.SINGLE),
						new InterimRemain("daikyu2", "", GeneralDate.ymd(2019, 11, 5), CreateAtr.RECORD,
								RemainType.SUBHOLIDAY, RemainAtr.SINGLE),
						new InterimRemain("daikyu3", "", GeneralDate.ymd(2019, 11, 8), CreateAtr.RECORD,
								RemainType.SUBHOLIDAY, RemainAtr.SINGLE),
						new InterimRemain("daikyu4", "", GeneralDate.ymd(2019, 11, 9), CreateAtr.RECORD,
								RemainType.SUBHOLIDAY, RemainAtr.SINGLE));

				// 暫定代休管理データ
				require.getDayOffBySidPeriod(anyString, (DatePeriod) any);
				result = Arrays.asList(
						new InterimDayOffMng("daikyu1", new RequiredTime(0), new RequiredDay(1.0), new UnOffsetTime(0),
								new UnOffsetDay(1.0)),
						new InterimDayOffMng("daikyu2", new RequiredTime(0), new RequiredDay(1.0), new UnOffsetTime(0),
								new UnOffsetDay(1.0)),
						new InterimDayOffMng("daikyu3", new RequiredTime(0), new RequiredDay(1.0), new UnOffsetTime(0),
								new UnOffsetDay(1.0)),
						new InterimDayOffMng("daikyu4", new RequiredTime(0), new RequiredDay(1.0), new UnOffsetTime(0),
								new UnOffsetDay(1.0)));

//				require.getBycomDayOffID(anyString, GeneralDate.ymd(2019, 11, 10));
//				result = new ArrayList<>();

			}

		};
		SubstituteHolidayAggrResult resultActual = NumberRemainVacationLeaveRangeQuery
				.getBreakDayOffMngInPeriod(require, inputParam);

		SubstituteHolidayAggrResult resultExpected = DaikyuFurikyuHelper.createBeforeResult(new ArrayList<>(), // 休出代休明細
				-4.0, 0, // 残日時間
				4.0, 0, // 使用
				0.0, 0, // 発生
				0d, 0, // 繰越
				0d, 0, // 未消化
				Arrays.asList(DayOffError.DAYERROR), // エラー情報
				GeneralDate.ymd(2020, 11, 01));// 前回集計期間の翌日

		assertData(resultActual, resultExpected);
		assertThat(resultActual.getCarryoverDay().v()).isEqualTo(0.0);
		assertThat(resultActual.getVacationDetails().getLstAcctAbsenDetail()).extracting(x -> x.getManageId(), // 残数管理データID
				x -> x.getDateOccur().getDayoffDate(), // 年月日
				x -> x.getNumberOccurren().getDay().v(), x -> x.getNumberOccurren().getTime(), // 発生
				x -> x.getOccurrentClass(), // 発生-消化
				x -> x.getUnbalanceNumber().getDay().v(), x -> x.getUnbalanceNumber().getTime())// 未相殺
				.containsExactly(
						Tuple.tuple("daikyu1", Optional.of(GeneralDate.ymd(2019, 11, 4)), 1.0,
								Optional.of(new AttendanceTime(0)), OccurrenceDigClass.DIGESTION, 1.0,
								Optional.of(new AttendanceTime(0))),
						Tuple.tuple("daikyu2", Optional.of(GeneralDate.ymd(2019, 11, 5)), 1.0,
								Optional.of(new AttendanceTime(0)), OccurrenceDigClass.DIGESTION, 1.0,
								Optional.of(new AttendanceTime(0))),
						Tuple.tuple("daikyu3", Optional.of(GeneralDate.ymd(2019, 11, 8)), 1.0,
								Optional.of(new AttendanceTime(0)), OccurrenceDigClass.DIGESTION, 1.0,
								Optional.of(new AttendanceTime(0))),
						Tuple.tuple("daikyu4", Optional.of(GeneralDate.ymd(2019, 11, 9)), 1.0,
								Optional.of(new AttendanceTime(0)), OccurrenceDigClass.DIGESTION, 1.0,
								Optional.of(new AttendanceTime(0))));

	}

	/**
	 * テストしたい内容
	 * 
	 * 前回集計結果のデータを使用して処理おこなう
	 * 
	 * 準備するデータ
	 * 
	 * 「 前回代休の集計結果.逐次発生の休暇明細」がある
	 * 
	 * モードがその他の月次モード
	 * 
	 * →代休の集計結果 (残日数, 使用日数...)
	 */
	@Test
	public void testOptBeforePresent() {

		List<InterimBreakMng> breakMng = new ArrayList<>();
		List<InterimDayOffMng> dayOffMng = new ArrayList<>();

		List<InterimRemain> interimMng = new ArrayList<>();

		List<AccumulationAbsenceDetail> lstAccDetail = Arrays.asList(DaikyuFurikyuHelper.createDetailDefault(true, // 代休
				OccurrenceDigClass.OCCURRENCE, // 発生
				Optional.of(GeneralDate.ymd(2019, 11, 4)), // 年月日
				"kyuushutsu1", // 残数管理データID
				1.0, 480, // 発生
				1.0, 0// 未相殺
		), DaikyuFurikyuHelper.createDetailDefault(true, // 代休
				OccurrenceDigClass.DIGESTION, // 消化
				Optional.of(GeneralDate.ymd(2019, 11, 5)), // 年月日
				"daikyu1", // 残数管理データID
				1.0, 480, // 発生
				0.5, 0// 未相殺
		));

		// 代休の集計結果
		Optional<SubstituteHolidayAggrResult> holidayAggrResult = Optional
				.of(DaikyuFurikyuHelper.createDefaultResult(lstAccDetail, // 休出代休明細
						12.0,//繰越日数
						GeneralDate.ymd(2019, 11, 01)));// 前回集計期間の翌日

		BreakDayOffRemainMngRefactParam inputParam = DaikyuFurikyuHelper.inputParamDaikyu(
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2019, 11, 30)), // 集計開始日, 集計終了日
				false,//モード : True: 月次か, False: その他か
				GeneralDate.ymd(2019, 11, 30), //画面表示日 
				false, ///** 上書きフラグ: True: 上書き, False: 未上書き */
				interimMng, //暫定残数管理データ
				breakMng, //暫定休出管理データ
				dayOffMng, //暫定代休管理データ
				holidayAggrResult,// 代休の集計結果
				new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));//追加用確定管理データ

		SubstituteHolidayAggrResult resultActual = NumberRemainVacationLeaveRangeQuery
				.getBreakDayOffMngInPeriod(require, inputParam);

		SubstituteHolidayAggrResult resultExpected = DaikyuFurikyuHelper.createBeforeResult(new ArrayList<>(), // 休出代休明細
				0.5, 0, // 残日時間
				1.0, 480, // 使用
				1.0, 480, // 発生
				12.0, 0, // 繰越
				0d, 0, // 未消化
				Collections.emptyList(), // エラー情報
				GeneralDate.ymd(2019, 12, 01));// 前回集計期間の翌日

		assertData(resultActual, resultExpected);
		assertThat(resultActual.getCarryoverDay().v()).isEqualTo(12.0);
	}

	public static void assertData(SubstituteHolidayAggrResult resultActual,
			SubstituteHolidayAggrResult resultExpected) {

		// 残日数
		assertThat(resultActual.getRemainDay().v()).isEqualTo(resultExpected.getRemainDay().v());
		// 残時間
		assertThat(resultActual.getRemainTime().v()).isEqualTo(resultExpected.getRemainTime().v());
		// 使用日数
		assertThat(resultActual.getDayUse().v()).isEqualTo(resultExpected.getDayUse().v());
		// 使用時間
		assertThat(resultActual.getTimeUse().v()).isEqualTo(resultExpected.getTimeUse().v());
		// 発生日数
		assertThat(resultActual.getOccurrenceDay().v()).isEqualTo(resultExpected.getOccurrenceDay().v());
		// 発生時間
		assertThat(resultActual.getOccurrenceTime().v()).isEqualTo(resultExpected.getOccurrenceTime().v());
		// 繰越日数
		assertThat(resultActual.getCarryoverDay().v()).isEqualTo(resultExpected.getCarryoverDay().v());
		// 繰越時間
		assertThat(resultActual.getCarryoverTime().v()).isEqualTo(resultExpected.getCarryoverTime().v());
		// 未消化日数
		assertThat(resultActual.getUnusedDay().v()).isEqualTo(resultExpected.getUnusedDay().v());
		// 未消化時間
		assertThat(resultActual.getUnusedTime().v()).isEqualTo(resultExpected.getUnusedTime().v());

		// エラー情報
		assertThat(resultActual.getDayOffErrors()).isEqualTo(resultExpected.getDayOffErrors());

		// 前回集計期間の翌日
		assertThat(resultActual.getNextDay().get()).isEqualTo(resultExpected.getNextDay().get());
		// assertThat(resultActual.getLstSeqVacation()).isEqualTo(resultExpected.getLstSeqVacation());

	}

	public static Closure createClosure() {
		return new Closure(new ClosureGetMemento() {

			@Override
			public UseClassification getUseClassification() {
				return UseClassification.UseClass_Use;
			}

			@Override
			public CompanyId getCompanyId() {
				return new CompanyId(DaikyuFurikyuHelper.CID);
			}

			@Override
			public CurrentMonth getClosureMonth() {
				return new CurrentMonth(11);
			}

			@Override
			public ClosureId getClosureId() {
				return ClosureId.RegularEmployee;
			}

			@Override
			public List<ClosureHistory> getClosureHistories() {
				return Arrays.asList(new ClosureHistory(new ClosureHistoryGetMemento() {

					@Override
					public YearMonth getStartDate() {
						return YearMonth.of(1900, 1);
					}

					@Override
					public YearMonth getEndDate() {
						return YearMonth.of(9999, 12);
					}

					@Override
					public CompanyId getCompanyId() {
						return new CompanyId(DaikyuFurikyuHelper.CID);
					}

					@Override
					public ClosureName getClosureName() {
						return new ClosureName("AA");
					}

					@Override
					public ClosureId getClosureId() {
						return ClosureId.RegularEmployee;
					}

					@Override
					public ClosureDate getClosureDate() {
						return new ClosureDate(1, true);
					}
				}));
			}
		});
	}

	public static CompensatoryLeaveEmSetting createComLeav(ManageDistinct manageDistinct, ManageDistinct manageTime,
			String empCode) {
		return new CompensatoryLeaveEmSetting(new CompensatoryLeaveEmSettingGetMemento() {

			@Override
			public ManageDistinct getIsManaged() {
				return manageDistinct;
			}

			@Override
			public EmploymentCode getEmploymentCode() {
				return new EmploymentCode(empCode);
			}

			@Override
			public CompensatoryDigestiveTimeUnit getCompensatoryDigestiveTimeUnit() {
				return new CompensatoryDigestiveTimeUnit(new CompensatoryDigestiveTimeUnitGetMemento() {

					@Override
					public ManageDistinct getIsManageByTime() {
						return manageTime;
					}

					@Override
					public TimeDigestiveUnit getDigestiveUnit() {
						return TimeDigestiveUnit.OneMinute;
					}
				});
			}

			@Override
			public CompensatoryAcquisitionUse getCompensatoryAcquisitionUse() {
				return new CompensatoryAcquisitionUse(new CompensatoryAcquisitionUseGetMemento() {

					@Override
					public ApplyPermission getPreemptionPermit() {
						return ApplyPermission.ALLOW;
					}

					@Override
					public ExpirationTime getExpirationTime() {
						return ExpirationTime.THREE_MONTH;
					}

					@Override
					public DeadlCheckMonth getDeadlCheckMonth() {
						return DeadlCheckMonth.THREE_MONTH;
					}
				});
			}

			@Override
			public String getCompanyId() {
				return "";
			}
		});
	}
}