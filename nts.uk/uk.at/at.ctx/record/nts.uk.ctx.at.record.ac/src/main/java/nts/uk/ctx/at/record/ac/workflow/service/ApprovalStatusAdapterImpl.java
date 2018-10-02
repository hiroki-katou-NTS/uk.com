/**
 * 5:07:22 PM Mar 9, 2018
 */
package nts.uk.ctx.at.record.ac.workflow.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.ApprovalStatusAdapter;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApprovalRootOfEmployeeImport;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApprovalRootSituation;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApprovalRootStateStatusImport;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApprovalStatus;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApproveRootStatusForEmpImport;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.EmpPerformMonthParamImport;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.enums.ApprovalActionByEmpl;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.enums.ApprovalStatusForEmployee;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.enums.ApproverEmployeeState;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.enums.ReleasedProprietyDivision;
import nts.uk.ctx.workflow.pub.resultrecord.EmpPerformMonthParam;
import nts.uk.ctx.workflow.pub.resultrecord.EmployeePerformParam;
import nts.uk.ctx.workflow.pub.resultrecord.IntermediateDataPub;
import nts.uk.ctx.workflow.pub.resultrecord.export.AppEmpStatusExport;
import nts.uk.ctx.workflow.pub.service.ApprovalRootStatePub;
import nts.uk.ctx.workflow.pub.spr.SprAppRootStatePub;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * @author hungnm
 *
 */
@Stateless
public class ApprovalStatusAdapterImpl implements ApprovalStatusAdapter {

	@Inject
	private ApprovalRootStatePub approvalRootStatePub;
	
	@Inject
	private IntermediateDataPub intermediateDataPub;
	@Inject
	private SprAppRootStatePub sprPub;
	@Override
	public List<ApproveRootStatusForEmpImport> getApprovalByEmplAndDate(GeneralDate startDate, GeneralDate endDate,
			String employeeID, String companyID, Integer rootType) {
		return intermediateDataPub.getAppRootStatusByEmpPeriod(employeeID, new DatePeriod(startDate, endDate), rootType)
				.stream()
				.map((pub) -> new ApproveRootStatusForEmpImport(pub.getEmployeeID(), pub.getDate(),
						EnumAdaptor.valueOf(pub.getDailyConfirmAtr(), ApprovalStatusForEmployee.class)))
				.collect(Collectors.toList());
	}

	@Override
	public ApprovalRootOfEmployeeImport getApprovalRootOfEmloyee(GeneralDate startDate, GeneralDate endDate,
			String approverID, String companyID, Integer rootType) {
		//ApprovalRootOfEmployeeExport 
		AppEmpStatusExport export = intermediateDataPub.getApprovalEmpStatus(
				approverID, new DatePeriod(startDate, endDate), rootType);
		return convertFromExport(export);
	} 
	
	private ApprovalRootOfEmployeeImport convertFromExport(AppEmpStatusExport export) {
		//ApprovalRootOfEmployeeExport
		if(export.getRouteSituationLst() != null && export.getEmployeeID() != null){
		return new ApprovalRootOfEmployeeImport(export.getEmployeeID(),
				export.getRouteSituationLst().stream().map(situation -> {
					return new ApprovalRootSituation("",
							EnumAdaptor.valueOf(situation.getApproverEmpState(), ApproverEmployeeState.class),
							situation.getDate(),
							situation.getEmployeeID(),
							new ApprovalStatus(
									EnumAdaptor.valueOf(situation.getApprovalStatus().map(x -> x.getApprovalAction()).orElse(null),
											ApprovalActionByEmpl.class),
									EnumAdaptor.valueOf(situation.getApprovalStatus().map(x -> x.getReleaseAtr()).orElse(null),
											ReleasedProprietyDivision.class)));
				}).collect(Collectors.toList()));
		} else {
			return null;
		}
	}

	@Override
	public List<ApproveRootStatusForEmpImport> getApprovalByListEmplAndListApprovalRecordDate(
			GeneralDate startDate, GeneralDate endDate,
			List<String> employeeIDs, String companyID, Integer rootType) {
		return intermediateDataPub
				.getAppRootStatusByEmpsPeriod(employeeIDs, new DatePeriod(startDate, endDate), rootType).stream()
				.map((pub) -> new ApproveRootStatusForEmpImport(pub.getEmployeeID(), pub.getDate(),
						EnumAdaptor.valueOf(pub.getDailyConfirmAtr(), ApprovalStatusForEmployee.class)))
				.collect(Collectors.toList());
	}

	@Override
	public boolean releaseApproval(String approverID, List<Pair<String, GeneralDate>> empAndDates,
			Integer rootType, String companyID) {
		val approvalRecordDates = empAndDates.stream().map(x -> new EmployeePerformParam(x.getRight(), x.getLeft())).collect(Collectors.toList());
		return intermediateDataPub.cancel(approverID, approvalRecordDates, rootType);
	}

	@Override
	public void registerApproval(String approverID, List<Pair<String, GeneralDate>> empAndDates,
			Integer rootType, String companyID) {
		val approvalRecordDates = empAndDates.stream().map(x -> new EmployeePerformParam(x.getRight(), x.getLeft())).collect(Collectors.toList());
		intermediateDataPub.approve(approverID, approvalRecordDates, rootType);
	}

	@Override
	public List<ApproveRootStatusForEmpImport> getApprovalByListEmplAndListApprovalRecordDate(
			List<GeneralDate> approvalRecordDates, List<String> employeeID, Integer rootType) {
		return intermediateDataPub.getAppRootStatusByEmpsDates(employeeID, approvalRecordDates, rootType).stream()
				.map((pub) -> new ApproveRootStatusForEmpImport(pub.getEmployeeID(), pub.getDate(),
						EnumAdaptor.valueOf(pub.getDailyConfirmAtr(), ApprovalStatusForEmployee.class)))
				.collect(Collectors.toList());
	}

	@Override
	public void cleanApprovalRootState(String rootStateID, GeneralDate date, Integer rootType) {
		//intermediateDataPub
		intermediateDataPub.cleanApprovalRootState(rootStateID, date, rootType);
	}

	@Override
	public List<ApprovalRootStateStatusImport> getStatusByEmpAndDate(String employeeID, DatePeriod datePeriod,
			Integer rootType) {
		List<ApprovalRootStateStatusImport> lstOutput = sprPub.getStatusByEmpAndDate(employeeID, datePeriod.start(), datePeriod.end(), rootType)
				.stream().map(x -> new ApprovalRootStateStatusImport(x.getDate(), x.getEmployeeID(), x.getDailyConfirmAtr()))
				.collect(Collectors.toList());
		return lstOutput;
	}
	
	@Override
	public ApprovalRootOfEmployeeImport getApprovalRootOfEmloyeeNew(GeneralDate startDate, GeneralDate endDate,
			String approverID, String companyID, Integer rootType) {
		//ApprovalRootOfEmployeeExport 
		AppEmpStatusExport export = intermediateDataPub.getApprovalEmpStatus(
				approverID, new DatePeriod(startDate, endDate), rootType);
		return convertFromExportNew(export);
	}
	
	private ApprovalRootOfEmployeeImport convertFromExportNew(AppEmpStatusExport export) {
		//ApprovalRootOfEmployeeExport
		if(export.getRouteSituationLst() != null && export.getEmployeeID() != null){
		return new ApprovalRootOfEmployeeImport(export.getEmployeeID(),
				export.getRouteSituationLst().stream().map(situation -> {
					return new ApprovalRootSituation("",
							EnumAdaptor.valueOf(situation.getApproverEmpState(), ApproverEmployeeState.class),
							situation.getDate(),
							situation.getEmployeeID(),
							new ApprovalStatus(
									EnumAdaptor.valueOf(situation.getApprovalStatus().map(x -> x.getApprovalAction()).orElse(null),
											ApprovalActionByEmpl.class),
									EnumAdaptor.valueOf(situation.getApprovalStatus().map(x -> x.getReleaseAtr()).orElse(null),
											ReleasedProprietyDivision.class)));
				}).collect(Collectors.toList()));
		} else {
			return null;
		}
	}
	
	@Override
	public List<ApproveRootStatusForEmpImport> getApprovalByListEmplAndListApprovalRecordDateNew(
			List<GeneralDate> approvalRecordDates, List<String> employeeID, Integer rootType) {
		return intermediateDataPub.getAppRootStatusByEmpsDates(employeeID, approvalRecordDates, rootType).stream()
				.map((pub) -> new ApproveRootStatusForEmpImport(pub.getEmployeeID(), pub.getDate(),
						EnumAdaptor.valueOf(pub.getDailyConfirmAtr(), ApprovalStatusForEmployee.class)))
				.collect(Collectors.toList());
	}

	// RequestList 533
	@Override
	public List<ApproveRootStatusForEmpImport> getAppRootStatusByEmpsMonth(
			List<EmpPerformMonthParamImport> empPerformMonthParamLst) {
		List<EmpPerformMonthParam> listParam = empPerformMonthParamLst.stream()
				.map(i -> new EmpPerformMonthParam(i.getYearMonth(), i.getClosureID(), i.getClosureDate(),
						i.getBaseDate(), i.getEmployeeID()))
				.collect(Collectors.toList());
		val exportResult = intermediateDataPub.getAppRootStatusByEmpsMonth(listParam);
		return exportResult.stream()
				.map((pub) -> new ApproveRootStatusForEmpImport(pub.getEmployeeID(), pub.getDate(),
						EnumAdaptor.valueOf(pub.getDailyConfirmAtr(), ApprovalStatusForEmployee.class)))
				.collect(Collectors.toList());
	}

	// RequestList 534
	@Override
	public ApprovalRootOfEmployeeImport getApprovalEmpStatusMonth(String approverID, YearMonth yearMonth,
			Integer closureID, ClosureDate closureDate, GeneralDate baseDate) {
		val exportResult = intermediateDataPub.getApprovalEmpStatusMonth(approverID, yearMonth, closureID, closureDate, baseDate);
		return convertFromExportNew(exportResult);
	}

	@Override
	public void approveMonth(String approverID, List<EmpPerformMonthParamImport> empPerformMonthParamLst) {
		List<EmpPerformMonthParam> listParam = empPerformMonthParamLst.stream()
				.map(i -> new EmpPerformMonthParam(i.getYearMonth(), i.getClosureID(), i.getClosureDate(),
						i.getBaseDate(), i.getEmployeeID()))
				.collect(Collectors.toList());
		intermediateDataPub.approveMonth(approverID, listParam);		
	}

	@Override
	public boolean cancelMonth(String approverID, List<EmpPerformMonthParamImport> empPerformMonthParamLst) {
		List<EmpPerformMonthParam> listParam = empPerformMonthParamLst.stream()
				.map(i -> new EmpPerformMonthParam(i.getYearMonth(), i.getClosureID(), i.getClosureDate(),
						i.getBaseDate(), i.getEmployeeID()))
				.collect(Collectors.toList());
		return intermediateDataPub.cancelMonth(approverID, listParam);
	}
	
}
