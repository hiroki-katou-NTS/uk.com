package nts.uk.ctx.at.schedule.ac.workplace;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.adapter.workplace.SWkpHistImported;
import nts.uk.ctx.at.schedule.dom.adapter.workplace.SyWorkplaceAdapter;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class SyWorkplaceAdapterImpl implements SyWorkplaceAdapter {

//	@Inject
//	private SyWorkplacePub syWorkplacePub;
	
	@Inject
	private WorkplacePub workplacePub;

	@Override
	public Optional<SWkpHistImported> findBySid(String employeeId, GeneralDate baseDate) {
		return this.workplacePub.findBySid(employeeId, baseDate)
				.map(x -> new SWkpHistImported(x.getDateRange(), x.getEmployeeId(), x.getWorkplaceId(),
						x.getWorkplaceCode(), x.getWorkplaceName(), x.getWkpDisplayName()));
	}

	@Override
	public Map<String, Pair<String,String>> getWorkplaceMapCodeBaseDateName(String companyId,
			List<String> wpkCodes, List<GeneralDate> baseDates) {
		return this.workplacePub.getWorkplaceMapCodeBaseDateName(companyId, wpkCodes, baseDates);
	}

}
