package nts.uk.ctx.at.schedule.dom.displaysetting;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 表示の範囲
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.表示設定.表示の範囲 
 * @author hiroko_miura
 *
 */

@RequiredArgsConstructor
public enum DisplayRangeType {
	
	/**
	 * 24時間
	 */
	DISP24H(0),
	
	/**
	 * 48時間
	 */
	DISP48H(1);
	
	public final int value;
	
	public static DisplayRangeType of(int value) {
		return EnumAdaptor.valueOf(value, DisplayRangeType.class);
	}
	
}
