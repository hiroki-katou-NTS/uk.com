package nts.uk.ctx.at.record.app.find.stamp.management;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.stampinputfunctionsettings.notificationmessagesettings.ColorSetting;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;

@AllArgsConstructor
@Data
public class ColorSettingDto {
	
	/** 文字色 */
	private String textColor;
	
	/** 背景色 */
	private String backGroundColor;

	public ColorSettingDto(ColorSetting domain) {
		this.textColor = domain.getTextColor().v();
		this.backGroundColor = domain.getBackGroundColor().v();
	}
	
	public ColorSetting toDomain() {
		return new ColorSetting(new ColorCode(this.textColor), new ColorCode(this.backGroundColor));
	}
}
