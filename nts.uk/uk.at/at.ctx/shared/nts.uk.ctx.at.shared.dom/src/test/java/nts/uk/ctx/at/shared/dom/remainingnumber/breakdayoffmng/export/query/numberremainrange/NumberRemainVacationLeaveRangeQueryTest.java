package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.BreakDayOffRemainMngRefactParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SubstituteHolidayAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.breakinfo.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryAcquisitionUse;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryAcquisitionUseGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSettingGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.DeadlCheckMonth;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.TermManagement;
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
	 * ????????????????????????
	 * 
	 * ????????????????????????????????????
	 * 
	 * ?????????????????????
	 * 
	 * ????????????????????????????????????
	 * 
	 * ???????????????????????????????????????
	 * 
	 * ???????????????????????? (?????????, ????????????)
	 */
	@Test
	public void testOptBeforeResultNoPresent() {
		/*List<InterimDayOffMng> dayOffMng = new ArrayList<InterimDayOffMng>();
		List<InterimBreakMng> breakMng = new ArrayList<>();
		List<InterimRemain> interimMng = new ArrayList<>();
		
		// ?????????????????????
		Optional<SubstituteHolidayAggrResult> holidayAggrResult = Optional
				.of(DaikyuFurikyuHelper.createDefaultResult(new ArrayList<>(), 12.0, // ??????????????????
						GeneralDate.ymd(2019, 10, 01)));// ???????????????????????????

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

				//???????????????????????????
				require.getRemainBySidPriod(anyString, (DatePeriod) any, (RemainType) any);
				result = Arrays.asList(
						new InterimRemain("daikyu1", "", GeneralDate.ymd(2019, 11, 4), CreateAtr.RECORD,
								RemainType.SUBHOLIDAY),
						new InterimRemain("daikyu2", "", GeneralDate.ymd(2019, 11, 5), CreateAtr.RECORD,
								RemainType.SUBHOLIDAY),
						new InterimRemain("daikyu3", "", GeneralDate.ymd(2019, 11, 8), CreateAtr.RECORD,
								RemainType.SUBHOLIDAY),
						new InterimRemain("daikyu4", "", GeneralDate.ymd(2019, 11, 9), CreateAtr.RECORD,
								RemainType.SUBHOLIDAY));

				// ???????????????????????????
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

		SubstituteHolidayAggrResult resultExpected = DaikyuFurikyuHelper.createBeforeResult(new ArrayList<>(), // ??????????????????
				-4.0, 0, // ????????????
				4.0, 0, // ??????
				0.0, 0, // ??????
				0d, 0, // ??????
				0d, 0, // ?????????
				Arrays.asList(DayOffError.DAYERROR), // ???????????????
				GeneralDate.ymd(2020, 11, 01));// ???????????????????????????

		assertData(resultActual, resultExpected);
		assertThat(resultActual.getCarryoverDay().v()).isEqualTo(0.0);
		assertThat(resultActual.getVacationDetails().getLstAcctAbsenDetail()).extracting(x -> x.getManageId(), // ?????????????????????ID
				x -> x.getDateOccur().getDayoffDate(), // ?????????
				x -> x.getNumberOccurren().getDay().v(), x -> x.getNumberOccurren().getTime(), // ??????
				x -> x.getOccurrentClass(), // ??????-??????
				x -> x.getUnbalanceNumber().getDay().v(), x -> x.getUnbalanceNumber().getTime())// ?????????
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
								Optional.of(new AttendanceTime(0))));*/

	}

	/**
	 * ????????????????????????
	 * 
	 * ???????????????????????????????????????????????????????????????
	 * 
	 * ?????????????????????
	 * 
	 * ??? ???????????????????????????.???????????????????????????????????????
	 * 
	 * ???????????????????????????????????????
	 * 
	 * ???????????????????????? (?????????, ????????????...)
	 */
	@Test
	public void testOptBeforePresent() {

		List<InterimBreakMng> breakMng = new ArrayList<>();
		List<InterimDayOffMng> dayOffMng = new ArrayList<>();

		List<InterimRemain> interimMng = new ArrayList<>();

		List<AccumulationAbsenceDetail> lstAccDetail = Arrays.asList(DaikyuFurikyuHelper.createDetailDefault(true, // ??????
				OccurrenceDigClass.OCCURRENCE, // ??????
				Optional.of(GeneralDate.ymd(2019, 11, 4)), // ?????????
				"kyuushutsu1", // ?????????????????????ID
				1.0, 480, // ??????
				1.0, 0// ?????????
		), DaikyuFurikyuHelper.createDetailDefault(true, // ??????
				OccurrenceDigClass.DIGESTION, // ??????
				Optional.of(GeneralDate.ymd(2019, 11, 5)), // ?????????
				"daikyu1", // ?????????????????????ID
				1.0, 480, // ??????
				0.5, 0// ?????????
		));

		// ?????????????????????
		Optional<SubstituteHolidayAggrResult> holidayAggrResult = Optional
				.of(DaikyuFurikyuHelper.createDefaultResult(lstAccDetail, // ??????????????????
						12.0,//????????????
						GeneralDate.ymd(2019, 11, 01)));// ???????????????????????????

		BreakDayOffRemainMngRefactParam inputParam = DaikyuFurikyuHelper.inputParamDaikyu(
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2019, 11, 30)), // ???????????????, ???????????????
				false,//????????? : True: ?????????, False: ????????????
				GeneralDate.ymd(2019, 11, 30), //??????????????? 
				false, ///** ??????????????????: True: ?????????, False: ???????????? */
				interimMng, //???????????????????????????
				breakMng, //???????????????????????????
				dayOffMng, //???????????????????????????
				holidayAggrResult,// ?????????????????????
				new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));//??????????????????????????????

		SubstituteHolidayAggrResult resultActual = NumberRemainVacationLeaveRangeQuery
				.getBreakDayOffMngInPeriod(require, inputParam);

		SubstituteHolidayAggrResult resultExpected = DaikyuFurikyuHelper.createBeforeResult(new ArrayList<>(), // ??????????????????
				0.5, 0, // ????????????
				1.0, 480, // ??????
				1.0, 480, // ??????
				12.0, 0, // ??????
				0d, 0, // ?????????
				Collections.emptyList(), // ???????????????
				GeneralDate.ymd(2019, 12, 01));// ???????????????????????????

		assertData(resultActual, resultExpected);
		assertThat(resultActual.getCarryoverDay().v()).isEqualTo(0.0);
	}

	public static void assertData(SubstituteHolidayAggrResult resultActual,
			SubstituteHolidayAggrResult resultExpected) {

		// ?????????
		/*assertThat(resultActual.getRemainDay().v()).isEqualTo(resultExpected.getRemainDay().v());
		// ?????????
		assertThat(resultActual.getRemainTime().v()).isEqualTo(resultExpected.getRemainTime().v());
		// ????????????
		assertThat(resultActual.getDayUse().v()).isEqualTo(resultExpected.getDayUse().v());
		// ????????????
		assertThat(resultActual.getTimeUse().v()).isEqualTo(resultExpected.getTimeUse().v());
		// ????????????
		assertThat(resultActual.getOccurrenceDay().v()).isEqualTo(resultExpected.getOccurrenceDay().v());
		// ????????????
		assertThat(resultActual.getOccurrenceTime().v()).isEqualTo(resultExpected.getOccurrenceTime().v());
		// ????????????
		assertThat(resultActual.getCarryoverDay().v()).isEqualTo(resultExpected.getCarryoverDay().v());
		// ????????????
		assertThat(resultActual.getCarryoverTime().v()).isEqualTo(resultExpected.getCarryoverTime().v());
		// ???????????????
		assertThat(resultActual.getUnusedDay().v()).isEqualTo(resultExpected.getUnusedDay().v());
		// ???????????????
		assertThat(resultActual.getUnusedTime().v()).isEqualTo(resultExpected.getUnusedTime().v());

		// ???????????????
		assertThat(resultActual.getDayOffErrors()).isEqualTo(resultExpected.getDayOffErrors());

		// ???????????????????????????
		assertThat(resultActual.getNextDay().get()).isEqualTo(resultExpected.getNextDay().get());*/
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

			/*@Override
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
*/
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

					@Override
					public TermManagement termManagement() {
						return TermManagement.MANAGE_BASED_ON_THE_DATE;
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