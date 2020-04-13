package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;

/**
 * @author thanhpv
 * 振休振出申請起動時の表示情報
 */
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
	//振休振出申請
	private Optional<Application_New> application;
}
