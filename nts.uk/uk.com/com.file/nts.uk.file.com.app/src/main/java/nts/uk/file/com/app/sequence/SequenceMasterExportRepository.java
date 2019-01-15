package nts.uk.file.com.app.sequence;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface SequenceMasterExportRepository {
	List<SequenceMasterExportData> findAll(String companyId, GeneralDate baseDate);
}
