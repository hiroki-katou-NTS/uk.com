package nts.uk.ctx.exio.dom.input.canonicalize;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 受入モード
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ImportingMode {

	/** 新規受入のみ */
	INSERT_ONLY(1, "ImportingMode_INSERT_ONLY"),
	
	/** 上書き受入のみ */
	UPDATE_ONLY(2, "ImportingMode_UPDATE_ONLY"),
	
	/** 新規受入と上書き受入 */
	INSERT_AND_UPDATE(3, "ImportingMode_INSERT_AND_UPDATE"),
	
	;
	
	public final int value;
	
	public final String nameId;
	
	public static ImportingMode valueOf(int value) {
		return EnumAdaptor.valueOf(value, ImportingMode.class);
	}
}
