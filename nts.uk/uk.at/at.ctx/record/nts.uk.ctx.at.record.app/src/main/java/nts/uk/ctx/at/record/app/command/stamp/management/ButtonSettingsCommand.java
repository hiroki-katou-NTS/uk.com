package nts.uk.ctx.at.record.app.command.stamp.management;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.AudioType;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonPositionNo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonSettings;
import nts.uk.shr.com.enumcommon.NotUseAtr;
/**
 * ボタン詳細設定
 * @author phongtq
 *
 */
@Data
@NoArgsConstructor
public class ButtonSettingsCommand {
	/** ボタン位置NO */
	private int buttonPositionNo;

	/** ボタンの表示設定 */
	private ButtonDisSetCommand buttonDisSet;

	/** ボタン種類 */
	private ButtonTypeCommand buttonType;

	/** 使用区分 */
	private int usrArt;

	/** 音声使用方法 */
	private int audioType;
	
	public ButtonSettings toDomain() {
		return new ButtonSettings(
				new ButtonPositionNo(this.getButtonPositionNo()), 
				this.buttonDisSet.toDomain(), 
				this.buttonType.toDomain(), 
				NotUseAtr.valueOf(this.usrArt), 
				AudioType.valueOf(this.audioType)
			);
	}



	public ButtonSettingsCommand(int buttonPositionNo, ButtonDisSetCommand buttonDisSet, ButtonTypeCommand buttonType,
			int usrArt, int audioType) {
		super();
		this.buttonPositionNo = buttonPositionNo;
		this.buttonDisSet = buttonDisSet;
		this.buttonType = buttonType;
		this.usrArt = usrArt;
		this.audioType = audioType;
	}
}
