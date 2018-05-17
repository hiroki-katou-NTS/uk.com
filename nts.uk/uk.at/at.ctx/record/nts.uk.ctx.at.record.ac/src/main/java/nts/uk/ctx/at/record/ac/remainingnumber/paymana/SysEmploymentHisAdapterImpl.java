package nts.uk.ctx.at.record.ac.remainingnumber.paymana;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SEmpHistoryExport;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SysEmploymentHisAdapter;
import nts.uk.ctx.bs.employee.pub.employment.SEmpHistExport;
import nts.uk.ctx.bs.employee.pub.employment.SyEmploymentPub;

@Stateless
public class SysEmploymentHisAdapterImpl implements SysEmploymentHisAdapter{

	@Inject
	private SyEmploymentPub syEmploymentPub;
	
	@Override
	public Optional<SEmpHistoryExport> findSEmpHistBySid(String companyId, String employeeId, GeneralDate baseDate) {
		val sEmp = syEmploymentPub.findSEmpHistBySid(companyId, employeeId, baseDate);
		return convertToSEmpHistExport(sEmp);
	}
	
	private Optional<SEmpHistoryExport> convertToSEmpHistExport(Optional<SEmpHistExport> ex){
		if (ex.isPresent()){
			SEmpHistExport exItem = ex.get();
			return Optional.of(new SEmpHistoryExport(exItem.getEmployeeId(), exItem.getEmploymentCode(), exItem.getEmploymentName(), exItem.getPeriod()));
		}
		return Optional.empty();
	}

}
