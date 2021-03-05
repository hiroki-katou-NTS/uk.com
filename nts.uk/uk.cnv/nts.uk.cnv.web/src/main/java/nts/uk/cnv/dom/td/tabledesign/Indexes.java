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
	private Boolean clustered;
	private Boolean unique;
	private List<String> colmns;
	private List<String> params;

	public static Indexes createPk(TableName tableName, List<String> columns, boolean clustred) {
		return new Indexes(
				tableName.pkName(),
				"PRIMARY KEY",
				clustred,
				true,
				columns,
				new ArrayList<>());
	}

	public static Indexes createUk(String name, List<String> columns, boolean clustred) {
		return new Indexes(
				name,
				"UNIQUE KEY",
				clustred,
				true,
				columns,
				new ArrayList<>());
	}

	public static Indexes createIndex(String name, List<String> columns, boolean clustred, boolean unique) {
		return new Indexes(
				name,
				"INDEX",
				clustred,
				unique,
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
		return "CREATE INDEX " + this.name + " ON " + tablename + " (" + String.join(",", this.colmns) + ")";
	}

	public String getTableContaintDdl() {
		return "\t" + "CONSTRAINT " + this.name + " " + this.constraintType + " (" + String.join(",", this.colmns) + ")";
	}
}
