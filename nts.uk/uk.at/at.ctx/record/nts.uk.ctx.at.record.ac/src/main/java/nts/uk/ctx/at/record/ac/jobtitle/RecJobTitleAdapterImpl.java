package nts.uk.ctx.at.record.ac.jobtitle;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.jobtitle.RecJobTitleAdapter;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrweb.master.AllMasterAttItem.MasterAttItemDetail;
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
	public Map<Pair<String, GeneralDate>, Pair<String, String>> getJobTitleMapIdBaseDateName(String companyId,
			List<String> jobIds, List<GeneralDate> baseDates) {
		return this.syJobTitlePub.getJobTitleMapIdBaseDateName(companyId, jobIds, baseDates);
	}

	@Override
	public List<MasterAttItemDetail> findAll(String companyId, GeneralDate baseDate) {
		return syJobTitlePub.findAll(companyId, baseDate).stream()
				.map(x -> new MasterAttItemDetail(x.getJobTitleId(), x.getJobTitleCode(), x.getJobTitleName()))
				.collect(Collectors.toList());
	}
}
