/**
 * 
 */
package nts.uk.screen.at.app.ksu001.start;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;
import nts.uk.screen.at.app.ksu001.eventinformationandpersonal.DataSpecDateAndHolidayDto;
import nts.uk.screen.at.app.ksu001.eventinformationandpersonal.EventInfoAndPerCondPeriodParam;
import nts.uk.screen.at.app.ksu001.eventinformationandpersonal.EventInfoAndPersonalConditionsPeriod;
import nts.uk.screen.at.app.ksu001.extracttargetemployees.ExtractTargetEmployeesParam;
import nts.uk.screen.at.app.ksu001.extracttargetemployees.ScreenQueryExtractTargetEmployees;
import nts.uk.screen.at.app.ksu001.getinfoofInitstartup.DataScreenQueryGetInforDto;
import nts.uk.screen.at.app.ksu001.getinfoofInitstartup.ScreenQueryGetInforOfInitStartup;

/**
 * @author laitv
 * 初期起動
 * path: UKDesign.UniversalK.就業.KSU_スケジュール.KSU001_個人スケジュール修正(職場別).A：個人スケジュール修正（職場別）.メニュー別OCD.初期起動
 *
 */
@Stateless
public class StartKSU001 {
	
	@Inject
	private ScreenQueryGetInforOfInitStartup getInforOfInitStartup;
	@Inject
	private ScreenQueryExtractTargetEmployees extractTargetEmployees;
	@Inject
	private EventInfoAndPersonalConditionsPeriod eventInfoAndPersonalCondPeriod;
	
	public StartKSU001Dto initialData(StartKSU001Param param) {
		
		//step 1 
		DataScreenQueryGetInforDto resultStep1 = getInforOfInitStartup.getDataInit();
		
		//step 2
		String workplaceId = resultStep1.targetOrgIdenInfor.workplaceId.isPresent()
				? resultStep1.targetOrgIdenInfor.workplaceId.get() : null;
		String workplaceGroupId = resultStep1.targetOrgIdenInfor.workplaceGroupId.isPresent()
				? resultStep1.targetOrgIdenInfor.workplaceGroupId.get() : null;
		GeneralDate startDate = param.startDate == null ? resultStep1.startDate : param.startDate;
		GeneralDate endDate = param.endDate == null ? resultStep1.endDate : param.endDate;
		
		ExtractTargetEmployeesParam param2 = new ExtractTargetEmployeesParam(endDate, workplaceId, workplaceGroupId);
		List<EmployeeInformationImport> resultStep2 = extractTargetEmployees.getListEmp(param2);
		
		//step 3
		List<String> listSid = resultStep2.stream().map(mapper -> mapper.getEmployeeId()).collect(Collectors.toList());
		EventInfoAndPerCondPeriodParam param3 = new EventInfoAndPerCondPeriodParam(startDate, endDate, workplaceId, workplaceGroupId, listSid);
		DataSpecDateAndHolidayDto resultStep3 = eventInfoAndPersonalCondPeriod.get(param3);
		
		
		if (param.dataLocalstorageEmpty) {
			// step4 || step 5.2
			
			

		} else {
			// step5.1

		}
		
		return new StartKSU001Dto(resultStep1, resultStep2, resultStep3);
	}
}
