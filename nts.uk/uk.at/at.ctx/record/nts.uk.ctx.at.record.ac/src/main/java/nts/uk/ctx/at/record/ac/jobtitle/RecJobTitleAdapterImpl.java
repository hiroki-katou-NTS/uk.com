package nts.uk.ctx.at.record.ac.jobtitle;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.jobtitle.RecJobTitleAdapter;
import nts.uk.ctx.bs.employee.pub.jobtitle.SyJobTitlePub;
/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class RecJobTitleAdapterImpl implements RecJobTitleAdapter {
	@Inject
	private SyJobTitlePub syJobTitlePub;
	
	@Override
	public Map<Pair<String, GeneralDate>, Pair<String,String>> getJobTitleMapIdBaseDateName(String companyId, List<String> jobIds,
			List<GeneralDate> baseDates) {
		return this.syJobTitlePub.getJobTitleMapIdBaseDateName(companyId, jobIds, baseDates);
	}
}
