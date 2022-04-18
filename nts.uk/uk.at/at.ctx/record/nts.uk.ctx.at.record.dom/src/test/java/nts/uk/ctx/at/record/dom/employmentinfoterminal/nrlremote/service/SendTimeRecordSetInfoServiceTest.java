package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Optional;

import org.assertj.core.groups.Tuple;
import org.junit.Before;
import org.junit.BeforeClass;
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
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.dto.TimeRecordSettingInfoDto;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

@RunWith(JMockit.class)
public class SendTimeRecordSetInfoServiceTest {

	@Injectable
	private SendTimeRecordSetInfoService.Require require;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testNotFoundEmpTer() {

		Optional<TimeRecordSettingInfoDto> actualResult = SendTimeRecordSetInfoService.send(require,
				new ContractCode(""), new EmpInfoTerminalCode(""));

		assertThat(actualResult).isEqualTo(Optional.empty());

	}

	@Test
	public void testNotFoundDataMaster() {

		Optional<TimeRecordSettingInfoDto> actualResult = SendTimeRecordSetInfoService.send(require,
				new ContractCode(""), new EmpInfoTerminalCode(""));

		assertThat(actualResult).isEqualTo(Optional.empty());

	}

	@Test
	public void testNotFoundDataUpdate() {

		new Expectations() {
			{
				require.findSettingUpdate((EmpInfoTerminalCode) any, (ContractCode) any);
				result = Optional.of(new TimeRecordSetUpdateList(new EmpInfoTerminalCode("1234"),
						new EmpInfoTerminalName("AT"), new NRRomVersion("111"), ModelEmpInfoTer.NRL_1,
						Arrays.asList(new TimeRecordSetUpdate(new VariableName("sp_vol"), new SettingValue("68")))));
			}
		};

		Optional<TimeRecordSettingInfoDto> actualResult = SendTimeRecordSetInfoService.send(require,
				new ContractCode(""), new EmpInfoTerminalCode(""));

		assertThat(actualResult).isEqualTo(Optional.empty());

	}

	@Test
	public void testGetDone() {

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

		Optional<TimeRecordSettingInfoDto> actualResult = SendTimeRecordSetInfoService.send(require,
				new ContractCode(""), new EmpInfoTerminalCode(""));

		assertThat(actualResult.get().getLstReceptFormat())
				.extracting(x -> x.getMajorClassification(), x -> x.getSmallClassification(), x -> x.getVariableName(),
						x -> x.getType(), x -> x.getNumberOfDigits(), x -> x.getSettingValue(), x -> x.getInputRange(),
						x -> x.getRebootFlg())
				.containsExactly(Tuple.tuple("基本設定", "ボリューム", "sp_vol", "num", "123", "68", "0:9", "1"),
						Tuple.tuple("IDカード設定", "位置", "iditi1", "num", "2", "2", "2:70", "0"));

		assertThat(actualResult.get().getLstUpdateRecept())
				.extracting(x -> x.getVariableName(), x -> x.getUpdateValue())
				.containsExactly(Tuple.tuple("sp_vol", "68"), Tuple.tuple("iditi1", "10"));

	}

}
