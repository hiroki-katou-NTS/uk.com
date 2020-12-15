package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import static org.assertj.core.api.Assertions.assertThat;

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
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MonitorIntervalTime;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.OutPlaceConvert;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.PartialIpAddress;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.adapter.imploymentinfoterminal.infoterminal.EmpInfoTerminalComStatusImport;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ComState;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ConvertEmbossCategory;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.CreateStampInfo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerSerialNo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalName;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.FullIpAddress;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MacAddress;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ModelEmpInfoTer;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.StateCount;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TerminalComStatus;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TerminalCurrentState;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal.EmpInfoTerminalBuilder;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@RunWith(JMockit.class)
public class JudgCurrentStatusEmpInfoTerminalTest {

	@Injectable
	private JudgCurrentStatusEmpInfoTerminal.Require require;
	
	@Test
	public void testJudgCurrentStatusEmpInfoTerminal() {
		
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
		
		EmpInfoTerminalComStatusImport empInfoTerminalComStatusImport = new EmpInfoTerminalComStatusImport(new ContractCode("1"),
																		new EmpInfoTerminalCode("1"),
																		GeneralDateTime.now().addHours(-60));
		
		List<EmpInfoTerminalComStatusImport> listEmpInfoTerminalComStatus = new ArrayList<EmpInfoTerminalComStatusImport>();
		listEmpInfoTerminalComStatus.add(empInfoTerminalComStatusImport);
		
		new Expectations() {
			{
				require.get(contractCode, listEmpInfoTerminalCode);
				result = listEmpInfoTerminalComStatus;
			}
		};
		
		Map<EmpInfoTerminalCode, Optional<GeneralDateTime>> mapSignalLastTime = listEmpInfoTerminalComStatus.stream()
				.collect(Collectors.toMap(e -> e.getEmpInfoTerCode(), e -> Optional.ofNullable(e.getSignalLastTime())));
		
		List<ComState> listComstate = empInfoTerminalList.stream()
				.map(e -> ComState.createComState(e.getEmpInfoTerCode(), JudgCurrentStatusEmpInfoTerminal.judgmentComStatus(listEmpInfoTerminalComStatus, e.getEmpInfoTerCode(), e.getIntervalTime()), mapSignalLastTime.get(e.getEmpInfoTerCode())))
				.collect(Collectors.toList());
		List<StateCount> listStateCount = new ArrayList<StateCount>();
		
		int totalNumberOfTer = listEmpInfoTerminalCode.size();
		int numOfNormal = (int) listComstate.stream()
						.filter(e -> e.getTerminalCurrentState().value == TerminalCurrentState.NORMAL.value).count();
		int numOfAbnormal = (int) listComstate.stream()
						.filter(e -> e.getTerminalCurrentState().value == TerminalCurrentState.ABNORMAL.value).count();
		int numOfNotCom = (int) listComstate.stream()
						.filter(e -> e.getTerminalCurrentState().value == TerminalCurrentState.NOT_COMMUNICATED.value).count();
		
		listStateCount.add(StateCount.createStateCount(TerminalCurrentState.NORMAL, numOfNormal));
		listStateCount.add(StateCount.createStateCount(TerminalCurrentState.ABNORMAL, numOfAbnormal));
		listStateCount.add(StateCount.createStateCount(TerminalCurrentState.NOT_COMMUNICATED, numOfNotCom));
		
		val judgCurrentStatusEmpInfoTerminal = JudgCurrentStatusEmpInfoTerminal.judgingTerminalCurrentState(require, contractCode, empInfoTerminalList);
		TerminalComStatus terminalComStatus = TerminalComStatus.createTerminalComStatus(listStateCount, listComstate, totalNumberOfTer);
		
		assertThat(judgCurrentStatusEmpInfoTerminal.getTotalNumberOfTer()).isEqualTo(terminalComStatus.getTotalNumberOfTer());
		assertThat(judgCurrentStatusEmpInfoTerminal.getListComstate().size()).isEqualTo(terminalComStatus.getListComstate().size());
		assertThat(judgCurrentStatusEmpInfoTerminal.getListStateCount().size()).isEqualTo(terminalComStatus.getListStateCount().size());	
	}
	
	@Test
	public void judgmentComStatus1() {
		
		List<EmpInfoTerminalComStatusImport> listEmpInfoTerminalComStatus = Collections.emptyList();
		EmpInfoTerminalCode empInfoTerCode = new EmpInfoTerminalCode("1111");
		val intervalTime = new MonitorIntervalTime(1);
		
		TerminalCurrentState actual = JudgCurrentStatusEmpInfoTerminal.judgmentComStatus(listEmpInfoTerminalComStatus, empInfoTerCode, intervalTime);
		
		assertThat(actual).isEqualTo(TerminalCurrentState.NOT_COMMUNICATED);
	}
	
	// 通信異常があったか判断する(監視間隔時間): true
	@Test
	public void judgmentComStatus2() {
		
		List<EmpInfoTerminalComStatusImport> listEmpInfoTerminalComStatus = new ArrayList<EmpInfoTerminalComStatusImport>();
		EmpInfoTerminalComStatusImport empInfoTerminalComStatusImport = new EmpInfoTerminalComStatusImport(new ContractCode("1"),
																					new EmpInfoTerminalCode("1111"),
																					GeneralDateTime.now().addHours(-60));
		listEmpInfoTerminalComStatus.add(empInfoTerminalComStatusImport);
		EmpInfoTerminalCode empInfoTerCode = new EmpInfoTerminalCode("1111");
		val intervalTime = new MonitorIntervalTime(1);
		
		TerminalCurrentState actual = JudgCurrentStatusEmpInfoTerminal.judgmentComStatus(listEmpInfoTerminalComStatus, empInfoTerCode, intervalTime);
		
		assertThat(actual).isEqualTo(TerminalCurrentState.ABNORMAL);
	}
	
	// 通信異常があったか判断する(監視間隔時間): false
	@Test
	public void judgmentComStatus3() {
		
		List<EmpInfoTerminalComStatusImport> listEmpInfoTerminalComStatus = new ArrayList<EmpInfoTerminalComStatusImport>();
		EmpInfoTerminalComStatusImport empInfoTerminalComStatusImport = new EmpInfoTerminalComStatusImport(new ContractCode("1"),
																					new EmpInfoTerminalCode("1111"),
																					GeneralDateTime.now().addHours(60));
		listEmpInfoTerminalComStatus.add(empInfoTerminalComStatusImport);
		EmpInfoTerminalCode empInfoTerCode = new EmpInfoTerminalCode("1111");
		val intervalTime = new MonitorIntervalTime(1);
		
		TerminalCurrentState actual = JudgCurrentStatusEmpInfoTerminal.judgmentComStatus(listEmpInfoTerminalComStatus, empInfoTerCode, intervalTime);
		
		assertThat(actual).isEqualTo(TerminalCurrentState.NORMAL);
	}
	
	
}
