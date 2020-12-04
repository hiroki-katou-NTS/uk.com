package nts.uk.ctx.at.request.app.find.application.overtime;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OneMonthTimeDto {
	
	public OneMonthErrorAlarmTimeDto erAlTime;
	
	public Integer upperLimit;
	
	public static OneMonthTimeDto fromDomain(OneMonthTime oneMonthTime) {
		
		return new OneMonthTimeDto(
				OneMonthErrorAlarmTimeDto.fromDomain(oneMonthTime.getErAlTime()),
				oneMonthTime.getUpperLimit().v());
	}
}
