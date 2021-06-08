package nts.uk.ctx.exio.dom.input.revise.type.time;

import java.math.BigDecimal;
import java.util.Optional;

import nts.uk.ctx.exio.dom.input.revise.ReviseValue;
import nts.uk.ctx.exio.dom.input.revise.RevisedValueResult;
import nts.uk.ctx.exio.dom.input.revise.type.RangeOfValue;

public class TimeRevise implements ReviseValue {
	
	/** 値の有効範囲を指定する */
	private boolean useSpecifyRange;
	
	/** 値の有効範囲 */
	private Optional<RangeOfValue> rangeOfValue;
	
	/** 時分 */
	private HourlySegment hourly;
	
	/** 進数 */
	private TimeBaseNumber baseNumber;
	
	/** 区切り文字 */
	private Optional<TimeDelimiter> delimiter;
	
	/** 端数処理 */
	private TimeRounding rounding;

	@Override
	public RevisedValueResult revise(String target) {
		String strTarget = target;
		if(useSpecifyRange) {
			// 値の有効範囲を指定する場合
			strTarget = this.rangeOfValue.get().extract(target);
		}
		
		Long longResult;
		if (hourly == HourlySegment.HOUR_MINUTE) {
			// 時分の場合
			if (baseNumber == TimeBaseNumber.HEXA_DECIMAL) {
				// 60進数の場合
				// 区切り文字を用いて時分→分変換
				longResult = delimiter.get().convert(strTarget);
			}else {
				// 10進数の場合
				// 時分区分を用いて時分→分変換
				BigDecimal decimalTarget = new BigDecimal(strTarget);
				longResult = hourly.hourToMinute(decimalTarget, rounding);
			}
		}else {
			// 分の場合
			// 端数処理を用いて端数を処理
			BigDecimal decimalTarget = new BigDecimal(strTarget);
			longResult = rounding.round(decimalTarget);
		}
		return RevisedValueResult.succeeded(longResult);
	}
}
