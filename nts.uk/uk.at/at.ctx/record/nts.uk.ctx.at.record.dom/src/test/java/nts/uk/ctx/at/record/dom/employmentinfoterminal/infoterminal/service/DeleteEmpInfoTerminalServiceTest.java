package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.adapter.imploymentinfoterminal.infoterminal.EmpInfoTerminalComStatusImport;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ResultOfDeletion;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

/**
 * 
 * @author dungbn
 *
 */
@RunWith(JMockit.class)
public class DeleteEmpInfoTerminalServiceTest {

	@Injectable
	private DeleteEmpInfoTerminalService.Require require;

	/**
	 * 就業情報端末を取得する(契約コード,就業情報端末コード) is empty -> $エラーか  = true
	 * 就業情報端末通信状況を取得する(契約コード,就業情報端末コード) not empty
	 */
	@Test
	public void testDeleteEmpInfoTerminalService_1() {
		ContractCode contractCode = new ContractCode("contract");
		EmpInfoTerminalCode empInfoTerminalCode = new EmpInfoTerminalCode("1");
		Optional<EmpInfoTerminalComStatusImport> empInfoTerminalComStatusImport = DeleteEmpInfoTerminalServiceTestHelper.createEmpInfoTerminalComStatusImport();
		new Expectations() {
			{
				require.getEmpInfoTerminal(empInfoTerminalCode, contractCode);
				result = Optional.empty();
				require.get(contractCode, empInfoTerminalCode);
				result = empInfoTerminalComStatusImport;
			}
		};
		ResultOfDeletion resultOfDeletion = DeleteEmpInfoTerminalService.create(require, contractCode.v(), empInfoTerminalCode.v());
		
		assertThat(resultOfDeletion.isError()).isTrue();
		assertThat(resultOfDeletion.getDeleteEmpInfoTerminal()).isEmpty();
		
		NtsAssert.atomTask(
				() -> DeleteEmpInfoTerminalService.create(require, contractCode.v(), empInfoTerminalCode.v()).getDeleteEmpInfoTerminalComStatus().get(),
				any -> require.delete(empInfoTerminalComStatusImport.get()));
		
	}

	/**
	 *  就業情報端末を取得する(契約コード,就業情報端末コード) not empty -> $エラーか  = false
	 *  就業情報端末通信状況を取得する(契約コード,就業情報端末コード) is empty
	 */
	@Test
	public void testDeleteEmpInfoTerminalService_2() {
		ContractCode contractCode = new ContractCode("1");
		EmpInfoTerminalCode empInfoTerminalCode = new EmpInfoTerminalCode("1");
		Optional<EmpInfoTerminal> empInfoTerminal = DeleteEmpInfoTerminalServiceTestHelper.createEmpInfoTerminal();
		new Expectations() {
			{
				require.getEmpInfoTerminal(empInfoTerminalCode, contractCode);
				result = empInfoTerminal;
				require.get(contractCode, empInfoTerminalCode);
				result = Optional.empty();
			}
		};
		
		ResultOfDeletion resultOfDeletion = DeleteEmpInfoTerminalService.create(require, contractCode.v(), empInfoTerminalCode.v());
		
		assertThat(resultOfDeletion.isError()).isFalse();
		assertThat(resultOfDeletion.getDeleteEmpInfoTerminalComStatus()).isEmpty();
		
		NtsAssert.atomTask(
				() -> DeleteEmpInfoTerminalService.create(require, contractCode.v(), empInfoTerminalCode.v()).getDeleteEmpInfoTerminal().get(),
				any -> require.delete(empInfoTerminal.get())
		);
	}
	
	/**
	 *  就業情報端末を取得する(契約コード,就業情報端末コード) not empty -> $エラーか  = false 
	 *  就業情報端末通信状況を取得する(契約コード,就業情報端末コード) not empty; 
	 */
	@Test
	public void testDeleteEmpInfoTerminalService_3() {
		ContractCode contractCode = new ContractCode("1");
		EmpInfoTerminalCode empInfoTerminalCode = new EmpInfoTerminalCode("1");
		
		Optional<EmpInfoTerminal> empInfoTerminal = DeleteEmpInfoTerminalServiceTestHelper.createEmpInfoTerminal();
		Optional<EmpInfoTerminalComStatusImport> empInfoTerminalComStatusImport = DeleteEmpInfoTerminalServiceTestHelper.createEmpInfoTerminalComStatusImport();
		
		new Expectations() {
			{
				require.getEmpInfoTerminal(empInfoTerminalCode, contractCode);
				result = empInfoTerminal;
				require.get(contractCode, empInfoTerminalCode);
				result = empInfoTerminalComStatusImport;
			}
		};
		
		ResultOfDeletion resultOfDeletion = DeleteEmpInfoTerminalService.create(require, contractCode.v(), empInfoTerminalCode.v());
		assertThat(resultOfDeletion.isError()).isFalse();
		
		NtsAssert.atomTask(
				() -> resultOfDeletion.getDeleteEmpInfoTerminal().get(),
				any -> require.delete( empInfoTerminal.get())
		);
		NtsAssert.atomTask(
				() -> resultOfDeletion.getDeleteEmpInfoTerminalComStatus().get(),
				any -> require.delete(empInfoTerminalComStatusImport.get())
		);
	}
	
	/**
	 * 就業情報端末を取得する(契約コード,就業情報端末コード) is empty -> $エラーか  = true 
	 *  就業情報端末通信状況を取得する(契約コード,就業情報端末コード) is empty; 
	 */
	@Test
	public void testDeleteEmpInfoTerminalService_4() {
		ContractCode contractCode = new ContractCode("1");
		EmpInfoTerminalCode empInfoTerminalCode = new EmpInfoTerminalCode("1");
		new Expectations() {
			{
				require.getEmpInfoTerminal(empInfoTerminalCode, contractCode);
				result = Optional.empty();
				require.get(contractCode, empInfoTerminalCode);
				result = Optional.empty();
			}
		};
		
		ResultOfDeletion resultOfDeletion = DeleteEmpInfoTerminalService.create(require, contractCode.v(), empInfoTerminalCode.v());
		
		assertThat(resultOfDeletion.isError()).isTrue();
		assertThat(resultOfDeletion.getDeleteEmpInfoTerminal()).isEmpty();
		assertThat(resultOfDeletion.getDeleteEmpInfoTerminalComStatus()).isEmpty();
		
	}

}
