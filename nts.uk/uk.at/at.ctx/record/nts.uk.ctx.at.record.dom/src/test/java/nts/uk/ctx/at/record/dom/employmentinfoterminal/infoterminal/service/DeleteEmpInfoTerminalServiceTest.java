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

@RunWith(JMockit.class)
public class DeleteEmpInfoTerminalServiceTest {

	@Injectable
	private DeleteEmpInfoTerminalService.Require require;

	/**
	 * 就業情報端末を取得する(契約コード,就業情報端末コード) is empty -> isError = true 
	 *就業情報端末を削除する(就業情報端末) is empty
	 */
	@Test
	public void testDeleteEmpInfoTerminalService_1() {
		ContractCode contractCode = new ContractCode("dum");
		EmpInfoTerminalCode empInfoTerminalCode = new EmpInfoTerminalCode(1111);

		new Expectations() {
			{
				require.getEmpInfoTerminal(empInfoTerminalCode, contractCode);
				result = Optional.empty();
				require.get(contractCode, empInfoTerminalCode);
				result = Optional.empty();
			}
		};

		val actual = DeleteEmpInfoTerminalService.create(require, contractCode.v(), empInfoTerminalCode.v().intValue());
		val expected = new ResultOfDeletion(true, Optional.empty(), Optional.empty());

		assertThat(actual).isEqualTo(expected);
	}

	/**
	 *  就業情報端末を取得する(契約コード,就業情報端末コード) not empty; -> isError = false
	 *  就業情報端末を削除する(就業情報端末) is empty;
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
				require.get(contractCode, empInfoTerminalCode);
				result = Optional.empty();
			}
		};
		
		NtsAssert.atomTask(
				() -> DeleteEmpInfoTerminalService.create(require, contractCode.v(), empInfoTerminalCode.v().intValue()).getDeleteEmpInfoTerminal().get(),
				any -> require.delete(empInfoTerminal.get())
		);
	}

}
