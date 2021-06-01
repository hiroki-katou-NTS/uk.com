package nts.uk.ctx.exio.dom.input.canonicalize;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.cnv.core.dom.conversiontable.ConversionCodeType;

/**
 * 受入モード
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ImportingMode {

	/** 既存データが存在しないデータのみ受け入れる */
	INSERT_ONLY(1),
	
	/** 既存データが存在するデータのみ受け入れる */
	UPDATE_ONLY(2),

	/** 受入対象レコードを削除して受け入れる */
	DELETE_RECORD_BEFOREHAND(3),
	
	/** 受入対象グループのデータをすべて削除して受け入れる */
	DELETE_GROUP_BEFOREHAND(4),
	;
	
	public final int value;
	
	public static ImportingMode valueOf(int value) {
		return EnumAdaptor.valueOf(value, ImportingMode.class);
	}

	public ConversionCodeType getType() {
		return (this == UPDATE_ONLY)
				? ConversionCodeType.UPDATE
				: ConversionCodeType.INSERT;
	}
}
