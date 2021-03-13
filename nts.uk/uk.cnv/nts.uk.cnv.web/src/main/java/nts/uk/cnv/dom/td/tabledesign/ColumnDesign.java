package nts.uk.cnv.dom.td.tabledesign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.cnv.dom.td.tabledefinetype.DataType;
import nts.uk.cnv.dom.td.tabledefinetype.TableDefineType;

@AllArgsConstructor
@Getter
public class ColumnDesign {
	private String id;
	private String name;
	private String jpName;

	private DefineColumnType type;

	private String comment;

	private int dispOrder;

	public String getColumnContaintDdl(TableDefineType datatypedefine) {
		return "\t" + this.name + " " +
				datatypedefine.dataType(this.type.type, this.type.length, this.type.scale) +
			(this.type.nullable ? " NULL" : " NOT NULL") +
			(
				this.type.defaultValue != null && !this.type.defaultValue.isEmpty()
				? " DEFAULT " + getDefaultValue(this.type.defaultValue, datatypedefine)
				: ""
			);
	}

	public DataType getType() {
		return this.type.type;
	}

	public int getMaxLength() {
		return this.type.length;
	}

	public int getScale() {
		return this.type.scale;
	}

	public boolean isNullable() {
		return this.type.nullable;
	}

	public String getDefaultValue() {
		return this.type.defaultValue;
	}

	public String getCheck() {
		return this.type.checkConstaint;
	}

	private String getDefaultValue(String value, TableDefineType datatypedefine) {
		if (this.type.type != DataType.BOOL) return value;

		return datatypedefine.convertBoolDefault(value);
	}

	public boolean isContractCd() {
		return this.name.equals("CONTRACT_CD");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type.checkConstaint == null) ? 0 : type.checkConstaint.hashCode());
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((type.defaultValue == null) ? 0 : type.defaultValue.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((jpName == null) ? 0 : jpName.hashCode());
		result = prime * result + type.length;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (type.nullable ? 1231 : 1237);
		result = prime * result + type.scale;
		result = prime * result + ((type.type == null) ? 0 : type.type.hashCode());
		result = prime * result + dispOrder;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColumnDesign other = (ColumnDesign) obj;
		if (type.checkConstaint == null) {
			if (other.type.checkConstaint != null)
				return false;
		} else if (!type.checkConstaint.equals(other.type.checkConstaint))
			return false;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (type.defaultValue == null) {
			if (other.type.defaultValue != null)
				return false;
		} else if (!type.defaultValue.equals(other.type.defaultValue))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (jpName == null) {
			if (other.jpName != null)
				return false;
		} else if (!jpName.equals(other.jpName))
			return false;
		if (type.length != other.type.length)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type.nullable != other.type.nullable)
			return false;
		if (type.scale != other.type.scale)
			return false;
		if (!type.equals(other.type))
			return false;
		if (dispOrder != other.dispOrder)
			return false;
		return true;
	}
}
