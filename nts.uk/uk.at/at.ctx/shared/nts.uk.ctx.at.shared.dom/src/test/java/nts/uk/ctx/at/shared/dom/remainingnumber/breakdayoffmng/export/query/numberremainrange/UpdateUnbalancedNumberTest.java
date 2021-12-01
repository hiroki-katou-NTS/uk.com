package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;

public class UpdateUnbalancedNumberTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	/*
	 * 　テストしたい内容
	 *     　未相殺数を更新する
	 * 
	 * 　準備するデータ
	 * 　　「逐次発生の休暇明細」(消化)．未相殺数>= 「逐次発生の休暇明細」(発生)．未相殺数
	 *           逐次発生休暇設定.時間管理区分 = false
	 * */
	@Test
	// manager time = false, accdigest >= occur
	public void test() {

		
		AccumulationAbsenceDetail accdigest = DaikyuFurikyuHelper.createDetailDefaultUnba(true, // 代休
				OccurrenceDigClass.DIGESTION, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 11)), // 年月日
				"a5", 1.0, 120);// 未相殺数

		AccumulationAbsenceDetail occur = DaikyuFurikyuHelper.createDetailDefault(true, // 代休
				OccurrenceDigClass.OCCURRENCE, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 4)), // 年月日
				"a6", GeneralDate.ymd(2019, 12, 8), //期限日
				1.0, 120);// 未相殺数
		
		UpdateUnbalancedNumber.updateUnbalanced(false, accdigest, occur, TypeOffsetJudgment.REAMAIN);

		AccumulationAbsenceDetail accdigestExpected = DaikyuFurikyuHelper.createDetailDefaultUnba(true, // 代休
				OccurrenceDigClass.DIGESTION, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 11)), // 年月日
				"a5", 0.0, 0);// 未相殺数

		AccumulationAbsenceDetail occurExpected = DaikyuFurikyuHelper.createDetailDefault(true, // 代休
				OccurrenceDigClass.OCCURRENCE, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 4)), // 年月日
				"a6", GeneralDate.ymd(2019, 12, 8), //期限日
				0.0, 0);// 未相殺数
		
		assertDataNoTime(accdigest, accdigestExpected);

		assertDataNoTime(occur, occurExpected);
	}

	@Test
	// manager time = false, accdigest < occur
	/*
	 * 　テストしたい内容
	 *     　未相殺数を更新する
	 * 
	 * 　準備するデータ
	 * 　　「逐次発生の休暇明細」(消化)．未相殺数< 「逐次発生の休暇明細」(発生)．未相殺数
	 *           逐次発生休暇設定.時間管理区分 = false
	 * */
	public void test2() {

		AccumulationAbsenceDetail accdigest = DaikyuFurikyuHelper.createDetailDefaultUnba(true, // 代休
				OccurrenceDigClass.DIGESTION, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 11)), // 年月日
				"a5", 0.5, 120);// 未相殺数

		AccumulationAbsenceDetail occur = DaikyuFurikyuHelper.createDetailDefault(true, // 代休
				OccurrenceDigClass.OCCURRENCE, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 4)), // 年月日
				"a6", GeneralDate.ymd(2019, 12, 8), //期限日
				1.0, 120);// 未相殺数
		
		UpdateUnbalancedNumber.updateUnbalanced(false, accdigest, occur, TypeOffsetJudgment.REAMAIN);

		AccumulationAbsenceDetail accdigestExpected = DaikyuFurikyuHelper.createDetailDefaultUnba(true, // 代休
				OccurrenceDigClass.DIGESTION, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 11)), // 年月日
				"a5", 0.0, 0);// 未相殺数

		AccumulationAbsenceDetail occurExpected = DaikyuFurikyuHelper.createDetailDefault(true, // 代休
				OccurrenceDigClass.OCCURRENCE, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 4)), // 年月日
				"a6", GeneralDate.ymd(2019, 12, 8), //期限日
				0.5, 0);// 未相殺数
		
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

		AccumulationAbsenceDetail accdigest = DaikyuFurikyuHelper.createDetailDefaultUnba(true, // 代休
				OccurrenceDigClass.DIGESTION, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 11)), // 年月日
				"a5", 1.0, 480);// 未相殺数

		AccumulationAbsenceDetail occur = DaikyuFurikyuHelper.createDetailDefault(true, // 代休
				OccurrenceDigClass.OCCURRENCE, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 4)), // 年月日
				"a6", GeneralDate.ymd(2019, 12, 8), //期限日
				1.0, 120);// 未相殺数
		
		UpdateUnbalancedNumber.updateUnbalanced(true, accdigest, occur, TypeOffsetJudgment.REAMAIN);

		AccumulationAbsenceDetail accdigestExpected = DaikyuFurikyuHelper.createDetailDefaultUnba(true, // 代休
				OccurrenceDigClass.DIGESTION, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 11)), // 年月日
				"a5", 1.0, 360);// 未相殺数

		AccumulationAbsenceDetail occurExpected = DaikyuFurikyuHelper.createDetailDefault(true, // 代休
				OccurrenceDigClass.OCCURRENCE, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 4)), // 年月日
				"a6", GeneralDate.ymd(2019, 12, 8), //期限日
				1.0, 0);// 未相殺数

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

		AccumulationAbsenceDetail accdigest = DaikyuFurikyuHelper.createDetailDefaultUnba(true, // 代休
				OccurrenceDigClass.DIGESTION, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 11)), // 年月日
				"a5", 1.0, 120);// 未相殺数

		AccumulationAbsenceDetail occur = DaikyuFurikyuHelper.createDetailDefault(true, // 代休
				OccurrenceDigClass.OCCURRENCE, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 4)), // 年月日
				"a6", GeneralDate.ymd(2019, 12, 8), //期限日
				1.0, 480);// 未相殺数
		
		UpdateUnbalancedNumber.updateUnbalanced(true, accdigest, occur, TypeOffsetJudgment.REAMAIN);

		AccumulationAbsenceDetail accdigestExpected = DaikyuFurikyuHelper.createDetailDefaultUnba(true, // 代休
				OccurrenceDigClass.DIGESTION, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 11)), // 年月日
				"a5", 1.0, 0);// 未相殺数

		AccumulationAbsenceDetail occurExpected = DaikyuFurikyuHelper.createDetailDefault(true, // 代休
				OccurrenceDigClass.OCCURRENCE, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 4)), // 年月日
				"a6", GeneralDate.ymd(2019, 12, 8), //期限日
				1.0, 360);// 未相殺数
		
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

		AccumulationAbsenceDetail accdigest = DaikyuFurikyuHelper.createDetailDefaultUnba(true, // 代休
				OccurrenceDigClass.DIGESTION, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 11)), // 年月日
				"a5", 1.0, 480);// 未相殺数

		AccumulationAbsenceDetail occur = DaikyuFurikyuHelper.createDetailDefault(true, // 代休
				OccurrenceDigClass.OCCURRENCE, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 4)), // 年月日
				"a6", GeneralDate.ymd(2019, 12, 8), //期限日
				0.5, 120);// 未相殺数
		
		UpdateUnbalancedNumber.updateUnbalanced(false, accdigest, occur, TypeOffsetJudgment.ABSENCE);
		AccumulationAbsenceDetail accdigestExpected = DaikyuFurikyuHelper.createDetailDefaultUnba(true, // 代休
				OccurrenceDigClass.DIGESTION, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 11)), // 年月日
				"a5", 0.5, 480);// 未相殺数

		AccumulationAbsenceDetail occurExpected = DaikyuFurikyuHelper.createDetailDefault(true, // 代休
				OccurrenceDigClass.OCCURRENCE, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 4)), // 年月日
				"a6", GeneralDate.ymd(2019, 12, 8), //期限日
				0.0, 120);// 未相殺数
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

		AccumulationAbsenceDetail accdigest = DaikyuFurikyuHelper.createDetailDefaultUnba(true, // 代休
				OccurrenceDigClass.DIGESTION, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 11)), // 年月日
				"a5", 0.5, 480);// 未相殺数

		AccumulationAbsenceDetail occur = DaikyuFurikyuHelper.createDetailDefault(true, // 代休
				OccurrenceDigClass.OCCURRENCE, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 4)), // 年月日
				"a6", GeneralDate.ymd(2019, 12, 8), //期限日
				1.0, 120);// 未相殺数

		UpdateUnbalancedNumber.updateUnbalanced(false, accdigest, occur, TypeOffsetJudgment.REAMAIN);

		AccumulationAbsenceDetail accdigestExpected = DaikyuFurikyuHelper.createDetailDefaultUnba(true, // 代休
				OccurrenceDigClass.DIGESTION, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 11)), // 年月日
				"a5", 0.0, 480);// 未相殺数

		AccumulationAbsenceDetail occurExpected = DaikyuFurikyuHelper.createDetailDefault(true, // 代休
				OccurrenceDigClass.OCCURRENCE, // 発生消化区分
				Optional.of(GeneralDate.ymd(2019, 11, 4)), // 年月日
				"a6", GeneralDate.ymd(2019, 12, 8), //期限日
				0.5, 120);// 未相殺数
		
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
