package nts.uk.ctx.at.record.app.command.stamp.management;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ButtonDisSetCommand {
	/** ボタン名称設定 */
	private ButtonNameSetCommand buttonNameSet;
	
	/** 背景色 */
	private String backGroundColor;
}
