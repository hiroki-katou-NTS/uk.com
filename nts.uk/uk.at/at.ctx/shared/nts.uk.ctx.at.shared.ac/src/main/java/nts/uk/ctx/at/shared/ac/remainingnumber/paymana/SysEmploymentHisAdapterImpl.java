package nts.uk.ctx.at.shared.ac.remainingnumber.paymana;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SEmpHistoryImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SysEmploymentHisAdapter;
import nts.uk.ctx.bs.employee.pub.employment.SEmpHistExport;
import nts.uk.ctx.bs.employee.pub.employment.SyEmploymentPub;

@Stateless
public class SysEmploymentHisAdapterImpl implements SysEmploymentHisAdapter{

	@Inject
	private SyEmploymentPub syEmploymentPub;
	
	@Override
	public Optional<SEmpHistoryImport> findSEmpHistBySid(String companyId, String employeeId, GeneralDate baseDate) {
		val cacheCarrier = new CacheCarrier();
		return findSEmpHistBySidRequire(cacheCarrier, companyId, employeeId, baseDate);
	}
	@Override
	public Optional<SEmpHistoryImport> findSEmpHistBySidRequire(CacheCarrier cacheCarrier, String companyId, String employeeId, GeneralDate baseDate) {
		val sEmp = syEmploymentPub.findSEmpHistBySidRequire(cacheCarrier, companyId, employeeId, baseDate);
		return convertToSEmpHistExport(sEmp);
	}
	
	private Optional<SEmpHistoryImport> convertToSEmpHistExport(Optional<SEmpHistExport> ex){
		if (ex.isPresent()){
			SEmpHistExport exItem = ex.get();
			return Optional.of(new SEmpHistoryImport(exItem.getEmployeeId(), exItem.getEmploymentCode(), exItem.getEmploymentName(), exItem.getPeriod()));
		}
		return Optional.empty();
	}

}
