package nts.uk.screen.at.app.query.kdp.kdp001.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.DisplaySettingsStampScreen;

/**
 * 
 * @author sonnlb
 *
 */
@AllArgsConstructor
@Data
public class DisplaySettingsStampScreenDto {
	/** 打刻画面のサーバー時刻補正間隔 */
	private Integer serverCorrectionInterval;

	/** 打刻画面の日時の色設定 */
	private SettingDateTimeColorOfStampScreenDto settingDateTimeColor;

	/** 打刻結果自動閉じる時間 */
	private Integer resultDisplayTime;

	public static DisplaySettingsStampScreenDto fromDomain(DisplaySettingsStampScreen domain) {

		return new DisplaySettingsStampScreenDto(domain.getServerCorrectionInterval().v(),
				SettingDateTimeColorOfStampScreenDto.fromDomain(domain.getSettingDateTimeColor()),
				domain.getResultDisplayTime().v());
	}
}
