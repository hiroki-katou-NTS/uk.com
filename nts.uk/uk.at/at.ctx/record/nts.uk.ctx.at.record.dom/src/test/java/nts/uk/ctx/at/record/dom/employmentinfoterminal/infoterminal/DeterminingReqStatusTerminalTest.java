package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service.DeterminingReqStatusTerminal;

@RunWith(JMockit.class)
public class DeterminingReqStatusTerminalTest {

	@Injectable
	private DeterminingReqStatusTerminal.RequireDetermining require;
	
	// リクエストFlag： false
	@Test
	public void testDeterminingReqStatusTerminal() {
		
		List<EmpInfoTerminal> empInfoTerminalList = DeterminingReqStatusTerminalTestHelper.createEmpInfoTerminalList();
		List<EmpInfoTerminalCode> listEmpInfoTerminalCode = empInfoTerminalList.stream().map(e -> e.getEmpInfoTerCode()).collect(Collectors.toList());
		List<TimeRecordReqSetting> listTimeRecordReqSetting = DeterminingReqStatusTerminalTestHelper.createListTimeRecordReqSetting();
		
		new Expectations() {
			{
				require.get(DeterminingReqStatusTerminalTestHelper.contractCode, listEmpInfoTerminalCode);
				result = listTimeRecordReqSetting;
			}
		};
		
		val actual = DeterminingReqStatusTerminal.determiningReqStatusTerminal(require, DeterminingReqStatusTerminalTestHelper.contractCode, empInfoTerminalList);
		assertThat(actual.get(DeterminingReqStatusTerminalTestHelper.empInfoTerminalCode)).isFalse();
	}	
	
	// リクエストFlag： true
	@Test
	public void testDeterminingReqStatusTerminal1() {
		List<EmpInfoTerminal> empInfoTerminalList = DeterminingReqStatusTerminalTestHelper.createEmpInfoTerminalList();
		List<EmpInfoTerminalCode> listEmpInfoTerminalCode = empInfoTerminalList.stream().map(e -> e.getEmpInfoTerCode()).collect(Collectors.toList());
		List<TimeRecordReqSetting> listTimeRecordReqSetting = DeterminingReqStatusTerminalTestHelper.createListTimeRecordReqSetting1();
		
		new Expectations() {
			{
				require.get(DeterminingReqStatusTerminalTestHelper.contractCode, listEmpInfoTerminalCode);
				result = listTimeRecordReqSetting;
			}
		};
		
		val actual = DeterminingReqStatusTerminal.determiningReqStatusTerminal(require, DeterminingReqStatusTerminalTestHelper.contractCode, empInfoTerminalList);
		assertThat(actual.get(DeterminingReqStatusTerminalTestHelper.empInfoTerminalCode)).isTrue();
	}
	
	// 就業情報端末のリクエスト一覧 Empty
		@Test
		public void testDeterminingReqStatusTerminal2() {
			
			List<EmpInfoTerminal> empInfoTerminalList = new ArrayList<EmpInfoTerminal>();
			List<EmpInfoTerminalCode> listEmpInfoTerminalCode = empInfoTerminalList.stream().map(e -> e.getEmpInfoTerCode()).collect(Collectors.toList());
			
			new Expectations() {
				{
					require.get(DeterminingReqStatusTerminalTestHelper.contractCode, listEmpInfoTerminalCode);
					result = Collections.EMPTY_LIST;
				}
			};
			
			val actual = DeterminingReqStatusTerminal.determiningReqStatusTerminal(require, DeterminingReqStatusTerminalTestHelper.contractCode, empInfoTerminalList);
			assertThat(actual.isEmpty()).isTrue();
		}
	
}
