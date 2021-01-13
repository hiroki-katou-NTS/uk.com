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
	
}
