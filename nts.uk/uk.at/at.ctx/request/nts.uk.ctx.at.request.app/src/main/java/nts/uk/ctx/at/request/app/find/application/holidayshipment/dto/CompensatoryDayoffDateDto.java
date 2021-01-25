package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CompensatoryDayoffDateDto {
	
	// 日付不明
	private boolean unknownDate;
	
	// 年月日
	private String dayoffDate;

	public CompensatoryDayoffDateDto(CompensatoryDayoffDate compensatoryDayoffDate) {
		super();
		this.unknownDate = compensatoryDayoffDate.isUnknownDate();
		this.dayoffDate = compensatoryDayoffDate.getDayoffDate().map(x -> x.toString("yyyy/MM/dd")).orElse(null);
	}
	
	public CompensatoryDayoffDate toDomain() {
	    return new CompensatoryDayoffDate(
	            unknownDate, 
	            dayoffDate == null ? Optional.empty() : Optional.of(GeneralDate.fromString(dayoffDate, "yyyy/MM/dd")));
	}
}
