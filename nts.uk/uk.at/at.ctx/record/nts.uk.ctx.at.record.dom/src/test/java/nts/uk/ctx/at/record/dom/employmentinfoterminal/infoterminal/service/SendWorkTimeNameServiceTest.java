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
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send.SendWorkTimeName;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.predset.UseSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeAbName;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDisplayName;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeName;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSymbol;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * @author ThanhNX
 *
 *         就業時間帯をNRに 送信するデータに変換するTest
 */
@RunWith(JMockit.class)
public class SendWorkTimeNameServiceTest {

	@Injectable
	private SendWorkTimeNameService.Require require;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSendEmpty() {

		List<SendWorkTimeName> actual = SendWorkTimeNameService.send(require, new EmpInfoTerminalCode(1),
				new ContractCode("1"));
		assertThat(actual).isEqualTo(Collections.emptyList());
	}

	@Test
	public void testSendEmptyWorkTime() {

		Optional<TimeRecordReqSetting> timeRecordReqSetting = Optional
				.of(new ReqSettingBuilder(new EmpInfoTerminalCode(1), new ContractCode("1"), new CompanyId("1"), "1",
						Collections.emptyList(), null, Collections.emptyList()).overTimeHoliday(true)
								.workTime(Collections.emptyList()).build());
		new Expectations() {
			{

				require.getTimeRecordReqSetting((EmpInfoTerminalCode) any, (ContractCode) any);
				result = timeRecordReqSetting;
			}
		};
		List<SendWorkTimeName> actual = SendWorkTimeNameService.send(require, new EmpInfoTerminalCode(1),
				new ContractCode("1"));
		assertThat(actual).isEqualTo(Collections.emptyList());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSendDone() {

		Optional<TimeRecordReqSetting> timeRecordReqSetting = Optional
				.of(new ReqSettingBuilder(new EmpInfoTerminalCode(1), new ContractCode("1"), new CompanyId("1"), "1",
						Collections.emptyList(), null, Collections.emptyList()).overTimeHoliday(true)
								.workTime(Arrays.asList(new WorkTimeCode("A"), new WorkTimeCode("B"))).build());
		new Expectations() {
			{

				require.getTimeRecordReqSetting((EmpInfoTerminalCode) any, (ContractCode) any);
				result = timeRecordReqSetting;

				require.findByCodes(anyString, (List<String>) any);
				result = Arrays.asList(
						new WorkTimeSetting("1", new WorkTimeCode("A"), null, null, null,
								new WorkTimeDisplayName(new WorkTimeName("AAA"), new WorkTimeAbName("BBB"),
										new WorkTimeSymbol("BBB")),
								null, null),

						new WorkTimeSetting("1", new WorkTimeCode("B"), null, null, null,
								new WorkTimeDisplayName(new WorkTimeName("AAA"), new WorkTimeAbName("BBB"),
										new WorkTimeSymbol("BBB")),
								null, null),

						new WorkTimeSetting("1", new WorkTimeCode("C"), null, null, null,
								new WorkTimeDisplayName(new WorkTimeName("AAA"), new WorkTimeAbName("BBB"),
										new WorkTimeSymbol("BBB")),
								null, null),
						new WorkTimeSetting("1", new WorkTimeCode("D"), null, null, null,
								new WorkTimeDisplayName(new WorkTimeName("AAA"), new WorkTimeAbName("BBB"),
										new WorkTimeSymbol("BBB")),
								null, null));

				require.findByCodeList(anyString, (List<String>) any);
				result = Arrays.asList(
						new PredetemineTimeSetting("1", null, new WorkTimeCode("A"), null, true,
								new PrescribedTimezoneSetting(new TimeWithDayAttr(720), new TimeWithDayAttr(780),
										Arrays.asList(new TimezoneUse(new TimeWithDayAttr(480),
												new TimeWithDayAttr(1020), UseSetting.USE, 1))),
								null, false),

						new PredetemineTimeSetting("1", null, new WorkTimeCode("B"), null, true,
								new PrescribedTimezoneSetting(new TimeWithDayAttr(720), new TimeWithDayAttr(780),
										Arrays.asList(new TimezoneUse(new TimeWithDayAttr(480),
												new TimeWithDayAttr(1020), UseSetting.USE, 1))),
								null, false),
						new PredetemineTimeSetting("1", null, new WorkTimeCode("B"), null, true,
								new PrescribedTimezoneSetting(new TimeWithDayAttr(720), new TimeWithDayAttr(780),
										Arrays.asList(new TimezoneUse(new TimeWithDayAttr(480),
												new TimeWithDayAttr(1020), UseSetting.USE, 1))),
								null, false),
						new PredetemineTimeSetting("1", null, new WorkTimeCode("D"), null, true,
								new PrescribedTimezoneSetting(new TimeWithDayAttr(720), new TimeWithDayAttr(780),
										Collections.emptyList()),
								null, false));

			}
		};
		List<SendWorkTimeName> actual = SendWorkTimeNameService.send(require, new EmpInfoTerminalCode(1),
				new ContractCode("1"));
		assertThat(actual).extracting(d -> d.getWorkTimeNumber(), d -> d.getWorkTimeName(), d -> d.getTime())
				.containsExactly(Tuple.tuple("00A", "AAA", "+08001700"), Tuple.tuple("00B", "AAA", "+08001700"),
						Tuple.tuple("00C", "AAA", ""), Tuple.tuple("00D", "AAA", ""));

	}

}
