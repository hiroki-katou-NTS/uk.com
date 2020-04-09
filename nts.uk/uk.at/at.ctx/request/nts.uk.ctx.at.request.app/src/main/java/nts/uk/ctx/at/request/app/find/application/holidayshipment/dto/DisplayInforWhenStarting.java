package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;

@NoArgsConstructor
@Getter
@Setter
public class DisplayInforWhenStarting {

	//振出申請起動時の表示情報
	private DisplayInformationApplication applicationForWorkingDay;
	//申請表示情報
	private AppDispInfoStartupOutput appDispInfoStartupOutput;
	//振休申請起動時の表示情報
	private DisplayInformationApplication applicationForHoliday;
	//振休残数情報
	private RemainingHolidayInfor remainingHolidayInfor;
}
