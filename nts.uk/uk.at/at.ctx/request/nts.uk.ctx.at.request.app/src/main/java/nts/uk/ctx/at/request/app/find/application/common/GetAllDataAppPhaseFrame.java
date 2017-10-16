package nts.uk.ctx.at.request.app.find.application.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.find.application.common.appapprovalphase.AppApprovalPhaseDto;
import nts.uk.ctx.at.request.app.find.application.common.approvalframe.ApprovalFrameDto;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrameRepository;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAcceptedRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 15.詳細画面申請データを取得する
 * @author tutk
 *
 */
@Stateless
public class GetAllDataAppPhaseFrame {
	
	@Inject
	private ApplicationRepository appRepo;

	@Inject
	private AppApprovalPhaseRepository appApprovalPhaseRepository;
	
	@Inject
	private ApprovalFrameRepository approvalFrameRepository;
	
	@Inject 
	private ApproveAcceptedRepository approveAcceptedRepo;
	
	@Inject
	private EmployeeAdapter employeeAdapter;
	
	public ApplicationDto getAllDataAppPhaseFrame(String applicationID) {
		
		String companyID = AppContexts.user().companyId();
		//info app
		ApplicationDto application = this.appRepo.getAppById(companyID, applicationID)
				.map(c->ApplicationDto.fromDomain(c)).get();
		//list output
		//List<OutputPhaseAndFrame> listOutputPhaseAndFrame = new ArrayList<>();
		//list phase
		List<AppApprovalPhaseDto> listPhaseByAppID = this.appApprovalPhaseRepository.findPhaseByAppID(companyID, applicationID)
				.stream().map(appApprovalPhase -> AppApprovalPhaseDto.fromDomain(appApprovalPhase))
				.collect(Collectors.toList());
		if(!CollectionUtil.isEmpty(listPhaseByAppID)) {
			application.setListPhase(listPhaseByAppID);
		}
		//duyet list phase
		if(!CollectionUtil.isEmpty(listPhaseByAppID)) {
			for(AppApprovalPhaseDto appApprovalPhase : listPhaseByAppID) {
				//get list frame by phaseID
				List<ApprovalFrameDto> listFrame = 
						this.approvalFrameRepository.getAllApproverByPhaseID(companyID, appApprovalPhase.getPhaseID())
						.stream().map(approvalFrame -> ApprovalFrameDto.fromDomain(approvalFrame)).collect(Collectors.toList());
				appApprovalPhase.setListFrame(listFrame);
				//get list approve accepted
				if(!CollectionUtil.isEmpty(listFrame)) {
					for(ApprovalFrameDto approvalFrameDto:listFrame) {
						List<ApproveAcceptedDto> listApproveAccepted = 
							this.approveAcceptedRepo.getAllApproverAccepted(companyID, approvalFrameDto.getFrameID())
							.stream().map(c -> ApproveAcceptedDto.fromDomain(c)).collect(Collectors.toList());
						//set list approveAccepted to frame
						approvalFrameDto.setListApproveAccepted(listApproveAccepted);
					}
					
					//set value : reasonAll,ApproveAll,nameAll to frame
					for(ApprovalFrameDto approvalFrameDto:listFrame) {
						String nameAll = "";
						String approveAll ="";
						String reasonAll = "";
						//duyet list approveaccepted to frame
						for(ApproveAcceptedDto approveAcceptedDto : approvalFrameDto.getListApproveAccepted() ) {
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
					//OutputPhaseAndFrame outputPhaseAndFrame = new OutputPhaseAndFrame(appApprovalPhase, listFrame);
					
					//listOutputPhaseAndFrame.add(outputPhaseAndFrame);
				}
				
				
			}
		}
		
		//OutputGetAllDataApp dataApp = new OutputGetAllDataApp(application, listOutputPhaseAndFrame);
		
		if(application.equals(null)) {
			throw new BusinessException("Msg_198");
		}
		return application;
	}
	
}
