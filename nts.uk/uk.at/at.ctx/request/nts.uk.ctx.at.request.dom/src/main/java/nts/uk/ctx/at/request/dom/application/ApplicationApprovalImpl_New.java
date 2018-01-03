package nts.uk.ctx.at.request.dom.application;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeRepository;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampRepository;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class ApplicationApprovalImpl_New implements ApplicationApprovalService_New {

	@Inject
	private ApplicationRepository_New applicationRepository;
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Inject
	private AppStampRepository appStampRepository;
	
	@Inject
	private OvertimeRepository overtimeRepository;
	
	@Override
	public void insert(Application_New application) {
		applicationRepository.insert(application);
		approvalRootStateAdapter.insertByAppType(
				application.getCompanyID(), 
				application.getEmployeeID(), 
				application.getAppType().value, 
				application.getAppDate(),
				application.getAppID());
	}

	@Override
	public void delete(String companyID, String appID, Long version, ApplicationType appType) {
		switch (appType) {
		case STAMP_APPLICATION:
			appStampRepository.delete(companyID, appID);
			break;
		case OVER_TIME_APPLICATION:
			overtimeRepository.delete(companyID, appID);
			break;
		default:
			break;
		}
		applicationRepository.delete(companyID, appID);
		approvalRootStateAdapter.deleteApprovalRootState(appID);
		
		
	}

}
