package nts.uk.ctx.hr.notice.dom.report;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PersonalReportClassificationRepository {
	
 List<PersonalReportClassification> getAllByCid(String cid, boolean abolition);
 
 Optional<PersonalReportClassification> getDetailReportClsByReportClsID(String cid, int reportClsID);
 
 List<PersonalReportClassification> getAllSameNameByCid(String cid, String reportName);
 
 List<PersonalReportClassification> getAllSameCodeByCid(String cid, String reportCode);
 
 Map<String, Boolean> checkExist(String cid, String reportCode, String reportName);
 
 int maxId(String cid);
 
 int maxDisorder(String cid);
 
 void insert(PersonalReportClassification domain);
 
 void update(PersonalReportClassification domain);
 
}
