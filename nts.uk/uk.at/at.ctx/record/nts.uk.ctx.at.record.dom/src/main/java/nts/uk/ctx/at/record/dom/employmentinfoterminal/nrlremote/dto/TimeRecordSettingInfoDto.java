package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetUpdateList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.dto.TimeRecordSetReceptFormatDto.TimeRecordSetFormatDtoBuilder;

/**
 * @author ThanhNX
 *
 *         タイムレコードの設定情報
 */
@Setter
@Getter
public class TimeRecordSettingInfoDto {

	// MACアドレス
	private String macAdd;

	// 機種名
	private String empInfoTerName;

	// ROMバージョン
	private String romVersion;

	// 機種区分
	private String modelEmpInfoTer;

	// タイムレコード設定受信フォーマットリスト
	private List<TimeRecordSetReceptFormatDto> lstReceptFormat;

	// タイムレコード設定現在と更新受信リスト
	private List<TimeRecordSetUpdateReceptDto> lstUpdateRecept;

	public TimeRecordSettingInfoDto(String macAdd, String empInfoTerName, String romVersion, String modelEmpInfoTer,
			List<TimeRecordSetReceptFormatDto> lstReceptFormat, List<TimeRecordSetUpdateReceptDto> lstUpdateRecept) {
		this.macAdd = macAdd;
		this.empInfoTerName = empInfoTerName;
		this.romVersion = romVersion;
		this.modelEmpInfoTer = modelEmpInfoTer;
		this.lstReceptFormat = lstReceptFormat;
		this.lstUpdateRecept = lstUpdateRecept;
	}

	// [S-1] ペイロード分析
	public static TimeRecordSettingInfoDto payloadAnalysis(String macAdd, String payload) {

		String[] rowElementNow = payload.split(NRRemoteCommon.ROW_ELM_NOW);
		if (rowElementNow.length < NRRemoteCommon.LENGTH) {
			return null;
		}
		String objectRecv = rowElementNow[0];
		String objectValueCurrent = rowElementNow[1];

		String empInfoTerName = "", romVersion = "", modelEmpInfoTer = "";

		List<TimeRecordSetReceptFormatDto> lstReceptFormat = new ArrayList<>();
		List<TimeRecordSetUpdateReceptDto> lstUpdateRecept = new ArrayList<>();
		String[] lstDataRecv = objectRecv.split(NRRemoteCommon.ROW);
		for (int i = 0; i < lstDataRecv.length; i++) {
			if (i == 0) {
				empInfoTerName = lstDataRecv[i].split(NRRemoteCommon.ELEMENT)[0];
				romVersion = lstDataRecv[i].split(NRRemoteCommon.ELEMENT)[1];
				modelEmpInfoTer = lstDataRecv[i].split(NRRemoteCommon.ELEMENT)[2];
			} else {
				lstReceptFormat.add(createSettingFormat(lstDataRecv[i]));
			}
		}

		String[] lstDataUpdate = objectValueCurrent.split(NRRemoteCommon.ROW);
		for (int i = 0; i < lstDataUpdate.length; i++) {
			lstUpdateRecept.add(createSettingUpdate(lstDataUpdate[i]));
		}

		return new TimeRecordSettingInfoDto(macAdd, empInfoTerName, romVersion, modelEmpInfoTer, lstReceptFormat,
				lstUpdateRecept);
	}

	// [S-2] タイムレコードの設定情報を作る
	public static TimeRecordSettingInfoDto create(TimeRecordSetFormatList setFormat,
			TimeRecordSetUpdateList setUpdate) {

		List<TimeRecordSetReceptFormatDto> lstReceptFormat = setFormat.getLstTRSetFormat().stream().map(x -> {
			return new TimeRecordSetFormatDtoBuilder(x.getMajorClassification().v(), x.getSmallClassification().v(),
					x.getVariableName().v(), x.getType().inputType, String.valueOf(x.getNumberOfDigits().v()))
							.settingValue(x.getSettingValue().v()).inputRange(x.getInputRange().v())
							.rebootFlg(x.isRebootFlg() ? "1" : "0").build();
		}).collect(Collectors.toList());

		List<TimeRecordSetUpdateReceptDto> lstUpdateRecept = setUpdate.getLstTRecordSetUpdate().stream().map(x -> {
			return new TimeRecordSetUpdateReceptDto(x.getVariableName().v(), x.getUpdateValue().v());
		}).collect(Collectors.toList());

		return new TimeRecordSettingInfoDto(String.valueOf(setFormat.getEmpInfoTerCode().v()),
				setFormat.getEmpInfoTerName().v(), setFormat.getRomVersion().v(),
				String.valueOf(setFormat.getModelEmpInfoTer().value), lstReceptFormat, lstUpdateRecept);

	}

	// [S-3] payloadを作る
	public static String createPayLoad(TimeRecordSettingInfoDto settingDto) {
		if (settingDto.getLstUpdateRecept().isEmpty())
			return "";
		StringBuilder builder = new StringBuilder("");
		settingDto.getLstUpdateRecept().stream().forEach(x -> {
			val master = settingDto.getLstReceptFormat().stream()
					.filter(y -> y.getVariableName().equals(x.getVariableName())).findFirst();
			builder.append(x.getVariableName());
			builder.append("=");
			builder.append(x.getUpdateValue());
			master.ifPresent(data -> builder.append(data.getRebootFlg().equals("1") ? ",1" : ""));
			builder.append("@");
		});
		return StringUtils.removeEnd(builder.toString(), "@");
	}

	// [S-1] タイムレコード設定受信フォーマットリストを作る
	private static TimeRecordSetReceptFormatDto createSettingFormat(String data) {

		String[] filed = data.split(NRRemoteCommon.ELEMENT);
		return new TimeRecordSetFormatDtoBuilder(filed[0], filed[1], filed[2], filed[3], filed[4])
				.settingValue(filed[5]).inputRange(filed[6]).rebootFlg(filed[7]).build();

	}

	// [S-2] タイムレコード設定更新受信リストを作る
	private static TimeRecordSetUpdateReceptDto createSettingUpdate(String data) {

		String[] filed = data.split(NRRemoteCommon.ELEMENT);
		return new TimeRecordSetUpdateReceptDto(filed[0], filed[1]);
	}

}
