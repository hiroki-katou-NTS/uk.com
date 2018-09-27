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

	@Override
	public Map<Pair<String, GeneralDate>, Pair<String,String>> getWorkplaceMapCodeBaseDateName(String companyId,
			List<String> wpkCodes, List<GeneralDate> baseDates) {
		return this.syWorkplacePub.getWorkplaceMapCodeBaseDateName(companyId, wpkCodes, baseDates);
	}

}
