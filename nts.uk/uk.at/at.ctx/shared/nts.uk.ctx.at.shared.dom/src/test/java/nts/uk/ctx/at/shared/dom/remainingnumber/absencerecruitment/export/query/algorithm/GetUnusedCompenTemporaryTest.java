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
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.AbsRecMngInPeriodRefactParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.UnbalanceCompensation;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.DaikyuFurikyuHelper;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;

@RunWith(JMockit.class)
public class GetUnusedCompenTemporaryTest {

	private static String CID = "000000000000-0117";

	private static String SID = "292ae91c-508c-4c6e-8fe8-3e72277dec16";

	@Injectable
	private GetUnusedCompenTemporary.Require require;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	/*
	 * テストしたい内容 
	 * 暫定データから発生データを作成する
	 * 
	 * 準備するデータ 
	 * 暫定振出管理データがある 
	 * →紐づけがなくて残ってるやつ 
	 * →紐づけしても残ってるやつ 
	 * 暫定振出振休紐付け管理がある 
	 * →最初から残ってない
	 * →紐づけしたら残ってない 
	 * モード : 月次か
	 * 
	 */
	@Test
	public void testModeMonth() {
		List<InterimRecMng> useRecMng = Arrays.asList(
				DaikyuFurikyuHelper.createRecMng("a1", GeneralDate.max(), 1.0),
				DaikyuFurikyuHelper.createRecMng("a2", GeneralDate.max(),1.0),
				DaikyuFurikyuHelper.createRecMng("a3", GeneralDate.max(), 0.0));

		List<InterimRemain> interimMng = Arrays.asList(
				DaikyuFurikyuHelper.createRemain("a1", GeneralDate.ymd(2019, 11, 4),
						CreateAtr.SCHEDULE, RemainType.PICKINGUP),
				DaikyuFurikyuHelper.createRemain("a2", GeneralDate.ymd(2019, 11, 7),
						CreateAtr.RECORD, RemainType.PICKINGUP),
				DaikyuFurikyuHelper.createRemain("a3", GeneralDate.ymd(2019, 11, 8),
						CreateAtr.RECORD, RemainType.PICKINGUP));

		AbsRecMngInPeriodRefactParamInput inputParam = DaikyuFurikyuHelper.createAbsRecInput(
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)),//集計開始日, 集計終了日 
				GeneralDate.ymd(2019, 11, 30), //画面表示日
				true, //モード 
				false, // 上書きフラグ
				new ArrayList<>(), interimMng, useRecMng);//暫定管理データ
		
		new Expectations() {
			{
				
				require.getByPayoutId(SID, GeneralDate.ymd(2019, 11, 4));
				result = Arrays.asList(DaikyuFurikyuHelper.createHD(GeneralDate.ymd(2019, 11, 04), GeneralDate.ymd(2019, 11, 30), 1.0));
				
				
				require.findEmploymentHistory(CID, SID, (GeneralDate) any);
				result = Optional.of(new BsEmploymentHistoryImport(SID, 
						"00",  //雇用コード
						"A",// 雇用名称.
						new DatePeriod(GeneralDate.min(), GeneralDate.max())));

			}

		};

		List<AccumulationAbsenceDetail> actualResult = GetUnusedCompenTemporary.process(require, inputParam);

		assertThat(actualResult)
				.extracting(x -> x.getManageId(),
						x -> x.getDataAtr(),//状態
						x -> x.getDateOccur().isUnknownDate(), x -> x.getDateOccur().getDayoffDate(),//年月日
						x -> x.getOccurrentClass(),//発生消化区分
						x -> x.getUnbalanceNumber().getDay().v(),//未相殺数
						x -> ((UnbalanceCompensation) x).getDeadline(),//期限日
						x -> ((UnbalanceCompensation) x).getDigestionCate())//消化区分
				.containsExactly(
						Tuple.tuple("a1",MngDataStatus.SCHEDULE, false,
								Optional.of(GeneralDate.ymd(2019, 11, 4)),OccurrenceDigClass.OCCURRENCE, 0.0,
								GeneralDate.ymd(9999, 12, 31), DigestionAtr.USED),
						Tuple.tuple("a2", MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 7)),OccurrenceDigClass.OCCURRENCE, 1.0,
								GeneralDate.ymd(9999, 12, 31), DigestionAtr.USED),
						Tuple.tuple("a3", MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 8)), OccurrenceDigClass.OCCURRENCE, 0.0,
								GeneralDate.ymd(9999, 12, 31), DigestionAtr.USED));
		}

	/*
	 * テストしたい内容 
	 * 暫定データから発生データを作成する
	 * 
	 * 準備するデータ 
	 * 暫定振出管理データがある 
	 * →紐づけがなくて残ってるやつ 
	 * →紐づけしても残ってるやつ 
	 * 暫定振出振休紐付け管理がある 
	 * →最初から残ってない
	 * →紐づけしたら残ってない 
	 * モード : その他
	 * 
	 */
	@Test
	public void testModeOther() {

		AbsRecMngInPeriodRefactParamInput inputParam = DaikyuFurikyuHelper.createAbsRecInput(
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)),//集計開始日, 集計終了日 
				GeneralDate.ymd(2019, 11, 30), //画面表示日
				false, //モード 
				false, // 上書きフラグ
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>());//暫定管理データ
		
		new Expectations() {
			{

				require.getRemainBySidPriod(anyString, (DatePeriod) any, RemainType.PICKINGUP);
				result = Arrays.asList(
						DaikyuFurikyuHelper.createRemain("a1", GeneralDate.ymd(2019, 11, 4),
								CreateAtr.SCHEDULE, RemainType.PICKINGUP),
						DaikyuFurikyuHelper.createRemain("a2",GeneralDate.ymd(2019, 11, 7),
								CreateAtr.RECORD, RemainType.PICKINGUP),
						DaikyuFurikyuHelper.createRemain("a3", GeneralDate.ymd(2019, 11, 8),
								CreateAtr.RECORD, RemainType.PICKINGUP));

				require.getRecBySidDatePeriod(anyString, (DatePeriod) any);
				result = Arrays.asList(
						DaikyuFurikyuHelper.createRecMng("a1", GeneralDate.max(),1.0),
						DaikyuFurikyuHelper.createRecMng("a2", GeneralDate.max(),
								1.0),
						DaikyuFurikyuHelper.createRecMng("a3", GeneralDate.max(),0.0));

				require.getByPayoutId(SID, GeneralDate.ymd(2019, 11, 4));
				result = Arrays.asList(DaikyuFurikyuHelper.createHD(GeneralDate.ymd(2019, 11, 04), GeneralDate.ymd(2019, 11, 30), 1.0));
				

				require.findEmploymentHistory(CID, SID, (GeneralDate) any);
				result = Optional.of(new BsEmploymentHistoryImport(SID, 
						"00",  //雇用コード
						"A",// 雇用名称.
						new DatePeriod(GeneralDate.min(), GeneralDate.max())));

			}

		};

		List<AccumulationAbsenceDetail> actualResult = GetUnusedCompenTemporary.process(require, inputParam);

		assertThat(actualResult)
		.extracting(x -> x.getManageId(),
				x -> x.getDataAtr(),//状態
				x -> x.getDateOccur().isUnknownDate(), x -> x.getDateOccur().getDayoffDate(),//年月日
				x -> x.getOccurrentClass(),//発生消化区分
				x -> x.getUnbalanceNumber().getDay().v(),//未相殺数
				x -> ((UnbalanceCompensation) x).getDeadline(),//期限日
				x -> ((UnbalanceCompensation) x).getDigestionCate())//消化区分
				.containsExactly(
						Tuple.tuple("a1",MngDataStatus.SCHEDULE, false,
								Optional.of(GeneralDate.ymd(2019, 11, 4)), OccurrenceDigClass.OCCURRENCE, 0.0,
								GeneralDate.ymd(9999, 12, 31), DigestionAtr.USED),
						Tuple.tuple("a2", MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 7)),OccurrenceDigClass.OCCURRENCE, 1.0,
								GeneralDate.ymd(9999, 12, 31), DigestionAtr.USED),
						Tuple.tuple("a3", MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 8)),  OccurrenceDigClass.OCCURRENCE, 0.0,
								GeneralDate.ymd(9999, 12, 31), DigestionAtr.USED));


	}

}
