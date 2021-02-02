package nts.uk.ctx.at.request.app.find.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValue;

@AllArgsConstructor
@NoArgsConstructor
public class AnyItemValueDto {
	/** 任意項目NO: 任意項目NO */
	public Integer itemNo;

	/** 回数: 日次任意回数 */
	public Integer times;

	/** 金額: 日次任意金額 */
	public Integer amount;

	/** 時間: 日次任意時間 */
	public Integer time;
	
	public static AnyItemValueDto fromDomain(AnyItemValue anyItemValue) {
		
		return new AnyItemValueDto(
				anyItemValue.getItemNo().v(),
				anyItemValue.getRowTimes().intValue(),
				anyItemValue.getAmount().map(x -> x.v()).orElse(null),
				anyItemValue.getTime().map(x -> x.v()).orElse(null));
	}
}
