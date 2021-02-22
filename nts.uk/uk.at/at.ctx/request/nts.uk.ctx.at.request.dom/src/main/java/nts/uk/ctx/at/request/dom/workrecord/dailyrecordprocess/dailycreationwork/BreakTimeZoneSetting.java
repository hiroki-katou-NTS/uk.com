package nts.uk.ctx.at.request.dom.workrecord.dailyrecordprocess.dailycreationwork;

import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;

/**
 * Refactor5
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.日別実績処理.作成処理.作成用クラス.日別作成WORK
 * @author hoangnd
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
// 休憩時間帯設定
public class BreakTimeZoneSetting {
	// 時間帯
	private List<DeductionTime> timeZones = Collections.emptyList();
}
