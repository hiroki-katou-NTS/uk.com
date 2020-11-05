package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerSerialNo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal.EmpInfoTerminalBuilder;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalName;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.IPAddress;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MacAddress;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ModelEmpInfoTer;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.MajorNameClassification;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.NRRomVersion;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.NrlRemoteInputRange;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.NrlRemoteInputType;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.NumberOfDigits;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.SettingValue;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormat.TimeRecordSetFormatBuilder;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetUpdate;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetUpdateList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.VariableName;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.xml.NRLRemoteDataXml;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

@RunWith(JMockit.class)
public class ConvertTimeRecordUpdateToXmlServiceTest {

	@Injectable
	private ConvertTimeRecordUpdateToXmlService.Require require;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		NRLRemoteDataXml result = ConvertTimeRecordUpdateToXmlService.convertToXml(require, "00-14-22-01-23-45");
		assertThat(result).isEqualTo(null);
	}

	@Test
	public void testDone() {
		new Expectations() {
			{
				require.getEmpInfoTerWithMac(new MacAddress("00-14-22-01-23-45"), (ContractCode) any);
				result = Optional.of(
						new EmpInfoTerminalBuilder(Optional.of(new IPAddress("192.168.1.1")), new MacAddress("00-14-22-01-23-45"),
								new EmpInfoTerminalCode(1234), Optional.of(new EmpInfoTerSerialNo("1111")),
								new EmpInfoTerminalName("AT"), new ContractCode("0000000000000"))
										.modelEmpInfoTer(ModelEmpInfoTer.NRL_1).build());

				require.findSettingUpdate((EmpInfoTerminalCode) any, (ContractCode) any);
				result = Optional.of(new TimeRecordSetUpdateList(new EmpInfoTerminalCode(1234),
						new EmpInfoTerminalName("AT"), new NRRomVersion("111"), ModelEmpInfoTer.NRL_1,
						Arrays.asList(new TimeRecordSetUpdate(new VariableName("sp_vol"), new SettingValue("68")),
								new TimeRecordSetUpdate(new VariableName("iditi1"), new SettingValue("10"))

						)));

				require.findSetFormat((EmpInfoTerminalCode) any, (ContractCode) any);
				result = Optional.of(new TimeRecordSetFormatList(new EmpInfoTerminalCode(1234),
						new EmpInfoTerminalName("AT"), new NRRomVersion("111"), ModelEmpInfoTer.NRL_1,
						Arrays.asList(
								new TimeRecordSetFormatBuilder(new MajorNameClassification("基本設定"),
										new MajorNameClassification("ボリューム"), new VariableName("sp_vol"),
										NrlRemoteInputType.valueInputTypeOf("num"), new NumberOfDigits(123))
												.settingValue(new SettingValue("68"))
												.inputRange(new NrlRemoteInputRange("0:9")).rebootFlg(true).build(),
								new TimeRecordSetFormatBuilder(new MajorNameClassification("IDカード設定"),
										new MajorNameClassification("位置"), new VariableName("iditi1"),
										NrlRemoteInputType.valueInputTypeOf("num"), new NumberOfDigits(2))
												.settingValue(new SettingValue("2"))
												.inputRange(new NrlRemoteInputRange("2:70")).rebootFlg(false).build())

				));

			}
		};

		NRLRemoteDataXml actualResult = ConvertTimeRecordUpdateToXmlService.convertToXml(require, "00-14-22-01-23-45");

		assertThat(actualResult.getPayload()).isEqualTo("sp_vol=68,1@iditi1=10");
	}
}
