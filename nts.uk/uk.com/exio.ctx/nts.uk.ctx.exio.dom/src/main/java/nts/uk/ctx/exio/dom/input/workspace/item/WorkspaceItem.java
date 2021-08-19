package nts.uk.ctx.exio.dom.input.workspace.item;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataTypeConfiguration;

/**
 * ワークスペースの項目
 */
@Value
public class WorkspaceItem {

	/** グループID */
	private final ImportingDomainId groupId;
	
	/** 項目No */
	private final int itemNo;
	
	/** 項目名 */
	private final String name;
	
	/** データ型構成 */
	private final DataTypeConfiguration dataTypeConfig;
}
