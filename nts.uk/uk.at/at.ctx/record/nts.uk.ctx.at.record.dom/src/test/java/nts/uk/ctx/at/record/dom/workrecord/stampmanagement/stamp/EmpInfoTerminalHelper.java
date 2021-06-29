/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.CreateStampInfo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerSerialNo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal.EmpInfoTerminalBuilder;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalName;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MacAddress;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ModelEmpInfoTer;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MonitorIntervalTime;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.shr.com.net.Ipv4Address;

/**
 * @author laitv
 *
 */
public class EmpInfoTerminalHelper {
	
	public static EmpInfoTerminal getEmpInfoTerminalDefault() {
		EmpInfoTerminal empInfoTerminal = new EmpInfoTerminalBuilder(Optional.of(
				Ipv4Address.parse("192.168.50.4")), 
				new MacAddress("AABBCCDD"),
				new EmpInfoTerminalCode("1"), 
				Optional.of(new EmpInfoTerSerialNo("1")), 
				new EmpInfoTerminalName(""),
				new ContractCode("1"))
				.createStampInfo(new CreateStampInfo(null, null, Optional.of(new WorkLocationCD("WCD")), Optional.of(new WorkplaceId("WID"))))
				.modelEmpInfoTer(ModelEmpInfoTer.NRL_1)
				.intervalTime((new MonitorIntervalTime(1))).build();
		return empInfoTerminal;
	}
	
	public static EmpInfoTerminal getEmpInfoTerminalDefault2() {
		EmpInfoTerminal empInfoTerminal = new EmpInfoTerminalBuilder(Optional.of(
				Ipv4Address.parse("192.168.50.4")), 
				new MacAddress("AABBCCDD"),
				new EmpInfoTerminalCode("1"), 
				Optional.of(new EmpInfoTerSerialNo("1")), 
				new EmpInfoTerminalName(""),
				new ContractCode("1"))
				.createStampInfo(null)
				.modelEmpInfoTer(ModelEmpInfoTer.NRL_1)
				.intervalTime((new MonitorIntervalTime(1))).build();
		return empInfoTerminal;
	}

}
