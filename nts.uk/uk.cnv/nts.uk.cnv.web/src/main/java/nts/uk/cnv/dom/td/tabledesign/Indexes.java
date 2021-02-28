package nts.uk.cnv.dom.td.tabledesign;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Indexes {
	private String name;
	private String constraintType;
	private Boolean clustered;
	private List<String> colmns;
	private List<String> params;

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
