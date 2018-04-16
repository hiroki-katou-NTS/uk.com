package nts.uk.ctx.at.request.dom.application.appabsence.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsence;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsenceRepository;

@Stateless
public class AbsenceServiceProcessImpl implements AbsenceServiceProcess{
	@Inject
	private AppAbsenceRepository appAbsenceRepository;
	@Inject
	private ApplicationApprovalService_New appRepository;
	
	@Override
	public SpecialLeaveInfor getSpecialLeaveInfor(String workTypeCode) {
		SpecialLeaveInfor specialLeaveInfor = new SpecialLeaveInfor();
		boolean relationFlg = false;
		boolean mournerDisplayFlg = false;
		boolean displayRelationReasonFlg = false;
		int maxDayRelate = 0;
		//指定した勤務種類に特別休暇に当てはまるかチェックする
		
		return specialLeaveInfor;
	}

	@Override
	public void CreateAbsence(AppAbsence domain, Application_New newApp) {
		// insert Application
		this.appRepository.insert(newApp);
		// insert Absence
		this.appAbsenceRepository.insertAbsence(domain);
		
	}
	
}
