package nts.uk.cnv.dom.td.schema.tabledesign.column;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 列の型定義
 *
 */
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class DefineColumnType {
	DataType dataType;
	int length;
	int scale;
	boolean nullable;
	String defaultValue;
	String checkConstraint;
	
	public DefineColumnType(DefineColumnType source) {
		dataType = source.dataType;
		length = source.length;
		scale = source.scale;
		nullable = source.nullable;
		defaultValue = source.defaultValue;
		checkConstraint = source.checkConstraint;
	}
}
