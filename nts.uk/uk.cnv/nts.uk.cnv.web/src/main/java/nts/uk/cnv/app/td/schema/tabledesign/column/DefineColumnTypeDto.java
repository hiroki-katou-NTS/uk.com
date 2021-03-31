package nts.uk.cnv.app.td.schema.tabledesign.column;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DataType;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DefineColumnType;

@Data
@NoArgsConstructor
public class DefineColumnTypeDto {

	DataType dataType;
	int length;
	int scale;
	boolean nullable;
	String defaultValue;
	String checkConstraint;
	
	public DefineColumnTypeDto(DefineColumnType d) {
		dataType = d.getDataType();
		length = d.getLength();
		scale = d.getScale();
		nullable = d.isNullable();
		defaultValue = d.getDefaultValue();
		checkConstraint = d.getCheckConstraint();
	}
	
	public DefineColumnType toDomain() {
		return new DefineColumnType(
				dataType,
				length,
				scale,
				nullable,
				defaultValue,
				checkConstraint);
	}
}
