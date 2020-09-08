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
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;

public class AccumulationAbsenceDetailComparatorTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	/*
	 * テストしたい内容
	 * 
	 * 「逐次発生の休暇明細」ソート
	 * 
	 * 日付不明 → 一番
	 * 
	 * 日が大きい → 下に配置
	 * 
	 * 準備するデータ
	 * 
	 * 逐次発生の休暇明細
	 * 
	 * 日付不明と日付ありの順番がバラバラ
	 * 
	 * 代休と休出が両方あり
	 * 
	 */
	@Test
	public void testCompare() {

		List<AccumulationAbsenceDetail> lstAccDetail = Arrays
				.asList(DaikyuFurikyuHelper.createDetailDefault(OccurrenceDigClass.DIGESTION, // 消化
						Optional.of(GeneralDate.ymd(2019, 11, 11)), // 年月日
						"d6"// 残数管理データID
				), DaikyuFurikyuHelper.createDetailDefault(OccurrenceDigClass.DIGESTION, // 消化
						Optional.of(GeneralDate.ymd(2019, 11, 4)), // 年月日
						"d2" // 残数管理データID
				), DaikyuFurikyuHelper.createDetailDefault(OccurrenceDigClass.DIGESTION, // 消化
						Optional.empty(), // 年月日
						"d3"// 残数管理データID
				), DaikyuFurikyuHelper.createDetailDefault(OccurrenceDigClass.DIGESTION, // 消化
						Optional.of(GeneralDate.ymd(2019, 11, 9)), // 年月日
						"d1" // 残数管理データID
				), DaikyuFurikyuHelper.createDetailDefault(OccurrenceDigClass.DIGESTION, // 消化
						Optional.empty(), // 年月日
						"d4" // 残数管理データID

				), DaikyuFurikyuHelper.createDetailDefault(OccurrenceDigClass.DIGESTION, // 消化
						Optional.of(GeneralDate.ymd(2019, 11, 10)), // 年月日
						"d5" // 残数管理データID

				));

		lstAccDetail.sort(new AccumulationAbsenceDetailComparator());

		assertThat(lstAccDetail).extracting(x -> x.getManageId(), x -> x.getDateOccur().isUnknownDate(),
				x -> x.getDateOccur().getDayoffDate(), x -> x.getOccurrentClass()).containsExactly(

						Tuple.tuple("d3", true, Optional.empty(), OccurrenceDigClass.DIGESTION),

						Tuple.tuple("d4", true, Optional.empty(), OccurrenceDigClass.DIGESTION),

						Tuple.tuple("d2", false, Optional.of(GeneralDate.ymd(2019, 11, 4)),
								OccurrenceDigClass.DIGESTION),

						Tuple.tuple("d1", false, Optional.of(GeneralDate.ymd(2019, 11, 9)),
								OccurrenceDigClass.DIGESTION),

						Tuple.tuple("d5", false, Optional.of(GeneralDate.ymd(2019, 11, 10)),
								OccurrenceDigClass.DIGESTION),

						Tuple.tuple("d6", false, Optional.of(GeneralDate.ymd(2019, 11, 11)),
								OccurrenceDigClass.DIGESTION));
	}

}
