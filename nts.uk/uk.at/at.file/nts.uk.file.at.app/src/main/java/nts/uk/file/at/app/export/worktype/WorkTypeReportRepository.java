package nts.uk.file.at.app.export.worktype;

import java.util.List;

/**
 * Work Type Report Repository
 * 
 * @author sonnh
 *
 */
public interface WorkTypeReportRepository {
 
   List<WorkTypeReportData> findAllWorkType(String companyId,String langId);
}
