package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.assertj.core.groups.Tuple;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.record.dom.adapter.application.setting.ApplicationReasonRc;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting.ReqSettingBuilder;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send.SendReasonApplication;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.ApplicationType;

/**
 * @author ThanhNX
 *
 *         申請理由をNRに 送信するデータに変換するTest
 */
@RunWith(JMockit.class)
public class SendReasonApplicationServiceTest {

	@Injectable
	private SendReasonApplicationService.Require require;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSendEmpty() {

		List<SendReasonApplication> actual = SendReasonApplicationService.send(require, new EmpInfoTerminalCode("1"),
				new ContractCode("1"));
		assertThat(actual).isEqualTo(Collections.emptyList());
	}

	@Test
	public void testSendEmptyReasonApp() {

		Optional<TimeRecordReqSetting> timeRecordReqSetting = Optional
				.of(new ReqSettingBuilder(new EmpInfoTerminalCode("1"), new ContractCode("1"), new CompanyId("1"), "1",
						Collections.emptyList(), Collections.emptyList(), Collections.emptyList()).overTimeHoliday(true)
								.workTime(Collections.emptyList()).reservationReceive(false).build());
		new Expectations() {
			{

				require.getTimeRecordReqSetting((EmpInfoTerminalCode) any, (ContractCode) any);
				result = timeRecordReqSetting;
			}
		};
		List<SendReasonApplication> actual = SendReasonApplicationService.send(require, new EmpInfoTerminalCode("1"),
				new ContractCode("1"));
		assertThat(actual).isEqualTo(Collections.emptyList());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDone() {
		Optional<TimeRecordReqSetting> timeRecordReqSetting = Optional
				.of(new ReqSettingBuilder(new EmpInfoTerminalCode("1"), new ContractCode("1"), new CompanyId("1"), "1",
						Collections.emptyList(), Collections.emptyList(), Collections.emptyList()).overTimeHoliday(true)
								.workTime(Collections.emptyList()).reservationReceive(false).build());
		new Expectations() {
			{

				require.getTimeRecordReqSetting((EmpInfoTerminalCode) any, (ContractCode) any);
				result = timeRecordReqSetting;

				require.getReasonByAppType(anyString, (List<Integer>) any);
				result = Arrays.asList(
						new ApplicationReasonRc("1", ApplicationType.OVER_TIME_APPLICATION,
								Arrays.asList(Pair.of(1, "A1"))),
						new ApplicationReasonRc("1", ApplicationType.ABSENCE_APPLICATION,
								Arrays.asList(Pair.of(2, "A2"))),
						new ApplicationReasonRc("1", ApplicationType.WORK_CHANGE_APPLICATION,
								Arrays.asList(Pair.of(3, "A3"))),
						new ApplicationReasonRc("1", ApplicationType.BREAK_TIME_APPLICATION,
								Arrays.asList(Pair.of(4, "A4"))),
						new ApplicationReasonRc("1", ApplicationType.STAMP_APPLICATION,
								Arrays.asList(Pair.of(5, "A5"))),
						new ApplicationReasonRc("1", ApplicationType.ANNUAL_HOLIDAY_APPLICATION,
								Arrays.asList(Pair.of(6, "A6"))),
						new ApplicationReasonRc("1", ApplicationType.EARLY_LEAVE_CANCEL_APPLICATION,
								Arrays.asList(Pair.of(7, "A7"))),
						new ApplicationReasonRc("1", ApplicationType.APPLICATION_36, Arrays.asList(Pair.of(8, "A8")))

				);
			}
		};
		List<SendReasonApplication> actual = SendReasonApplicationService.send(require, new EmpInfoTerminalCode("1"),
				new ContractCode("1"));
		assertThat(actual).extracting(d -> d.getAppReasonNo(), d -> d.getAppReasonName()).containsExactly(
				Tuple.tuple("11", "A1"), Tuple.tuple("22", "A2"), Tuple.tuple("33", "A3"), Tuple.tuple("44", "A4"),
				Tuple.tuple("05", "A5"), Tuple.tuple("66", "A6"), Tuple.tuple("57", "A7"));

	}

}
