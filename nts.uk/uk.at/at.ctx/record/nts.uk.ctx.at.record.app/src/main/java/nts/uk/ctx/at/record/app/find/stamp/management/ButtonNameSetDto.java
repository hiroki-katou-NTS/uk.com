package nts.uk.ctx.at.record.app.find.stamp.management;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ButtonNameSetDto {
	/** 文字色 */
	private String textColor;
	
	/** ボタン名称 */
	private Optional<String> buttonName;
}
