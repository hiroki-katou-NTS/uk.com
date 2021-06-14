package nts.uk.ctx.exio.dom.input.workspace;

import lombok.RequiredArgsConstructor;
import nts.uk.ctx.exio.dom.input.importableitem.ItemType;

/**
 * データ型
 */
@RequiredArgsConstructor
public enum DataType {
	STRING(0),
	INT(1),
	REAL(2),
	DATE(3),
	;

	public final int value;

	public static DataType of(ItemType itemType) {
		
		switch (itemType) {
		case INT:
		case TIME_DURATION:
		case TIME_POINT:
			return DataType.INT;
		case STRING:
			return DataType.STRING;
		case REAL:
			return DataType.REAL;
		case DATE:
			return DataType.DATE;
		default:
			throw new RuntimeException("unknown: " + itemType);
		}
	}
}
