package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.adapter.imploymentinfoterminal.infoterminal.EmpInfoTerminalComStatusImport;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ConvertEmbossCategory;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.CreateStampInfo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerSerialNo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal.EmpInfoTerminalBuilder;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalName;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.IPAddress;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MacAddress;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ModelEmpInfoTer;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MonitorIntervalTime;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.OutPlaceConvert;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ResultOfDeletion;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

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
	 * 就業情報端末を取得する(契約コード,就業情報端末コード) is empty 
	 */
	@Test
	public void testDeleteEmpInfoTerminalService_1() {
		ContractCode contractCode = new ContractCode("contract");
		EmpInfoTerminalCode empInfoTerminalCode = new EmpInfoTerminalCode(1);
		new Expectations() {
			{
				require.getEmpInfoTerminal(empInfoTerminalCode, contractCode);
				result = Optional.empty();
			}
		};
		ResultOfDeletion resultOfDeletion = DeleteEmpInfoTerminalService.create(require, contractCode.v(), empInfoTerminalCode.v().intValue());
		assertThat(resultOfDeletion.getDeleteEmpInfoTerminal()).isEmpty();
	}

	/**
	 *  就業情報端末を取得する(契約コード,就業情報端末コード) not empty; 
	 */
	@Test
	public void testDeleteEmpInfoTerminalService_2() {
		ContractCode contractCode = new ContractCode("1");
		EmpInfoTerminalCode empInfoTerminalCode = new EmpInfoTerminalCode(1);
		Optional<EmpInfoTerminal> empInfoTerminal = Optional.of(new EmpInfoTerminalBuilder(Optional.of(new IPAddress("192.168.1.1")), new MacAddress("AABBCCDD"),
				new EmpInfoTerminalCode(1), Optional.of(new EmpInfoTerSerialNo("1")), new EmpInfoTerminalName(""),
				new ContractCode("1"))
						.createStampInfo(new CreateStampInfo(new OutPlaceConvert(NotUseAtr.NOT_USE, Optional.empty()),
								new ConvertEmbossCategory(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE), Optional.empty()))
						.modelEmpInfoTer(ModelEmpInfoTer.NRL_1).intervalTime((new MonitorIntervalTime(1))).build());
		new Expectations() {
			{
				require.getEmpInfoTerminal(empInfoTerminalCode, contractCode);
				result = empInfoTerminal;
			}
		};
		
		NtsAssert.atomTask(
				() -> DeleteEmpInfoTerminalService.create(require, contractCode.v(), empInfoTerminalCode.v().intValue()).getDeleteEmpInfoTerminal().get(),
				any -> require.delete(empInfoTerminal.get())
		);
	}
	
	/**
	 *  就業情報端末通信状況を取得する(契約コード,就業情報端末コード) is empty; 
	 */
	@Test
	public void testDeleteEmpInfoTerminalService_3() {
		ContractCode contractCode = new ContractCode("1");
		EmpInfoTerminalCode empInfoTerminalCode = new EmpInfoTerminalCode(1);
		new Expectations() {
			{
				require.get(contractCode, empInfoTerminalCode);
				result = Optional.empty();
			}
		};
		ResultOfDeletion resultOfDeletion = DeleteEmpInfoTerminalService.create(require, contractCode.v(), empInfoTerminalCode.v().intValue());
		assertThat(resultOfDeletion.getDeleteEmpInfoTerminalComStatus()).isEmpty();
	}
	
	/**
	 *  就業情報端末通信状況を取得する(契約コード,就業情報端末コード) not empty; 
	 */
	@Test
	public void testDeleteEmpInfoTerminalService_4() {
		ContractCode contractCode = new ContractCode("1");
		EmpInfoTerminalCode empInfoTerminalCode = new EmpInfoTerminalCode(1);
		Optional<EmpInfoTerminalComStatusImport> empInfoTerminalComStatusImport = Optional.of(new EmpInfoTerminalComStatusImport(contractCode, empInfoTerminalCode, GeneralDateTime.ymdhms(2020, 12, 31, 0, 0, 0)));
		new Expectations() {
			{
				require.get(contractCode, empInfoTerminalCode);
				result = empInfoTerminalComStatusImport;
			}
		};
		
		NtsAssert.atomTask(
				() -> DeleteEmpInfoTerminalService.create(require, contractCode.v(), empInfoTerminalCode.v().intValue()).getDeleteEmpInfoTerminalComStatus().get(),
				any -> require.delete(empInfoTerminalComStatusImport.get()));
	}

}
