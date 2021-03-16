package nts.uk.ctx.at.record.ac.approvalrootstate;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.approvalrootstate.AppRootStateConfirmAdapter;
import nts.uk.ctx.at.record.dom.adapter.approvalrootstate.AppRootStateConfirmImport;
import nts.uk.ctx.at.record.dom.adapter.approvalrootstate.AppRootStateStatusSprImport;
import nts.uk.ctx.at.record.dom.adapter.approvalrootstate.Request113Import;
import nts.uk.ctx.workflow.pub.resultrecord.IntermediateDataPub;
import nts.uk.ctx.workflow.pub.resultrecord.export.Request113Export;
import nts.uk.ctx.workflow.pub.service.ApprovalRootStatePub;
import nts.uk.ctx.workflow.pub.service.export.AppRootStateConfirmExport;

@Stateless
public class AppRootStateConfirmAdapterImpl implements AppRootStateConfirmAdapter {

	@Inject
	private ApprovalRootStatePub approvalRootStatePub;
	
	@Inject
	private IntermediateDataPub interpub;

	@Override
	public AppRootStateConfirmImport appRootStateConfirmFinder(String companyID, String employeeID, Integer confirmAtr,
			Integer appType, GeneralDate date) {
		AppRootStateConfirmExport appRootStateConfirmExport = this.approvalRootStatePub.getApprovalRootState(companyID,
				employeeID, confirmAtr, appType, date);
		return new AppRootStateConfirmImport(appRootStateConfirmExport.getIsError(),
				appRootStateConfirmExport.getRootStateID(), appRootStateConfirmExport.getErrorMsg());
	}

	@Override
	public void clearAppRootstate(String rootId) {
		approvalRootStatePub.cleanApprovalRootState(rootId, 1);
	}

	@Override
	public void deleteApprovalByEmployeeIdAndDate(String employeeID, GeneralDate date) {
		approvalRootStatePub.deleteConfirmDay(employeeID, date);
	}

	@Override
	public Request113Import getAppRootStatusByEmpPeriod(String employeeID, DatePeriod period, Integer rootType) {
		
		Request113Export requestExport = interpub.getAppRootStatusByEmpPeriod(employeeID, period, rootType);
		Request113Import requestImport = new Request113Import(requestExport.getAppRootStateStatusLst().stream()
																			.map(x -> new AppRootStateStatusSprImport(x.getDate(), x.getEmployeeID(), x.getDailyConfirmAtr()))
																			.collect(Collectors.toList()), 
															requestExport.isErrorFlg(),
															requestExport.getErrorMsgID(), requestExport.getEmpLst());
		
		return requestImport;
	}

}
