package nts.uk.ctx.hr.develop.app.sysoperationset.businessrecognition.find;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.hr.develop.app.sysoperationset.businessrecognition.dto.DepartmentInforDto;
import nts.uk.ctx.hr.develop.app.sysoperationset.businessrecognition.dto.EmployeeInforDto;
import nts.uk.ctx.hr.develop.app.sysoperationset.businessrecognition.dto.MenuApprovalSettingsInforDto;
import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.MenuApprovalSettingsRepository;
import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.algorithm.IDepartmentInforImport;
import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.algorithm.dto.DepartmentInforImport;
import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.algorithm.dto.RequestList643Import;
import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.algorithm.dto.ResultRequest600Import;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class MenuApprovalSettingsFinder {

	@Inject
	private MenuApprovalSettingsRepository repo;
	
	@Inject
	private IDepartmentInforImport IDepartmentInfor;
	
	public List<MenuApprovalSettingsInforDto> get() {
		String cid = AppContexts.user().companyId();
		return repo.getBusinessApprovalSettings(cid).stream().map(c -> new MenuApprovalSettingsInforDto(c)).collect(Collectors.toList());
	}
	
	public DepartmentInforDto JMM018YStart() {
		String sid = AppContexts.user().employeeId();
		String cid = AppContexts.user().companyId();
		GeneralDate baseDate = GeneralDate.today();
		
		/**call RQ 562 */
		List<DepartmentInforImport> departmentInfors = IDepartmentInfor.getAllActiveDepartment(cid, baseDate);
		
		/**call RQ 643 */
		List<RequestList643Import> affDeptHist = IDepartmentInfor.getAffDeptHistByEmpIdAndBaseDate(Arrays.asList(sid), baseDate);
		if(affDeptHist.isEmpty()) {
			return new DepartmentInforDto(departmentInfors, null);
		}else {
			return new DepartmentInforDto(departmentInfors, affDeptHist.get(0).getDepartmentId());
		}
		
	}
	
	public List<EmployeeInforDto> getEmployee(String departmentId, Boolean checkBox) {
		GeneralDate baseDate = GeneralDate.today();
		List<String> departmentIds = new ArrayList<>();
		departmentIds.add(departmentId);
		if(checkBox) {
			departmentIds.addAll(IDepartmentInfor.getDepartmentIdAndChildren(AppContexts.user().companyId(), GeneralDate.today(), departmentId));
		}
		
		/**call RQ 644 */
		List<RequestList643Import> affDepartmentHistoryItems = IDepartmentInfor.getAffDepartmentHistoryItems(departmentIds, baseDate);
		
		List<String> sid = affDepartmentHistoryItems.stream().map(c->c.getEmployeeId()).collect(Collectors.toList());
		
		/**call RQ 600 */
		List<ResultRequest600Import> result = IDepartmentInfor.getEmpInfoLstBySids(sid, new DatePeriod(baseDate, GeneralDate.max()), true, true);
		
		return result.stream().map(c-> new EmployeeInforDto(c.getSid(), c.getEmployeeCode(), c.getEmployeeName())).collect(Collectors.toList());
	}
	
	public List<EmployeeInforDto> searchEmployee(String key) {
		GeneralDate baseDate = GeneralDate.today();
		String cid = AppContexts.user().companyId();
		
		/**call RQ 614 for JMM018Z*/
		List<String> employeeIds = IDepartmentInfor.searchEmployeeByKey(cid, key);
		
		/**call RQ 600 */
		List<ResultRequest600Import> result = IDepartmentInfor.getEmpInfoLstBySids(employeeIds, new DatePeriod(baseDate, GeneralDate.max()), true, true);
		
		return result.stream().map(c-> new EmployeeInforDto(c.getSid(), c.getEmployeeCode(), c.getEmployeeName())).collect(Collectors.toList());
	}
}
