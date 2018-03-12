package nts.uk.ctx.at.request.app.find.application.applicationlist;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.setting.company.displayname.AppDispNameRepository;
import nts.uk.ctx.at.request.dom.setting.company.displayname.HdAppDispNameRepository;

@Stateless
public class ApplicationListForScreen {
	
	@Inject
	private ApplicationRepository_New applicationRepository_New;
	@Inject
	private AppDispNameRepository appDispNameRepository;
	@Inject
	private HdAppDispNameRepository hdAppDispNameRepository;
	/**
	 * requestList #26
	 * getApplicationBySID 
	 * @param employeeID
	 * @param startDate
	 * @param endDate
	 * @return list<ApplicationExport>
	 */
	public List<ApplicationExportDto> getApplicationBySID(List<String> employeeID, GeneralDate startDate, GeneralDate endDate){
		List<ApplicationExportDto> applicationExports = new ArrayList<>();
		List<Application_New> application = this.applicationRepository_New.getApplicationBySIDs(employeeID, startDate, endDate);
		if(CollectionUtil.isEmpty(application)){
			return applicationExports;
		}
		List<Application_New> applicationExcessHoliday = application.stream().filter(x -> x.getAppType().value != ApplicationType.ABSENCE_APPLICATION.value).collect(Collectors.toList());
		for(Application_New app : applicationExcessHoliday){
			ApplicationExportDto applicationExport = new ApplicationExportDto();
			applicationExport.setAppDate(app.getAppDate());
			applicationExport.setAppType(app.getAppType().value);
			applicationExport.setEmployeeID(app.getEmployeeID());
			applicationExport.setAppTypeName(appDispNameRepository.getDisplay(app.getAppType().value).isPresent() ? appDispNameRepository.getDisplay(app.getAppType().value).get().getDispName().toString() : "" );
			applicationExports.add(applicationExport);
		}
		List<Application_New> applicationHoliday = application.stream().filter(x -> x.getAppType().value == ApplicationType.ABSENCE_APPLICATION.value).collect(Collectors.toList());
		for(Application_New app : applicationHoliday){
			ApplicationExportDto applicationExport = new ApplicationExportDto();
			applicationExport.setAppDate(app.getAppDate());
			applicationExport.setAppType(app.getAppType().value);
			applicationExport.setEmployeeID(app.getEmployeeID());
			applicationExport.setAppTypeName(hdAppDispNameRepository.getHdApp(app.getAppType().value).isPresent() ? hdAppDispNameRepository.getHdApp(app.getAppType().value).get().getDispName().toString() : "" );
			applicationExports.add(applicationExport);
		}
		return applicationExports;
	} 
}
