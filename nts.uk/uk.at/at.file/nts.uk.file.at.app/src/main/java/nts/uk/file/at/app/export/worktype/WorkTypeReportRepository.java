package nts.uk.file.at.app.export.worktype;

import java.util.List;

public interface WorkTypeReportRepository {
 
   List<WorkTypeReportData> findAllWorkType(String companyId);
}
