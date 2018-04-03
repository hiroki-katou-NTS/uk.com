package nts.uk.ctx.at.request.pubimpemploymentfunction.algorithm.dailyaggregation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;
import nts.uk.ctx.at.request.dom.setting.company.displayname.AppDispNameRepository;
import nts.uk.ctx.at.request.dom.setting.company.displayname.HdAppDispNameRepository;
import nts.uk.ctx.at.request.pub.screen.nts.uk.ctx.workflow.pub.employmentfunction.algorithm.dailyaggregation.DailyAggregationProcessExport;
import nts.uk.ctx.at.request.pub.screen.nts.uk.ctx.workflow.pub.employmentfunction.algorithm.dailyaggregation.DailyProcessRemandsPub;
/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class DailyProcessRemandsImpl implements DailyProcessRemandsPub {

	@Inject
	private ApplicationRepository_New respo;
	
	@Inject
	private AppDispNameRepository appDispNameRepository;
	
	@Inject
	private HdAppDispNameRepository hdAppDispNameRepository;
	

	@Override
	public List<DailyAggregationProcessExport> findByIDRemands(List<String> employeeID, GeneralDate startDate, GeneralDate endDate) {
		return this.getApplicationBySIDs(employeeID, startDate, endDate, ReflectedState_New.REMAND);
	}	
	 
	
	/**
	 * 
	 * @param employeeID
	 * @param startDate
	 * @param endDate
	 * @param stateReflectionReal
	 * @return List<DailyAggregationProcessExport>
	 */
	private List<DailyAggregationProcessExport> getApplicationBySIDs(List<String> employeeID, GeneralDate startDate, GeneralDate endDate, ReflectedState_New stateReflectionReal) {
		List<Application_New> listApp = this.respo.getApplicationBySIDs(employeeID, startDate, endDate)
				.stream().filter(x -> x.getReflectionInformation().getStateReflectionReal().value == stateReflectionReal.value)
				.collect(Collectors.toList());;
		
		List<DailyAggregationProcessExport> dailyAggregationProcessExports = new ArrayList<>();
		List<GeneralDate> date = new ArrayList<>();
				
		for (Application_New app : listApp){
			if(app.getAppDate().after(startDate) && app.getAppDate().before(endDate)) {
			     date.add(app.getAppDate());
			    }
		}
	     if(date.size() != 0){
	    	 List<Application_New> applicationExcessHoliday = listApp.stream().filter(x -> x.getAppType().value != ApplicationType.ABSENCE_APPLICATION.value).collect(Collectors.toList());
	 		for(Application_New app : applicationExcessHoliday){
	 			DailyAggregationProcessExport processExport = new DailyAggregationProcessExport();
	 			processExport.setEmployeeID(app.getEmployeeID());
	 			processExport.setAppDate(app.getAppDate());
	 			processExport.setAppType(app.getAppType().value);
	 			processExport.setAppTypeName(appDispNameRepository.getDisplay(app.getAppType().value).isPresent() ? appDispNameRepository.getDisplay(app.getAppType().value).get().getDispName().toString() : "" );
	 			dailyAggregationProcessExports.add(processExport);
	 		}
	 		
	 		List<Application_New> applicationHoliday = listApp.stream().filter(x -> x.getAppType().value == ApplicationType.ABSENCE_APPLICATION.value).collect(Collectors.toList());
			for(Application_New application_New : applicationHoliday){
				DailyAggregationProcessExport applicationExport = new DailyAggregationProcessExport();
				applicationExport.setEmployeeID(application_New.getEmployeeID());
				applicationExport.setAppDate(application_New.getAppDate());
				applicationExport.setAppType(application_New.getAppType().value);
				applicationExport.setAppTypeName(hdAppDispNameRepository.getHdApp(application_New.getAppType().value).isPresent() ? hdAppDispNameRepository.getHdApp(application_New.getAppType().value).get().getDispName().toString() : "" );
				dailyAggregationProcessExports.add(applicationExport);
			}
	     }
		
		return dailyAggregationProcessExports;
	}

}
