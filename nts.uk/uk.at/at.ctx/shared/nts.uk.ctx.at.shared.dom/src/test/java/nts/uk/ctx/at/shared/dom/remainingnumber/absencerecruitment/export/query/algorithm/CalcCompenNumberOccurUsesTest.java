package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.CompenSuspensionAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.UnbalanceCompensation;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.AccuVacationBuilder;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.NumberConsecuVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.StatutoryAtr;

@RunWith(JMockit.class)
public class CalcCompenNumberOccurUsesTest {

	private static String SID = "292ae91c-508c-4c6e-8fe8-3e72277dec16";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {

		List<AccumulationAbsenceDetail> lstAccDetail = Arrays.asList(
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 3))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554ddff")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.build(),
				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 04, 11))),
						OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554dddd")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.build(),

				new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554bbbb")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(0))))
								.build(),
				new UnbalanceCompensation(new AccuVacationBuilder(SID,
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 14))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554eaaa")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(0))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(0))))
								.build(),
						GeneralDate.ymd(2019, 12, 30), DigestionAtr.UNUSED, Optional.empty(), StatutoryAtr.PUBLIC));

		CompenSuspensionAggrResult resultActual = CalcCompenNumberOccurUses.calc(lstAccDetail,
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2019, 11, 30)));

		assertThat(resultActual.getSuOccurDay()).isEqualTo(2.0);

		assertThat(resultActual.getSuDayUse()).isEqualTo(1.0);
	}

}
