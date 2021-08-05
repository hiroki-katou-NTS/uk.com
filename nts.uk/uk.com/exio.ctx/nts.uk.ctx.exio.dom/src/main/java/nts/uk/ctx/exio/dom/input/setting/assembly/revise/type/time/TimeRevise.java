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
	private Optional<TimeBaseNumber> baseNumber;
	
	/** 区切り文字 */
	private Optional<TimeBase60Delimiter> delimiter;
	
	/** 端数処理 */
	private Optional<TimeBase10Rounding> rounding;

	@Override
	public Object revise(String target) {
		
		if (hourly == HourlySegment.MINUTE) {
			return hourly.toMinutesDecimal(new BigDecimal(target)).intValue();
		}
		
		if (baseNumber.get() == TimeBaseNumber.SEXAGESIMAL) {
			// 60進数は、区切り文字を用いて時分→分変換
			return delimiter.get().toMinutes(target);
		} else {
			// 10進数は、分に変換して端数処理
			BigDecimal minutes = hourly.toMinutesDecimal(new BigDecimal(target));
			return rounding.get().round(minutes);
		}
	}
}
