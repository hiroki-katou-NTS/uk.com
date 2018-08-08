package nts.uk.ctx.at.request.pubimp.application.approvalstatus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.ReflectionInformation_New;
import nts.uk.ctx.at.request.pub.application.approvalstatus.ApplicationNewExport;
import nts.uk.ctx.at.request.pub.application.approvalstatus.ReflectionInformation_NewDto;
import nts.uk.ctx.at.request.pub.application.approvalstatus.ReflectionStatusPub;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class ReflectionStatusImpl implements ReflectionStatusPub {
	
	@Inject
	private ApplicationRepository_New repo;


	private ApplicationNewExport fromDomain(Application_New domain){
		return new ApplicationNewExport(
				domain.getAppID(),
				domain.getPrePostAtr().value,
				domain.getInputDate(),
				domain.getEnteredPersonID(),
				from(domain.getReflectionInformation()),
				domain.getReversionReason().v(),
				domain.getAppDate(),
				domain.getAppReason().v(),
				domain.getAppType().value,
				domain.getEmployeeID(),
				domain.getStartDate(),
				domain.getEndDate());
	}
	private ReflectionInformation_NewDto from (ReflectionInformation_New domain){
		return new ReflectionInformation_NewDto(
				domain.getStateReflection().value, 
				domain.getStateReflectionReal().value, 
				domain.getForcedReflection().value, 
				domain.getForcedReflectionReal().value,
				!domain.getNotReason().isPresent()?null:domain.getNotReason().get().value,
				!domain.getNotReasonReal().isPresent()?null:domain.getNotReasonReal().get().value,
				domain.getDateTimeReflection(),
				domain.getDateTimeReflectionReal());
	}
	@Override
	public List<ApplicationNewExport> getByListRefStatus(String employeeID, GeneralDate startDate, GeneralDate endDate, List<Integer> listReflecInfor) {
		String companyID = AppContexts.user().companyId();
		List<ApplicationNewExport> data = repo.getByListRefStatus(companyID, employeeID, startDate, endDate, listReflecInfor).stream().map(c->fromDomain(c)).collect(Collectors.toList()); 
		return data;
	}
	
	

}
