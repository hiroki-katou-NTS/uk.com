package nts.uk.ctx.at.schedule.ac.workplace;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.adapter.workplace.SWkpHistImported;
import nts.uk.ctx.at.schedule.dom.adapter.workplace.SyWorkplaceAdapter;
import nts.uk.ctx.bs.employee.pub.workplace.SyWorkplacePub;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class SyWorkplaceAdapterImpl implements SyWorkplaceAdapter {

	@Inject
	private SyWorkplacePub syWorkplacePub;

	@Override
	public Optional<SWkpHistImported> findBySid(String employeeId, GeneralDate baseDate) {
		return this.syWorkplacePub.findBySid(employeeId, baseDate)
				.map(x -> new SWkpHistImported(x.getDateRange(), x.getEmployeeId(), x.getWorkplaceId(),
						x.getWorkplaceCode(), x.getWorkplaceName(), x.getWkpDisplayName()));
	}

}
