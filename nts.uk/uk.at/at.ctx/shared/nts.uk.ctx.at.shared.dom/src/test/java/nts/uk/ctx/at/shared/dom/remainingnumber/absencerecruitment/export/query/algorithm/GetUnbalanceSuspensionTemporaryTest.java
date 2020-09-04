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
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.DataManagementAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.OccurrenceDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.SelectedAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.StatutoryAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnUsedDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;

@RunWith(JMockit.class)
public class GetUnbalanceSuspensionTemporaryTest {

	private static String CID = "000000000000-0117";

	private static String SID = "292ae91c-508c-4c6e-8fe8-3e72277dec16";

	@Injectable
	private GetUnbalanceSuspensionTemporary.Require require;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test() {

		List<InterimAbsMng> useAbsMng = Arrays.asList(
				new InterimAbsMng("adda6a46-2cbe-48c8-85f8-c04ca554e132", new RequiredDay(1.0), new UnOffsetDay(1.0)),
				new InterimAbsMng("adda6a46-2cbe-48c8-85f8-c04ca554e136", new RequiredDay(1.0), new UnOffsetDay(1.0)));

		List<InterimRecMng> useRecMng = Arrays.asList(new InterimRecMng("adda6a46-2cbe-48c8-85f8-c04ca554e333",
				GeneralDate.max(), new OccurrenceDay(1.0), StatutoryAtr.PUBLIC, new UnUsedDay(1.0)));

		List<InterimRemain> interimMng = Arrays.asList(
				new InterimRemain("adda6a46-2cbe-48c8-85f8-c04ca554e132", SID, GeneralDate.ymd(2019, 11, 4),
						CreateAtr.SCHEDULE, RemainType.PAUSE, RemainAtr.SINGLE),
				new InterimRemain("adda6a46-2cbe-48c8-85f8-c04ca554e136", SID, GeneralDate.ymd(2019, 11, 7),
						CreateAtr.RECORD, RemainType.PAUSE, RemainAtr.SINGLE),

				new InterimRemain("adda6a46-2cbe-48c8-85f8-c04ca554e333", SID, GeneralDate.ymd(2019, 11, 5),
						CreateAtr.SCHEDULE, RemainType.PICKINGUP, RemainAtr.SINGLE));

		AbsRecMngInPeriodRefactParamInput inputParam = new AbsRecMngInPeriodRefactParamInput(CID, SID,
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)),
				GeneralDate.ymd(2019, 11, 30), true, false, useAbsMng, interimMng, useRecMng, Optional.empty(),
				Optional.empty(), Optional.empty(), new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));
		new Expectations() {
			{

				require.getRecOrAbsMngs((List<String>) any, false, DataManagementAtr.INTERIM);

				result = Arrays.asList(new InterimRecAbsMng("adda6a46-2cbe-48c8-85f8-c04ca554e132",
						DataManagementAtr.INTERIM, "", DataManagementAtr.INTERIM, new UseDay(1.0), SelectedAtr.MANUAL));

			}

		};

		List<AccumulationAbsenceDetail> actualResult = GetUnbalanceSuspensionTemporary.process(require, inputParam);

		assertThat(actualResult)
				.extracting(x -> x.getManageId(), x -> x.getEmployeeId(), x -> x.getDataAtr(),
						x -> x.getDateOccur().isUnknownDate(), x -> x.getDateOccur().getDayoffDate(),
						x -> x.getNumberOccurren().getDay().v(), x -> x.getOccurrentClass(),
						x -> x.getUnbalanceNumber().getDay().v())
				.containsExactly(
						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554e132", SID, MngDataStatus.SCHEDULE, false,
								Optional.of(GeneralDate.ymd(2019, 11, 4)), 1.0, OccurrenceDigClass.DIGESTION, 0.0),
						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554e136", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 7)), 1.0, OccurrenceDigClass.DIGESTION, 1.0));

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testOther() {

		AbsRecMngInPeriodRefactParamInput inputParam = new AbsRecMngInPeriodRefactParamInput(CID, SID,
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)),
				GeneralDate.ymd(2019, 11, 30), false, false, new ArrayList<>(),  new ArrayList<>(),  new ArrayList<>(), Optional.empty(),
				Optional.empty(), Optional.empty(), new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));
		new Expectations() {
			{

				require.getRemainBySidPriod(SID, (DatePeriod) any, RemainType.PAUSE);
				result = Arrays.asList(
						new InterimRemain("adda6a46-2cbe-48c8-85f8-c04ca554e132", SID, GeneralDate.ymd(2019, 11, 4),
								CreateAtr.SCHEDULE, RemainType.PAUSE, RemainAtr.SINGLE),
						new InterimRemain("adda6a46-2cbe-48c8-85f8-c04ca554e136", SID, GeneralDate.ymd(2019, 11, 7),
								CreateAtr.RECORD, RemainType.PAUSE, RemainAtr.SINGLE));

				require.getAbsBySidDatePeriod(SID, (DatePeriod) any);
				result = Arrays.asList(
						new InterimAbsMng("adda6a46-2cbe-48c8-85f8-c04ca554e132", new RequiredDay(1.0),
								new UnOffsetDay(1.0)),
						new InterimAbsMng("adda6a46-2cbe-48c8-85f8-c04ca554e136", new RequiredDay(1.0),
								new UnOffsetDay(1.0)));

				require.getRecOrAbsMngs((List<String>) any, false, DataManagementAtr.INTERIM);
				result = Arrays.asList(new InterimRecAbsMng("adda6a46-2cbe-48c8-85f8-c04ca554e132",
						DataManagementAtr.INTERIM, "", DataManagementAtr.INTERIM, new UseDay(1.0), SelectedAtr.MANUAL));

			}

		};

		List<AccumulationAbsenceDetail> actualResult = GetUnbalanceSuspensionTemporary.process(require, inputParam);

		assertThat(actualResult)
				.extracting(x -> x.getManageId(), x -> x.getEmployeeId(), x -> x.getDataAtr(),
						x -> x.getDateOccur().isUnknownDate(), x -> x.getDateOccur().getDayoffDate(),
						x -> x.getNumberOccurren().getDay().v(), x -> x.getOccurrentClass(),
						x -> x.getUnbalanceNumber().getDay().v())
				.containsExactly(
						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554e132", SID, MngDataStatus.SCHEDULE, false,
								Optional.of(GeneralDate.ymd(2019, 11, 4)), 1.0, OccurrenceDigClass.DIGESTION, 0.0),
						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554e136", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 7)), 1.0, OccurrenceDigClass.DIGESTION, 1.0));

	}

}
