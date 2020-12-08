package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.groups.Tuple;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.dto.TimeRecordSetReceptFormatDto.TimeRecordSetFormatDtoBuilder;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetUpdate;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetUpdateList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.VariableName;

public class TimeRecordSettingInfoDtoTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void payloadAnalysisNull() {
		String payLoad = "NRL-m,200,9@基本設定,ボリューム,sp_vol,num,1,5,0:9,0@基本設定,時計表示,ampm,single,,0,0(12時間表示)/1(24時間表示),0";

		TimeRecordSettingInfoDto actualResult = TimeRecordSettingInfoDto.payloadAnalysis("00-14-22-01-23-45", payLoad);
		assertThat(actualResult).isEqualTo(null);
	}

	@Test
	public void payloadAnalysis() {
		String payLoad = "NRL-m,200,9@基本設定,ボリューム,sp_vol,num,1,5,0:9,0@基本設定,時計表示,ampm,single,,0,0(12時間表示)/1(24時間表示),0@@@@@sp_vol,111@ampm,12";

		TimeRecordSettingInfoDto actualResult = TimeRecordSettingInfoDto.payloadAnalysis("00-14-22-01-23-45", payLoad);

		assertThat(actualResult.getMacAdd()).isEqualTo("00-14-22-01-23-45");
		assertThat(actualResult.getModelEmpInfoTer()).isEqualTo("9");
		assertThat(actualResult.getEmpInfoTerName()).isEqualTo("NRL-m");
		assertThat(actualResult.getRomVersion()).isEqualTo("200");
		assertThat(actualResult.getLstReceptFormat())
				.extracting(x -> x.getMajorClassification(), x -> x.getSmallClassification(), x -> x.getVariableName(),
						x -> x.getType(), x -> x.getNumberOfDigits(), x -> x.getSettingValue(), x -> x.getInputRange(),
						x -> x.getRebootFlg())
				.containsExactly(Tuple.tuple("基本設定", "ボリューム", "sp_vol", "num", "1", "5", "0:9", "0"),
						Tuple.tuple("基本設定", "時計表示", "ampm", "single", "", "0", "0(12時間表示)/1(24時間表示)", "0"));

		assertThat(actualResult.getLstUpdateRecept()).extracting(x -> x.getVariableName(), x -> x.getUpdateValue())
				.containsExactly(Tuple.tuple("sp_vol", "111"), Tuple.tuple("ampm", "12"));
	}

	@Test
	public void create() {

		TimeRecordSetUpdateList setUpdate = new TimeRecordSetUpdateList(new EmpInfoTerminalCode("1234"),
				new EmpInfoTerminalName("AT"), new NRRomVersion("111"), ModelEmpInfoTer.NRL_1,
				Arrays.asList(new TimeRecordSetUpdate(new VariableName("sp_vol"), new SettingValue("68")),
						new TimeRecordSetUpdate(new VariableName("iditi1"), new SettingValue("10"))

				));

		TimeRecordSetFormatList setFormat = new TimeRecordSetFormatList(new EmpInfoTerminalCode("1234"),
				new EmpInfoTerminalName("AT"), new NRRomVersion("111"), ModelEmpInfoTer.NRL_1,
				Arrays.asList(
						new TimeRecordSetFormatBuilder(new MajorNameClassification("基本設定"),
								new MajorNameClassification("ボリューム"), new VariableName("sp_vol"),
								NrlRemoteInputType.valueInputTypeOf("num"), new NumberOfDigits(123))
										.settingValue(new SettingValue("68")).inputRange(new NrlRemoteInputRange("0:9"))
										.rebootFlg(true).build(),
						new TimeRecordSetFormatBuilder(new MajorNameClassification("IDカード設定"),
								new MajorNameClassification("位置"), new VariableName("iditi1"),
								NrlRemoteInputType.valueInputTypeOf("num"), new NumberOfDigits(2))
										.settingValue(new SettingValue("2")).inputRange(new NrlRemoteInputRange("2:70"))
										.rebootFlg(false).build()));

		TimeRecordSettingInfoDto actualResult = TimeRecordSettingInfoDto.create(setFormat, setUpdate);

		assertThat(actualResult.getLstReceptFormat())
				.extracting(x -> x.getMajorClassification(), x -> x.getSmallClassification(), x -> x.getVariableName(),
						x -> x.getType(), x -> x.getNumberOfDigits(), x -> x.getSettingValue(), x -> x.getInputRange(),
						x -> x.getRebootFlg())
				.containsExactly(Tuple.tuple("基本設定", "ボリューム", "sp_vol", "num", "123", "68", "0:9", "1"),
						Tuple.tuple("IDカード設定", "位置", "iditi1", "num", "2", "2", "2:70", "0"));

		assertThat(actualResult.getLstUpdateRecept()).extracting(x -> x.getVariableName(), x -> x.getUpdateValue())
				.containsExactly(Tuple.tuple("sp_vol", "68"), Tuple.tuple("iditi1", "10"));

	}

	@Test
	public void createPayloadEmpty() {
		TimeRecordSettingInfoDto settingDto = new TimeRecordSettingInfoDto("00-14-22-01-23-45", "NRL-m", "200",
				String.valueOf(ModelEmpInfoTer.NRL_1.value), new ArrayList<>(), new ArrayList<>());

		String actualResult = TimeRecordSettingInfoDto.createPayLoad(settingDto);

		assertThat(actualResult).isEqualTo("");
	}

	@Test
	public void createPayload() {

		// タイムレコード設定受信フォーマットリスト
		List<TimeRecordSetReceptFormatDto> lstReceptFormat = new ArrayList<>();
		lstReceptFormat.add(new TimeRecordSetFormatDtoBuilder("基本設定", "ボリューム", "sp_vol", "num", "1").settingValue("5")
				.inputRange("0:9").rebootFlg("1").build());
		lstReceptFormat.add(new TimeRecordSetFormatDtoBuilder("基本設定2", "ボリューム2", "sp_vol2", "num", "0")
				.settingValue("5").inputRange("0:9").rebootFlg("0").build());

		// タイムレコード設定現在と更新受信リスト
		List<TimeRecordSetUpdateReceptDto> lstUpdateRecept = new ArrayList<>();
		lstUpdateRecept.add(new TimeRecordSetUpdateReceptDto("sp_vol", "1234"));
		lstUpdateRecept.add(new TimeRecordSetUpdateReceptDto("sp_vol2", "1268"));

		TimeRecordSettingInfoDto settingDto = new TimeRecordSettingInfoDto("00-14-22-01-23-45", "NRL-m", "200",
				String.valueOf(ModelEmpInfoTer.NRL_1.value), lstReceptFormat, lstUpdateRecept);

		String actualResult = TimeRecordSettingInfoDto.createPayLoad(settingDto);

		String expected = "sp_vol=1234,1@sp_vol2=1268";
		assertThat(actualResult).isEqualTo(expected);
	}

}
