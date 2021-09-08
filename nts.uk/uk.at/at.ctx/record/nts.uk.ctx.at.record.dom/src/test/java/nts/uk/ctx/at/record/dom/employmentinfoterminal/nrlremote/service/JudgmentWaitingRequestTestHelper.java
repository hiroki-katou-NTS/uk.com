package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ConvertEmbossCategory;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.CreateStampInfo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerSerialNo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalName;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.FullIpAddress;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MacAddress;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ModelEmpInfoTer;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MonitorIntervalTime;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.OutPlaceConvert;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.PartialIpAddress;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.NRRomVersion;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetUpdateList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal.EmpInfoTerminalBuilder;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.net.Ipv4Address;

public class JudgmentWaitingRequestTestHelper {

	public static final ContractCode contractCode = new ContractCode("1");
	public static final EmpInfoTerminalCode empInfoTerminalCode = new EmpInfoTerminalCode("1");
	public static final EmpInfoTerminalCode empInfoTerminalCode2 = new EmpInfoTerminalCode("2");
	
	public static List<EmpInfoTerminal> createEmpInfoTerminalList() {
		
		EmpInfoTerminal empInfoTerminal = new EmpInfoTerminalBuilder(Optional.of(Ipv4Address.parse("192.168.1.1")), new MacAddress("AABBCCDD"),
				empInfoTerminalCode, Optional.of(new EmpInfoTerSerialNo("1")), new EmpInfoTerminalName(""),
				contractCode)
						.createStampInfo(new CreateStampInfo(new OutPlaceConvert(NotUseAtr.NOT_USE, Optional.empty()),
								new ConvertEmbossCategory(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE), Optional.empty(),Optional.empty()))
						.modelEmpInfoTer(ModelEmpInfoTer.NRL_1).intervalTime((new MonitorIntervalTime(1))).build();
		
		EmpInfoTerminal empInfoTerminal2 = new EmpInfoTerminalBuilder(Optional.of(Ipv4Address.parse("192.168.1.1")), new MacAddress("AABBCCDD"),
				empInfoTerminalCode2, Optional.of(new EmpInfoTerSerialNo("1")), new EmpInfoTerminalName(""),
				contractCode)
						.createStampInfo(new CreateStampInfo(new OutPlaceConvert(NotUseAtr.NOT_USE, Optional.empty()),
								new ConvertEmbossCategory(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE), Optional.empty(),Optional.empty()))
						.modelEmpInfoTer(ModelEmpInfoTer.NRL_1).intervalTime((new MonitorIntervalTime(1))).build();
		
		List<EmpInfoTerminal> empInfoTerminalList = new ArrayList<EmpInfoTerminal>();
		empInfoTerminalList.add(empInfoTerminal);
		empInfoTerminalList.add(empInfoTerminal2);
		
		return empInfoTerminalList;
	}
	
	public static List<TimeRecordSetFormatList> createListTimeRecordSetFormatList() {
		
		TimeRecordSetFormatList timeRecordSetFormatList = new TimeRecordSetFormatList(empInfoTerminalCode,
															new EmpInfoTerminalName(""), new NRRomVersion("003"),
															ModelEmpInfoTer.valueOf(7), Collections.emptyList());
		
		List<TimeRecordSetFormatList> listTimeRecordSetFormatList = new ArrayList<TimeRecordSetFormatList>();
		listTimeRecordSetFormatList.add(timeRecordSetFormatList);
		
		return listTimeRecordSetFormatList;
	}
	
	public static List<TimeRecordSetUpdateList> createListTimeRecordSetUpdateList() {
		
		TimeRecordSetUpdateList timeRecordSetUpdateList = new TimeRecordSetUpdateList(empInfoTerminalCode,
															new EmpInfoTerminalName(""), new NRRomVersion("003"),
															ModelEmpInfoTer.valueOf(7), Collections.emptyList());
		
		List<TimeRecordSetUpdateList> listTimeRecordSetUpdateList = new ArrayList<TimeRecordSetUpdateList>();
		listTimeRecordSetUpdateList.add(timeRecordSetUpdateList);
		
		
		return listTimeRecordSetUpdateList;
	}
	
	public static List<TimeRecordSetUpdateList> createListTimeRecordSetUpdateList1() {
		
		TimeRecordSetUpdateList timeRecordSetUpdateList = new TimeRecordSetUpdateList(empInfoTerminalCode,
															new EmpInfoTerminalName(""), new NRRomVersion("003"),
															ModelEmpInfoTer.valueOf(7), Collections.emptyList());
		
		TimeRecordSetUpdateList timeRecordSetUpdateList2 = new TimeRecordSetUpdateList(empInfoTerminalCode2,
				new EmpInfoTerminalName(""), new NRRomVersion("003"),
				ModelEmpInfoTer.valueOf(7), Collections.emptyList());
		
		List<TimeRecordSetUpdateList> listTimeRecordSetUpdateList = new ArrayList<TimeRecordSetUpdateList>();
		listTimeRecordSetUpdateList.add(timeRecordSetUpdateList);
		listTimeRecordSetUpdateList.add(timeRecordSetUpdateList2);
		
		
		return listTimeRecordSetUpdateList;
	}
	

}
