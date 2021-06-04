package nts.uk.ctx.exio.dom.exi.extcategory;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SpecialExternalItem {
	/**	0 特殊項目ではない  */
	NOTSPECIAL(0),
	/**	1 会社CD　⇒　会社ID*/
	CID(1),
	/** 2 社員CD　⇒　社員ID */
	SID(2),
	/**
	 * 
	 */
	NO_ALPHA(3);
	public final Integer value;
}
