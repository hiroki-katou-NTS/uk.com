package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm;

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
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyDto;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.AbsRecMngInPeriodRefactParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.CompenLeaveAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.NumberRemainVacationLeaveRangeQueryTest;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.DataManagementAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.OccurrenceDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.SelectedAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.StatutoryAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnUsedDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.SubstVacationSetting;

@RunWith(JMockit.class)
public class ProcessCompenSuspensionAllTest {

	private static String CID = "000000000000-0117";

	private static String SID = "292ae91c-508c-4c6e-8fe8-3e72277dec16";

	@Injectable
	private ProcessCompenSuspensionAll.Require require;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test() {

		new Expectations() {
			{

				require.getRecOrAbsMngs((List<String>) (any), anyBoolean, DataManagementAtr.INTERIM);
				result = Arrays.asList(
						new InterimRecAbsMng("adda6a46-2cbe-48c8-85f8-c04ca554e132", DataManagementAtr.INTERIM, "",
								DataManagementAtr.INTERIM, new UseDay(1.0), SelectedAtr.MANUAL),

						new InterimRecAbsMng("", DataManagementAtr.INTERIM, "adda6a46-2cbe-48c8-85f8-c04ca554e333",
								DataManagementAtr.INTERIM, new UseDay(1.0), SelectedAtr.MANUAL),
						new InterimRecAbsMng("", DataManagementAtr.INTERIM, "62d542c3-4b79-4bf3-bd39-7e7f06711c34",
								DataManagementAtr.INTERIM, new UseDay(1.0), SelectedAtr.MANUAL),
						new InterimRecAbsMng("", DataManagementAtr.INTERIM, "077a8929-3df0-4fd6-859e-29e615a921ee",
								DataManagementAtr.INTERIM, new UseDay(1.0), SelectedAtr.MANUAL));

				require.findEmploymentHistory(CID, SID, (GeneralDate) any);
				result = Optional.of(new BsEmploymentHistoryImport(SID, "00", "A",
						new DatePeriod(GeneralDate.min(), GeneralDate.max())));

				require.getClosureDataByEmployee(SID, (GeneralDate) any);
				result = NumberRemainVacationLeaveRangeQueryTest.createClosure();

				require.getFirstMonth(CID);
				result = new CompanyDto(11);

				require.findEmpById(anyString, anyString);
				result = Optional.of(new EmpSubstVacation(CID, "00", new SubstVacationSetting(ManageDistinct.YES,
						ExpirationTime.THIS_MONTH, ApplyPermission.ALLOW)));

			}
		};

		List<InterimAbsMng> useAbsMng = Arrays.asList(
				new InterimAbsMng("adda6a46-2cbe-48c8-85f8-c04ca554e132", new RequiredDay(1.0), new UnOffsetDay(1.0)),
				new InterimAbsMng("adda6a46-2cbe-48c8-85f8-c04ca554e133", new RequiredDay(0.5), new UnOffsetDay(0.5)),
				new InterimAbsMng("adda6a46-2cbe-48c8-85f8-c04ca554e136", new RequiredDay(1.0), new UnOffsetDay(1.0)));

		List<InterimRecMng> useRecMng = Arrays.asList(
				new InterimRecMng("adda6a46-2cbe-48c8-85f8-c04ca554e333", GeneralDate.max(), new OccurrenceDay(1.0),
						StatutoryAtr.PUBLIC, new UnUsedDay(1.0)),
				new InterimRecMng("62d542c3-4b79-4bf3-bd39-7e7f06711c34", GeneralDate.max(), new OccurrenceDay(1.0),
						StatutoryAtr.PUBLIC, new UnUsedDay(1.0)),
				new InterimRecMng("077a8929-3df0-4fd6-859e-29e615a921ee", GeneralDate.max(), new OccurrenceDay(1.0),
						StatutoryAtr.PUBLIC, new UnUsedDay(1.0)),
				new InterimRecMng("077a8929-3df0-4fd6-859e-29e615a921e7", GeneralDate.max(), new OccurrenceDay(1.0),
						StatutoryAtr.PUBLIC, new UnUsedDay(1.0)),
				new InterimRecMng("077a8929-3df0-4fd6-859e-29e615a921e8", GeneralDate.max(), new OccurrenceDay(0.5),
						StatutoryAtr.PUBLIC, new UnUsedDay(0.5)),
				new InterimRecMng("077a8929-3df0-4fd6-859e-29e615a921e6", GeneralDate.ymd(2010, 10, 4),
						new OccurrenceDay(1.0), StatutoryAtr.PUBLIC, new UnUsedDay(1.0)));

		List<InterimRemain> interimMng = Arrays.asList(
				new InterimRemain("adda6a46-2cbe-48c8-85f8-c04ca554e132", SID, GeneralDate.ymd(2019, 11, 4),
						CreateAtr.SCHEDULE, RemainType.PAUSE, RemainAtr.SINGLE),
				new InterimRemain("adda6a46-2cbe-48c8-85f8-c04ca554e133", SID, GeneralDate.ymd(2019, 11, 6),
						CreateAtr.RECORD, RemainType.PAUSE, RemainAtr.SINGLE),
				new InterimRemain("adda6a46-2cbe-48c8-85f8-c04ca554e136", SID, GeneralDate.ymd(2019, 11, 7),
						CreateAtr.RECORD, RemainType.PAUSE, RemainAtr.SINGLE),

				new InterimRemain("adda6a46-2cbe-48c8-85f8-c04ca554e333", SID, GeneralDate.ymd(2019, 11, 5),
						CreateAtr.SCHEDULE, RemainType.PICKINGUP, RemainAtr.SINGLE),
				new InterimRemain("62d542c3-4b79-4bf3-bd39-7e7f06711c34", SID, GeneralDate.ymd(2019, 11, 14),
						CreateAtr.RECORD, RemainType.PICKINGUP, RemainAtr.SINGLE),
				new InterimRemain("077a8929-3df0-4fd6-859e-29e615a921ee", SID, GeneralDate.ymd(2019, 11, 15),
						CreateAtr.RECORD, RemainType.PICKINGUP, RemainAtr.SINGLE),
				new InterimRemain("077a8929-3df0-4fd6-859e-29e615a921e7", SID, GeneralDate.ymd(2019, 11, 11),
						CreateAtr.RECORD, RemainType.PICKINGUP, RemainAtr.SINGLE),
				new InterimRemain("077a8929-3df0-4fd6-859e-29e615a921e8", SID, GeneralDate.ymd(2019, 11, 11),
						CreateAtr.RECORD, RemainType.PICKINGUP, RemainAtr.SINGLE),
				new InterimRemain("077a8929-3df0-4fd6-859e-29e615a921e6", SID, GeneralDate.ymd(2019, 11, 16),
						CreateAtr.RECORD, RemainType.PICKINGUP, RemainAtr.SINGLE));

		CompenLeaveAggrResult compenLeaveAggrResult = new CompenLeaveAggrResult(
				new VacationDetails(Collections.emptyList()), new ReserveLeaveRemainingDayNumber(1.0),
				new ReserveLeaveRemainingDayNumber(1.0), new ReserveLeaveRemainingDayNumber(1.0),
				new ReserveLeaveRemainingDayNumber(1.0), new ReserveLeaveRemainingDayNumber(1.0),
				Finally.of(GeneralDate.ymd(2019, 11, 01)), Collections.emptyList(), Collections.emptyList());

		AbsRecMngInPeriodRefactParamInput inputParam = new AbsRecMngInPeriodRefactParamInput(CID, SID,
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)),
				GeneralDate.ymd(2019, 11, 30), true, true, useAbsMng, interimMng, useRecMng,
				Optional.of(compenLeaveAggrResult), Optional.empty(), Optional.empty(),
				new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));

		List<AccumulationAbsenceDetail> actual = ProcessCompenSuspensionAll.process(require, inputParam);

		assertThat(actual)
				.extracting(x -> x.getManageId(), x -> x.getEmployeeId(), x -> x.getDataAtr(),
						x -> x.getDateOccur().isUnknownDate(), x -> x.getDateOccur().getDayoffDate(),
						x -> x.getNumberOccurren().getDay().v(), x -> x.getNumberOccurren().getTime(),
						x -> x.getOccurrentClass(), x -> x.getUnbalanceNumber().getDay().v(),
						x -> x.getUnbalanceNumber().getTime())
				.containsExactly(
						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554e132", SID, MngDataStatus.SCHEDULE, false,
								Optional.of(GeneralDate.ymd(2019, 11, 04)), 1.0, Optional.empty(),
								OccurrenceDigClass.DIGESTION, 0.0, Optional.empty()),

						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554e133", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 6)), 0.5, Optional.empty(),
								OccurrenceDigClass.DIGESTION, 0.5, Optional.empty()),

						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554e136", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 7)), 1.0, Optional.empty(),
								OccurrenceDigClass.DIGESTION, 1.0, Optional.empty()),

						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554e333", SID, MngDataStatus.SCHEDULE, false,
								Optional.of(GeneralDate.ymd(2019, 11, 5)), 1.0, Optional.empty(),
								OccurrenceDigClass.OCCURRENCE, 0.0, Optional.empty()),

						Tuple.tuple("62d542c3-4b79-4bf3-bd39-7e7f06711c34", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 14)), 1.0, Optional.empty(),
								OccurrenceDigClass.OCCURRENCE, 0.0, Optional.empty()),

						Tuple.tuple("077a8929-3df0-4fd6-859e-29e615a921ee", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 15)), 1.0, Optional.empty(),
								OccurrenceDigClass.OCCURRENCE, 0.0, Optional.empty()),

						Tuple.tuple("077a8929-3df0-4fd6-859e-29e615a921e8", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 11)), 0.5, Optional.empty(),
								OccurrenceDigClass.OCCURRENCE, 0.5, Optional.empty()),

						Tuple.tuple("077a8929-3df0-4fd6-859e-29e615a921e6", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 16)), 1.0, Optional.empty(),
								OccurrenceDigClass.OCCURRENCE, 1.0, Optional.empty()));
	}

}
