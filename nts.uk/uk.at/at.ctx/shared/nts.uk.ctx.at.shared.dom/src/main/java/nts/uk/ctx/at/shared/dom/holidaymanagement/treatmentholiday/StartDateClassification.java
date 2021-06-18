package nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 起算日区分 : enum
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.休日管理.休日の扱い.起算日区分
 * @author tutk
 *
 */
@RequiredArgsConstructor
public enum StartDateClassification {
	/**　年月日指定 */
	SPECIFY_YMD(0),
	
	/** 月日指定 */
	SPECIFY_MD(1);
	
	public final int value;
	
	public static StartDateClassification valueOf(int value) {
		return EnumAdaptor.valueOf(value, StartDateClassification.class);
	}
}
