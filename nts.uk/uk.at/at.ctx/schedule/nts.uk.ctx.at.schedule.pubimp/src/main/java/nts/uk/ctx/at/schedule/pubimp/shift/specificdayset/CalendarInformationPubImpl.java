package nts.uk.ctx.at.schedule.pubimp.shift.specificdayset;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSetting;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.service.CalendarInformationOutput;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.service.ICalendarInformationService;
import nts.uk.ctx.at.schedule.pub.shift.specificdayset.BasicWorkSettingExport;
import nts.uk.ctx.at.schedule.pub.shift.specificdayset.CalendarInformationExport;
import nts.uk.ctx.at.schedule.pub.shift.specificdayset.CalendarInformationPub;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class CalendarInformationPubImpl implements CalendarInformationPub{
	
	@Inject
	private ICalendarInformationService calendarInformationService;
	
	@Override
	public CalendarInformationExport getCalendarInformation(String companyID, String workplaceID, String classCD,
			GeneralDate date) {
		CalendarInformationOutput calendarInformationOutput = calendarInformationService.getCalendarInformation(companyID, workplaceID, classCD, date);
		return new CalendarInformationExport(calendarInformationOutput.getWorkTypeCD(), calendarInformationOutput.getSiftCD(), calendarInformationOutput.getDate());
	}

	@Override
	public Integer getWorkingDayAtr(String companyID, String workplaceID, String classCD, GeneralDate date) {
		WorkdayDivision useSet =calendarInformationService.getWorkingDayAtr(companyID, workplaceID, classCD, date);
		if(useSet != null)
			return useSet.value;
		return null;
	}
	
	@Override
	public BasicWorkSettingExport getBasicWorkSetting (String companyID, String workplaceID, String classCD, Integer workingDayAtr) {
		BasicWorkSetting basicWorkSetting = calendarInformationService.getBasicWorkSetting(companyID, workplaceID, classCD, workingDayAtr);
		return new BasicWorkSettingExport(basicWorkSetting.getWorktypeCode().v(), basicWorkSetting.getWorkingCode().v(), basicWorkSetting.getWorkdayDivision().value);
	}

}
