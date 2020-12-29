package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

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
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send.SendTimeRecordSetting;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * @author ThanhNX
 *
 *         タイムレコードに設定を送るTest
 */
@RunWith(JMockit.class)
public class SendTimeRecordSettingServiceTest {

	@Injectable
	private SendTimeRecordSettingService.Require require;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	/*
	 * テストしたい内容
	 * 
	 * →設定を送信しない
	 * 
	 * 準備するデータ
	 * 
	 * →設定がない
	 */
	@Test
	public void testSendEmpty() {

		Optional<SendTimeRecordSetting> actual = SendTimeRecordSettingService.send(require, new EmpInfoTerminalCode("1"),
				new ContractCode("1"));
		assertThat(actual).isEqualTo(Optional.empty());
	}

	/*
	 * テストしたい内容
	 * 
	 * →設定を送信する
	 * 
	 * →値がfalse
	 * 
	 * 準備するデータ
	 * 
	 * →設定がある
	 * 
	 * →リスト「社員ID、勤務種類コード」があるが送信設定がfalse
	 */
	@Test
	public void testSendTimeRecord() {

		Optional<TimeRecordReqSetting> timeRecordReqSetting = Optional
				.of(new ReqSettingBuilder(new EmpInfoTerminalCode("1"), new ContractCode("1"), new CompanyId("1"), "1",
						Arrays.asList(new EmployeeId("1")), Arrays.asList(1), Arrays.asList(new WorkTypeCode("1")))
								.overTimeHoliday(false).applicationReason(false).stampReceive(false)
								.reservationReceive(false).applicationReceive(false).timeSetting(false)
								.sendEmployeeId(false).sendBentoMenu(false).sendWorkType(false).sendWorkTime(false)
								.workTime(Collections.emptyList()).build());
		new Expectations() {
			{

				require.getTimeRecordReqSetting((EmpInfoTerminalCode) any, (ContractCode) any);
				result = timeRecordReqSetting;
			}
		};
		Optional<SendTimeRecordSetting> actual = SendTimeRecordSettingService.send(require, new EmpInfoTerminalCode("1"),
				new ContractCode("1"));

		assertThat(actual.get()).isEqualTo(new SendTimeRecordSetting.SettingBuilder(false, false, false, false, false)
				.createReq7(false).createReq8(false).createReq9(false).createReq10(false).createReq11(false).build());
	}

	/*
	 * テストしたい内容
	 * 
	 * →設定を送信する
	 * 
	 * →値がtrue
	 * 
	 * 準備するデータ
	 * 
	 * →設定がある
	 * 
	 * →リスト「社員ID、勤務種類コード」がある、送信設定がtrue
	 */
	@Test
	public void testSendTimeRecord2() {

		Optional<TimeRecordReqSetting> timeRecordReqSetting = Optional
				.of(new ReqSettingBuilder(new EmpInfoTerminalCode("1"), new ContractCode("1"), new CompanyId("1"), "1",
						Arrays.asList(new EmployeeId("1")), Arrays.asList(1), Arrays.asList(new WorkTypeCode("1")))
								.overTimeHoliday(true).applicationReason(true).stampReceive(true)
								.reservationReceive(true).applicationReceive(true).timeSetting(true)
								.sendEmployeeId(true).sendBentoMenu(true).sendWorkType(true).sendWorkTime(true)
								.workTime(Arrays.asList(new WorkTimeCode("1"))).build());
		new Expectations() {
			{

				require.getTimeRecordReqSetting((EmpInfoTerminalCode) any, (ContractCode) any);
				result = timeRecordReqSetting;
			}
		};
		Optional<SendTimeRecordSetting> actual2 = SendTimeRecordSettingService.send(require, new EmpInfoTerminalCode("1"),
				new ContractCode("1"));

		assertThat(actual2.get()).isEqualTo(new SendTimeRecordSetting.SettingBuilder(true, true, true, true, true)
				.createReq7(true).createReq8(true).createReq9(true).createReq10(true).createReq11(true).build());
	}

}
