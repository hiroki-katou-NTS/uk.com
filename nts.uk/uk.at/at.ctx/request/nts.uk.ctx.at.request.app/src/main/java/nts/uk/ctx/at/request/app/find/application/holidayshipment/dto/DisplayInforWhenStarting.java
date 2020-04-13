package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.absenceleaveapp.AbsenceLeaveAppDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.recruitmentapp.RecruitmentAppDto;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;

/**
 * @author thanhpv
 * 振休振出申請起動時の表示情報
 */
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Data
public class DisplayInforWhenStarting {

	//振出申請起動時の表示情報
	public DisplayInformationApplication applicationForWorkingDay;
	//振休申請起動時の表示情報
	public DisplayInformationApplication applicationForHoliday;
	//振休残数情報
	public RemainingHolidayInfor remainingHolidayInfor;
	//申請表示情報
	public AppDispInfoStartupOutput appDispInfoStartupOutput;
	/**
	 * 振休申請
	 * 
	 */
	public AbsenceLeaveAppDto absApp;

	/**
	 * 振出申請
	 */
	public RecruitmentAppDto recApp;
}
