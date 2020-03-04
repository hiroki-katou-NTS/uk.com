package nts.uk.ctx.hr.notice.dom.report;

import java.util.List;

public interface RegisterPersonalReportItemRepository {
	
	List<RegisterPersonalReportItem> getAllItemBy(String cid, int rptLayoutId);
	
    void insertAll(List<RegisterPersonalReportItem>  domain);
    
    void updateAll(List<RegisterPersonalReportItem>  domain);
    
    void removeAllByLayoutId(String cid, int rptLayoutId);
    
}
