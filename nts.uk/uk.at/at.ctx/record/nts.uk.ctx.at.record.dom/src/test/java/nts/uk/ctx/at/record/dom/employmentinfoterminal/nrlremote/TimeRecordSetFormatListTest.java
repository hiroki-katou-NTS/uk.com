package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.groups.Tuple;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ModelEmpInfoTer;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.dto.TimeRecordSetReceptFormatDto;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.dto.TimeRecordSetReceptFormatDto.TimeRecordSetFormatDtoBuilder;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.dto.TimeRecordSetUpdateReceptDto;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.dto.TimeRecordSettingInfoDto;

public class TimeRecordSetFormatListTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testConvert() {

		// タイムレコード設定受信フォーマットリスト
		List<TimeRecordSetReceptFormatDto> lstReceptFormat = new ArrayList<>();
		lstReceptFormat.add(new TimeRecordSetFormatDtoBuilder("基本設定", "ボリューム", "sp_vol", "num", "1").settingValue("5")
				.inputRange("0:9").rebootFlg("1").build());

		// タイムレコード設定現在と更新受信リスト
		List<TimeRecordSetUpdateReceptDto> lstUpdateRecept = new ArrayList<>();
		lstUpdateRecept.add(new TimeRecordSetUpdateReceptDto("sp_vol", "1234"));

		TimeRecordSettingInfoDto input = new TimeRecordSettingInfoDto("00-14-22-01-23-45", "A", "012",
				String.valueOf(ModelEmpInfoTer.NRL_1.value), lstReceptFormat, lstUpdateRecept);

		TimeRecordSetFormatList actualResult = TimeRecordSetFormatList.convert(new EmpInfoTerminalCode(1234), input);

		assertThat(actualResult.getLstTRSetFormat())
				.extracting(x -> x.getMajorClassification().v(), x -> x.getSmallClassification().v(),
						x -> x.getVariableName().v(), x -> x.getType(), x -> x.getNumberOfDigits().v(),
						x -> x.getSettingValue().v(), x -> x.getInputRange().v(), x -> x.isRebootFlg())
				.containsExactly(Tuple.tuple("基本設定", "ボリューム", "sp_vol", NrlRemoteInputType.NUM, 1, "5", "0:9", true));

	}

}
