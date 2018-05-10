package nts.uk.ctx.at.function.ac.dailyworkschedule;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.dailyworkschedule.scrA.SEmpHistExportAdapter;
import nts.uk.ctx.at.function.dom.dailyworkschedule.scrA.SEmpHistExportImported;
import nts.uk.ctx.bs.employee.pub.employment.SEmpHistExport;
import nts.uk.ctx.bs.employee.pub.employment.SyEmploymentPub;

@Stateless
public class SEmpHistExportFinder implements SEmpHistExportAdapter{

	@Inject
	private SyEmploymentPub syEmploymentPub; 
	
	@Override
	public Optional<SEmpHistExportImported> getSEmpHistExport(String companyId, String employeeId,
			GeneralDate baseDate) {
		Optional<SEmpHistExport> optSEmpHistExport = syEmploymentPub.findSEmpHistBySid(companyId, employeeId, baseDate);
		if (optSEmpHistExport.isPresent()) {
			SEmpHistExport empHistExport = optSEmpHistExport.get(); 
			return Optional.of(new SEmpHistExportImported(empHistExport.getEmployeeId(), empHistExport.getPeriod(), 
												empHistExport.getEmploymentCode(), empHistExport.getEmploymentName()));
		}
		return Optional.empty();
	}

}
