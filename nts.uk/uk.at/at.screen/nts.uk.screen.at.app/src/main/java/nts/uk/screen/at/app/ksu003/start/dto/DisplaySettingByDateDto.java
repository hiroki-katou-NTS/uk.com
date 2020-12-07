package nts.uk.screen.at.app.ksu003.start.dto;

import lombok.Value;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplaySettingByDate;
/**
 * スケジュール修正日付別の表示設定 Dto
 * @author phongtq
 *
 */
@Value
public class DisplaySettingByDateDto {
	/** 表示範囲 */
	private int dispRange;

	/** 開始時刻 */
	private int dispStart;

	/** スケジュール修正日付別の表示設定 */
	private int initDispStart;
	
	public static DisplaySettingByDateDto convert(DisplaySettingByDate settingByDate) {
		return new DisplaySettingByDateDto(settingByDate.getDispRange().value, settingByDate.getDispStart().v(), settingByDate.getInitDispStart().v());
	}
}
