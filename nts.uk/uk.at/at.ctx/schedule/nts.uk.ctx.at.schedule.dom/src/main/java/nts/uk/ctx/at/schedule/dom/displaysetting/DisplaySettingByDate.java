package nts.uk.ctx.at.schedule.dom.displaysetting;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * スケジュール修正日付別の表示設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.表示設定.スケジュール修正日付別の表示設定
 * @author hiroko_miura
 *
 */
@Value
@RequiredArgsConstructor
public class DisplaySettingByDate implements DomainValue{

	// 表示範囲
	private final DisplayRangeType dispRange;
	
	// 開始時刻
	private final DisplayStartTime dispStart;
	
	// 初期表示の開始時刻
	private final DisplayStartTime initDispStart;
	
	/**
	 * [C-1] 作る
	 * @param rangeType
	 * @param start
	 * @param initStart
	 * @return DisplaySettingByDate
	 */
	public static DisplaySettingByDate create (DisplayRangeType rangeType, DisplayStartTime start, DisplayStartTime initStart) {
		if (start.v() > initStart.v()) {
			throw new BusinessException("Msg_1804");
		}
		return new DisplaySettingByDate(rangeType, start, initStart);	
	}
}
