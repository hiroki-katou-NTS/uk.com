package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.applicationlist.AppTypeSetDto;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.absenceleaveapp.AbsenceLeaveAppDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.recruitmentapp.RecruitmentAppDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSetDto;

/**
 * @author thanhpv
 * 振休振出申請起動時の表示情報
 */
@NoArgsConstructor
@Getter
@Setter
public class DisplayInforWhenStarting {

	//振出申請起動時の表示情報
	public DisplayInformationApplication applicationForWorkingDay;
	//振休申請起動時の表示情報
	public DisplayInformationApplication applicationForHoliday;
	//振休残数情報
	public RemainingHolidayInfor remainingHolidayInfor;
	//申請表示情報
	public AppDispInfoStartupDto appDispInfoStartup;
	//振休振出申請設定
	private WithDrawalReqSetDto drawalReqSet;
	
	//Phần này không có trong tài liệu thiết kế
	
	//振休申請
	public AbsenceLeaveAppDto absApp;
	//振出申請
	public RecruitmentAppDto recApp;
	//申請
	private ApplicationDto application;
	//社員名
	private String employeeName;
	//申請種類別設定
	private AppTypeSetDto appTypeSet;
	
}
