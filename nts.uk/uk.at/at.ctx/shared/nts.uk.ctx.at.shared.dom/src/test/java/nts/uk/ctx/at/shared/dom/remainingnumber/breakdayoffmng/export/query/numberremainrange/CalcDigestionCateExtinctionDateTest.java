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
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.UnbalanceCompensation;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.AccuVacationBuilder;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.NumberConsecuVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.UnbalanceVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.StatutoryAtr;

public class CalcDigestionCateExtinctionDateTest {

	private static String SID = "292ae91c-508c-4c6e-8fe8-3e72277dec16";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testDaikyu() {

		List<AccumulationAbsenceDetail> lstAccAbse = Arrays.asList(
				new UnbalanceVacation(GeneralDate.ymd(2019, 12, 8), DigestionAtr.UNUSED,
						Optional.of(GeneralDate.ymd(2019, 12, 30)),
						new AccuVacationBuilder(SID,
								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
								OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD,
								"adda6a46-2cbe-48c8-85f8-c04ca554bbbb")
										.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
												Optional.of(new AttendanceTime(480))))
										.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0d),
												Optional.of(new AttendanceTime(0))))
										.build(),
						new AttendanceTime(480), new AttendanceTime(240)),
				new UnbalanceVacation(GeneralDate.ymd(2019, 12, 8), DigestionAtr.UNUSED,
						Optional.of(GeneralDate.ymd(2019, 12, 30)),
						new AccuVacationBuilder(SID, new CompensatoryDayoffDate(false, Optional.empty()),
								OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD,
								"adda6a46-2cbe-48c8-85f8-c04ca554ddde")
										.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
												Optional.of(new AttendanceTime(480))))
										.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
												Optional.of(new AttendanceTime(480))))
										.build(),
						new AttendanceTime(480), new AttendanceTime(240)),
				new UnbalanceVacation(GeneralDate.ymd(2019, 6, 8), DigestionAtr.UNUSED,
						Optional.of(GeneralDate.ymd(2019, 12, 30)),
						new AccuVacationBuilder(SID,
								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 10))),
								OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD,
								"adda6a46-2cbe-48c8-85f8-c04ca554dddf")
										.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
												Optional.of(new AttendanceTime(480))))
										.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
												Optional.of(new AttendanceTime(240))))
										.build(),
						new AttendanceTime(480), new AttendanceTime(240)),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554eaaa")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(480))))
								.unbalanceNumber(
										new NumberConsecuVacation(new ManagementDataRemainUnit(0.5), Optional.empty()))
								.build());

		CalcDigestionCateExtinctionDate.calc(lstAccAbse, GeneralDate.ymd(2019, 11, 3), TypeOffsetJudgment.REAMAIN);

		assertThat(lstAccAbse)
				.extracting(x -> x.getManageId(), x -> x.getEmployeeId(), x -> x.getOccurrentClass(),
						x -> x.getOccurrentClass() == OccurrenceDigClass.DIGESTION ? Optional.empty()
								: ((UnbalanceVacation) x).getDigestionCate(),
						x -> x.getOccurrentClass() == OccurrenceDigClass.DIGESTION ? Optional.empty()
								: ((UnbalanceVacation) x).getExtinctionDate())
				.containsExactly(
						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554bbbb", SID, OccurrenceDigClass.OCCURRENCE,
								DigestionAtr.USED, Optional.of(GeneralDate.ymd(2019, 12, 30))),
						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554ddde", SID, OccurrenceDigClass.OCCURRENCE,
								DigestionAtr.UNUSED, Optional.of(GeneralDate.ymd(2019, 12, 30))),
						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554dddf", SID, OccurrenceDigClass.OCCURRENCE,
								DigestionAtr.EXPIRED, Optional.of(GeneralDate.ymd(2019, 6, 8))),
						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554eaaa", SID, OccurrenceDigClass.DIGESTION,
								Optional.empty(), Optional.empty()));

	}

	@Test
	public void testFurikyu() {
		List<AccumulationAbsenceDetail> lstAccAbse = Arrays.asList(
				new UnbalanceCompensation(new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554bbbb")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(480))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0d),
										Optional.of(new AttendanceTime(0))))
								.build(),
						GeneralDate.ymd(2019, 12, 8), DigestionAtr.UNUSED, Optional.of(GeneralDate.ymd(2019, 12, 30)),
						StatutoryAtr.PUBLIC),
				new UnbalanceCompensation(
						new AccuVacationBuilder(SID, new CompensatoryDayoffDate(false, Optional.empty()),
								OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD,
								"adda6a46-2cbe-48c8-85f8-c04ca554ddde")
										.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
												Optional.of(new AttendanceTime(480))))
										.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
												Optional.of(new AttendanceTime(480))))
										.build(),
						GeneralDate.ymd(2019, 12, 8), DigestionAtr.UNUSED, Optional.of(GeneralDate.ymd(2019, 12, 30)),
						StatutoryAtr.PUBLIC),
				new UnbalanceCompensation(new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 10))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554dddf")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(480))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(240))))
								.build(),
						GeneralDate.ymd(2019, 6, 8), DigestionAtr.UNUSED, Optional.of(GeneralDate.ymd(2019, 12, 30)),
						StatutoryAtr.PUBLIC),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554eaaa")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(480))))
								.unbalanceNumber(
										new NumberConsecuVacation(new ManagementDataRemainUnit(0.5), Optional.empty()))
								.build());

		CalcDigestionCateExtinctionDate.calc(lstAccAbse, GeneralDate.ymd(2019, 11, 3), TypeOffsetJudgment.ABSENCE);

		assertThat(lstAccAbse)
				.extracting(x -> x.getManageId(), x -> x.getEmployeeId(), x -> x.getOccurrentClass(),
						x -> x.getOccurrentClass() == OccurrenceDigClass.DIGESTION ? Optional.empty()
								: ((UnbalanceCompensation) x).getDigestionCate(),
						x -> x.getOccurrentClass() == OccurrenceDigClass.DIGESTION ? Optional.empty()
								: ((UnbalanceCompensation) x).getExtinctionDate())
				.containsExactly(
						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554bbbb", SID, OccurrenceDigClass.OCCURRENCE,
								DigestionAtr.USED, Optional.of(GeneralDate.ymd(2019, 12, 30))),
						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554ddde", SID, OccurrenceDigClass.OCCURRENCE,
								DigestionAtr.UNUSED, Optional.of(GeneralDate.ymd(2019, 12, 30))),
						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554dddf", SID, OccurrenceDigClass.OCCURRENCE,
								DigestionAtr.EXPIRED, Optional.of(GeneralDate.ymd(2019, 6, 8))),
						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554eaaa", SID, OccurrenceDigClass.DIGESTION,
								Optional.empty(), Optional.empty()));
	}

}
