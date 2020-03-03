package nts.uk.ctx.at.record.app.find.stamp.management;

import lombok.AllArgsConstructor;
import lombok.Data;

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

}
