package nts.uk.ctx.at.request.dom.applicationapproval.application.common.service.other;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.request.dom.applicationapproval.application.Application;
import nts.uk.ctx.at.request.dom.applicationapproval.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.approvalframe.ApprovalFrameRepository;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.approveaccepted.ApproveAccepted;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.approveaccepted.ApproveAcceptedRepository;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.service.other.output.OutputAllDataApp;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.service.other.output.OutputPhaseFrame;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DataAppPhaseFrameImpl implements DataAppPhaseFrame {

	@Inject
	private ApplicationRepository appRepo;

	@Inject
	private AppApprovalPhaseRepository appApprovalPhaseRepository;
	
	@Inject
	private ApprovalFrameRepository approvalFrameRepository;
	
	@Inject 
	private ApproveAcceptedRepository approveAcceptedRepo;
	
//	@Inject
//	private EmployeeAdapter employeeAdapter;
	
	@Override
	public OutputAllDataApp getAllDataAppPhaseFrame(String applicationID) {
		String companyID = AppContexts.user().companyId();
		//info app
		Optional<Application> application = this.appRepo.getAppById(companyID, applicationID);
		//list output
		List<OutputPhaseFrame> listOutputPhaseAndFrame = new ArrayList<>();
		//list phase
		List<AppApprovalPhase> listPhaseByAppID = this.appApprovalPhaseRepository.findPhaseByAppID(companyID, applicationID);
		//duyet list phase
		for(AppApprovalPhase appApprovalPhase : listPhaseByAppID) {
			//get list frame by phaseID
			List<ApprovalFrame> listFrame = 
					this.approvalFrameRepository.getAllApproverByPhaseID(companyID, appApprovalPhase.getPhaseID());
			//get list approve accepted
			for(ApprovalFrame approvalFrame :listFrame) {
				List<ApproveAccepted> listApproveAccepted = 
					this.approveAcceptedRepo.getAllApproverAccepted(companyID, approvalFrame.getFrameID());
				//set list approveAccepted to frame
				approvalFrame.setListApproveAccepted(listApproveAccepted);
			}
			//=================================================================================
			//set value : reasonAll,ApproveAll,nameAll to frame
/*			for(ApprovalFrameDto approvalFrameDto:listFrame) {
				String nameAll = "";
				String approveAll ="";
				String reasonAll = "";
				//duyet list approveaccepted to frame
				for(ApproveAcceptedDto approveAcceptedDto : approvalFrameDto.getListApproveAcceptedDto() ) {
					String str ="";
					String str1 ="";
					String str2 ="";
					String name = "";
					name = employeeAdapter.getEmployeeName(approveAcceptedDto.getApproverSID());
					if(name != "" && nameAll != "" ) {
						str = ",";
					}
					if( approveAll != "" ) {
						str1 = ",";
					}
					if( approveAcceptedDto.getReason().isEmpty() == false  && reasonAll != "" ) {
						str2 = ",";
					}
					approveAll += str1 + EnumAdaptor.valueOf(approveAcceptedDto.getApprovalATR(), ApprovalAtr.class);
					reasonAll += str2 + approveAcceptedDto.getReason() ;
					nameAll += str + name;
					
				}//end for approveAccepted
				
				
				approvalFrameDto.setNameAll(nameAll);
				approvalFrameDto.setApproveAll(approveAll);
				approvalFrameDto.setReasonAll(reasonAll);
			}//end for listFrame
*/			
			OutputPhaseFrame outputPhaseFrame = new OutputPhaseFrame(appApprovalPhase, listFrame);
					
			listOutputPhaseAndFrame.add(outputPhaseFrame);
			
		}
		OutputAllDataApp dataApp = new OutputAllDataApp(application, listOutputPhaseAndFrame);
		
		if(dataApp.equals(null)) {
			throw new BusinessException("Msg_198");
		}
		return dataApp;
	}

}
