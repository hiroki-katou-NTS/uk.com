package nts.uk.ctx.exio.app.input.develop.workspace.oruta.detail;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;

@Value
public class OrutaTypeConfig {
	
	String dataType;
	int length;
	int scale;
	boolean nullable;
	String defaultValue;
	String checkConstraint;
	
	public DataType convertDataType() {
		switch (dataType.toUpperCase()) {
		case "CHAR":
		case "NCHAR":
		case "VARCHAR":
		case "NVARCHAR":
		case "GUID":
			return DataType.STRING;
		case "INT":
		case "BOOL":
			return DataType.INT;
		case "REAL":
			return DataType.REAL;
		case "DATE":
			return DataType.DATE;
		case "DATETIME":
		case "DATETIMEMS":
			return DataType.DATETIME;
		}
		
		throw new RuntimeException("unknown: " + dataType);
	}
}
