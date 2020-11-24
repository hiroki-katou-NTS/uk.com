package nts.uk.ctx.at.request.app.command.application.holidaywork;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.overtime.AgreeOverTimeCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.AppOvertimeDetailCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.AppReflectOtHdWorkCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.ApplicationTimeCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.DivergenceReasonSelectCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.OvertimeWorkFrameCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.TimeZoneWithWorkNoCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.WorkInformationCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.WorkdayoffFrameCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.hdworkapplicationsetting.HolidayWorkAppSetCommand;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr;
import nts.uk.shr.com.context.AppContexts;

/**
 * Refactor5
 * @author huylq
 *
 */
@NoArgsConstructor
@Data
public class AppHolidayWorkCmd {

	/**
	 * 勤務情報
	 */
	private WorkInformationCommand workInformation;
	
	/**
	 * 申請時間
	 */
	private ApplicationTimeCommand applicationTime;
	
	/**
	 * 直帰区分
	 */
	private boolean backHomeAtr;
	
	/**
	 * 直行区分
	 */
	private boolean goWorkAtr;
	
	/**
	 * 休憩時間帯
	 */
	private TimeZoneWithWorkNoCommand breakTime;
	
	/**
	 * 勤務時間帯
	 */
	private TimeZoneWithWorkNoCommand workingTime;
	
	/**
	 * 時間外時間の詳細
	 */
	private AppOvertimeDetailCommand appOvertimeDetail;
	
	public ApplicationDto application;
	
	public AppHolidayWorkCmd(WorkInformationCommand workInformation, ApplicationTimeCommand applicationTime, boolean backHomeAtr, boolean goWorkAtr, 
			TimeZoneWithWorkNoCommand breakTime, TimeZoneWithWorkNoCommand workingTime, AppOvertimeDetailCommand appOvertimeDetail) {
		this.workInformation = workInformation;
		this.applicationTime = applicationTime;
		this.backHomeAtr = backHomeAtr;
		this.goWorkAtr = goWorkAtr;
		this.breakTime = breakTime;
		this.workingTime = workingTime;
		this.appOvertimeDetail = appOvertimeDetail;
	}
	
	public Application toDomainApplication() {
		return application.toDomain();
	}
	
	public AppHolidayWork toDomain() {
		return new AppHolidayWork(this.workInformation.toDomain(), this.applicationTime.toDomain(), 
				this.backHomeAtr ? NotUseAtr.USE : NotUseAtr.NOT_USE, this.goWorkAtr ? NotUseAtr.USE : NotUseAtr.NOT_USE, 
				Optional.ofNullable(this.breakTime.toDomain()), Optional.ofNullable(this.workingTime.toDomain()), 
				Optional.ofNullable(this.appOvertimeDetail.toDomain(AppContexts.user().companyId(), application.getAppID())));
	}
}
