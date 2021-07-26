package nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.time;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.ReviseValue;

/**
 * 時間・時刻型編集
 */
@AllArgsConstructor
@Getter
public class TimeRevise implements ReviseValue {
	
	/** 時分 */
	private HourlySegment hourly;
	
	/** 進数 */
	private TimeBaseNumber baseNumber;
	
	/** 区切り文字 */
	private Optional<TimeBase60Delimiter> delimiter;
	
	/** 端数処理 */
	private TimeRounding rounding;

	@Override
	public Object revise(String target) {
		Long longResult;
		
		if (baseNumber == TimeBaseNumber.HEXA_DECIMAL) {
			// 60進数の場合
			// 区切り文字を用いて時分→分変換
			longResult = delimiter.get().convert(target);
		}else {
			// 10進数の場合
			// 分に揃える
			BigDecimal decimalTarget = hourly.toMinute(new BigDecimal(target));
			
			// 端数処理を用いて端数を処理
			longResult = rounding.round(decimalTarget);
		}
		return longResult;
	}
}
