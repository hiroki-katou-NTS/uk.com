package nts.uk.ctx.exio.dom.input.workspace.datatype;

import static nts.uk.ctx.exio.dom.input.workspace.datatype.DataType.*;

import lombok.Value;

/**
 * データ型構成
 */
@Value
public class DataTypeConfiguration {

	DataType type;
	
	int length;
	
	int scale;
	
	public static DataTypeConfiguration guid() {
		return new DataTypeConfiguration(STRING, 36, 0);
	}
	
	public static DataTypeConfiguration integer(int length) {
		return new DataTypeConfiguration(DataType.INT, length, 0);
	}
	
	public static DataTypeConfiguration text(int length) {
		return new DataTypeConfiguration(DataType.STRING, length, 0);
	}
	
	public static DataTypeConfiguration autonumber() {
		return new DataTypeConfiguration(DataType.AUTONUMBER, 0, 0);
	}
}
