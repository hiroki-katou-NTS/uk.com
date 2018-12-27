package nts.uk.ctx.at.record.ac.workplace;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.workplace.SyWorkplaceAdapter;
import nts.uk.ctx.bs.employee.pub.workplace.SyWorkplacePub;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class SyWorkplaceAdapterImp implements SyWorkplaceAdapter {

	@Inject
	private SyWorkplacePub syWorkplacePub;

	@Override
	public Map<Pair<String, GeneralDate>, Pair<String,String>> getWorkplaceMapCodeBaseDateName(String companyId,
			List<String> wpkCodes, List<GeneralDate> baseDates) {
		return this.syWorkplacePub.getWorkplaceMapCodeBaseDateName(companyId, wpkCodes, baseDates);
	}


}
