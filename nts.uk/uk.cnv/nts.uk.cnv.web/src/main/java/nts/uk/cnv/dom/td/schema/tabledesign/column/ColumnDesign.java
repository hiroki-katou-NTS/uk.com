package nts.uk.cnv.dom.td.schema.tabledesign.column;

import com.google.common.base.Objects;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.uk.cnv.dom.td.tabledefinetype.TableDefineType;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
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
				? " DEFAULT " + getDefaultValue(this.type.defaultValue, datatypedefine)
				: ""
			);
	}

	private String getDefaultValue(String value, TableDefineType datatypedefine) {
		if (this.type.dataType != DataType.BOOL) return value;

		return datatypedefine.convertBoolDefault(value);
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
