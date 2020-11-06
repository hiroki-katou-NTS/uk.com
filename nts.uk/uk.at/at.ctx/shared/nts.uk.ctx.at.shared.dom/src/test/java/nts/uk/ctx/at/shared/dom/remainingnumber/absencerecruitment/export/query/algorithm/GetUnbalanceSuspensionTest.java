package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.groups.Tuple;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataDaysAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.DaikyuFurikyuHelper;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;

@RunWith(JMockit.class)
public class GetUnbalanceSuspensionTest {

	private static String CID = "000000000000-0117";

	private static String SID = "292ae91c-508c-4c6e-8fe8-3e72277dec16";

	@Injectable
	private GetUnbalanceSuspension.Require require;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	/*
	 * 　テストしたい内容
	 * 　　逐次発生の休暇明細データを作成しない
	 * 
	 * 　準備するデータ
	 * 　　確定データがない
	 * 　　　
	 * */
	@Test
	public void testEmpty() {
		List<AccumulationAbsenceDetail> actualResult = GetUnbalanceSuspension.process(require, CID, SID,
				GeneralDate.ymd(2019, 4, 1), new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));

		assertThat(actualResult).isEqualTo(new ArrayList<>());
	}

	/*
	 * 　テストしたい内容
	 * 　　未相殺のデータだけが取得できるか。
	 * 
	 * 　準備するデータ
	 * 　　未相殺のデータ
	 * 　　　→紐づけがなくて残ってるやつ
	 * 　　　→紐づけしても残ってるやつ
	 * 　　相殺済みのデータ
	 * 　　　→最初から残ってない
	 * 　　　→紐づけしたら残ってない

	 * */
	@Test
	public void testUnbalanceUnused() {

		new Expectations() {
			{

				require.getByYmdUnOffset(CID, SID, (GeneralDate) any, 0);
				result = Arrays.asList(createSubOfHD("a1", 
						GeneralDate.ymd(2019, 11, 30), // 振休日
						1.0),// 未相殺日数
						createSubOfHD("a4", 
								GeneralDate.ymd(2019, 11, 10), // 振休日
								0.0),// 未相殺日数
						createSubOfHD("a2", 
								GeneralDate.ymd(2019, 11, 20), // 振休日
								1.0));// 未相殺日数

//				require.getBySidMng(DataManagementAtr.INTERIM, DataManagementAtr.CONFIRM,
//						"a1");
//				result = Arrays.asList(
//						createRecAbs("a1",1.0));//使用日数
//
//				require.getBySidMng(DataManagementAtr.INTERIM, DataManagementAtr.CONFIRM,
//						"a2");
//				result = Arrays.asList(
//						createRecAbs("a2",0.5));//使用日数
				
				require.getBySubId(SID, GeneralDate.ymd(2019, 11, 30));
				result = Arrays.asList(DaikyuFurikyuHelper.createHD(GeneralDate.ymd(2019, 11, 03), GeneralDate.ymd(2019, 11, 30), 1.0));
				
				require.getBySubId(SID, GeneralDate.ymd(2019, 11, 20));
				result = Arrays.asList(DaikyuFurikyuHelper.createHD(GeneralDate.ymd(2019, 11, 03), GeneralDate.ymd(2019, 11, 20), 0.5));

			}
		};

		List<SubstitutionOfHDManagementData> subOfHd = new ArrayList<>();
		subOfHd.add(createSubOfHD("a4", null, 1.0));
		List<AccumulationAbsenceDetail> actualResult = GetUnbalanceSuspension.process(require, CID, SID,
				GeneralDate.ymd(2019, 11, 1),
				new FixedManagementDataMonth(new ArrayList<>(), subOfHd));

		assertThat(actualResult).extracting(x -> x.getManageId(), x -> x.getDataAtr(), // 状態
				x -> x.getDateOccur().isUnknownDate(), x -> x.getDateOccur().getDayoffDate(), // 年月日
				x -> x.getOccurrentClass(), // 発生消化区分
				x -> x.getUnbalanceNumber().getDay().v())// 未相殺数
				.containsExactly(
						Tuple.tuple("a2", MngDataStatus.CONFIRMED, false, Optional.of(GeneralDate.ymd(2019, 11, 20)),
								OccurrenceDigClass.DIGESTION, 0.5),
						Tuple.tuple("a4", MngDataStatus.CONFIRMED, true, Optional.empty(), OccurrenceDigClass.DIGESTION,
								1.0));
	}
	
	private SubstitutionOfHDManagementData createSubOfHD(String id, GeneralDate date, Double remainDay) {
		
		return new SubstitutionOfHDManagementData(id, CID, SID,
				new CompensatoryDayoffDate(date == null, Optional.ofNullable(date)),
				new ManagementDataDaysAtr(1.0), new ManagementDataRemainUnit(remainDay));
	}
	
}
