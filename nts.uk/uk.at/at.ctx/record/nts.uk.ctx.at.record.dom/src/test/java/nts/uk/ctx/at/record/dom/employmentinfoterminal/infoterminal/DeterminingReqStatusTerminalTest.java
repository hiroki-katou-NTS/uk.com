package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal.EmpInfoTerminalBuilder;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting.ReqSettingBuilder;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service.DeterminingReqStatusTerminal;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@RunWith(JMockit.class)
public class DeterminingReqStatusTerminalTest {

	@Injectable
	private DeterminingReqStatusTerminal.RequireDetermining require;
	
	@Test
	public void testDeterminingReqStatusTerminal() {
		
		ContractCode contractCode = new ContractCode("1");
		EmpInfoTerminalCode empInfoTerminalCode = new EmpInfoTerminalCode("1");
		
		EmpInfoTerminal empInfoTerminal = new EmpInfoTerminalBuilder(Optional.of(new FullIpAddress(
				new PartialIpAddress(192), new PartialIpAddress(168), new PartialIpAddress(1), new PartialIpAddress(1))), new MacAddress("AABBCCDD"),
				empInfoTerminalCode, Optional.of(new EmpInfoTerSerialNo("1")), new EmpInfoTerminalName(""),
				contractCode)
						.createStampInfo(new CreateStampInfo(new OutPlaceConvert(NotUseAtr.NOT_USE, Optional.empty()),
								new ConvertEmbossCategory(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE), Optional.empty()))
						.modelEmpInfoTer(ModelEmpInfoTer.NRL_1).intervalTime((new MonitorIntervalTime(1))).build();
		
		List<EmpInfoTerminal> empInfoTerminalList = new ArrayList<EmpInfoTerminal>();
		empInfoTerminalList.add(empInfoTerminal);
		List<EmpInfoTerminalCode> listEmpInfoTerminalCode = empInfoTerminalList.stream().map(e -> e.getEmpInfoTerCode()).collect(Collectors.toList());
		
		TimeRecordReqSetting timeRecordReqSetting = new ReqSettingBuilder(new EmpInfoTerminalCode("1"), new ContractCode("1"),
													new CompanyId(""), "", Collections.emptyList(), Collections.emptyList(), Collections.emptyList())
													.workTime(Collections.emptyList()).overTimeHoliday(false).applicationReason(false)
													.stampReceive(false).reservationReceive(false).reboot(false).sendEmployeeId(false)
													.applicationReceive(false).timeSetting(false).remoteSetting(false)
													.sendWorkType(false).sendWorkTime(false).sendBentoMenu(false)
													.build();
		
		List<TimeRecordReqSetting> listTimeRecordReqSetting = new ArrayList<TimeRecordReqSetting>();
		listTimeRecordReqSetting.add(timeRecordReqSetting);
		
		new Expectations() {
			{
				require.get(contractCode, listEmpInfoTerminalCode);
				result = listTimeRecordReqSetting;
			}
		};
		
		val actual = DeterminingReqStatusTerminal.determiningReqStatusTerminal(require, contractCode, empInfoTerminalList);
		
		assertThat(actual.get(empInfoTerminalCode)).isFalse();
		
	}
	
}
