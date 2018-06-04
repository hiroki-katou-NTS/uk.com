package nts.uk.ctx.at.record.dom.remainingnumber.excessleave;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 超過有休数情報
 * @author HopNT
 *
 */
@NoArgsConstructor
@Getter
public class ExcessHolidayInfo {

	// 発生数
	private ExcessiveOccurrenceTime occurrencesNumber;
	
	// 使用数
	private ExcessiveUsedTime usedNumber;
	
	// 残数
	private RemainTime remainNumer;
	
	public ExcessHolidayInfo(int occurrencesNumber, int usedNumber, int remainNumer){
		this.occurrencesNumber = new ExcessiveOccurrenceTime(occurrencesNumber);
		this.usedNumber = new ExcessiveUsedTime(usedNumber);
		this.remainNumer = new RemainTime(remainNumer);
	}
}
