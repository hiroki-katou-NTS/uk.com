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
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataDaysAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.DataManagementAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.SelectedAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;

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

	@Test
	public void testEmpty() {
		List<AccumulationAbsenceDetail> actualResult = GetUnbalanceSuspension.process(require, CID, SID,
				GeneralDate.ymd(2019, 4, 1), new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));

		assertThat(actualResult).isEqualTo(new ArrayList<>());
	}

	@Test
	public void testUnbalanceUnused() {

		List<CompensatoryDayOffManaData> lstComMock = new ArrayList<>();
		lstComMock.add(new CompensatoryDayOffManaData("adda6a46-2cbe-48c8-85f8-c04ca554e133", CID, SID, false,
				GeneralDate.ymd(2019, 11, 10), 1.0, 240, 1.0, 240));
		lstComMock.add(new CompensatoryDayOffManaData("adda6a46-2cbe-48c8-85f8-c04ca554e134", CID, SID, false,
				GeneralDate.ymd(2019, 11, 12), 1.0, 240, 1.0, 240));

		new Expectations() {
			{

				require.getByYmdUnOffset(CID, SID, (GeneralDate) any, 0);
				result = Arrays.asList(
						new SubstitutionOfHDManagementData("adda6a46-2cbe-48c8-85f8-c04ca554e133", CID, SID,
								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 30))),
								new ManagementDataDaysAtr(1.0), new ManagementDataRemainUnit(1.0)),
						new SubstitutionOfHDManagementData("adda6a46-2cbe-48c8-85f8-c04ca554e134", CID, SID,
								new CompensatoryDayoffDate(false, Optional.of(GeneralDate.ymd(2019, 11, 20))),
								new ManagementDataDaysAtr(1.0), new ManagementDataRemainUnit(1.0)));

				require.getBySidMng(DataManagementAtr.INTERIM, DataManagementAtr.CONFIRM,
						"adda6a46-2cbe-48c8-85f8-c04ca554e133");
				result = Arrays.asList(
						new InterimRecAbsMng("", DataManagementAtr.INTERIM, "adda6a46-2cbe-48c8-85f8-c04ca554e133",
								DataManagementAtr.CONFIRM, new UseDay(1.0), SelectedAtr.MANUAL));

				require.getBySidMng(DataManagementAtr.INTERIM, DataManagementAtr.CONFIRM,
						"adda6a46-2cbe-48c8-85f8-c04ca554e134");
				result = Arrays.asList(
						new InterimRecAbsMng("", DataManagementAtr.INTERIM, "adda6a46-2cbe-48c8-85f8-c04ca554e134",
								DataManagementAtr.CONFIRM, new UseDay(0.5), SelectedAtr.MANUAL));

			}
		};

		List<AccumulationAbsenceDetail> actualResult = GetUnbalanceSuspension.process(require, CID, SID,
				GeneralDate.ymd(2019, 11, 1), new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));

		assertThat(actualResult)
				.extracting(x -> x.getManageId(), x -> x.getEmployeeId(), x -> x.getDataAtr(),
						x -> x.getDateOccur().isUnknownDate(), x -> x.getDateOccur().getDayoffDate(),
						x -> x.getNumberOccurren().getDay().v(), x -> x.getOccurrentClass(),
						x -> x.getUnbalanceNumber().getDay().v())
				.containsExactly(Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554e134", SID, MngDataStatus.CONFIRMED,
						false, Optional.of(GeneralDate.ymd(2019, 11, 20)), 1.0, OccurrenceDigClass.DIGESTION, 0.5));
	}
}
