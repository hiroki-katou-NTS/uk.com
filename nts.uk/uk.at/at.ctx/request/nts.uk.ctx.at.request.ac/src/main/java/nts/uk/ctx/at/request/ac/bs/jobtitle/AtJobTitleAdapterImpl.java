package nts.uk.ctx.at.request.ac.bs.jobtitle;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.jobtitle.AtJobTitleAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.jobtitle.dto.AffJobTitleHistoryImport;
import nts.uk.ctx.bs.employee.pub.jobtitle.AffJobTitleBasicExport;
import nts.uk.ctx.bs.employee.pub.jobtitle.SyJobTitlePub;

@Stateless
public class AtJobTitleAdapterImpl implements AtJobTitleAdapter{

	@Inject
	private SyJobTitlePub jobTitlePub;
	
	@Override
	public Optional<AffJobTitleHistoryImport> getJobTitlebBySIDAndDate(String sID, GeneralDate baseDate) {
		Optional<AffJobTitleBasicExport> jobTitle = jobTitlePub.getBySidAndBaseDate(sID, baseDate);
		if (jobTitle.isPresent()){
			return Optional.of(new AffJobTitleHistoryImport(jobTitle.get().getJobTitleId(), jobTitle.get().getJobTitleCode(),jobTitle.get().getJobTitleName()));
		}
		return Optional.ofNullable(null);
	}

}
