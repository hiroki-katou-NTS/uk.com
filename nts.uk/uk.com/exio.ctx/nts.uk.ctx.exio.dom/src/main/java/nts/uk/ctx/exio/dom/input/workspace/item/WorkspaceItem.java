package nts.uk.ctx.exio.dom.input.workspace.item;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataTypeConfiguration;

/**
 * ワークスペースの項目
 */
@Value
public class WorkspaceItem {

	/** グループID */
	private final ImportingGroupId groupId;
	
	/** 項目No */
	private final int itemNo;
	
	/** 項目名 */
	private final String name;
	
	/** 種別 */
	private final WorkspaceItemType type;
	
	/**
	 * データ型構成を作る
	 * @param require
	 * @return
	 */
	public DataTypeConfiguration configureDataType(RequireConfigureDataType require) {
		
		switch (type) {
		case IMPORTABLE_ITEM:
			return DataTypeConfiguration.of(require.getImportableItem(groupId, itemNo));
		case GUID:
			return DataTypeConfiguration.guid();
		}
		
		throw new RuntimeException("unknown: " + type);
	}
	
	public static interface RequireConfigureDataType {
		
		ImportableItem getImportableItem(ImportingGroupId groupId, int itemNo);
	}
}
