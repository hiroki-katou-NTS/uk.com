package nts.uk.ctx.at.record.app.command.stamp.management;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author phongtq
 *
 */
@Data
@NoArgsConstructor
public class ButtonDisSetCommand {
	/** ボタン名称設定 */
	private ButtonNameSetCommand buttonNameSet;

	/** 背景色 */
	private String backGroundColor;

	public ButtonDisSetCommand(ButtonNameSetCommand buttonNameSet, String backGroundColor) {
		this.buttonNameSet = buttonNameSet;
		this.backGroundColor = backGroundColor;
	}
}
