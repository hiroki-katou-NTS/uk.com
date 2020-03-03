package nts.uk.ctx.at.record.app.command.stamp.management;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class ButtonNameSetCommand {
	/** 文字色 */
	private String textColor;
	
	/** ボタン名称 */
	@Getter
	private Optional<String> buttonName;
}
