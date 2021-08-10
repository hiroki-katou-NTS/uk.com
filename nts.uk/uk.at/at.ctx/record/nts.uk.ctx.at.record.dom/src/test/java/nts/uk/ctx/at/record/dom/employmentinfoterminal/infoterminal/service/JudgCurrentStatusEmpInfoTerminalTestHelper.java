package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import java.util.Optional;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.adapter.employmentinfoterminal.infoterminal.EmpInfoTerminalComStatusImport;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.CreateStampInfo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerSerialNo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal.EmpInfoTerminalBuilder;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalName;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MacAddress;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ModelEmpInfoTer;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MonitorIntervalTime;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.NRConvertInfo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.OutPlaceConvert;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.net.Ipv4Address;

public class JudgCurrentStatusEmpInfoTerminalTestHelper {

	public static final ContractCode contractCode = new ContractCode("1");
	public static final EmpInfoTerminalCode empInfoTerminalCode = new EmpInfoTerminalCode("1111");

	public static EmpInfoTerminal createEmpInfoTerminal() {
		CreateStampInfo temFix = new CreateStampInfo(
				new NRConvertInfo(new OutPlaceConvert(NotUseAtr.NOT_USE, Optional.of(GoingOutReason.PRIVATE)),
						NotUseAtr.NOT_USE),
				Optional.empty(), Optional.empty());
		EmpInfoTerminal empInfoTerminal = new EmpInfoTerminalBuilder(
				Optional.of(Ipv4Address.parse("192.168.1.1")),
				new MacAddress("AABBCCDD"), empInfoTerminalCode, Optional.of(new EmpInfoTerSerialNo("1")),
				new EmpInfoTerminalName(""), contractCode)
						.createStampInfo(temFix)
						.modelEmpInfoTer(ModelEmpInfoTer.NRL_1).intervalTime((new MonitorIntervalTime(1))).build();

		return empInfoTerminal;
	}

	public static EmpInfoTerminalComStatusImport createEmpInfoTerminalComStatusImport() {

		return new EmpInfoTerminalComStatusImport(contractCode, empInfoTerminalCode,
				GeneralDateTime.now().addHours(-60));
	}

	public static EmpInfoTerminalComStatusImport createEmpInfoTerminalComStatusImport1() {

		return new EmpInfoTerminalComStatusImport(contractCode, empInfoTerminalCode,
				GeneralDateTime.now().addHours(60));
	}
	
	public static EmpInfoTerminalComStatusImport createEmpInfoTerminalComStatusImport2() {
		return new EmpInfoTerminalComStatusImport(contractCode, empInfoTerminalCode,
				GeneralDateTime.now());
	}
}
