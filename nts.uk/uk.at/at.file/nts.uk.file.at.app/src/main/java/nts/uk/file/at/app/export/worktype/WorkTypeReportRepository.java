package nts.uk.file.at.app.export.worktype;

import java.util.List;

import nts.uk.file.at.app.export.worktype.data.WorkTypeReportData;

public interface WorkTypeReportRepository {
 
   List<WorkTypeReportData> findAllWorkType(String companyId);
}
