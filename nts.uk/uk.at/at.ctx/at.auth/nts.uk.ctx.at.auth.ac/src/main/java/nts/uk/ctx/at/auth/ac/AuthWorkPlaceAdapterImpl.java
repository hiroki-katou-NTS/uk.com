package nts.uk.ctx.at.auth.ac;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.auth.dom.adapter.workplace.AffWorkplaceHistoryItemImport;
import nts.uk.ctx.at.auth.dom.adapter.workplace.AuthWorkPlaceAdapter;
import nts.uk.ctx.at.auth.dom.adapter.workplace.WorkplaceInfoImport;
import nts.uk.ctx.at.auth.dom.adapter.workplace.WorkplaceInforImport2;
import nts.uk.ctx.at.auth.dom.adapter.workplace.WorkplaceManagerImport;
import nts.uk.ctx.bs.employee.pub.employee.workplace.export.WorkplaceExportPub;
import nts.uk.ctx.bs.employee.pub.workplace.AffWorkplaceHistoryItemExport;
import nts.uk.ctx.bs.employee.pub.workplace.AffWorkplaceHistoryItemExport2;
import nts.uk.ctx.bs.employee.pub.workplace.AffWorkplaceHistoryItemExport3;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;
import nts.uk.ctx.sys.auth.pub.employee.EmployeePublisher;
import nts.uk.ctx.sys.auth.pub.workplace.WorkplaceInfoExport;
import nts.uk.ctx.sys.auth.pub.workplace.WorkplaceListPub;
import nts.uk.ctx.sys.auth.pub.workplace.WorkplaceManagerExport;


@Stateless
public class AuthWorkPlaceAdapterImpl implements AuthWorkPlaceAdapter{
	
	@Inject
	private EmployeePublisher employeePublisher;

	@Inject
	private WorkplaceListPub  workplaceListPub;
	
	@Inject
	private WorkplacePub  workplacePub;
	
	@Inject
	private WorkplaceExportPub workplaceExportPub;
	
	@Override
	public List<String> getListWorkPlaceID(String employeeID, GeneralDate referenceDate) {
		List<String> listWorkPlaceID = employeePublisher.getListWorkPlaceID(employeeID, referenceDate);
		return listWorkPlaceID;
	}

	@Override
	public WorkplaceInfoImport getWorkplaceListId(GeneralDate referenceDate, String employeeID, boolean referEmployee) {
		
		WorkplaceInfoExport workplaceInfoExport = workplaceListPub.getWorkplaceListId(referenceDate, employeeID, referEmployee);
		WorkplaceInfoImport workplaceInfoImport = new WorkplaceInfoImport (workplaceInfoExport.lstWorkPlaceID, workplaceInfoExport.getEmployeeRange());
		return workplaceInfoImport;
	}

	@Override
	public List<AffWorkplaceHistoryItemImport> getWorkHisItemfromWkpIdAndBaseDate(String workPlaceId, GeneralDate baseDate) {

		List<AffWorkplaceHistoryItemExport2> export = workplacePub.getWorkHisItemfromWkpIdAndBaseDate(workPlaceId, baseDate);
		if (export.isEmpty()) {
			return new ArrayList<AffWorkplaceHistoryItemImport>();
		}
		
		List<AffWorkplaceHistoryItemImport> result = export.stream().map(item -> {
			return new AffWorkplaceHistoryItemImport(item.getHistoryId(), item.getEmployeeId(), item.getWorkplaceId(),
					item.getNormalWorkplaceId());
		}).collect(Collectors.toList());

		return result;
	}

	@Override
	public List<String> getListWorkPlaceIDNoWkpAdmin(String employeeID, int empRange, GeneralDate referenceDate) {
		List<String> result = workplaceListPub.getListWorkPlaceIDNoWkpAdmin(employeeID, empRange, referenceDate);
		return result;
	}

	@Override
	public List<WorkplaceManagerImport> findListWkpManagerByEmpIdAndBaseDate(String employeeId, GeneralDate baseDate) {
		
		List<WorkplaceManagerExport> dataExport = workplaceListPub.findListWkpManagerByEmpIdAndBaseDate(employeeId, baseDate);
		if (dataExport.isEmpty()) {
			return new ArrayList<>();
		}
		
		List<WorkplaceManagerImport> result = dataExport.stream().map(i -> {
			WorkplaceManagerImport export = new WorkplaceManagerImport(i.getWorkplaceManagerId(), i.getEmployeeId(), i.getWorkplaceId(), i.getHistoryPeriod());
			return export;
		}).collect(Collectors.toList());
		
		return result;
	}

	@Override
	public AffWorkplaceHistoryItemImport getAffWkpHistItemByEmpDate(String employeeID, GeneralDate date) {
		
		AffWorkplaceHistoryItemExport d = this.workplacePub.getAffWkpHistItemByEmpDate(employeeID, date);
		
		return new AffWorkplaceHistoryItemImport(d.getHistoryId(), employeeID, d.getWorkplaceId(), d.getNormalWorkplaceId());
	}

	@Override
	public List<AffWorkplaceHistoryItemImport> getWorkHisItemfromWkpIdsAndBaseDate(List<String> workPlaceIds,
			GeneralDate baseDate) {
		List<AffWorkplaceHistoryItemExport3> export = workplacePub.getWorkHisItemfromWkpIdsAndBaseDate(workPlaceIds, baseDate);
		if (export.isEmpty()) {
			return new ArrayList<AffWorkplaceHistoryItemImport>();
		}
		
		List<AffWorkplaceHistoryItemImport> result = export.stream().map(item -> {
			return new AffWorkplaceHistoryItemImport(item.getHistoryId(), item.getEmployeeId(), item.getWorkplaceId(),
					item.getNormalWorkplaceId());
		}).collect(Collectors.toList());

		return result;
	}

	@Override
	public List<WorkplaceInforImport2> getAllActiveWorkplaceInfor(String companyId, GeneralDate baseDate) {
		// TODO Auto-generated method stub
		return workplacePub.getAllActiveWorkplaceInfor(companyId, baseDate)
				.stream().map(c -> 
					new WorkplaceInforImport2(
							c.getWorkplaceId(), 
							c.getHierarchyCode(), 
							c.getWorkplaceCode(), 
							c.getWorkplaceName(), 
							c.getWorkplaceDisplayName(), 
							c.getWorkplaceGenericName(), 
							c.getWorkplaceExternalCode())
			).collect(Collectors.toList());
	}

	@Override
	public Map<String, String> getReferenceableEmployees(String userID, String employeeID, GeneralDate date) {
		// return 参照可能社員の所属職場を取得するPublish.取得する(ユーザID,社員ID,基準日)
		return workplaceListPub.getWorkPlace(userID, employeeID, date);
	}

	@Override
	public Map<String, String> getemployeesAllWorkplaces(String companyId, GeneralDate baseDate) {
		// 	$所属職場 = 全ての職場の所属社員を取得するPublish.取得する(会社ID,基準日)
		List<AffWorkplaceHistoryItemExport3> list = workplaceExportPub.getByCID(companyId, baseDate);
		
		Map<String, String> result = new HashMap<String, String>();
		for (AffWorkplaceHistoryItemExport3 i : list) {
			result.put(i.getEmployeeId(), i.getWorkplaceId());
		}
		//return $所属職場： map <$.社員ID,$.職場ID>
		return result;
	}

}
