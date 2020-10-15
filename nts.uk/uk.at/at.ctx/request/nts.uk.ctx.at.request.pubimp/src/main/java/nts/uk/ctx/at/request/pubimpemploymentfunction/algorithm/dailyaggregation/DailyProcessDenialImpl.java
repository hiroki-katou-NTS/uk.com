package nts.uk.ctx.at.request.pubimpemploymentfunction.algorithm.dailyaggregation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.request.dom.setting.company.displayname.AppDispNameRepository;
import nts.uk.ctx.at.request.dom.setting.company.displayname.HdAppDispNameRepository;
import nts.uk.ctx.at.request.pub.screen.nts.uk.ctx.workflow.pub.employmentfunction.algorithm.dailyaggregation.DailyAggregationProcessExport;
import nts.uk.ctx.at.request.pub.screen.nts.uk.ctx.workflow.pub.employmentfunction.algorithm.dailyaggregation.DailyProcessDenialPub;
/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class DailyProcessDenialImpl implements DailyProcessDenialPub {

	@Inject
	private ApplicationRepository respo;
	
	@Inject
	private AppDispNameRepository appDispNameRepository;
	
	@Inject
	private HdAppDispNameRepository hdAppDispNameRepository;
	
	@Override
	public List<DailyAggregationProcessExport> findByIDDenial(List<String> employeeID, GeneralDate startDate, GeneralDate endDate) {
		return this.getApplicationBySIDs(employeeID, startDate, endDate, ReflectedState.DENIAL);
	}	
	
	/**
	 * 
	 * @param employeeID
	 * @param startDate
	 * @param endDate
	 * @param stateReflectionReal
	 * @return List<DailyAggregationProcessExport>
	 */
	private List<DailyAggregationProcessExport> getApplicationBySIDs(List<String> employeeID, GeneralDate startDate, GeneralDate endDate, ReflectedState stateReflectionReal) {
		List<Application> listApp = this.respo.getApplicationBySIDs(employeeID, startDate, endDate)
				.stream().filter(x -> x.getAppReflectedState().value == stateReflectionReal.value)
				.collect(Collectors.toList());;
		
		List<DailyAggregationProcessExport> dailyAggregationProcessExports = new ArrayList<>();
		List<GeneralDate> date = new ArrayList<>();
				
		for (Application app : listApp){
			if(app.getAppDate().getApplicationDate().after(startDate) && app.getAppDate().getApplicationDate().before(endDate)) {
			     date.add(app.getAppDate().getApplicationDate());
			    }
		}
	     if(date.size() != 0){
	    	 List<Application> applicationExcessHoliday = listApp.stream().filter(x -> x.getAppType().value != ApplicationType.ABSENCE_APPLICATION.value).collect(Collectors.toList());
	 		for(Application app : applicationExcessHoliday){
	 			DailyAggregationProcessExport processExport = new DailyAggregationProcessExport();
	 			processExport.setEmployeeID(app.getEmployeeID());
	 			processExport.setAppDate(app.getAppDate().getApplicationDate());
	 			processExport.setAppType(app.getAppType().value);
	 			processExport.setAppTypeName(appDispNameRepository.getDisplay(app.getAppType().value).isPresent() ? appDispNameRepository.getDisplay(app.getAppType().value).get().getDispName().toString() : "" );
	 			dailyAggregationProcessExports.add(processExport);
	 		}
	 		
	 		List<Application> applicationHoliday = listApp.stream().filter(x -> x.getAppType().value == ApplicationType.ABSENCE_APPLICATION.value).collect(Collectors.toList());
			for(Application application_New : applicationHoliday){
				DailyAggregationProcessExport applicationExport = new DailyAggregationProcessExport();
				applicationExport.setEmployeeID(application_New.getEmployeeID());
				applicationExport.setAppDate(application_New.getAppDate().getApplicationDate());
				applicationExport.setAppType(application_New.getAppType().value);
				applicationExport.setAppTypeName(hdAppDispNameRepository.getHdApp(application_New.getAppType().value).isPresent() ? hdAppDispNameRepository.getHdApp(application_New.getAppType().value).get().getDispName().toString() : "" );
				dailyAggregationProcessExports.add(applicationExport);
			}
	     }
		
		return dailyAggregationProcessExports;
	}

}
