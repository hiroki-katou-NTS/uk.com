package nts.uk.ctx.exio.dom.input.workspace;

import lombok.RequiredArgsConstructor;

/**
 * ワークスペースの項目の種別
 */
@RequiredArgsConstructor
public enum WorkspaceItemType {

	/** 受入可能項目 */
	IMPORTABLE_ITEM(1),
	
	/** GUID */
	GUID(2),
	
	;
	
	public final int value;
}
