package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import java.util.Collections;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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

}
