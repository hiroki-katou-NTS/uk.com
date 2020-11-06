package nts.uk.ctx.at.request.ac.workplace;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.EmployeeBasicInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.EmploymentHistoryImported;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WkpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WkpInfo;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkPlaceHistBySIDImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.WorkplaceInforExport;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.ctx.bs.employee.pub.employment.SyEmploymentPub;
import nts.uk.ctx.bs.employee.pub.workplace.SWkpHistExport;
import nts.uk.ctx.bs.employee.pub.workplace.WkpByEmpExport;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;

/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class ReqWorkplaceAdapterImpl implements WorkplaceAdapter {

	/** The emp pub. */
	@Inject
	private SyEmploymentPub empPub;

	@Inject
	private SyEmployeePub syEmpPub;
	
	@Inject
	private WorkplacePub wkpPubNew;
	
	/**
	 * アルゴリズム「社員から職場を取得する」を実行する
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public WkpHistImport findWkpBySid(String sID, GeneralDate date) {
		Optional<SWkpHistExport> wkpExport = wkpPubNew.findBySid(sID, date);

		if (wkpExport.isPresent()) {
			return toImport(wkpExport.get());
		}
		return null;
	}
	
	@Override
	public List<WkpHistImport> findWkpBySid(List<String> sID, GeneralDate date) {
		List<SWkpHistExport> wkpExport = wkpPubNew.findBySId(sID);
		return wkpExport.stream().filter(w -> w.getDateRange().contains(date)).map(w -> toImport(w)).collect(Collectors.toList());
	}

	@Override
	public List<WkpHistImport> findWkpBySidAndBaseDate(List<String> sID, GeneralDate date) {
		return wkpPubNew.findBySId(sID, date).stream().map(this::toImport).collect(Collectors.toList());
	}

	private WkpHistImport toImport(SWkpHistExport export) {
		return new WkpHistImport(export.getDateRange(), export.getEmployeeId(), export.getWorkplaceId(),
				export.getWorkplaceCode(), export.getWorkplaceName(), export.getWkpDisplayName());
	}


	@Override
	public Optional<EmploymentHistoryImported> getEmpHistBySid(String companyId, String employeeId,
			GeneralDate baseDate) {
		return this.empPub.findSEmpHistBySid(companyId, employeeId, baseDate)
				.map(f -> new EmploymentHistoryImported(f.getEmployeeId(), f.getEmploymentCode(), f.getPeriod()));
	}

	@Override
	public List<EmployeeBasicInfoImport> findBySIds(List<String> sIds) {

		return this.syEmpPub.findBySIds(sIds).stream()
				.map(x -> new EmployeeBasicInfoImport(x.getPId(), x.getEmployeeId(), Objects.isNull(x.getPName()) ? "" : x.getPName(), x.getGender(),
						x.getBirthDay(), Objects.isNull(x.getPMailAddr()) ? "" : x.getPMailAddr().v() , x.getEmployeeCode(),
						x.getEntryDate(), x.getRetiredDate(), Objects.isNull(x.getCompanyMailAddr()) ? "" : x.getCompanyMailAddr().v()))
				.collect(Collectors.toList());
	}

	@Override
	public WorkPlaceHistBySIDImport findWpkBySIDandPeriod(String sID, DatePeriod datePeriod) {
		WkpByEmpExport wkp = wkpPubNew.getLstHistByEmpAndPeriod(sID, datePeriod.start(), datePeriod.end());
		List<WkpInfo> lstWkpInfo = wkp.getLstWkpInfo().stream()
				.map(c-> new WkpInfo(c.getDatePeriod(), c.getWpkID(), c.getWpkCD(), c.getWpkName()))
				.collect(Collectors.toList());
		return new WorkPlaceHistBySIDImport(wkp.getEmployeeID(), lstWkpInfo);
	}

	/**
	 * 上位階層の職場の設定を取得する
	 * RequestList #83
	 */
	@Override
	public List<String> findListWpkIDParent(String companyId, String workplaceId, GeneralDate date) {
		return wkpPubNew.getWorkplaceIdAndChildren(companyId, date, workplaceId);
	}

	@Override
	public List<String> findListWpkIDParentDesc(String companyId, String workplaceId, GeneralDate date) {
		return wkpPubNew.getWorkplaceIdAndChildren(companyId, date, workplaceId);
	}

	@Override
	public List<String> getUpperWorkplaceRQ569(String companyId, String workplaceId, GeneralDate date) {
		return wkpPubNew.getUpperWorkplace(companyId, workplaceId, date);
	}

	@Override
	public List<WorkplaceInforExport> getWorkplaceInforByWkpIds(String companyId, List<String> listWorkplaceId,
			GeneralDate baseDate) {
		return wkpPubNew.getWorkplaceInforByWkpIds(companyId, listWorkplaceId, baseDate)
				.stream().map(x -> new WorkplaceInforExport(
						x.getWorkplaceId(), 
						x.getHierarchyCode(), 
						x.getWorkplaceCode(), 
						x.getWorkplaceName(), 
						x.getWorkplaceDisplayName(), 
						x.getWorkplaceGenericName(), 
						x.getWorkplaceExternalCode()))
				.collect(Collectors.toList());
	}
}
