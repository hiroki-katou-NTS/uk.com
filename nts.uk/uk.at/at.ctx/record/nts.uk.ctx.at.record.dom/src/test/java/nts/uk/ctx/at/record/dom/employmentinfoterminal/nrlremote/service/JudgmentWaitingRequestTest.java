package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
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

/**
 * 
 * @author dungbn
 *
 */
@RunWith(JMockit.class)
public class JudgmentWaitingRequestTest {

	@Injectable
	private JudgmentWaitingRequest.Require require;
	
	@Test
	public void testJudgmentWaitingRequest() {
		ContractCode contractCode = new ContractCode("1");
		EmpInfoTerminalCode empInfoTerminalCode = new EmpInfoTerminalCode("1");
		
		EmpInfoTerminal empInfoTerminal = new EmpInfoTerminalBuilder(Optional.of(new FullIpAddress(
				new PartialIpAddress(192), new PartialIpAddress(168), new PartialIpAddress(1), new PartialIpAddress(1))), new MacAddress("AABBCCDD"),
				new EmpInfoTerminalCode("1"), Optional.of(new EmpInfoTerSerialNo("1")), new EmpInfoTerminalName(""),
				contractCode)
						.createStampInfo(new CreateStampInfo(new OutPlaceConvert(NotUseAtr.NOT_USE, Optional.empty()),
								new ConvertEmbossCategory(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE), Optional.empty()))
						.modelEmpInfoTer(ModelEmpInfoTer.NRL_1).intervalTime((new MonitorIntervalTime(1))).build();
		
		List<EmpInfoTerminal> empInfoTerminalList = new ArrayList<EmpInfoTerminal>();
		empInfoTerminalList.add(empInfoTerminal);
		List<EmpInfoTerminalCode> listEmpInfoTerminalCode = empInfoTerminalList.stream().map(e -> e.getEmpInfoTerCode()).collect(Collectors.toList());
		
		TimeRecordSetFormatList timeRecordSetFormatList = new TimeRecordSetFormatList(
				new EmpInfoTerminalCode("1"), new EmpInfoTerminalName(""), new NRRomVersion("003"),
				ModelEmpInfoTer.valueOf(7), Collections.emptyList());
		
		List<TimeRecordSetFormatList> listTimeRecordSetFormatList = new ArrayList<TimeRecordSetFormatList>();
		listTimeRecordSetFormatList.add(timeRecordSetFormatList);
		
		TimeRecordSetUpdateList timeRecordSetUpdateList = new TimeRecordSetUpdateList(new EmpInfoTerminalCode("1"),
				new EmpInfoTerminalName(""), new NRRomVersion("003"), ModelEmpInfoTer.valueOf(7), Collections.emptyList());
		
		List<TimeRecordSetUpdateList> listTimeRecordSetUpdateList = new ArrayList<TimeRecordSetUpdateList>();
		listTimeRecordSetUpdateList.add(timeRecordSetUpdateList);
		
		new Expectations() {
			{
				require.getTimeRecordSetFormatList(contractCode, listEmpInfoTerminalCode);
				result = listTimeRecordSetFormatList;
				require.getTimeRecordUpdateList(contractCode, listEmpInfoTerminalCode);
				result = listTimeRecordSetUpdateList;
			}
		};
		
		Map<EmpInfoTerminalCode, Integer> mapCodeSeveralItem = listTimeRecordSetFormatList.stream().collect(Collectors.toMap(e -> e.getEmpInfoTerCode(), e -> listTimeRecordSetFormatList.size()));
		
		Map<EmpInfoTerminalCode, Boolean> mapCodeFlag = listTimeRecordSetUpdateList.stream()
				.collect(Collectors.toMap(e -> e.getEmpInfoTerCode(), e -> e.isWaitingReqRecovery(mapCodeSeveralItem.get(e.getEmpInfoTerCode()))));
		
		val actual = JudgmentWaitingRequest.judgmentReqWaitingStatus(require, contractCode, empInfoTerminalList);
		
//		assertThat(actual).isEqualTo(mapCodeFlag);
		
		assertThat(actual.get(empInfoTerminalCode)).isEqualTo(mapCodeFlag.get(empInfoTerminalCode));
		
	}
}
