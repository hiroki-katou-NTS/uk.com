package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ThanhNX
 *
 *         タイムレコード設定現在と更新受信リスト
 */
@Getter
@Setter
public class TimeRecordSetUpdateReceptDto {
	// 変数名
	private final String variableName;

	// 更新の値
	private final String updateValue;

	public TimeRecordSetUpdateReceptDto(String variableName, String updateValue) {
		this.variableName = variableName;
		this.updateValue = updateValue;
	}

}
