package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal.EmpInfoTerminalBuilder;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting.ReqSettingBuilder;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.net.Ipv4Address;

public class DeterminingReqStatusTerminalTestHelper {

	public static final ContractCode contractCode = new ContractCode("1");
	public static final EmpInfoTerminalCode empInfoTerminalCode = new EmpInfoTerminalCode("1");
	
	public static List<EmpInfoTerminal> createEmpInfoTerminalList() {
		
		EmpInfoTerminal empInfoTerminal = new EmpInfoTerminalBuilder(Optional.of(Ipv4Address.parse("192.168.1.1")), new MacAddress("AABBCCDD"),
				empInfoTerminalCode, Optional.of(new EmpInfoTerSerialNo("1")), new EmpInfoTerminalName(""),
				contractCode)
						.createStampInfo(new CreateStampInfo(new OutPlaceConvert(NotUseAtr.NOT_USE, Optional.empty()),
								new ConvertEmbossCategory(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE), Optional.empty(), Optional.empty()))
						.modelEmpInfoTer(ModelEmpInfoTer.NRL_1).intervalTime((new MonitorIntervalTime(1))).build();
		
		List<EmpInfoTerminal> empInfoTerminalList = new ArrayList<EmpInfoTerminal>();
		empInfoTerminalList.add(empInfoTerminal);
		
		return empInfoTerminalList;
	}
	
	// root = true 
	public static List<TimeRecordReqSetting> createListTimeRecordReqSetting() {
		
		TimeRecordReqSetting timeRecordReqSetting = new ReqSettingBuilder(new EmpInfoTerminalCode("1"), new ContractCode("1"),
				new CompanyId(""), "", Collections.emptyList(), Collections.emptyList(), Collections.emptyList())
				.workTime(Collections.emptyList()).overTimeHoliday(false).applicationReason(false)
				.stampReceive(false).reservationReceive(false).reboot(false).sendEmployeeId(false)
				.applicationReceive(false).timeSetting(false).remoteSetting(false)
				.sendWorkType(false).sendWorkTime(false).sendBentoMenu(false)
				.build();
		
		List<TimeRecordReqSetting> listTimeRecordReqSetting = new ArrayList<TimeRecordReqSetting>();
		listTimeRecordReqSetting.add(timeRecordReqSetting);
		
		return listTimeRecordReqSetting;
	}

	// reboot = false
	public static List<TimeRecordReqSetting> createListTimeRecordReqSetting1() {
		
		TimeRecordReqSetting timeRecordReqSetting = new ReqSettingBuilder(new EmpInfoTerminalCode("1"), new ContractCode("1"),
				new CompanyId(""), "", Collections.emptyList(), Collections.emptyList(), Collections.emptyList())
				.workTime(Collections.emptyList()).overTimeHoliday(false).applicationReason(false)
				.stampReceive(false).reservationReceive(false).reboot(true).sendEmployeeId(false)
				.applicationReceive(false).timeSetting(false).remoteSetting(false)
				.sendWorkType(false).sendWorkTime(false).sendBentoMenu(false)
				.build();
		
		List<TimeRecordReqSetting> listTimeRecordReqSetting = new ArrayList<TimeRecordReqSetting>();
		listTimeRecordReqSetting.add(timeRecordReqSetting);
		
		return listTimeRecordReqSetting;
	}
	
	
}
