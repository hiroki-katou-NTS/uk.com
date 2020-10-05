package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

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
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.RemainUnDigestedDayTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;

@RunWith(JMockit.class)
public class CalcNumberOccurUsesTest {


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	
	/*
	 * テストしたい内容
	 *　　 逐次発生の休暇明細から「残時間 , 残日数、未消化時間、未消化日数 」を計算する
	 * 準備するデータ
	 * 　　  逐次発生の休暇明細がある
	 */
	@Test
	public void testProcess() {

		List<AccumulationAbsenceDetail> lstAccDetail = Arrays.asList(DaikyuFurikyuHelper.createDetailDefault(true, // 代休
				OccurrenceDigClass.DIGESTION, // 消化
				Optional.of(GeneralDate.ymd(2019, 11, 3)), // 年月日
				"d1", // 残数管理データID
				1.0, 0// 発生数
		),DaikyuFurikyuHelper.createDetailDefault(true, // 代休
				OccurrenceDigClass.DIGESTION, // 消化
				Optional.of(GeneralDate.ymd(2019, 04, 11)), // 年月日
				"d2", // 残数管理データID
				1.0, 0// 発生数
		), DaikyuFurikyuHelper.createDetailDefault(true, // 代休
				OccurrenceDigClass.OCCURRENCE, // 発生
				Optional.of(GeneralDate.ymd(2019, 11, 4)), // 年月日
				"k1", // 残数管理データID
				1.0, 0// 発生数
		), DaikyuFurikyuHelper.createDetailDefault(true, // 代休
				OccurrenceDigClass.OCCURRENCE, // 発生
				Optional.of(GeneralDate.ymd(2019, 11, 14)), // 年月日
				"k2", // 残数管理データID
				1.0, 0// 発生数
		));
		
		RemainUnDigestedDayTimes actualResult = CalcNumberOccurUses.process(lstAccDetail,
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)));

		assertThat(actualResult.getRemainDays()).isEqualTo(2.0);
		assertThat(actualResult.getRemainTimes()).isEqualTo(0);
		assertThat(actualResult.getUnDigestedDays()).isEqualTo(1.0);
		assertThat(actualResult.getUnDigestedTimes()).isEqualTo(0);
	}

}
