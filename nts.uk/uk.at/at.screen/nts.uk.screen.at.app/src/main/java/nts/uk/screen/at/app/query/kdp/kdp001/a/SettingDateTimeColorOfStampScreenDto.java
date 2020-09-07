package nts.uk.screen.at.app.query.kdp.kdp001.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SettingDateTimeColorOfStampScreen;

/**
 * 
 * @author sonnlb
 *
 */
@AllArgsConstructor
@Data
public class SettingDateTimeColorOfStampScreenDto {
	/** 文字色 */
	private String textColor;

	/** 背景色 */
	private String backgroundColor;

	public static SettingDateTimeColorOfStampScreenDto fromDomain(SettingDateTimeColorOfStampScreen domain) {
		return new SettingDateTimeColorOfStampScreenDto(domain.getTextColor().v(), domain.getBackGroundColor().v());
	}
}
