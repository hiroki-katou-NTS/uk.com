package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerSerialNo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal.EmpInfoTerminalBuilder;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalName;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.IPAddress;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MacAddress;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ModelEmpInfoTer;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.dto.TimeRecordSetReceptFormatDto;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.dto.TimeRecordSetReceptFormatDto.TimeRecordSetFormatDtoBuilder;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.dto.TimeRecordSetUpdateReceptDto;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.dto.TimeRecordSettingInfoDto;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

@RunWith(JMockit.class)
public class ReceiveNRRemoteSettingServiceTest {

	@Injectable
	private ReceiveNRRemoteSettingService.Require require;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testNotFoundEmpTer() {

		TimeRecordSettingInfoDto input = new TimeRecordSettingInfoDto("", "A", "012",
				String.valueOf(ModelEmpInfoTer.NRL_1.value), new ArrayList<>(), new ArrayList<>());

		AtomTask atomTask = ReceiveNRRemoteSettingService.processInfo(require, input);

		new Verifications() {
			{
				require.removeTRSetFormatList((EmpInfoTerminalCode) any, (ContractCode) any);
				times = 0;
				require.insert((ContractCode) any, (TimeRecordSetFormatList) any);
				times = 0;
			}
		};

		atomTask.run();

		new Verifications() {
			{
				require.removeTRSetFormatList((EmpInfoTerminalCode) any, (ContractCode) any);
				times = 0;
				require.insert((ContractCode) any, (TimeRecordSetFormatList) any);
				times = 0;
			}
		};

	}

	@Test
	public void testReceiveDone() {

		// タイムレコード設定受信フォーマットリスト
		List<TimeRecordSetReceptFormatDto> lstReceptFormat = new ArrayList<>();
		lstReceptFormat.add(new TimeRecordSetFormatDtoBuilder("基本設定", "ボリューム", "sp_vol", "num", "1").settingValue("5")
				.inputRange("0:9").rebootFlg("1").build());

		// タイムレコード設定現在と更新受信リスト
		List<TimeRecordSetUpdateReceptDto> lstUpdateRecept = new ArrayList<>();
		lstUpdateRecept.add(new TimeRecordSetUpdateReceptDto("sp_vol", "1234"));

		TimeRecordSettingInfoDto input = new TimeRecordSettingInfoDto("00-14-22-01-23-45", "A", "012",
				String.valueOf(ModelEmpInfoTer.NRL_1.value), lstReceptFormat, lstUpdateRecept);

		new Expectations() {
			{
				require.getEmpInfoTerWithMac(new MacAddress("00-14-22-01-23-45"), (ContractCode) any);
				result = Optional.of(new EmpInfoTerminalBuilder(new IPAddress("192.168.1.1"),
						new MacAddress("00-14-22-01-23-45"), new EmpInfoTerminalCode(1234),
						new EmpInfoTerSerialNo("1111"), new EmpInfoTerminalName("AT"), new ContractCode("000000000000"))
								.modelEmpInfoTer(ModelEmpInfoTer.NRL_1).build());

			}

		};

		AtomTask atomTask = ReceiveNRRemoteSettingService.processInfo(require, input);

		NtsAssert.atomTask(() -> atomTask,
				any -> require.removeTRSetFormatList((EmpInfoTerminalCode) (any.get()), (ContractCode) (any.get())),
				any -> require.insert((ContractCode) (any.get()), (TimeRecordSetFormatList) (any.get())));
	}

}
