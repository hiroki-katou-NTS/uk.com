package nts.uk.ctx.at.request.app.find.application.common;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.common.dto.AchievementDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.RootData;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.other.CollectAchievement;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class GetDataApprovalRootOfSubjectRequest {
	
	@Inject 
	private ApprovalRootAdapter approvalRootRepo;
	
	@Inject
	private CollectAchievement collectAchievement;
	
	public RootData getApprovalRootOfSubjectRequest(ObjApprovalRootInput objApprovalRootInput){
		String companyID = AppContexts.user().companyId();
		String sid = "";
		if(Strings.isBlank(objApprovalRootInput.getSid())){
			sid = AppContexts.user().employeeId();
		} else {
			sid = objApprovalRootInput.getSid();
		}
		GeneralDate generalDate = GeneralDate.fromString(objApprovalRootInput.getStandardDate(), "yyyy/MM/dd");
		List<ApprovalRootOfSubjectRequestDto> listApproval =  this.approvalRootRepo.getApprovalRootOfSubjectRequest(companyID,
				sid, objApprovalRootInput.getEmploymentRootAtr(), 
				objApprovalRootInput.getAppType(),generalDate)
				.stream()
				.map(c->ApprovalRootOfSubjectRequestDto.fromDomain(c))
				.collect(Collectors.toList());
		AchievementOutput achievementOutput = collectAchievement.getAchievement(companyID, sid, generalDate);
		AchievementDto achievementDto = AchievementDto.convertFromAchievementOutput(achievementOutput);
		return new RootData(listApproval, achievementDto);
	}

}
