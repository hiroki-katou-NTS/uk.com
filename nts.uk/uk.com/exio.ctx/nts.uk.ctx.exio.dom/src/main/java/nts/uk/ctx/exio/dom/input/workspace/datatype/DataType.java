package nts.uk.ctx.exio.dom.input.workspace.datatype;

import java.math.BigDecimal;

import lombok.RequiredArgsConstructor;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDate;
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
	
	public void setParam(NtsStatement statement, String paramName, Object paramValue) {
		
		switch (this) {
		case STRING:
			statement.paramString(paramName, (String) paramValue);
			break;
		case INT:
			statement.paramInt(paramName, (Integer) paramValue);
			break;
		case REAL:
			statement.paramDecimal(paramName, (BigDecimal) paramValue);
			break;
		case DATE:
			statement.paramDate(paramName, (GeneralDate) paramValue);
			break;
		default:
			throw new RuntimeException("unknown: " + this + ", " + paramName + " = " + paramValue);
		}
	}
}
