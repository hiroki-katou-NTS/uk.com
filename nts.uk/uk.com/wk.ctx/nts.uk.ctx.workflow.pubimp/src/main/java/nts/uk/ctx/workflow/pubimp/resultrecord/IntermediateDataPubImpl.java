package nts.uk.ctx.workflow.pubimp.resultrecord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.resultrecord.RecordRootType;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRootStateStatus;
import nts.uk.ctx.workflow.dom.service.resultrecord.AppRootInstanceService;
import nts.uk.ctx.workflow.pub.resultrecord.IntermediateDataPub;
import nts.uk.ctx.workflow.pub.spr.export.AppRootStateStatusSprExport;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class IntermediateDataPubImpl implements IntermediateDataPub {
	
	@Inject
	private AppRootInstanceService appRootInstanceService;

	@Override
	public List<AppRootStateStatusSprExport> getAppRootStatusByEmpsPeriod(String employeeID, DatePeriod period,
			Integer rootType) {
		List<String> employeeIDLst = Arrays.asList(employeeID);
		return appRootInstanceService.getAppRootStatusByEmpsPeriod(employeeIDLst, period, EnumAdaptor.valueOf(rootType, RecordRootType.class))
				.stream().map(x -> convertStatusFromDomain(x)).collect(Collectors.toList());
	}

	@Override
	public List<AppRootStateStatusSprExport> getAppRootStatusByEmpsPeriod(List<String> employeeIDLst,
			List<GeneralDate> dateLst, Integer rootType) {
		List<AppRootStateStatusSprExport> result = new ArrayList<>();
		dateLst.forEach(date -> {
			DatePeriod period = new DatePeriod(date, date);
			result.addAll(appRootInstanceService.getAppRootStatusByEmpsPeriod(employeeIDLst, period, EnumAdaptor.valueOf(rootType, RecordRootType.class))
			.stream().map(x -> convertStatusFromDomain(x)).collect(Collectors.toList()));
		});
		return result;
	}

	@Override
	public List<AppRootStateStatusSprExport> getAppRootStatusByEmpsPeriod(List<String> employeeIDLst, DatePeriod period,
			Integer rootType) {
		return appRootInstanceService.getAppRootStatusByEmpsPeriod(employeeIDLst, period, EnumAdaptor.valueOf(rootType, RecordRootType.class))
			.stream().map(x -> convertStatusFromDomain(x)).collect(Collectors.toList());
	}
	
	private AppRootStateStatusSprExport convertStatusFromDomain(ApprovalRootStateStatus approvalRootStateStatus){
		return new AppRootStateStatusSprExport(
				approvalRootStateStatus.getDate(), 
				approvalRootStateStatus.getEmployeeID(), 
				approvalRootStateStatus.getDailyConfirmAtr().value);
	}

}
