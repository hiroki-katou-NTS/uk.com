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
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.CompenSuspensionAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.DaikyuFurikyuHelper;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;

@RunWith(JMockit.class)
public class CalcCompenNumberOccurUsesTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {

		List<AccumulationAbsenceDetail> lstAccDetail = Arrays.asList(DaikyuFurikyuHelper.createDetailDefault(false, // 振休
				OccurrenceDigClass.DIGESTION, // 消化
				Optional.of(GeneralDate.ymd(2019, 11, 3)), // 年月日
				"a1", // 残数管理データID
				1.0, 0// 発生数
		), DaikyuFurikyuHelper.createDetailDefault(false, // 振休
				OccurrenceDigClass.DIGESTION, // 消化
				Optional.of(GeneralDate.ymd(2019, 04, 11)), // 年月日
				"a2", // 残数管理データID
				1.0, 0// 発生数
		), DaikyuFurikyuHelper.createDetailDefault(false, // 振休
				OccurrenceDigClass.OCCURRENCE, // 発生
				Optional.of(GeneralDate.ymd(2019, 11, 4)), // 年月日
				"a3", // 残数管理データID
				1.0, 0// 発生数
		), DaikyuFurikyuHelper.createDetailDefault(false, // 振休
				OccurrenceDigClass.OCCURRENCE, // 発生
				Optional.of(GeneralDate.ymd(2019, 11, 14)), // 年月日
				"a4", // 残数管理データID
				1.0, 0// 発生数
		));
		
		CompenSuspensionAggrResult resultActual = CalcCompenNumberOccurUses.calc(lstAccDetail,
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2019, 11, 30)));

		assertThat(resultActual.getSuOccurDay()).isEqualTo(2.0);

		assertThat(resultActual.getSuDayUse()).isEqualTo(1.0);
	}

}
