package nts.uk.ctx.at.request.app.find.application.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.builder.CompareToBuilder;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.find.application.common.appapprovalphase.AppApprovalPhaseDto;
import nts.uk.ctx.at.request.app.find.application.common.approvalframe.ApprovalFrameDto;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverInfoImport;
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
	private EmployeeRequestAdapter employeeAdapter;
	
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
			Collections.sort(listPhaseByAppID, Comparator.comparing(AppApprovalPhaseDto::getDispOrder));
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
							switch(approveAcceptedDto.getApprovalATR()) {
								case 0:
									approveAcceptedDto.setNameApprovalATR(ApprovalAtr.UNAPPROVED.nameId);break;
								case 1:
									approveAcceptedDto.setNameApprovalATR(ApprovalAtr.APPROVED.nameId);break;
								case 2 : 
									approveAcceptedDto.setNameApprovalATR(ApprovalAtr.DENIAL.nameId);break;
								default :
									approveAcceptedDto.setNameApprovalATR(ApprovalAtr.UNAPPROVED.nameId);break;
							}
							if(approveAcceptedDto.getApprovalATR() == ApprovalAtr.UNAPPROVED.value) {
								approveAcceptedDto.setNameApprovalATR(ApprovalAtr.UNAPPROVED.nameId);
							}
								
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
							approveAll += str1 + approveAcceptedDto.getNameApprovalATR();
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
		application.getListPhase().stream().forEach(x -> {
			Collections.sort(x.getListFrame(), Comparator.comparing(ApprovalFrameDto :: getDispOrder));
		});
		return application;
	}
	
}
