package nts.uk.ctx.bs.employee.dom.jobtitle.info;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface SequenceMasterExportRepository {
	List<JobTitleInfo> findAll(String companyId, GeneralDate baseDate);
	
}
