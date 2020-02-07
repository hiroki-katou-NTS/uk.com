package nts.uk.ctx.pereg.pub.person.info.item;

import java.util.List;

public interface PersonInfoItemPub {
	List<PerInfoItemDefExport> getPerInfoItemDefByListIdForLayout(List<String> listItemDefId);
	
	List<String> getAllItemIds(String cid, List<String> ctgCds, List<String> itemCds);
	
	String getCategoryName(String cid, String categoryCode);

}
