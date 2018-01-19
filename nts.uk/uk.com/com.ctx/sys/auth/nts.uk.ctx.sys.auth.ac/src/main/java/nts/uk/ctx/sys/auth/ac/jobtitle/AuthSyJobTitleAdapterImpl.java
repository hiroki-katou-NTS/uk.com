package nts.uk.ctx.sys.auth.ac.jobtitle;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.jobtitle.SyJobTitlePub;
import nts.uk.ctx.sys.auth.dom.adapter.jobtitle.AffJobTitleHistory_ver1Import;
import nts.uk.ctx.sys.auth.dom.adapter.jobtitle.SyJobTitleAdapter;

/**
 * 
 * @author lamdt
 *
 */
@Stateless
public class AuthSyJobTitleAdapterImpl implements SyJobTitleAdapter {

	@Inject
	private SyJobTitlePub syJobTitlePub;
	
	@Override
	public Optional<AffJobTitleHistory_ver1Import> gerBySidAndBaseDate(String employeeId, GeneralDate baseDate) {
		val optExportData = syJobTitlePub.gerBySidAndBaseDate(employeeId, baseDate);
		if (optExportData.isPresent()) {
			val exportData = optExportData.get();
			return Optional.of(new AffJobTitleHistory_ver1Import(exportData.companyId, exportData.employeeId, exportData.jobTitleId, exportData.historyItems));
		}
		return Optional.empty();
	}

}
