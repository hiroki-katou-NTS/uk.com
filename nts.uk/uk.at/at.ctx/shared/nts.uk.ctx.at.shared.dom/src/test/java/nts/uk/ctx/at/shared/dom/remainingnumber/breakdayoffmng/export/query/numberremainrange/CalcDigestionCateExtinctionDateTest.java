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
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.UnbalanceCompensation;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.UnbalanceVacation;

public class CalcDigestionCateExtinctionDateTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	/*
	 * テストしたい内容
	 * 
	 * 代休 消滅日を計算する
	 * 
	 * 準備するデータ
	 * 
	 * すべての逐次発生の休暇明細（発生）が0
	 * 
	 * → 消化済 期限が超えない→ 未消化 期限が超える→ 消滅
	 * 
	 */
	@Test
	public void testDaikyu() {

		List<AccumulationAbsenceDetail> lstAccDetail = Arrays.asList(DaikyuFurikyuHelper.createDetailDefault(true, // 代休
				OccurrenceDigClass.OCCURRENCE, // 発生
				Optional.of(GeneralDate.ymd(2019, 12, 30)), // 年月日
				"k1", // 残数管理データID
				DigestionAtr.UNUSED, // 消化区分
				GeneralDate.ymd(2019, 12, 30), // 期限日
				0.0, 0// 未相殺
		), DaikyuFurikyuHelper.createDetailDefault(true, // 代休
				OccurrenceDigClass.OCCURRENCE, // 発生
				Optional.empty(), // 年月日
				"k2", // 残数管理データID
				DigestionAtr.UNUSED, // 消化区分
				GeneralDate.ymd(2019, 12, 30), // 期限日
				1.0, 0// 未相殺
		), DaikyuFurikyuHelper.createDetailDefault(true, // 代休
				OccurrenceDigClass.OCCURRENCE, // 発生
				Optional.of(GeneralDate.ymd(2019, 04, 10)), // 年月日
				"k3", // 残数管理データID
				DigestionAtr.UNUSED, // 消化区分
				GeneralDate.ymd(2019, 10, 30), // 期限日
				1.0, 10// 未相殺
		), DaikyuFurikyuHelper.createDetailDefault(true, // 代休
				OccurrenceDigClass.DIGESTION, // 消化
				Optional.of(GeneralDate.ymd(2019, 11, 4)), // 年月日
				"d1", // 残数管理データID
				DigestionAtr.UNUSED, // 消化区分
				null, // 期限日
				1.0, 0// 未相殺
		));

		CalcDigestionCateExtinctionDate.calc(lstAccDetail, GeneralDate.ymd(2019, 11, 3), TypeOffsetJudgment.REAMAIN);

		assertThat(lstAccDetail)
				.extracting(x -> x.getManageId(), x -> x.getOccurrentClass(),
						x -> x.getOccurrentClass() == OccurrenceDigClass.DIGESTION ? Optional.empty()
								: ((UnbalanceVacation) x).getDigestionCate())
				.containsExactly(Tuple.tuple("k1", OccurrenceDigClass.OCCURRENCE, DigestionAtr.USED),
						Tuple.tuple("k2", OccurrenceDigClass.OCCURRENCE, DigestionAtr.UNUSED),
						Tuple.tuple("k3", OccurrenceDigClass.OCCURRENCE, DigestionAtr.EXPIRED),
						Tuple.tuple("d1", OccurrenceDigClass.DIGESTION, Optional.empty()));

	}

	/*
	 * テストしたい内容
	 * 
	 * 振休 消化区分と消滅日を計算する
	 * 
	 * 準備するデータ
	 * 
	 * すべての逐次発生の休暇明細（発生）が0
	 * 
	 * → 消化済 期限が超えない→ 未消化 期限が超える→ 消滅
	 * 
	 */
	@Test
	public void testFurikyu() {
		List<AccumulationAbsenceDetail> lstAccDetail = Arrays.asList(DaikyuFurikyuHelper.createDetailDefault(false, // 振休
				OccurrenceDigClass.OCCURRENCE, // 発生
				Optional.of(GeneralDate.ymd(2019, 12, 30)), // 年月日
				"k1", // 残数管理データID
				DigestionAtr.UNUSED, // 消化区分
				GeneralDate.ymd(2019, 12, 30), // 期限日
				0.0, 0// 未相殺
		), DaikyuFurikyuHelper.createDetailDefault(false, // 振休
				OccurrenceDigClass.OCCURRENCE, // 発生
				Optional.empty(), // 年月日
				"k2", // 残数管理データID
				DigestionAtr.UNUSED, // 消化区分
				GeneralDate.ymd(2019, 12, 30), // 期限日
				1.0, 0// 未相殺
		), DaikyuFurikyuHelper.createDetailDefault(false, // 振休
				OccurrenceDigClass.OCCURRENCE, // 発生
				Optional.of(GeneralDate.ymd(2019, 04, 10)), // 年月日
				"k3", // 残数管理データID
				DigestionAtr.UNUSED, // 消化区分
				GeneralDate.ymd(2019, 10, 30), // 期限日
				1.0, 10// 未相殺
		), DaikyuFurikyuHelper.createDetailDefault(false, // 振休
				OccurrenceDigClass.DIGESTION, // 消化
				Optional.of(GeneralDate.ymd(2019, 11, 4)), // 年月日
				"d1", // 残数管理データID
				DigestionAtr.UNUSED, // 消化区分
				null, // 期限日
				1.0, 0// 未相殺
		));

		CalcDigestionCateExtinctionDate.calc(lstAccDetail, GeneralDate.ymd(2019, 11, 3), TypeOffsetJudgment.ABSENCE);

		assertThat(lstAccDetail)
				.extracting(x -> x.getManageId(), x -> x.getOccurrentClass(),
						x -> x.getOccurrentClass() == OccurrenceDigClass.DIGESTION ? Optional.empty()
								: ((UnbalanceCompensation) x).getDigestionCate())
				.containsExactly(Tuple.tuple("k1", OccurrenceDigClass.OCCURRENCE, DigestionAtr.USED),
						Tuple.tuple("k2", OccurrenceDigClass.OCCURRENCE, DigestionAtr.UNUSED),
						Tuple.tuple("k3", OccurrenceDigClass.OCCURRENCE, DigestionAtr.EXPIRED),
						Tuple.tuple("d1", OccurrenceDigClass.DIGESTION, Optional.empty()));
	}

}
