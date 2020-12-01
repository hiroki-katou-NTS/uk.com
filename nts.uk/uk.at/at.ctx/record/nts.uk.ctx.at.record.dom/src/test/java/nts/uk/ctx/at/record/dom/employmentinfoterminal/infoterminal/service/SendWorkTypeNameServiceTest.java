package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
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
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting.ReqSettingBuilder;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send.SendWorkTypeName;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeAbbreviationName;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeMemo;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeName;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSymbolicName;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;

/**
 * @author ThanhNX
 *
 *         勤務種類をNRに 送信するデータに変換するTest
 */
@RunWith(JMockit.class)
public class SendWorkTypeNameServiceTest {

	@Injectable
	private SendWorkTypeNameService.Require require;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSendEmpty() {

		List<SendWorkTypeName> actual = SendWorkTypeNameService.send(require, new EmpInfoTerminalCode("1"),
				new ContractCode("1"));
		assertThat(actual).isEqualTo(Collections.emptyList());
	}

	@Test
	public void testSendEmptyWorkType() {

		Optional<TimeRecordReqSetting> timeRecordReqSetting = Optional
				.of(new ReqSettingBuilder(new EmpInfoTerminalCode("1"), new ContractCode("1"), new CompanyId("1"), "1",
						Collections.emptyList(), null, Collections.emptyList()).overTimeHoliday(true).build());
		new Expectations() {
			{

				require.getTimeRecordReqSetting((EmpInfoTerminalCode) any, (ContractCode) any);
				result = timeRecordReqSetting;
			}
		};
		List<SendWorkTypeName> actual = SendWorkTypeNameService.send(require, new EmpInfoTerminalCode("1"),
				new ContractCode("1"));
		assertThat(actual).isEqualTo(Collections.emptyList());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test() {

		Optional<TimeRecordReqSetting> timeRecordReqSetting = Optional
				.of(new ReqSettingBuilder(new EmpInfoTerminalCode("1"), new ContractCode("1"), new CompanyId("1"), "1",
						Collections.emptyList(), null, Arrays.asList(new WorkTypeCode("1"), new WorkTypeCode("2")))
								.overTimeHoliday(true).build());
		new Expectations() {
			{
				require.getTimeRecordReqSetting((EmpInfoTerminalCode) any, (ContractCode) any);
				result = timeRecordReqSetting;

				require.getPossibleWork(anyString, (List<String>) any);
				result = java.util.Arrays.asList(
						new WorkType(
								new WorkTypeCode("1"), new WorkTypeSymbolicName("B"), new WorkTypeName("A"),
								new WorkTypeAbbreviationName("BB"), new WorkTypeMemo("AAAAAA"),
								new DailyWork(
										WorkTypeUnit.OneDay, WorkTypeClassification.Attendance,
										WorkTypeClassification.SpecialHoliday, WorkTypeClassification.HolidayWork)),

						new WorkType(new WorkTypeCode("2"), new WorkTypeSymbolicName("C"), new WorkTypeName("C"),
								new WorkTypeAbbreviationName("BB"), new WorkTypeMemo("AAAAAA"),
								new DailyWork(WorkTypeUnit.MonringAndAfternoon, WorkTypeClassification.LeaveOfAbsence,
										WorkTypeClassification.Attendance, WorkTypeClassification.AnnualHoliday)),

						new WorkType(new WorkTypeCode("3"), new WorkTypeSymbolicName("B"), new WorkTypeName("A"),
								new WorkTypeAbbreviationName("BB"), new WorkTypeMemo("AAAAAA"),
								new DailyWork(WorkTypeUnit.OneDay, WorkTypeClassification.ContinuousWork,
										WorkTypeClassification.SpecialHoliday, WorkTypeClassification.HolidayWork)),

						new WorkType(new WorkTypeCode("4"), new WorkTypeSymbolicName("C"), new WorkTypeName("C"),
								new WorkTypeAbbreviationName("BB"), new WorkTypeMemo("AAAAAA"),
								new DailyWork(WorkTypeUnit.MonringAndAfternoon, WorkTypeClassification.LeaveOfAbsence,
										WorkTypeClassification.Absence, WorkTypeClassification.Holiday)),

						new WorkType(new WorkTypeCode("5"), new WorkTypeSymbolicName("B"), new WorkTypeName("A"),
								new WorkTypeAbbreviationName("BB"), new WorkTypeMemo("AAAAAA"),
								new DailyWork(WorkTypeUnit.OneDay, WorkTypeClassification.Shooting,
										WorkTypeClassification.SpecialHoliday, WorkTypeClassification.HolidayWork)),

						new WorkType(new WorkTypeCode("6"), new WorkTypeSymbolicName("C"), new WorkTypeName("C"),
								new WorkTypeAbbreviationName("BB"), new WorkTypeMemo("AAAAAA"),
								new DailyWork(WorkTypeUnit.MonringAndAfternoon, WorkTypeClassification.LeaveOfAbsence,
										WorkTypeClassification.SpecialHoliday, WorkTypeClassification.Pause)),

						new WorkType(new WorkTypeCode("7"), new WorkTypeSymbolicName("B"), new WorkTypeName("A"),
								new WorkTypeAbbreviationName("BB"), new WorkTypeMemo("AAAAAA"),
								new DailyWork(WorkTypeUnit.OneDay, WorkTypeClassification.SubstituteHoliday,
										WorkTypeClassification.SpecialHoliday, WorkTypeClassification.HolidayWork)),

						new WorkType(new WorkTypeCode("8"), new WorkTypeSymbolicName("C"), new WorkTypeName("C"),
								new WorkTypeAbbreviationName("BB"), new WorkTypeMemo("AAAAAA"),
								new DailyWork(WorkTypeUnit.MonringAndAfternoon, WorkTypeClassification.LeaveOfAbsence,
										WorkTypeClassification.TimeDigestVacation, WorkTypeClassification.HolidayWork)),

						new WorkType(new WorkTypeCode("9"), new WorkTypeSymbolicName("B"), new WorkTypeName("A"),
								new WorkTypeAbbreviationName("BB"), new WorkTypeMemo("AAAAAA"),
								new DailyWork(WorkTypeUnit.OneDay, WorkTypeClassification.YearlyReserved,
										WorkTypeClassification.SpecialHoliday, WorkTypeClassification.HolidayWork))

				);
			}
		};
		List<SendWorkTypeName> actual = SendWorkTypeNameService.send(require, new EmpInfoTerminalCode("1"),
				new ContractCode("1"));
		assertThat(actual).extracting(d -> d.getWorkTypeNumber(), d -> d.getDaiClassifiNum(), d -> d.getWorkName())
				.containsExactly(Tuple.tuple("001", "0", "A"), Tuple.tuple("002", "0", "C"),
						Tuple.tuple("003", "1", "A"), Tuple.tuple("004", "1", "C"), Tuple.tuple("005", "0", "A"),
						Tuple.tuple("006", "1", "C"), Tuple.tuple("007", "1", "A"), Tuple.tuple("008", "0", "C"),
						Tuple.tuple("009", "1", "A"));

	}

}
