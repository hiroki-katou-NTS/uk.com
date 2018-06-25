package nts.uk.ctx.sys.assist.app.find.datarestoration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoverySelection;

@Data
@AllArgsConstructor
@Value
public class DataRecoverySelectionDto {
	/**
	 * 保存セットコード
	 */
	private String code;

	/**
	 * 保存名称
	 */
	private String name;

	/**
	 * 補足説明
	 */
	private String suppleExplanation;

	/**
	 * 保存開始日時
	 */
	private GeneralDateTime saveStartDatetime;

	/**
	 * 保存形態
	 */
	private int saveForm;

	/**
	 * 対象人数
	 */
	private int targetNumberPeople;
	/**
	 * 保存ファイル名
	 */
	private String saveFileName;

	/**
	 * ファイルID
	 */
	private String fileId;
	
	/**
	 * データ保存処理ID
	 */
	private String storeProcessingId;

	/*
	 * public static DataRecoverySelectionDto
	 * fromDomain(List<DataRecoverySelection> optDataRecoverySelection) { return
	 * (DataRecoverySelectionDto) optDataRecoverySelection.stream().map(item ->
	 * { DataRecoverySelectionDto dto = new DataRecoverySelectionDto();
	 * dto.setCode(item.getCode()); dto.setFileId(item.getFileId());
	 * dto.setName(item.getName()); dto.setSaveFileName(item.getSaveFileName());
	 * dto.setSaveForm(item.getSaveForm());
	 * dto.setSaveStartDatetime(item.getSaveStartDatetime());
	 * dto.setSuppleExplanation(item.getSuppleExplanation());
	 * dto.setTargetNumberPeople(item.getTargetNumberPeople()); return dto;
	 * }).collect(Collectors.toList()); }
	 */

	public static DataRecoverySelectionDto fromDomain(DataRecoverySelection domain) {
		return new DataRecoverySelectionDto(domain.getCode(), domain.getName(), domain.getSuppleExplanation(),
				domain.getSaveStartDatetime(), domain.getSaveForm(), domain.getTargetNumberPeople(),
				domain.getSaveFileName(), domain.getFileId(), domain.getStoreProcessingId());
	}

}
