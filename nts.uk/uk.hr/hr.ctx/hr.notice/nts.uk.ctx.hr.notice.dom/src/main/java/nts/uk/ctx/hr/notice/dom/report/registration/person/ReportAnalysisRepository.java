package nts.uk.ctx.hr.notice.dom.report.registration.person;

import java.util.List;
import java.util.Optional;

public interface ReportAnalysisRepository {
	
	Optional<ReportAnalysis> getListReportAnalysis(String cid, int reportLayoutID,  int countClass, int  countClassSmall, int reportYearMonth);
	
	void updateAll(List<ReportAnalysis> domains);
	
	void update(ReportAnalysis domain);
	
	void insert(ReportAnalysis domain);
	
}
