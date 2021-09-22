package nts.uk.ctx.exio.dom.input.workspace.item;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

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
	
	public static WorkspaceItemType valueOf(int value) {
		return EnumAdaptor.valueOf(value, WorkspaceItemType.class);
	}
	
	public final int value;
}
