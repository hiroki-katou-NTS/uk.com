package nts.uk.ctx.at.request.pubimp.application.approvalstatus;
import java.util.ArrayList;
import java.util.HashMap;
/*import java.util.Optional;
import nts.arc.time.GeneralDateTime;*/
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.DailyAttendanceUpdateStatus;
import nts.uk.ctx.at.request.dom.application.ReflectionStatus;
import nts.uk.ctx.at.request.dom.application.ReflectionStatusOfDay;
import nts.uk.ctx.at.request.pub.application.approvalstatus.ApplicationDateExport;
import nts.uk.ctx.at.request.pub.application.approvalstatus.ApplicationExport;
import nts.uk.ctx.at.request.pub.application.approvalstatus.DailyAttendanceUpdateStatusExport;
import nts.uk.ctx.at.request.pub.application.approvalstatus.ReflectionStatusExport;
import nts.uk.ctx.at.request.pub.application.approvalstatus.ReflectionStatusOfDayExport;
import nts.uk.ctx.at.request.pub.application.approvalstatus.ReflectionStatusPub;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class ReflectionStatusImpl implements ReflectionStatusPub {
	
	@Inject
	private ApplicationRepository repo;

	@Override
	public Map<String, List<ApplicationDateExport>> getAppByEmployeeDate(List<String> employeeIDS,
			GeneralDate startDate, GeneralDate endDate) {
		
		Map<String, List<ApplicationDateExport>> mapList = new HashMap<>();
		String companyId = AppContexts.user().companyId();
		List<ApplicationDateExport> appdate = new ArrayList<>();
		
		for(String employee : employeeIDS) {
			
			// get application from list employee and date period
			List<Application> listApp =  repo.getListLateOrLeaveEarly(companyId, employee, startDate, endDate);
			
			// convert data type from domain to export 
			List<ApplicationExport> listAppExport = listApp.stream()
													.map(x -> new ApplicationExport(x.getAppID(), 
															x.getPrePostAtr().value, x.getEmployeeID(),
															x.getAppType().value, x.getAppDate().getApplicationDate(),
															x.getEnteredPersonID(), 
															x.getInputDate(), this.fromDomainStatus(x.getReflectionStatus()),
															x.getOpStampRequestMode().isPresent() ? x.getOpStampRequestMode().get().value : null, 
															x.getOpReversionReason().isPresent() ? x.getOpReversionReason().get().v() : null, 
															x.getOpAppStartDate().isPresent() ? x.getOpAppStartDate().get().getApplicationDate() : null, 
															x.getOpAppEndDate().isPresent() ? x.getOpAppEndDate().get().getApplicationDate() : null, 
															x.getOpAppReason().isPresent() ? x.getOpAppReason().get().v() : null, 
															x.getOpAppStandardReasonCD().isPresent() ? x.getOpAppStandardReasonCD().get().v() : null))
													.collect(Collectors.toList());
			
			for(ApplicationExport appExport :listAppExport) {
				appdate.add(new ApplicationDateExport(appExport.getAppDate(), appExport));
			}
		
			mapList.put(employee, appdate);
			
		}
		
		return mapList;
	}


//	private ApplicationNewExport fromDomain(Application domain){
//		return new ApplicationNewExport(
//				domain.getAppID(),
//				domain.getPrePostAtr().value,
//				domain.getInputDate(),
//				domain.getEnteredPersonID(),
//				from(domain.getReflectionInformation()),
//				domain.getReversionReason().v(),
//				domain.getAppDate(),
//				domain.getAppReason().v(),
//				domain.getAppType().value,
//				domain.getEmployeeID(),
//				domain.getStartDate(),
//				domain.getEndDate());
//	}
//	private ReflectionInformation_NewDto from (ReflectionInformation_New domain){
//		return new ReflectionInformation_NewDto(
//				domain.getStateReflection().value, 
//				domain.getStateReflectionReal().value, 
//				domain.getForcedReflection().value, 
//				domain.getForcedReflectionReal().value,
//				!domain.getNotReason().isPresent()?null:domain.getNotReason().get().value,
//				!domain.getNotReasonReal().isPresent()?null:domain.getNotReasonReal().get().value,
//				domain.getDateTimeReflection(),
//				domain.getDateTimeReflectionReal());
//	}
//	@Override
//	public List<ApplicationNewExport> getByListRefStatus(String employeeID, GeneralDate startDate, GeneralDate endDate, List<Integer> listReflecInfor) {
//		String companyID = AppContexts.user().companyId();
//		List<ApplicationNewExport> data = repo.getByListRefStatus(companyID, employeeID, startDate, endDate, listReflecInfor).stream().map(c->fromDomain(c)).collect(Collectors.toList()); 
//		return data;
//	}
	
	private ReflectionStatusExport fromDomainStatus(ReflectionStatus domain) {
		return new ReflectionStatusExport(this.convertfromDomain(domain.getListReflectionStatusOfDay()));
	}

	private List<ReflectionStatusOfDayExport> convertfromDomain(List<ReflectionStatusOfDay> listDomain){
		
		List<ReflectionStatusOfDayExport> listStatus = new ArrayList<>();
		
		listStatus = listDomain.stream().map(x -> this.fromDomainStatusDay(x)).collect(Collectors.toList());
		
		return listStatus;
	}
	
	private ReflectionStatusOfDayExport fromDomainStatusDay(ReflectionStatusOfDay domain) {
		return new ReflectionStatusOfDayExport(domain.getActualReflectStatus().value,
												domain.getScheReflectStatus().value, 
												domain.getTargetDate(), this.fromDomainUpdate(domain.getOpUpdateStatusAppReflect()), 
												this.fromDomainUpdate(domain.getOpUpdateStatusAppCancel()));
	}
	
	private Optional<DailyAttendanceUpdateStatusExport> fromDomainUpdate(Optional<DailyAttendanceUpdateStatus> domain) {
		
		if(domain.isPresent()){
			DailyAttendanceUpdateStatusExport daily = new DailyAttendanceUpdateStatusExport(domain.get().getOpActualReflectDateTime(), 
					domain.get().getOpScheReflectDateTime(),
					domain.get().getOpReasonActualCantReflect().isPresent() ? domain.get().getOpReasonActualCantReflect().get().value : null, 
					domain.get().getOpReasonScheCantReflect().isPresent() ? domain.get().getOpReasonActualCantReflect().get().value : null);
			return Optional.of(daily);
		}else {
			return Optional.empty();
		}
		
		
		
	}
}
