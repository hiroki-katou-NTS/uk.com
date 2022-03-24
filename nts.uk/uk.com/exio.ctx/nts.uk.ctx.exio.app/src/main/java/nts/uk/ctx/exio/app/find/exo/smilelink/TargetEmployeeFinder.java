package nts.uk.ctx.exio.app.find.exo.smilelink;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplaceInforExport;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;
import nts.uk.shr.com.context.AppContexts;


@Stateless
public class TargetEmployeeFinder {
	
    @Inject
    private EmployeeDataMngInfoRepository empMngDataRepo;
    
	@Inject
	private SyEmployeePub sysEmployee;
	
	@Inject
	private WorkplacePub workplacePub;
	
	public List<String> getTargetEmployee(List<String> employmentCd, GeneralDate startDate, GeneralDate endDate){
		String cid = AppContexts.user().companyId();
		List<WorkplaceInforExport> listWorkplaceExport = workplacePub.getAllActiveWorkplaceInfor(cid, GeneralDate.today()); 
		List<String> workplaceId = listWorkplaceExport.stream().map(x -> x.getWorkplaceId()).collect(Collectors.toList());
		
		// get employee ID
		List<String> employeeList = sysEmployee.getListEmpByWkpAndEmpt(workplaceId, employmentCd, new DatePeriod(startDate, endDate));
		
		// get employee code
        // ドメインモデル「社員データ管理情報」を全て取得する
        List<EmployeeDataMngInfo> empInfos = empMngDataRepo.findByListEmployeeId(employeeList);
        List<String> employeeCd = empInfos.stream().map(x -> x.getEmployeeCode().v()).collect(Collectors.toList());
		return employeeCd;
	}
	
	
}
