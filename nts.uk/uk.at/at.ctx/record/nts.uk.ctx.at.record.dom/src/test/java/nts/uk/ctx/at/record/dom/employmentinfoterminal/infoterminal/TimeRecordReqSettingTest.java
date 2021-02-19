package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting.ReqSettingBuilder;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * @author ThanhNX
 *
 *         タイムレコードのﾘｸｴｽﾄ設定Test
 */
public class TimeRecordReqSettingTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void getters() {

		TimeRecordReqSetting target = new ReqSettingBuilder(new EmpInfoTerminalCode("1"), new ContractCode("1"),
				new CompanyId(""), "", Collections.emptyList(), Collections.emptyList(), Collections.emptyList())
						.workTime(Collections.emptyList()).overTimeHoliday(false).applicationReason(false)
						.stampReceive(false).reservationReceive(false).applicationReason(false)
						.applicationReceive(false).timeSetting(false).build();
		NtsAssert.invokeGetters(target);
	}
	
	@Test
	public void determiningReqStatusTerminal1() {
		TimeRecordReqSetting target = new ReqSettingBuilder(new EmpInfoTerminalCode("1"), new ContractCode("1"),
				new CompanyId(""), "", Collections.emptyList(), Collections.emptyList(), Collections.emptyList())
						.workTime(Collections.emptyList()).overTimeHoliday(true).applicationReason(true)
						.stampReceive(false).reservationReceive(false)
						.applicationReceive(false).timeSetting(false).build();
		val result = target.determiningReqStatusTerminal();
		assertThat(result).isTrue();
	}
	
	@Test
	public void determiningReqStatusTerminal2() {
		TimeRecordReqSetting target = new ReqSettingBuilder(new EmpInfoTerminalCode("1"), new ContractCode("1"),
				new CompanyId(""), "", Collections.emptyList(), Collections.emptyList(), Collections.emptyList())
						.workTime(Collections.emptyList()).overTimeHoliday(false).applicationReason(false)
						.stampReceive(false).reservationReceive(false).reboot(false).sendEmployeeId(false)
						.applicationReceive(false).timeSetting(false).remoteSetting(false)
						.sendWorkType(false).sendWorkTime(false).sendBentoMenu(false)
						.build();
		val result = target.determiningReqStatusTerminal();
		assertThat(result).isFalse();
	}
}
