package nts.uk.ctx.at.record.ac.createdailyapprover;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.createdailyapprover.AppFrameInsFnImport;
import nts.uk.ctx.at.record.dom.adapter.createdailyapprover.AppPhaseInsFnImport;
import nts.uk.ctx.at.record.dom.adapter.createdailyapprover.AppRootInsContentFnImport;
import nts.uk.ctx.at.record.dom.adapter.createdailyapprover.AppRootInsFnImport;
import nts.uk.ctx.at.record.dom.adapter.createdailyapprover.CreateDailyApproverAdapter;
import nts.uk.ctx.workflow.pub.resultrecord.IntermediateDataPub;
import nts.uk.ctx.workflow.pub.resultrecord.export.AppFrameInsExport;
import nts.uk.ctx.workflow.pub.resultrecord.export.AppPhaseInsExport;
import nts.uk.ctx.workflow.pub.resultrecord.export.AppRootInsContentExport;
import nts.uk.ctx.workflow.pub.resultrecord.export.AppRootInsExport;

@Stateless
public class CreateDailyApproverAc implements CreateDailyApproverAdapter {

	@Inject
	private IntermediateDataPub intermediateDataPub;
	
	
	@Override
	public AppRootInsContentFnImport createDailyApprover(String employeeID, Integer rootType, GeneralDate recordDate) {
		AppRootInsContentExport data = intermediateDataPub.createDailyApprover(employeeID, rootType, recordDate);
		return convertToAppRootInsContentExport(data);
	}

	@Override
	public void createApprovalStatus(String employeeID, GeneralDate date, Integer rootType) {
		intermediateDataPub.createApprovalStatus(employeeID, date, rootType);
	}

	@Override
	public void deleteApprovalStatus(String employeeID, GeneralDate date, Integer rootType) {
		intermediateDataPub.deleteApprovalStatus(employeeID, date, rootType);
	}
	
	private AppRootInsContentFnImport convertToAppRootInsContentExport(AppRootInsContentExport export) {
		return new AppRootInsContentFnImport(
				convertToAppRootInsExport(export.getAppRootInstance()),
				export.getErrorFlag(),
				export.getErrorMsgID()
				);
	}
	
	private AppRootInsFnImport convertToAppRootInsExport(AppRootInsExport export) {
		return new AppRootInsFnImport(
				export.getRootID(),
				export.getCompanyID(),
				export.getEmployeeID(),
				export.getDatePeriod(),
				export.getRootType(),
				export.getListAppPhase().stream().map(c ->convertToAppPhaseInsExport(c)).collect(Collectors.toList())
				);
		
	}
	
	private AppPhaseInsFnImport convertToAppPhaseInsExport (AppPhaseInsExport export) {
		return new AppPhaseInsFnImport(
				export.getPhaseOrder(),
				export.getApprovalForm(),
				export.getListAppFrame().stream().map(c->convertToAppFrameInsExport(c)).collect(Collectors.toList())
				
				);
	}
	
	private AppFrameInsFnImport convertToAppFrameInsExport (AppFrameInsExport export) {
		return new AppFrameInsFnImport(
				export.getFrameOrder(),
				export.isConfirmAtr(),
				export.getListApprover()
				);
	}

}
