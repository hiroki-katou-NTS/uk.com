package nts.uk.screen.at.app.ktgwidget.find.dto;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.record.app.find.monthly.root.AgreementTimeOfManagePeriodDto;

@Data
@Builder
public class YearMonthOvertime {
	/**対象年月*/
	private Integer yearMonth;

	/** 対象年月の時間外時間 */
	private AgreementTimeOfManagePeriodDto agreeTime;
}
