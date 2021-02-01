package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.service.MaxDaySpecHdOutput;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaxDaySpecHdDto {
	//上限日数
	private int maxDay;
	//喪主加算日数
	private int dayOfRela;
	
	public static MaxDaySpecHdDto fromDomain(MaxDaySpecHdOutput output) {
		return new MaxDaySpecHdDto(
				output.getMaxDay(),
				output.getDayOfRela());
	}
	
}
