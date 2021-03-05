package nts.uk.cnv.dom.td.tabledesign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.cnv.dom.td.tabledefinetype.DataType;
import nts.uk.cnv.dom.td.tabledefinetype.TableDefineType;

@AllArgsConstructor
@Getter
public class ColumnDesign {
	private int id;
	private String name;
	private String jpName;
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

	private String check;

	public String getColumnContaintDdl(TableDefineType datatypedefine) {
		return "\t" + this.name + " " +
				datatypedefine.dataType(this.type, this.maxLength, this.scale) +
			(this.nullable ? " NULL" : " NOT NULL") +
			(
				this.defaultValue != null && !this.defaultValue.isEmpty()
				? " DEFAULT " + getDefaultValue(this.defaultValue, datatypedefine)
				: ""
			);
	}

	private String getDefaultValue(String value, TableDefineType datatypedefine) {
		if (this.type != DataType.BOOL) return value;

		return datatypedefine.convertBoolDefault(value);
	}

	public boolean isContractCd() {
		return this.name.equals("CONTRACT_CD");
	}
}
