package nts.uk.ctx.at.record.app.find.stamp.management;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ColorSettingDto {
	
	/** 文字色 */
	private String textColor;
	
	/** 背景色 */
	private String backGroundColor;
}
