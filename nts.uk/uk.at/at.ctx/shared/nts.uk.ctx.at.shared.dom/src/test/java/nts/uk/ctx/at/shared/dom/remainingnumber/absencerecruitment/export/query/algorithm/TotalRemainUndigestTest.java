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
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsDaysRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.DaikyuFurikyuHelper;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;

@RunWith(JMockit.class)
public class TotalRemainUndigestTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	
	/*
	 * 　テストしたい内容
	 * 　　残数と未消化数を集計する、ーーTinh so ngay nghi con lai, so ngay het han
	 * 　　期限切れの場合、相殺済みできません、未消化時間 に追加
	 *         ーーKhong the bu trong khi qua han、thêm vào thoi gian qua han
	 * 
	 * 　準備するデータ
	 * 　　　休出日が期限切れです
	 * 　　　　
	 * */
	@Test
	public void test() {

		List<AccumulationAbsenceDetail> lstAccDetail = Arrays.asList(
				DaikyuFurikyuHelper.createDetailDefault(false, //振休
						OccurrenceDigClass.DIGESTION,//発生消化区分
						Optional.of(GeneralDate.ymd(2019, 11, 3)), //年月日
						"a1", 
						null,//期限日
						1.0, 0),//未相殺数
				DaikyuFurikyuHelper.createDetailDefault(false, //振休
						OccurrenceDigClass.DIGESTION,//発生消化区分
						Optional.of(GeneralDate.ymd(2019, 4, 11)), //年月日
						"a2", 
						null,//期限日
						1.0, 0),
				DaikyuFurikyuHelper.createDetailDefault(false, //振休
						OccurrenceDigClass.DIGESTION,//発生消化区分
						Optional.of(GeneralDate.ymd(2019, 11, 4)), //年月日
						"a3", 
						null,//期限日
						1.0, 0),
				DaikyuFurikyuHelper.createDetailDefault(false, //振休
						OccurrenceDigClass.OCCURRENCE,//発生消化区分
						Optional.of(GeneralDate.ymd(2019, 11, 14)), //年月日
						"a4", 
						GeneralDate.ymd(2019, 12, 30),//期限日
						1.0, 0),
				DaikyuFurikyuHelper.createDetailDefault(false, //代休
						OccurrenceDigClass.OCCURRENCE,//発生消化区分
						Optional.of(GeneralDate.ymd(2019, 10, 16)), //年月日
						"a5", 
						GeneralDate.ymd(2019, 10, 30),//期限日
						1.0, 0)
		);
		
		AbsDaysRemain resultActual = TotalRemainUndigest.process(lstAccDetail, GeneralDate.ymd(2019, 11, 1), false);

		assertThat(resultActual.getRemainDays()).isEqualTo(-2.0);
		assertThat(resultActual.getUnDigestedDays()).isEqualTo(1.0);
	}

}
