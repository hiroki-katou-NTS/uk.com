package nts.uk.ctx.at.record.ac.workplace;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workplace.EmployeeInfoImported;
import nts.uk.ctx.at.record.dom.adapter.workplace.SWkpHistRcImported;
import nts.uk.ctx.at.record.dom.adapter.workplace.SyWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.adapter.workplace.WorkplaceInformationImport;
import nts.uk.ctx.bs.employee.pub.employee.workplace.export.WorkplaceExportPub;
import nts.uk.ctx.bs.employee.pub.workplace.AffWorkplaceHistoryItemExport3;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;
import nts.uk.ctx.sys.auth.pub.workplace.WorkplaceListPub;

/**
 *
 * @author sonnh1
 *
 */
@Stateless
public class SyWorkplaceAdapterImp implements SyWorkplaceAdapter {

//	@Inject
//	private SyWorkplacePub syWorkplacePub;

	@Inject
	private WorkplacePub workplacePub;
	
	@Inject
	private WorkplaceListPub workplaceListPub;
	
	@Inject
	private WorkplaceExportPub workplaceExportPub;

	@Override
	public Map<String, Pair<String, String>> getWorkplaceMapCodeBaseDateName(String companyId,
			List<String> wpkCodes, List<GeneralDate> baseDates) {
		return this.workplacePub.getWorkplaceMapCodeBaseDateName(companyId, wpkCodes, baseDates);
	}

	@Override
	public Optional<SWkpHistRcImported> findBySid(String employeeId, GeneralDate baseDate) {
		return this.workplacePub.findBySid(employeeId, baseDate)
				.map(x -> new SWkpHistRcImported(x.getDateRange(), x.getEmployeeId(), x.getWorkplaceId(),
						x.getWorkplaceCode(), x.getWorkplaceName(), x.getWkpDisplayName()));
	}

	//社員（List）と基準日から所属職場履歴項目を取得する
	@Override
	public List<SWkpHistRcImported> findBySid(List<String> employeeIds, GeneralDate baseDate) {
		return workplacePub.findBySId(employeeIds, baseDate).stream()
				.map(x -> new SWkpHistRcImported(x.getDateRange(), x.getEmployeeId(), x.getWorkplaceId(),
						x.getWorkplaceCode(), x.getWorkplaceName(), x.getWkpDisplayName()))
				.collect(Collectors.toList());
	}

	@Override
	public List<EmployeeInfoImported> getLstEmpByWorkplaceIdsAndPeriod(List<String> workplaceIds, DatePeriod period) {
		return workplacePub.getLstEmpByWorkplaceIdsAndPeriod(workplaceIds, period).stream()
				.map(x -> new EmployeeInfoImported(x.getSid(), x.getEmployeeCode(), x.getEmployeeName()))
				.collect(Collectors.toList());
	}

	@Override
	public List<WorkplaceInformationImport> getByCidAndPeriod(String companyId, DatePeriod datePeriod) {
		return workplacePub.getByCidAndPeriod(companyId, datePeriod).stream().map(x ->
				new WorkplaceInformationImport(
						x.getCompanyId(),
						x.isDeleteFlag(),
						x.getWorkplaceHistoryId(),
						x.getWorkplaceId(),
						x.getWorkplaceCode(),
						x.getWorkplaceName(),
						x.getWorkplaceGeneric(),
						x.getWorkplaceDisplayName(),
						x.getHierarchyCode(),
						x.getWorkplaceExternalCode()))
				.collect(Collectors.toList());
	}

	@Override
	public String getAffWkpHistItemByEmpDate(String employeeID, GeneralDate date) {
		// TODO Auto-generated method stub
		return this.workplacePub.getAffWkpHistItemByEmpDate(employeeID, date).getWorkplaceId();
	}

	@Override
	public Map<String, String> getWorkPlace(String userID, String employeeID, GeneralDate date) {
		// TODO Auto-generated method stub
		return this.workplaceListPub.getWorkPlace(userID, employeeID, date);
	}

	@Override
	public Map<String, String> getByCID(String companyId, GeneralDate baseDate) {
		// TODO Auto-generated method stub
		return this.workplaceExportPub.getByCID(companyId, baseDate).stream().collect(Collectors.toMap(AffWorkplaceHistoryItemExport3::getEmployeeId, AffWorkplaceHistoryItemExport3::getWorkplaceId));
	}

}
