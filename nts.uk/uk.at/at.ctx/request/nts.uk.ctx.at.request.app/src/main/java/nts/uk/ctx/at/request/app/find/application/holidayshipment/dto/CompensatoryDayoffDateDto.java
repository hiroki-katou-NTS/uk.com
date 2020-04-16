package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto;

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
	private GeneralDate dayoffDate;

	public CompensatoryDayoffDateDto(CompensatoryDayoffDate compensatoryDayoffDate) {
		super();
		this.unknownDate = compensatoryDayoffDate.isUnknownDate();
		this.dayoffDate = compensatoryDayoffDate.getDayoffDate().orElse(null);
	}
	
}
