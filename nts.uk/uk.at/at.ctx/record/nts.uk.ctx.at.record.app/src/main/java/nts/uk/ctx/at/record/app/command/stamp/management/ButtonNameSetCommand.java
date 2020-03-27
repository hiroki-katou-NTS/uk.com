package nts.uk.ctx.at.record.app.command.stamp.management;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * 
 * @author phongtq
 *
 */
@Data
@NoArgsConstructor
public class ButtonNameSetCommand {
	/** 文字色 */
	private String textColor;
	
	/** ボタン名称 */
	@Getter
	private String buttonName;

	public ButtonNameSetCommand(String textColor, String buttonName) {
		this.textColor = textColor;
		this.buttonName = buttonName;
	}
}
