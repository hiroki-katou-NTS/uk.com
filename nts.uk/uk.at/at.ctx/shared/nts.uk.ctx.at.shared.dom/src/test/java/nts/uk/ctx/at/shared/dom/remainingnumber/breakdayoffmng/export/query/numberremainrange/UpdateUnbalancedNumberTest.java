package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
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
import nts.uk.ctx.at.shared.dom.vacation.algorithm.TimeLapseVacationSetting;

public class UpdateUnbalancedNumberTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	/*
	 * 　テストしたい内容
	 *     　未相殺数を更新しない
	 * 
	 * 　準備するデータ
	 * 　　「逐次発生の休暇明細」(消化)．未相殺数> 「逐次発生の休暇明細」(発生)．未相殺数
	 *           逐次発生休暇設定.時間管理区分 = false
	 * */
	@Test
	// manager time = false, accdigest >= occur
	public void test() {

		TimeLapseVacationSetting setting = new TimeLapseVacationSetting(
				new DatePeriod(GeneralDate.ymd(2019, 4, 1), GeneralDate.ymd(2019, 4, 30)), true, 2, true,
				Optional.of(false), Optional.of(5));

		AccumulationAbsenceDetail accdigest = new UnbalanceVacation(GeneralDate.ymd(2019, 12, 8), DigestionAtr.UNUSED,
				Optional.of(GeneralDate.ymd(2019, 12, 30)),
				new AccuVacationBuilder("1",
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554bbbb")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(480))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(120))))
								.build(),
				new AttendanceTime(0), new AttendanceTime(0));

		AccumulationAbsenceDetail occur = new AccuVacationBuilder("1",
				new CompensatoryDayoffDate(false, Optional.empty()), OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD,
				"adda6a46-2cbe-48c8-85f8-c04ca554cccc")
						.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
								Optional.of(new AttendanceTime(480))))
						.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
								Optional.of(new AttendanceTime(120))))
						.build();

		UpdateUnbalancedNumber.updateUnbalanced(setting, accdigest, occur, TypeOffsetJudgment.REAMAIN);

		AccumulationAbsenceDetail accdigestExpected = new UnbalanceVacation(GeneralDate.ymd(2019, 12, 8),
				DigestionAtr.UNUSED, Optional.of(GeneralDate.ymd(2019, 12, 30)),
				new AccuVacationBuilder("1",
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554bbbb")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(480))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(120))))
								.build(),
				new AttendanceTime(0), new AttendanceTime(0));

		AccumulationAbsenceDetail occurExpected = new AccuVacationBuilder("1",
				new CompensatoryDayoffDate(false, Optional.empty()), OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD,
				"adda6a46-2cbe-48c8-85f8-c04ca554cccc")
						.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
								Optional.of(new AttendanceTime(480))))
						.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
								Optional.of(new AttendanceTime(120))))
						.build();

		assertDataNoTime(accdigest, accdigestExpected);

		assertDataNoTime(occur, occurExpected);
	}

	@Test
	// manager time = false, accdigest < occur
	/*
	 * 　テストしたい内容
	 *     　未相殺数を更新しない
	 * 
	 * 　準備するデータ
	 * 　　「逐次発生の休暇明細」(消化)．未相殺数< 「逐次発生の休暇明細」(発生)．未相殺数
	 *           逐次発生休暇設定.時間管理区分 = false
	 * */
	public void test2() {

		TimeLapseVacationSetting setting = new TimeLapseVacationSetting(
				new DatePeriod(GeneralDate.ymd(2019, 4, 1), GeneralDate.ymd(2019, 4, 30)), true, 2, true,
				Optional.of(false), Optional.of(5));

		AccumulationAbsenceDetail accdigest = new UnbalanceVacation(GeneralDate.ymd(2019, 12, 8), DigestionAtr.UNUSED,
				Optional.of(GeneralDate.ymd(2019, 12, 30)),
				new AccuVacationBuilder("1",
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554bbbb")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(480))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.5),
										Optional.of(new AttendanceTime(120))))
								.build(),
				new AttendanceTime(0), new AttendanceTime(0));

		AccumulationAbsenceDetail occur = new AccuVacationBuilder("1",
				new CompensatoryDayoffDate(false, Optional.empty()), OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD,
				"adda6a46-2cbe-48c8-85f8-c04ca554cccc")
						.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
								Optional.of(new AttendanceTime(480))))
						.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
								Optional.of(new AttendanceTime(120))))
						.build();

		UpdateUnbalancedNumber.updateUnbalanced(setting, accdigest, occur, TypeOffsetJudgment.REAMAIN);

		AccumulationAbsenceDetail accdigestExpected = new UnbalanceVacation(GeneralDate.ymd(2019, 12, 8),
				DigestionAtr.UNUSED, Optional.of(GeneralDate.ymd(2019, 12, 30)),
				new AccuVacationBuilder("1",
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554bbbb")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(480))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(120))))
								.build(),
				new AttendanceTime(0), new AttendanceTime(0));

		AccumulationAbsenceDetail occurExpected = new AccuVacationBuilder("1",
				new CompensatoryDayoffDate(false, Optional.empty()), OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD,
				"adda6a46-2cbe-48c8-85f8-c04ca554cccc")
						.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
								Optional.of(new AttendanceTime(480))))
						.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.5),
								Optional.of(new AttendanceTime(120))))
						.build();

		assertDataNoTime(accdigest, accdigestExpected);

		assertDataNoTime(occur, occurExpected);
	}

	@Test
	// manager time = true, accdigest >= occur
	/*
	 * 　テストしたい内容
	 *     　未相殺数を更新しない
	 * 
	 * 　準備するデータ
	 * 　　「逐次発生の休暇明細」(消化)．未相殺数> 「逐次発生の休暇明細」(発生)．未相殺数
	 *           逐次発生休暇設定.時間管理区分 = true
	 * */
	public void test3() {

		TimeLapseVacationSetting setting = new TimeLapseVacationSetting(
				new DatePeriod(GeneralDate.ymd(2019, 4, 1), GeneralDate.ymd(2019, 4, 30)), true, 2, true,
				Optional.of(true), Optional.of(5));

		AccumulationAbsenceDetail accdigest = new UnbalanceVacation(GeneralDate.ymd(2019, 12, 8), DigestionAtr.UNUSED,
				Optional.of(GeneralDate.ymd(2019, 12, 30)),
				new AccuVacationBuilder("1",
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554bbbb")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(480))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(480))))
								.build(),
				new AttendanceTime(0), new AttendanceTime(0));

		AccumulationAbsenceDetail occur = new AccuVacationBuilder("1",
				new CompensatoryDayoffDate(false, Optional.empty()), OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD,
				"adda6a46-2cbe-48c8-85f8-c04ca554cccc")
						.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
								Optional.of(new AttendanceTime(480))))
						.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
								Optional.of(new AttendanceTime(120))))
						.build();

		UpdateUnbalancedNumber.updateUnbalanced(setting, accdigest, occur, TypeOffsetJudgment.REAMAIN);

		AccumulationAbsenceDetail accdigestExpected = new UnbalanceVacation(GeneralDate.ymd(2019, 12, 8),
				DigestionAtr.UNUSED, Optional.of(GeneralDate.ymd(2019, 12, 30)),
				new AccuVacationBuilder("1",
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554bbbb")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(480))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(360))))
								.build(),
				new AttendanceTime(0), new AttendanceTime(0));

		AccumulationAbsenceDetail occurExpected = new AccuVacationBuilder("1",
				new CompensatoryDayoffDate(false, Optional.empty()), OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD,
				"adda6a46-2cbe-48c8-85f8-c04ca554cccc")
						.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
								Optional.of(new AttendanceTime(480))))
						.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
								Optional.of(new AttendanceTime(0))))
						.build();

		assertDataHasTime(accdigest, accdigestExpected);

		assertDataHasTime(occur, occurExpected);
	}

	@Test
	// manager time = true, accdigest < occur
	/*
	 * 　テストしたい内容
	 *     　未相殺数を更新しない
	 * 
	 * 　準備するデータ
	 * 　　「逐次発生の休暇明細」(消化)．未相殺数< 「逐次発生の休暇明細」(発生)．未相殺数
	 *           逐次発生休暇設定.時間管理区分 = true
	 * */
	public void test4() {

		TimeLapseVacationSetting setting = new TimeLapseVacationSetting(
				new DatePeriod(GeneralDate.ymd(2019, 4, 1), GeneralDate.ymd(2019, 4, 30)), true, 2, true,
				Optional.of(true), Optional.of(5));

		AccumulationAbsenceDetail accdigest = new UnbalanceVacation(GeneralDate.ymd(2019, 12, 8), DigestionAtr.UNUSED,
				Optional.of(GeneralDate.ymd(2019, 12, 30)),
				new AccuVacationBuilder("1",
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554bbbb")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(480))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(120))))
								.build(),
				new AttendanceTime(0), new AttendanceTime(0));

		AccumulationAbsenceDetail occur = new AccuVacationBuilder("1",
				new CompensatoryDayoffDate(false, Optional.empty()), OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD,
				"adda6a46-2cbe-48c8-85f8-c04ca554cccc")
						.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
								Optional.of(new AttendanceTime(480))))
						.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
								Optional.of(new AttendanceTime(480))))
						.build();

		UpdateUnbalancedNumber.updateUnbalanced(setting, accdigest, occur, TypeOffsetJudgment.REAMAIN);

		AccumulationAbsenceDetail accdigestExpected = new UnbalanceVacation(GeneralDate.ymd(2019, 12, 8),
				DigestionAtr.UNUSED, Optional.of(GeneralDate.ymd(2019, 12, 30)),
				new AccuVacationBuilder("1",
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554bbbb")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(480))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(0))))
								.build(),
				new AttendanceTime(0), new AttendanceTime(0));

		AccumulationAbsenceDetail occurExpected = new AccuVacationBuilder("1",
				new CompensatoryDayoffDate(false, Optional.empty()), OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD,
				"adda6a46-2cbe-48c8-85f8-c04ca554cccc")
						.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
								Optional.of(new AttendanceTime(480))))
						.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
								Optional.of(new AttendanceTime(360))))
						.build();

		assertDataHasTime(accdigest, accdigestExpected);

		assertDataHasTime(occur, occurExpected);
	}

	@Test
	// manager time = false, accdigest >= occur 振休
		/*
		 * 　テストしたい内容
		 *         振休
		 *     　未相殺数を更新しない
		 * 
		 * 　準備するデータ
		 * 　　「逐次発生の休暇明細」(消化)．未相殺数> 「逐次発生の休暇明細」(発生)．未相殺数
		 *           逐次発生休暇設定.時間管理区分 = false
		 * */
	public void test5() {

		TimeLapseVacationSetting setting = new TimeLapseVacationSetting(
				new DatePeriod(GeneralDate.ymd(2019, 4, 1), GeneralDate.ymd(2019, 4, 30)), true, 2, true,
				Optional.of(false), Optional.of(5));

		AccumulationAbsenceDetail accdigest = new UnbalanceCompensation(
				new AccuVacationBuilder("1",
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554bbbb")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(480))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(120))))
								.build(),
				GeneralDate.ymd(2019, 12, 8), DigestionAtr.UNUSED, Optional.of(GeneralDate.ymd(2019, 12, 30)),
				StatutoryAtr.PUBLIC);

		AccumulationAbsenceDetail occur = new AccuVacationBuilder("1",
				new CompensatoryDayoffDate(false, Optional.empty()), OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD,
				"adda6a46-2cbe-48c8-85f8-c04ca554cccc")
						.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
								Optional.of(new AttendanceTime(480))))
						.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
								Optional.of(new AttendanceTime(120))))
						.build();

		UpdateUnbalancedNumber.updateUnbalanced(setting, accdigest, occur, TypeOffsetJudgment.ABSENCE);

		AccumulationAbsenceDetail accdigestExpected = new UnbalanceCompensation(
				new AccuVacationBuilder("1",
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554bbbb")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(480))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(120))))
								.build(),
				GeneralDate.ymd(2019, 12, 8), DigestionAtr.UNUSED, Optional.of(GeneralDate.ymd(2019, 12, 30)),
				StatutoryAtr.PUBLIC);

		AccumulationAbsenceDetail occurExpected = new AccuVacationBuilder("1",
				new CompensatoryDayoffDate(false, Optional.empty()), OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD,
				"adda6a46-2cbe-48c8-85f8-c04ca554cccc")
						.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
								Optional.of(new AttendanceTime(480))))
						.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
								Optional.of(new AttendanceTime(120))))
						.build();

		assertDataNoTime(accdigest, accdigestExpected);

		assertDataNoTime(occur, occurExpected);
	}

	@Test
	// manager time = false, accdigest < occur
	/*
	 * 　テストしたい内容
	 *         振休
	 *     　未相殺数を更新しない
	 * 
	 * 　準備するデータ
	 * 　　「逐次発生の休暇明細」(消化)．未相殺数< 「逐次発生の休暇明細」(発生)．未相殺数
	 *           逐次発生休暇設定.時間管理区分 = false
	 * */
	public void test6() {

		TimeLapseVacationSetting setting = new TimeLapseVacationSetting(
				new DatePeriod(GeneralDate.ymd(2019, 4, 1), GeneralDate.ymd(2019, 4, 30)), true, 2, true,
				Optional.of(false), Optional.of(5));

		AccumulationAbsenceDetail accdigest = new UnbalanceVacation(GeneralDate.ymd(2019, 12, 8), DigestionAtr.UNUSED,
				Optional.of(GeneralDate.ymd(2019, 12, 30)),
				new AccuVacationBuilder("1",
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554bbbb")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(480))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.5),
										Optional.of(new AttendanceTime(120))))
								.build(),
				new AttendanceTime(0), new AttendanceTime(0));

		AccumulationAbsenceDetail occur = new AccuVacationBuilder("1",
				new CompensatoryDayoffDate(false, Optional.empty()), OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD,
				"adda6a46-2cbe-48c8-85f8-c04ca554cccc")
						.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
								Optional.of(new AttendanceTime(480))))
						.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
								Optional.of(new AttendanceTime(120))))
						.build();

		UpdateUnbalancedNumber.updateUnbalanced(setting, accdigest, occur, TypeOffsetJudgment.REAMAIN);

		AccumulationAbsenceDetail accdigestExpected = new UnbalanceVacation(GeneralDate.ymd(2019, 12, 8),
				DigestionAtr.UNUSED, Optional.of(GeneralDate.ymd(2019, 12, 30)),
				new AccuVacationBuilder("1",
						new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 4))),
						OccurrenceDigClass.OCCURRENCE, MngDataStatus.RECORD, "adda6a46-2cbe-48c8-85f8-c04ca554bbbb")
								.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
										Optional.of(new AttendanceTime(480))))
								.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
										Optional.of(new AttendanceTime(120))))
								.build(),
				new AttendanceTime(0), new AttendanceTime(0));

		AccumulationAbsenceDetail occurExpected = new AccuVacationBuilder("1",
				new CompensatoryDayoffDate(false, Optional.empty()), OccurrenceDigClass.DIGESTION, MngDataStatus.RECORD,
				"adda6a46-2cbe-48c8-85f8-c04ca554cccc")
						.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(1.0),
								Optional.of(new AttendanceTime(480))))
						.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.5),
								Optional.of(new AttendanceTime(120))))
						.build();

		assertDataNoTime(accdigest, accdigestExpected);

		assertDataNoTime(occur, occurExpected);
	}

	private void assertDataNoTime(AccumulationAbsenceDetail actual, AccumulationAbsenceDetail expected) {

		assertThat(expected.getUnbalanceNumber().getDay()).isEqualTo(actual.getUnbalanceNumber().getDay());
	}

	private void assertDataHasTime(AccumulationAbsenceDetail actual, AccumulationAbsenceDetail expected) {

		assertThat(expected.getUnbalanceNumber().getDay()).isEqualTo(actual.getUnbalanceNumber().getDay());

		assertThat(expected.getUnbalanceNumber().getTime().get())
				.isEqualTo(actual.getUnbalanceNumber().getTime().get());
	}

}
