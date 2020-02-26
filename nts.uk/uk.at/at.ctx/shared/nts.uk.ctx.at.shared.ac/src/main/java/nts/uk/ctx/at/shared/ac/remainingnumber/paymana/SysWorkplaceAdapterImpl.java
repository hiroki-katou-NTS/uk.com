package nts.uk.ctx.at.shared.ac.remainingnumber.paymana;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SWkpHistImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SysWorkplaceAdapter;
import nts.uk.ctx.bs.employee.pub.workplace.SWkpHistExport;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;

@Stateless
public class SysWorkplaceAdapterImpl implements SysWorkplaceAdapter{

//	@Inject
//	private SyWorkplacePub syWorkplacePub;
	
	@Inject
	private WorkplacePub workplacePub;
	
	@Override
	public Optional<SWkpHistImport> findBySid(String employeeId, GeneralDate baseDate) {
		Optional<SWkpHistExport> sWkpHist = workplacePub.findBySid(employeeId, baseDate);
		if (sWkpHist.isPresent()){
			SWkpHistExport sWkpHistData = sWkpHist.get();
			return Optional.of(SWkpHistImport.builder().dateRange(sWkpHistData.getDateRange())
					.employeeId(sWkpHistData.getEmployeeId()).workplaceId(sWkpHistData.getWorkplaceId())
					.workplaceCode(sWkpHistData.getWorkplaceCode()).workplaceName(sWkpHistData.getWorkplaceName())
					.wkpDisplayName(sWkpHistData.getWkpDisplayName()).build());
		}
		return Optional.empty();
	}

}
