package nts.uk.cnv.dom.td.schema.tabledesign.column;

import com.google.common.base.Objects;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import nts.uk.cnv.dom.td.tabledefinetype.TableDefineType;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class ColumnDesign implements Comparable<ColumnDesign> {
	private String id;
	private String name;
	private String jpName;

	private DefineColumnType type;

	private String comment;

	private int dispOrder;

	public String getColumnContaintDdl(TableDefineType datatypedefine) {
		return "\t" + this.name + " " +
				datatypedefine.dataType(this.type.dataType, this.type.length, this.type.scale) +
			(this.type.nullable ? " NULL" : " NOT NULL") +
			(
				this.type.defaultValue != null && !this.type.defaultValue.isEmpty()
				? " DEFAULT " + getDefaultValue(datatypedefine)
				: "" ) +
			(
				this.type.checkConstraint != null && !this.type.checkConstraint.isEmpty()
				? " CHECK " + getCheckValue(datatypedefine)
				: ""
			);
	}

	private String getDefaultValue(TableDefineType datatypedefine) {
		if (this.type.dataType != DataType.BOOL) return this.type.defaultValue;

		return datatypedefine.convertBoolValue(this.type.defaultValue);
	}

	private String getCheckValue(TableDefineType datatypedefine) {
		return (this.type.checkConstraint.contains(" to "))
				? formatNumericRange(datatypedefine, this.type.checkConstraint, this.name)
				: this.type.checkConstraint;
	}

	private String formatNumericRange(TableDefineType datatypedefine, String checkConstaint, String colName) {
		String[] num = checkConstaint.split(" to ");

		if (this.type.dataType == DataType.BOOL) {
			num[0] = datatypedefine.convertBoolValue(num[0]);
			num[1] = datatypedefine.convertBoolValue(num[1]);
		}

		return String.format("(%s >= %s AND %s <= %s)", colName, num[0], colName, num[1]);
	}

	public boolean isContractCd() {
		return this.name.equals("CONTRACT_CD");
	}

	@Override
	public int compareTo(ColumnDesign o) {
		return dispOrder - o.getDispOrder();
	}

	public boolean sameDesign(ColumnDesign other) {
		return Objects.equal(name, other.name)
				&& Objects.equal(jpName, other.jpName)
				&& Objects.equal(type, other.type)
				&& Objects.equal(comment, other.comment)
				&& Objects.equal(dispOrder, other.dispOrder);
	}
}
