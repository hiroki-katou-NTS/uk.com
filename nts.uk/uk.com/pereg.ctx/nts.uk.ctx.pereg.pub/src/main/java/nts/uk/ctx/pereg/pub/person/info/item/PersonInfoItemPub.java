package nts.uk.ctx.pereg.pub.person.info.item;

import java.util.List;
import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.ComboBoxObject;

public interface PersonInfoItemPub {
	List<PerInfoItemDefExport> getPerInfoItemDefByListIdForLayout(List<String> listItemDefId);
	
	List<String> getAllItemIds(String cid, List<String> ctgCds, List<String> itemCds);
	
	String getCategoryName(String cid, String categoryCode);
	
	DateRangeItemExport getDateRangeItemByCtgId(String categoryId);
	
	List<ComboBoxObject> getCombo(SelectionItemExport selectionItemDto,
			Map<String, Map<Boolean, List<ComboBoxObject>>> combobox, String employeeId,
			GeneralDate comboBoxStandardDate, boolean isRequired, int perEmplType, boolean isDataType6,
			String categoryCode);
	
	String getItemDfId(String ctgId , String itemCd);

}
