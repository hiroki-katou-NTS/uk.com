package nts.uk.screen.at.app.ktgwidget.find.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class YearMonthOvertime {
	/**対象年月*/
	private Integer yearMonth;

	/** 対象年月の時間外時間 */
	AgreementTimeOfManagePeriodDto agreeTime;

}
