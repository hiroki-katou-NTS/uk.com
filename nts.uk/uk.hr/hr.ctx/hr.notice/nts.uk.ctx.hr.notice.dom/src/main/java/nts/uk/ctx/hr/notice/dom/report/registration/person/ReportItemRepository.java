/**
 * 
 */
package nts.uk.ctx.hr.notice.dom.report.registration.person;

import java.util.List;

/**
 * @author laitv
 *
 */
public interface ReportItemRepository {
	
	List<ReportItem> getDetailReport(String cid, int reportId);
	
	boolean checkExit(String cid, int reportId, String ctgCode , String itemCd);
	
	void registerItemData(ReportItem domain);

	void add(ReportItem domain);
	
	void addAll(List<ReportItem> domains);
	
	void update(ReportItem domain);
	
	void deleteByReportId(String cid, int reportId);
}
