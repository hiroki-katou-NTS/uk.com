package nts.uk.ctx.at.record.app.find.stamp.management;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonSettings;

/**
 * 
 * @author phongtq
 *
 */
@Data
@AllArgsConstructor
public class ButtonSettingsDto {
	/** ボタン位置NO */
	private int buttonPositionNo;

	/** ボタンの表示設定 */
	private ButtonDisSetDto buttonDisSet;

	/** ボタン種類 */
	private ButtonTypeDto buttonType;

	/** 使用区分 */
	private int usrArt;

	/** 音声使用方法 */
	private int audioType;

	public static ButtonSettingsDto fromDomain(ButtonSettings domain) {
		return new ButtonSettingsDto(domain.getButtonPositionNo().v(),
				ButtonDisSetDto.fromDomain(domain.getButtonDisSet()), ButtonTypeDto.fromDomain(domain.getButtonType()),
				domain.getUsrArt().value, domain.getAudioType().value);
	}

}
