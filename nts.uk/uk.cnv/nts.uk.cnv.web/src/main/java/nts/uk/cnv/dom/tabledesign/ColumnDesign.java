package nts.uk.cnv.dom.tabledesign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.cnv.dom.databasetype.DataType;

@AllArgsConstructor
@Getter
public class ColumnDesign {
	private int id;
	private String name;
	private DataType type;
	private int maxLength;
	private int scale;
	/**private int precision;**/
	private boolean nullable;
	
	private boolean primaryKey;
	private int primaryKeySeq;
	private boolean uniqueKey;
	private int uniqueKeySeq;

	private String defaultValue;
	private String comment;
}
