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
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.adapter.employmentinfoterminal.infoterminal.EmpInfoTerminalComStatusImport;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ComState;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MonitorIntervalTime;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.StateCount;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TerminalComStatus;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TerminalCurrentState;

@RunWith(JMockit.class)
public class JudgCurrentStatusEmpInfoTerminalTest {

	@Injectable
	private JudgCurrentStatusEmpInfoTerminal.Require require;
	
	// $就業情報端末通信状況ListがNOTEmpty
	@Test
	public void testJudgCurrentStatusEmpInfoTerminal() {
		
		EmpInfoTerminal empInfoTerminal = JudgCurrentStatusEmpInfoTerminalTestHelper.createEmpInfoTerminal();
		
		List<EmpInfoTerminal> empInfoTerminalList = new ArrayList<EmpInfoTerminal>();
		empInfoTerminalList.add(empInfoTerminal);
		
		List<EmpInfoTerminalCode> listEmpInfoTerminalCode = empInfoTerminalList.stream().map(e -> e.getEmpInfoTerCode()).collect(Collectors.toList());
		EmpInfoTerminalComStatusImport empInfoTerminalComStatusImport = JudgCurrentStatusEmpInfoTerminalTestHelper.createEmpInfoTerminalComStatusImport();
		
		List<EmpInfoTerminalComStatusImport> listEmpInfoTerminalComStatus = new ArrayList<EmpInfoTerminalComStatusImport>();
		listEmpInfoTerminalComStatus.add(empInfoTerminalComStatusImport);
		
		new Expectations() {
			{
				require.get(JudgCurrentStatusEmpInfoTerminalTestHelper.contractCode, listEmpInfoTerminalCode);
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
		
		val judgCurrentStatusEmpInfoTerminal = JudgCurrentStatusEmpInfoTerminal.judgingTerminalCurrentState(require, JudgCurrentStatusEmpInfoTerminalTestHelper.contractCode, empInfoTerminalList);
		TerminalComStatus terminalComStatus = TerminalComStatus.createTerminalComStatus(listStateCount, listComstate, totalNumberOfTer);
		
		assertThat(judgCurrentStatusEmpInfoTerminal.getTotalNumberOfTer()).isEqualTo(terminalComStatus.getTotalNumberOfTer());
		assertThat(judgCurrentStatusEmpInfoTerminal.getListComstate().size()).isEqualTo(terminalComStatus.getListComstate().size());
		assertThat(judgCurrentStatusEmpInfoTerminal.getListStateCount().size()).isEqualTo(terminalComStatus.getListStateCount().size());	
	}
	
	// $就業情報端末通信状況ListがEmpty
	@Test
	public void testJudgCurrentStatusEmpInfoTerminal1() {
		
		List<EmpInfoTerminal> empInfoTerminalList = new ArrayList<EmpInfoTerminal>();
		List<EmpInfoTerminalCode> listEmpInfoTerminalCode = empInfoTerminalList.stream().map(e -> e.getEmpInfoTerCode()).collect(Collectors.toList());
		
		new Expectations() {
			{
				require.get(JudgCurrentStatusEmpInfoTerminalTestHelper.contractCode, listEmpInfoTerminalCode);
				result = Collections.EMPTY_LIST;
			}
		};
		
		List<ComState> listComstate = new ArrayList<ComState>();
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
		
		val judgCurrentStatusEmpInfoTerminal = JudgCurrentStatusEmpInfoTerminal.judgingTerminalCurrentState(require, JudgCurrentStatusEmpInfoTerminalTestHelper.contractCode, empInfoTerminalList);
		TerminalComStatus terminalComStatus = TerminalComStatus.createTerminalComStatus(listStateCount, listComstate, totalNumberOfTer);
		
		assertThat(judgCurrentStatusEmpInfoTerminal.getTotalNumberOfTer()).isEqualTo(terminalComStatus.getTotalNumberOfTer());
		assertThat(judgCurrentStatusEmpInfoTerminal.getListComstate().size()).isEqualTo(terminalComStatus.getListComstate().size());
		assertThat(judgCurrentStatusEmpInfoTerminal.getListStateCount().size()).isEqualTo(terminalComStatus.getListStateCount().size());
	}
	
	
	@Test
	public void judgmentComStatus1() {
		
		List<EmpInfoTerminalComStatusImport> listEmpInfoTerminalComStatus = Collections.emptyList();

		val intervalTime = new MonitorIntervalTime(1);
		
		TerminalCurrentState actual = JudgCurrentStatusEmpInfoTerminal.judgmentComStatus(listEmpInfoTerminalComStatus, JudgCurrentStatusEmpInfoTerminalTestHelper.empInfoTerminalCode, intervalTime);
		
		assertThat(actual).isEqualTo(TerminalCurrentState.NOT_COMMUNICATED);
	}
	
	// 通信異常があったか判断する(監視間隔時間): true
	@Test
	public void judgmentComStatus2() {
		
		List<EmpInfoTerminalComStatusImport> listEmpInfoTerminalComStatus = new ArrayList<EmpInfoTerminalComStatusImport>();
		EmpInfoTerminalComStatusImport empInfoTerminalComStatusImport = JudgCurrentStatusEmpInfoTerminalTestHelper.createEmpInfoTerminalComStatusImport();
		listEmpInfoTerminalComStatus.add(empInfoTerminalComStatusImport);
		
		val intervalTime = new MonitorIntervalTime(1);
		
		TerminalCurrentState actual = JudgCurrentStatusEmpInfoTerminal.judgmentComStatus(listEmpInfoTerminalComStatus, JudgCurrentStatusEmpInfoTerminalTestHelper.empInfoTerminalCode, intervalTime);
		
		assertThat(actual).isEqualTo(TerminalCurrentState.ABNORMAL);
	}
	
	// 通信異常があったか判断する(監視間隔時間): false　最終通信日時　+ 監視間隔時間　＞　日時。今
	@Test
	public void judgmentComStatus3() {
		
		List<EmpInfoTerminalComStatusImport> listEmpInfoTerminalComStatus = new ArrayList<EmpInfoTerminalComStatusImport>();
		EmpInfoTerminalComStatusImport empInfoTerminalComStatusImport = JudgCurrentStatusEmpInfoTerminalTestHelper.createEmpInfoTerminalComStatusImport1();
		listEmpInfoTerminalComStatus.add(empInfoTerminalComStatusImport);

		val intervalTime = new MonitorIntervalTime(1);
		
		TerminalCurrentState actual = JudgCurrentStatusEmpInfoTerminal.judgmentComStatus(listEmpInfoTerminalComStatus, JudgCurrentStatusEmpInfoTerminalTestHelper.empInfoTerminalCode, intervalTime);
		
		assertThat(actual).isEqualTo(TerminalCurrentState.NORMAL);
	}
	
	// 通信異常があったか判断する(監視間隔時間): false　最終通信日時　+ 監視間隔時間　＝　日時。今
//		@Test
//		public void judgmentComStatus4() {
//			
//			List<EmpInfoTerminalComStatusImport> listEmpInfoTerminalComStatus = new ArrayList<EmpInfoTerminalComStatusImport>();
//			EmpInfoTerminalComStatusImport empInfoTerminalComStatusImport = JudgCurrentStatusEmpInfoTerminalTestHelper.createEmpInfoTerminalComStatusImport2();
//			listEmpInfoTerminalComStatus.add(empInfoTerminalComStatusImport);
//
//			val intervalTime = new MonitorIntervalTime(0);
//			
//			TerminalCurrentState actual = JudgCurrentStatusEmpInfoTerminal.judgmentComStatus(listEmpInfoTerminalComStatus, JudgCurrentStatusEmpInfoTerminalTestHelper.empInfoTerminalCode, intervalTime);
//			
//			assertThat(actual).isEqualTo(TerminalCurrentState.NORMAL);
//		}
	
	
}
