package nts.uk.ctx.exio.dom.input.meta;

import java.util.ArrayList;
import java.util.List;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroupId;

/**
 * 受入データのメタ情報
 */
@Value
public class ImportingDataMeta {
	
	/** 会社ID */
	String companyId;
	
	ImportingGroupId groupId;

	/** 受入項目と正準化によって生成される項目の名称一覧 */
	List<String> itemNames;
	
	public ImportingDataMeta addItem(RequireAddItem require, int itemNo) {
		
		String name = require.getImportableItem(groupId, itemNo).getItemName();
		
		val newNames = new ArrayList<>(itemNames);
		newNames.add(name);
		
		return new ImportingDataMeta(name, groupId, newNames);
	}
	
	public static interface RequireAddItem {
		
		ImportableItem getImportableItem(ImportingGroupId groupId, int itemNo);
	}
}
