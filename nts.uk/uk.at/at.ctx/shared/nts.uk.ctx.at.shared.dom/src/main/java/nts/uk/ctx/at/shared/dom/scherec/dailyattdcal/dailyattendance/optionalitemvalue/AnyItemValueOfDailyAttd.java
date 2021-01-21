package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 日別勤怠の任意項目
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.任意項目値.日別勤怠の任意項目
 * @author tutk
 *
 */
@Getter
@NoArgsConstructor
public class AnyItemValueOfDailyAttd {
	/** 任意項目値: 任意項目値 */
	@Setter
	private List<AnyItemValue> items;

	public AnyItemValueOfDailyAttd(List<AnyItemValue> items) {
		super();
		this.items = items;
	}
}
