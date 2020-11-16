package nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 休日チェック単位 : enum
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.休日管理.休日の扱い.休日チェック単位
 * @author tutk
 *
 */
@RequiredArgsConstructor
public enum HolidayCheckUnit {
	
	/**　1週間	 */
	ONE_WEEK(0),
	
	/** 4週間 */
	FOUR_WEEK(1);
	
	public final int value;
	
	public static HolidayCheckUnit valueOf(int value) {
		return EnumAdaptor.valueOf(value, HolidayCheckUnit.class);
	}

}
