package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
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
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send.SendOvertimeName;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameGetMemento;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameName;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameGetMemento;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameName;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameNo;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRole;

/**
 * @author ThanhNX
 *
 *         残業・休日出勤をNRに 送信するデータに変換するTest
 */
@RunWith(JMockit.class)
public class SendOvertimeNameServiceTest {

	@Injectable
	private SendOvertimeNameService.Require require;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSendEmpty() {

		Optional<SendOvertimeName> actual = SendOvertimeNameService.send(require, new EmpInfoTerminalCode(1),
				new ContractCode("1"));
		assertThat(actual).isEqualTo(Optional.empty());
	}

	@Test
	public void testSend() {

		Optional<TimeRecordReqSetting> timeRecordReqSetting = Optional
				.of(new ReqSettingBuilder(new EmpInfoTerminalCode(1), new ContractCode("1"), new CompanyId("1"), "1",
						null, null, null).overTimeHoliday(true).build());

		new Expectations() {
			{

				require.getTimeRecordReqSetting((EmpInfoTerminalCode) any, (ContractCode) any);
				result = timeRecordReqSetting;

				require.getAllOvertimeWorkFrame(anyString);
				result = Arrays.asList(new OvertimeWorkFrame(createOvertime(1)),
						new OvertimeWorkFrame(createOvertime(2)));

				require.getAllWorkdayoffFrame(anyString);
				result = Arrays.asList(new WorkdayoffFrame(createWorkday(1)), new WorkdayoffFrame(createWorkday(2)));
			}
		};

		Optional<SendOvertimeName> actual = SendOvertimeNameService.send(require, new EmpInfoTerminalCode(1),
				new ContractCode("1"));

		assertThat(actual.get().getOvertimes()).extracting(d -> d.getSendOvertimeNo(), d -> d.getSendOvertimeName())
				.containsExactly(Tuple.tuple("1", "AOver"), Tuple.tuple("2", "AOver"));

		assertThat(actual.get().getVacations()).extracting(d -> d.getSendOvertimeNo(), d -> d.getSendOvertimeName())
				.containsExactly(Tuple.tuple("1", "AWorkday"), Tuple.tuple("2", "AWorkday"));
	}

	private OvertimeWorkFrameGetMemento createOvertime(int no) {
		return new OvertimeWorkFrameGetMemento() {

			@Override
			public NotUseAtr getUseClassification() {
				return NotUseAtr.USE;
			}

			@Override
			public OvertimeWorkFrameName getTransferFrameName() {
				return new OvertimeWorkFrameName("ATranfer");
			}

			@Override
			public OvertimeWorkFrameNo getOvertimeWorkFrameNo() {
				return new OvertimeWorkFrameNo(BigDecimal.valueOf(no));
			}

			@Override
			public OvertimeWorkFrameName getOvertimeWorkFrameName() {
				return new OvertimeWorkFrameName("AOver");
			}

			@Override
			public String getCompanyId() {
				return "1";
			}
		};
	}

	private WorkdayoffFrameGetMemento createWorkday(int no) {
		return new WorkdayoffFrameGetMemento() {

			@Override
			public WorkdayoffFrameNo getWorkdayoffFrameNo() {
				return new WorkdayoffFrameNo(BigDecimal.valueOf(no));
			}

			@Override
			public WorkdayoffFrameName getWorkdayoffFrameName() {
				return new WorkdayoffFrameName("AWorkday");
			}

			@Override
			public nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr getUseClassification() {
				return nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr.USE;
			}

			@Override
			public WorkdayoffFrameName getTransferFrameName() {
				return new WorkdayoffFrameName("ATrans");
			}

			@Override
			public String getCompanyId() {
				return "1";
			}

			@Override
			public WorkdayoffFrameRole getRole() {
				return WorkdayoffFrameRole.MIX_WITHIN_OUTSIDE_STATUTORY;
			}
		};
	}
}
