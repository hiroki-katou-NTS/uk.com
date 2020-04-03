package nts.uk.ctx.hr.notice.dom.report.valueImported;

import java.util.List;

public interface HumanItemPub {
	
	List<PerInfoItemDefImport> getAll(List<String> itemIds);
	
	List<String> getAllItemIds(String cid, List<String> ctgCodes, List<String> itemCds);
	
	String getCategoryName(String cid, String categoryCode);
	
	DateRangeItemImport getDateRangeItemByCtgId(String categoryId);
	
	List<DateRangeItemImport> getDateRangeItemByListCtgId(List<String> categoryIds);
	
	String getItemDfId(String ctgId , String itemCd);
	
}
