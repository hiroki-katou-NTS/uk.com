package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.groups.Tuple;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.AccuVacationBuilder;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.NumberConsecuVacation;

public class AccumulationAbsenceDetailComparatorTest {

	private static String SID = "292ae91c-508c-4c6e-8fe8-3e72277dec16";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCompare() {

		List<AccumulationAbsenceDetail> lstAccDetail = Arrays.asList(

				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 11))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "876caf30-5a4d-47b7-8147-d646f74be08a")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(0)))).build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554e132")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(0)))).build(),
				new AccuVacationBuilder(SID, new CompensatoryDayoffDate(true, Optional.empty()),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "62d542c3-4b79-4bf3-bd39-7e7f06711c34")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(0)))).build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 9))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "876caf30-5a4d-47b7-8147-d646f74be08a")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(0)))).build(),
				new AccuVacationBuilder(SID, new CompensatoryDayoffDate(true, Optional.empty()),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "077a8929-3df0-4fd6-859e-29e615a921ee")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(0)))).build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 10))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "876caf30-5a4d-47b7-8147-d646f74be08a")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(0)))).build());
		lstAccDetail.sort(new AccumulationAbsenceDetailComparator());
		
		assertThat(lstAccDetail).extracting(x -> x.getManageId(), x -> x.getEmployeeId(), x -> x.getDataAtr(),
				x -> x.getDateOccur().isUnknownDate(), x -> x.getDateOccur().getDayoffDate(),
				x -> x.getNumberOccurren().getDay().v(), x -> x.getNumberOccurren().getTime(),
				x -> x.getOccurrentClass(), x -> x.getUnbalanceNumber().getDay().v(),
				x -> x.getUnbalanceNumber().getTime()).containsExactly(
						
						Tuple.tuple("62d542c3-4b79-4bf3-bd39-7e7f06711c34", SID, MngDataStatus.RECORD, true,
								Optional.empty(), 0.0, Optional.of(new AttendanceTime(0)), OccurrenceDigClass.DIGESTION,
								0.0, Optional.of(new AttendanceTime(0))),
						
						Tuple.tuple("077a8929-3df0-4fd6-859e-29e615a921ee", SID, MngDataStatus.RECORD, true,
								Optional.empty(), 0.0, Optional.of(new AttendanceTime(0)), OccurrenceDigClass.DIGESTION,
								0.0, Optional.of(new AttendanceTime(0))),
						
						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554e132", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 4)), 0.0, Optional.of(new AttendanceTime(0)),
								OccurrenceDigClass.DIGESTION, 0.0, Optional.of(new AttendanceTime(0))),
						
						Tuple.tuple("876caf30-5a4d-47b7-8147-d646f74be08a", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 9)), 0.0, Optional.of(new AttendanceTime(0)),
								OccurrenceDigClass.DIGESTION, 0.0, Optional.of(new AttendanceTime(0))),

						Tuple.tuple("876caf30-5a4d-47b7-8147-d646f74be08a", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 10)), 0.0, Optional.of(new AttendanceTime(0)),
								OccurrenceDigClass.DIGESTION, 0.0, Optional.of(new AttendanceTime(0))),


						Tuple.tuple("876caf30-5a4d-47b7-8147-d646f74be08a", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 11)), 0.0, Optional.of(new AttendanceTime(0)),
								OccurrenceDigClass.DIGESTION, 0.0, Optional.of(new AttendanceTime(0))));
	}

}
