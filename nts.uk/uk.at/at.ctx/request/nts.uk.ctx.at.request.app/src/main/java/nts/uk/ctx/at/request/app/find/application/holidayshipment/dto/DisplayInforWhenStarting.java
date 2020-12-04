package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
	//申請表示情報
	public AppDispInfoStartupDto appDispInfoStartup;
	//振休申請起動時の表示情報
	public DisplayInformationApplication applicationForHoliday;
	//振休残数情報
	public RemainingHolidayInfor remainingHolidayInfor;
	//振休振出申請設定
	public WithDrawalReqSetDto drawalReqSet;
	
	//振休紐付け管理区分
	public Integer holidayManage;
	
	//代休紐付け管理区分
	public Integer substituteManagement;
	
	//振休申請の反映
	//振出申請の反映
	
	//振休申請
	public AbsenceLeaveAppDto absApp;
	//振出申請
	public RecruitmentAppDto recApp;
	
}
