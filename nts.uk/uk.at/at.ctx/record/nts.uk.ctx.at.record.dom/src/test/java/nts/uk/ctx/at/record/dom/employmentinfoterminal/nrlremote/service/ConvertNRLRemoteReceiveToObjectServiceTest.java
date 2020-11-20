package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.service;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerSerialNo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal.EmpInfoTerminalBuilder;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalName;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.FullIpAddress;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.IPAddress;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MacAddress;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ModelEmpInfoTer;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.PartialIpAddress;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatList;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

@RunWith(JMockit.class)
public class ConvertNRLRemoteReceiveToObjectServiceTest {

	@Injectable
	private ConvertNRLRemoteReceiveToObjectService.Require require;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testNull() {
		String string = "NRL-m,200,9@基本設定,ボリューム,sp_vol,num,1,5,0:9,0";
		InputStream input = new ByteArrayInputStream(string.getBytes(Charset.forName("UTF-8")));

		Optional<AtomTask> actualResult = ConvertNRLRemoteReceiveToObjectService.convertData(require, input);

		assertThat(actualResult).isEqualTo(Optional.empty());
	}

	@Test
	public void test() throws FileNotFoundException {

//		FileInputStream input = new FileInputStream(
//				ConvertNRLRemoteReceiveToObjectServiceTest.class.getResource("nrlremote.xml").getFile());
		
		String data = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n" + 
				"<!DOCTYPE xml>\r\n" + 
				"<doc>　\r\n" + 
				"    <MAC_Address>00-14-22-01-23-45</MAC_Address>\r\n" + 
				"	<payload>\r\n" + 
				"		NRL-m,200,9@基本設定,ボリューム,sp_vol,num,1,5,0:9,0@@@@@sp_vol,68\r\n" + 
				"	</payload>\r\n" + 
				"</doc>";
		InputStream input = new ByteArrayInputStream(data.getBytes(Charset.forName("UTF-8")));
		
		new Expectations() {
			{
				require.getEmpInfoTerWithMac(new MacAddress("00-14-22-01-23-45"), (ContractCode) any);
				result = Optional.of(new EmpInfoTerminalBuilder(Optional.of(new FullIpAddress(
						new PartialIpAddress(192), new PartialIpAddress(168), new PartialIpAddress(1), new PartialIpAddress(1))),
						new MacAddress("00-14-22-01-23-45"), new EmpInfoTerminalCode(1234),
						Optional.of(new EmpInfoTerSerialNo("1111")), new EmpInfoTerminalName("AT"),
						new ContractCode("000000000000")).modelEmpInfoTer(ModelEmpInfoTer.NRL_1).build());

			}

		};

		Optional<AtomTask> actualResult = ConvertNRLRemoteReceiveToObjectService.convertData(require, input);

		NtsAssert.atomTask(() -> actualResult.get(),
				any -> require.removeTRSetFormatList((EmpInfoTerminalCode) (any.get()), (ContractCode) (any.get())),
				any -> require.insert((ContractCode) (any.get()), (TimeRecordSetFormatList) (any.get())));
	}

}
