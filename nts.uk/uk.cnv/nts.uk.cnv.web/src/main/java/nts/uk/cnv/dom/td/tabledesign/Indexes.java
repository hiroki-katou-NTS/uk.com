package nts.uk.cnv.dom.td.tabledesign;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Indexes {
	private String name;
	private String constraintType;
	private boolean clustered;
	private List<String> columns;
	private List<String> params;

	public static Indexes createPk(TableName tableName, List<String> columns, boolean clustred) {
		return new Indexes(
				tableName.pkName(),
				"PRIMARY KEY",
				clustred,
				columns,
				new ArrayList<>());
	}

	public static Indexes createUk(String name, List<String> columns, boolean clustred) {
		return new Indexes(
				name,
				"UNIQUE KEY",
				clustred,
				columns,
				new ArrayList<>());
	}

	public static Indexes createIndex(String name, List<String> columns, boolean clustred) {
		return new Indexes(
				name,
				"INDEX",
				clustred,
				columns,
				new ArrayList<>());
	}

	public boolean isPK() {
		return ("PRIMARY KEY".equals(this.constraintType));
	}

	public boolean isUK() {
		return ("UNIQUE KEY".equals(this.constraintType));
	}

	public boolean isIndex() {
		return ("INDEX".equals(this.constraintType));
	}

	public String getCreateDdl(String tablename) {
		return "CREATE INDEX " + this.name + " ON " + tablename + " (" + String.join(",", this.columns) + ")";
	}

	public String getTableContaintDdl() {
		return "\t" + "CONSTRAINT " + this.name + " " + this.constraintType + " (" + String.join(",", this.columns) + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (clustered ? 0 : 1);
		result = prime * result + ((columns == null) ? 0 : columns.hashCode());
		result = prime * result + ((constraintType == null) ? 0 : constraintType.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((params == null) ? 0 : params.hashCode());
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
		Indexes other = (Indexes) obj;
		if (clustered) {
			if (!other.clustered)
				return false;
		} else if (clustered != other.clustered)
			return false;
		if (columns == null) {
			if (other.columns != null)
				return false;
		} else {
			for(int i=0; i< columns.size(); i++) {
				if(columns.get(i).equals(other.columns.get(i))) {
					return false;
				}
			}
		}

		if (constraintType == null) {
			if (other.constraintType != null)
				return false;
		} else if (!constraintType.equals(other.constraintType))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (params == null) {
			if (other.params != null)
				return false;
		} else if (!params.equals(other.params))
			return false;
		return true;
	}

}
