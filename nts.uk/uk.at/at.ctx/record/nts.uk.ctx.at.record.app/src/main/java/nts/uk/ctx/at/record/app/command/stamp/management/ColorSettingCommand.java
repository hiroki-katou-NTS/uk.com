package nts.uk.ctx.at.record.app.command.stamp.management;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ColorSettingCommand {
	
	/** 文字色 */
	private String textColor;
	
	/** 背景色 */
	private String backGroundColor;
}
