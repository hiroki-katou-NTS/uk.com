package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote;

import lombok.Getter;

/**
 * @author ThanhNX
 *
 *         タイムレコード設定更新
 */
@Getter
public class TimeRecordSetUpdate {

	// 変数名
	private final VariableName variableName;

	// 更新の値
	private final SettingValue updateValue;

	public TimeRecordSetUpdate(VariableName variableName, SettingValue updateValue) {
		this.variableName = variableName;
		this.updateValue = updateValue;
	}

}
