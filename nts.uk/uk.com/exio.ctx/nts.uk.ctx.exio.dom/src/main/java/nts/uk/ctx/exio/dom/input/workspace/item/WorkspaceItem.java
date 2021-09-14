package nts.uk.ctx.exio.dom.input.workspace.item;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataTypeConfiguration;

/**
 * ワークスペースの項目
 */
@Value
public class WorkspaceItem {

	/** グループID */
	private final ImportingDomainId domainId;
	
	/** 項目No */
	private final int itemNo;
	
	/** 項目名 */
	private final String name;
	
	/** データ型構成 */
	private final DataTypeConfiguration dataTypeConfig;
	
	/**
	 * 受入可能項目の定義と一致しているか診断する
	 * @param importableItem
	 * @return
	 */
	public boolean diagnose(ImportableItem importableItem) {
		return dataTypeConfig.getType() == DataType.of(importableItem.getItemType());
	}
}
