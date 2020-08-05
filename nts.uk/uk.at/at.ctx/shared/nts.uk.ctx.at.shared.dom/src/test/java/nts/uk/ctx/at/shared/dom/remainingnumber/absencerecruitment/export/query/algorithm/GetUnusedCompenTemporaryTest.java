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
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyDto;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.AbsRecMngInPeriodRefactParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.UnbalanceCompensation;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.NumberRemainVacationLeaveRangeQueryTest;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.DataManagementAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.OccurrenceDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.SelectedAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.StatutoryAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnUsedDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.SubstVacationSetting;

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

	@SuppressWarnings("unchecked")
	@Test
	public void testModeMonth() {

		List<InterimRecMng> useRecMng = Arrays.asList(
				new InterimRecMng("adda6a46-2cbe-48c8-85f8-c04ca554e132", GeneralDate.max(), new OccurrenceDay(1.0),
						StatutoryAtr.PUBLIC, new UnUsedDay(1.0)),
				new InterimRecMng("adda6a46-2cbe-48c8-85f8-c04ca554e136", GeneralDate.max(), new OccurrenceDay(1.0),
						StatutoryAtr.PUBLIC, new UnUsedDay(1.0)));

		List<InterimRemain> interimMng = Arrays.asList(
				new InterimRemain("adda6a46-2cbe-48c8-85f8-c04ca554e132", SID, GeneralDate.ymd(2019, 11, 4),
						CreateAtr.SCHEDULE, RemainType.PICKINGUP, RemainAtr.SINGLE),
				new InterimRemain("adda6a46-2cbe-48c8-85f8-c04ca554e136", SID, GeneralDate.ymd(2019, 11, 7),
						CreateAtr.RECORD, RemainType.PICKINGUP, RemainAtr.SINGLE));

		AbsRecMngInPeriodRefactParamInput inputParam = new AbsRecMngInPeriodRefactParamInput(CID, SID,
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)),
				GeneralDate.ymd(2019, 11, 30), true, false, new ArrayList<>(), interimMng, useRecMng, Optional.empty(),
				Optional.empty(), Optional.empty(), new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));

		new Expectations() {
			{

				require.getRecOrAbsMngs((List<String>) any, false, DataManagementAtr.INTERIM);

				result = Arrays.asList(new InterimRecAbsMng("adda6a46-2cbe-48c8-85f8-c04ca554",
						DataManagementAtr.INTERIM, "adda6a46-2cbe-48c8-85f8-c04ca554e132", DataManagementAtr.INTERIM,
						new UseDay(1.0), SelectedAtr.MANUAL));

				require.findEmploymentHistory(CID, SID, (GeneralDate) any);
				result = Optional.of(new BsEmploymentHistoryImport(SID, "00", "A",
						new DatePeriod(GeneralDate.min(), GeneralDate.max())));

				require.getClosureDataByEmployee(SID, (GeneralDate) any);
				result = NumberRemainVacationLeaveRangeQueryTest.createClosure();

				require.getFirstMonth(CID);
				result = new CompanyDto(11);

				require.findEmpById(anyString, anyString);
				result = Optional.of(new EmpSubstVacation(CID, "00", new SubstVacationSetting(ManageDistinct.YES,
						ExpirationTime.THIS_MONTH, ApplyPermission.ALLOW)));

			}

		};

		List<AccumulationAbsenceDetail> actualResult = GetUnusedCompenTemporary.process(require, inputParam);

		assertThat(actualResult)
				.extracting(x -> x.getManageId(), x -> x.getEmployeeId(), x -> x.getDataAtr(),
						x -> x.getDateOccur().isUnknownDate(), x -> x.getDateOccur().getDayoffDate(),
						x -> x.getNumberOccurren().getDay().v(), x -> x.getOccurrentClass(),
						x -> x.getUnbalanceNumber().getDay().v(), x -> ((UnbalanceCompensation) x).getDeadline(),
						x -> ((UnbalanceCompensation) x).getDigestionCate())
				.containsExactly(
						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554e132", SID, MngDataStatus.SCHEDULE, false,
								Optional.of(GeneralDate.ymd(2019, 11, 4)), 1.0, OccurrenceDigClass.OCCURRENCE, 0.0,
								GeneralDate.ymd(2019, 12, 1), DigestionAtr.USED),
						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554e136", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 7)), 1.0, OccurrenceDigClass.OCCURRENCE, 1.0,
								GeneralDate.ymd(2019, 12, 1), DigestionAtr.USED));

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testModeOther() {

		List<InterimRecMng> useRecMng = Arrays.asList(
				new InterimRecMng("adda6a46-2cbe-48c8-85f8-c04ca554e132", GeneralDate.max(), new OccurrenceDay(1.0),
						StatutoryAtr.PUBLIC, new UnUsedDay(1.0)),
				new InterimRecMng("adda6a46-2cbe-48c8-85f8-c04ca554e136", GeneralDate.max(), new OccurrenceDay(1.0),
						StatutoryAtr.PUBLIC, new UnUsedDay(1.0)));

		List<InterimRemain> interimMng = Arrays.asList(
				new InterimRemain("adda6a46-2cbe-48c8-85f8-c04ca554e132", SID, GeneralDate.ymd(2019, 11, 4),
						CreateAtr.SCHEDULE, RemainType.PICKINGUP, RemainAtr.SINGLE),
				new InterimRemain("adda6a46-2cbe-48c8-85f8-c04ca554e136", SID, GeneralDate.ymd(2019, 11, 7),
						CreateAtr.RECORD, RemainType.PICKINGUP, RemainAtr.SINGLE));

		AbsRecMngInPeriodRefactParamInput inputParam = new AbsRecMngInPeriodRefactParamInput(CID, SID,
				new DatePeriod(GeneralDate.ymd(2019, 11, 01), GeneralDate.ymd(2020, 10, 31)),
				GeneralDate.ymd(2019, 11, 30), false, false, new ArrayList<>(), interimMng, useRecMng, Optional.empty(),
				Optional.empty(), Optional.empty(), new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));

		new Expectations() {
			{

				require.getRemainBySidPriod(anyString, (DatePeriod) any, RemainType.PICKINGUP);
				result = Arrays.asList(
						new InterimRemain("adda6a46-2cbe-48c8-85f8-c04ca554e132", SID, GeneralDate.ymd(2019, 11, 4),
								CreateAtr.SCHEDULE, RemainType.PICKINGUP, RemainAtr.SINGLE),
						new InterimRemain("adda6a46-2cbe-48c8-85f8-c04ca554e136", SID, GeneralDate.ymd(2019, 11, 7),
								CreateAtr.RECORD, RemainType.PICKINGUP, RemainAtr.SINGLE));

				require.getRecBySidDatePeriod(anyString, (DatePeriod) any);
				result = Arrays.asList(
						new InterimRecMng("adda6a46-2cbe-48c8-85f8-c04ca554e132", GeneralDate.max(),
								new OccurrenceDay(1.0), StatutoryAtr.PUBLIC, new UnUsedDay(1.0)),
						new InterimRecMng("adda6a46-2cbe-48c8-85f8-c04ca554e136", GeneralDate.max(),
								new OccurrenceDay(1.0), StatutoryAtr.PUBLIC, new UnUsedDay(1.0)));

				require.getRecOrAbsMngs((List<String>) any, false, DataManagementAtr.INTERIM);

				result = Arrays.asList(new InterimRecAbsMng("adda6a46-2cbe-48c8-85f8-c04ca554",
						DataManagementAtr.INTERIM, "adda6a46-2cbe-48c8-85f8-c04ca554e132", DataManagementAtr.INTERIM,
						new UseDay(1.0), SelectedAtr.MANUAL));

				require.findEmploymentHistory(CID, SID, (GeneralDate) any);
				result = Optional.of(new BsEmploymentHistoryImport(SID, "00", "A",
						new DatePeriod(GeneralDate.min(), GeneralDate.max())));

				require.getClosureDataByEmployee(SID, (GeneralDate) any);
				result = NumberRemainVacationLeaveRangeQueryTest.createClosure();

				require.getFirstMonth(CID);
				result = new CompanyDto(11);

				require.findEmpById(anyString, anyString);
				result = Optional.of(new EmpSubstVacation(CID, "00", new SubstVacationSetting(ManageDistinct.YES,
						ExpirationTime.THIS_MONTH, ApplyPermission.ALLOW)));

			}

		};

		List<AccumulationAbsenceDetail> actualResult = GetUnusedCompenTemporary.process(require, inputParam);

		assertThat(actualResult)
				.extracting(x -> x.getManageId(), x -> x.getEmployeeId(), x -> x.getDataAtr(),
						x -> x.getDateOccur().isUnknownDate(), x -> x.getDateOccur().getDayoffDate(),
						x -> x.getNumberOccurren().getDay().v(), x -> x.getOccurrentClass(),
						x -> x.getUnbalanceNumber().getDay().v(), x -> ((UnbalanceCompensation) x).getDeadline(),
						x -> ((UnbalanceCompensation) x).getDigestionCate())
				.containsExactly(
						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554e132", SID, MngDataStatus.SCHEDULE, false,
								Optional.of(GeneralDate.ymd(2019, 11, 4)), 1.0, OccurrenceDigClass.OCCURRENCE, 0.0,
								GeneralDate.ymd(2019, 12, 1), DigestionAtr.USED),
						Tuple.tuple("adda6a46-2cbe-48c8-85f8-c04ca554e136", SID, MngDataStatus.RECORD, false,
								Optional.of(GeneralDate.ymd(2019, 11, 7)), 1.0, OccurrenceDigClass.OCCURRENCE, 1.0,
								GeneralDate.ymd(2019, 12, 1), DigestionAtr.USED));

	}

}
