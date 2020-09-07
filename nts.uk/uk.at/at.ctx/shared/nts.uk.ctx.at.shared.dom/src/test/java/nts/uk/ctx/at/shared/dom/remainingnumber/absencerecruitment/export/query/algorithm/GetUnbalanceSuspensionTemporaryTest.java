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
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.AbsRecMngInPeriodRefactParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.DaikyuFurikyuHelper;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.DataManagementAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.SelectedAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;

@RunWith(JMockit.class)
public class GetUnbalanceSuspensionTemporaryTest {

	private static String SID = "s1";

	@Injectable
	private GetUnbalanceSuspensionTemporary.Require require;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	/*
	 * テストしたい内容 
	 * 暫定データから消化データを作成する
	 * 
	 * 準備するデータ 
	 * 　暫定振休管理データがある 
	 * →紐づけがなくて残ってるやつ 
	 * →紐づけしても残ってるやつ 
	 * 　暫定振出振休紐付け管理がある 
	 * →最初から残ってない
	 * →紐づけしたら残ってない 
	 * モード : 月次か
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void test() {

		List<InterimAbsMng> useAbsMng = Arrays.asList(
				DaikyuFurikyuHelper.createAbsMng("a1", 1.0),//必要日数
				DaikyuFurikyuHelper.createAbsMng("a3", 1.0),//必要日数
				DaikyuFurikyuHelper.createAbsMng("a4", 0.0));//必要日数

		List<InterimRecMng> useRecMng = Arrays
				.asList(DaikyuFurikyuHelper.createRecUseMng("a5", 
						GeneralDate.max(), //使用期限日
						1.0));//未使用日数

		List<InterimRemain> interimMng = Arrays.asList(
				DaikyuFurikyuHelper.createRemain("a1",
						GeneralDate.ymd(2019, 11, 4),//対象日
						CreateAtr.SCHEDULE, //作成元区分
						RemainType.PAUSE),//残数種類
				DaikyuFurikyuHelper.createRemain("a3", 
						GeneralDate.ymd(2019, 11, 7),//対象日
						CreateAtr.RECORD, //作成元区分
						RemainType.PAUSE),//残数種類
				DaikyuFurikyuHelper.createRemain("a4", 
						GeneralDate.ymd(2019, 11, 8),//対象日
						CreateAtr.RECORD,  //作成元区分
						RemainType.PAUSE),//残数種類

				DaikyuFurikyuHelper.createRemain("a5",
						GeneralDate.ymd(2019, 11, 5),//対象日
						CreateAtr.SCHEDULE,  //作成元区分
						RemainType.PICKINGUP));//残数種類

		AbsRecMngInPeriodRefactParamInput inputParam = DaikyuFurikyuHelper.createAbsRecInput(
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)),//集計開始日, 集計終了日 
				GeneralDate.ymd(2019, 11, 30), //画面表示日
				true, //モード 
				false, // 上書きフラグ
				useAbsMng, interimMng, useRecMng);//暫定管理データ
		new Expectations() {
			{

				require.getRecOrAbsMngs((List<String>) any, false, DataManagementAtr.INTERIM);

				result = Arrays.asList(createRecAbs("a1", 1.0), //使用日数
						createRecAbs("aa9", 1.0));//使用日数

			}

		};

		List<AccumulationAbsenceDetail> actualResult = GetUnbalanceSuspensionTemporary.process(require, inputParam);

		assertThat(actualResult)
				.extracting(x -> x.getManageId(), x -> x.getDateOccur().isUnknownDate(),
						x -> x.getDateOccur().getDayoffDate(), x -> x.getOccurrentClass(),
						x -> x.getUnbalanceNumber().getDay().v())
				.containsExactly(
						Tuple.tuple("a1", false, Optional.of(GeneralDate.ymd(2019, 11, 4)), 
								OccurrenceDigClass.DIGESTION, 0.0),
						Tuple.tuple("a3", false, Optional.of(GeneralDate.ymd(2019, 11, 7)), 
								OccurrenceDigClass.DIGESTION, 1.0),
						Tuple.tuple("a4", false, Optional.of(GeneralDate.ymd(2019, 11, 8)), 
								OccurrenceDigClass.DIGESTION, 0.0));

	}

	/*
	 * テストしたい内容 
	 * 暫定データから消化データを作成する
	 * 
	 * 準備するデータ 
	 * 　暫定振休管理データがある 
	 * →紐づけがなくて残ってるやつ 
	 * →紐づけしても残ってるやつ 
	 * 　暫定振出振休紐付け管理がある 
	 * →最初から残ってない
	 * →紐づけしたら残ってない 
	 * モード : その他
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testOther() {

		AbsRecMngInPeriodRefactParamInput inputParam = DaikyuFurikyuHelper.createAbsRecInput(
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)),//集計開始日, 集計終了日 
				GeneralDate.ymd(2019, 11, 30), //画面表示日
				false, //モード 
				false, // 上書きフラグ
				new ArrayList<>(),  new ArrayList<>(),  new ArrayList<>());//暫定管理データ
		new Expectations() {
			{
				
				require.getRemainBySidPriod(SID, (DatePeriod) any, RemainType.PAUSE);
				result = Arrays.asList(
						DaikyuFurikyuHelper.createRemain("a1", 
								GeneralDate.ymd(2019, 11, 4),//対象日
								CreateAtr.SCHEDULE, //作成元区分
								RemainType.PAUSE),//残数種類
						DaikyuFurikyuHelper.createRemain("a3", 
								GeneralDate.ymd(2019, 11, 7),//対象日
								CreateAtr.RECORD, //作成元区分
								RemainType.PAUSE),//残数種類
						DaikyuFurikyuHelper.createRemain("a4", 
								GeneralDate.ymd(2019, 11, 8),//対象日
								CreateAtr.RECORD, //作成元区分
								RemainType.PAUSE));//残数種類

				require.getAbsBySidDatePeriod(SID, (DatePeriod) any);
				result = Arrays.asList(DaikyuFurikyuHelper.createAbsMng("a1", 1.0),//必要日数
						DaikyuFurikyuHelper.createAbsMng("a3", 1.0), //必要日数
						DaikyuFurikyuHelper.createAbsMng("a4", 0.0));//必要日数

				require.getRecOrAbsMngs((List<String>) any, false, DataManagementAtr.INTERIM);
				result = Arrays.asList(createRecAbs("a1", 1.0), //使用日数
						createRecAbs("88", 1.0));//使用日数

			}

		};

		List<AccumulationAbsenceDetail> actualResult = GetUnbalanceSuspensionTemporary.process(require, inputParam);

		assertThat(actualResult)
				.extracting(x -> x.getManageId(), x -> x.getDataAtr(), x -> x.getDateOccur().isUnknownDate(),
						x -> x.getDateOccur().getDayoffDate(), x -> x.getUnbalanceNumber().getDay().v())
				.containsExactly(
						Tuple.tuple("a1", MngDataStatus.SCHEDULE, false, Optional.of(GeneralDate.ymd(2019, 11, 4)),
								0.0),
						Tuple.tuple("a3", MngDataStatus.RECORD, false, Optional.of(GeneralDate.ymd(2019, 11, 7)), 1.0),
						Tuple.tuple("a4", MngDataStatus.RECORD, false, Optional.of(GeneralDate.ymd(2019, 11, 8)), 0.0));

	}

	private InterimRecAbsMng createRecAbs(String id, Double useDay) {
		return new InterimRecAbsMng(id,
				DataManagementAtr.INTERIM, "", DataManagementAtr.INTERIM, new UseDay(useDay), SelectedAtr.MANUAL);
	}
}
