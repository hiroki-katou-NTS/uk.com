package nts.uk.file.com.app.company.approval;

import java.util.Optional;


public interface SysUsageExportRepository {
	Optional<SysUsageSetData> findUsageSet(String companyId);
}
