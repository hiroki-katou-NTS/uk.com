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
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalName;
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
		Optional<String> result = ConvertTimeRecordUpdateToXmlService.convertToXml(require, new ContractCode(""), new EmpInfoTerminalCode(""));
		assertThat(result).isEqualTo(Optional.empty());
	}

	@Test
	public void testDone() {
		new Expectations() {
			{
				require.findSettingUpdate((EmpInfoTerminalCode) any, (ContractCode) any);
				result = Optional.of(new TimeRecordSetUpdateList(new EmpInfoTerminalCode("1234"),
						new EmpInfoTerminalName("AT"), new NRRomVersion("111"), ModelEmpInfoTer.NRL_1,
						Arrays.asList(new TimeRecordSetUpdate(new VariableName("sp_vol"), new SettingValue("68")),
								new TimeRecordSetUpdate(new VariableName("iditi1"), new SettingValue("10"))

						)));

				require.findSetFormat((EmpInfoTerminalCode) any, (ContractCode) any);
				result = Optional.of(new TimeRecordSetFormatList(new EmpInfoTerminalCode("1234"),
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

		Optional<String> actualResult = ConvertTimeRecordUpdateToXmlService.convertToXml(require, new ContractCode(""), new EmpInfoTerminalCode(""));

		assertThat(actualResult.get()).isEqualTo("sp_vol=68,1\niditi1=10\n");
	}
}
