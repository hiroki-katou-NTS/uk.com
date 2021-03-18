package nts.uk.cnv.infra.td.entity.uktabledesign;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class NemTdSnapshotTableIndexPk implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "SNAPSHOT_ID")
	public String snapshotId;
	
	@Column(name = "TABLE_ID")
	public String tableId;

	@Column(name = "TYPE")
	public String type;
	
	@Column(name = "SUFFIX")
	public String suffix;
	
	private static final String TYPE_PK = "PRIMARY KEY";
	private static final String TYPE_UK = "UNIQUE KEY";
	private static final String TYPE_INDEX = "INDEX";

	public static NemTdSnapshotTableIndexPk asPK(ScvmtUkTableDesignPk parentPk) {
		return new NemTdSnapshotTableIndexPk(parentPk.snapshotId, parentPk.tableId, TYPE_PK, "PK");
	}
	
	public static NemTdSnapshotTableIndexPk asUK(ScvmtUkTableDesignPk parentPk, String suffix) {
		return new NemTdSnapshotTableIndexPk(parentPk.snapshotId, parentPk.tableId, TYPE_UK, suffix);
	}
	
	public static NemTdSnapshotTableIndexPk asIndex(ScvmtUkTableDesignPk parentPk, String suffix) {
		return new NemTdSnapshotTableIndexPk(parentPk.snapshotId, parentPk.tableId, TYPE_INDEX, suffix);
	}
	
	public boolean isPK() {
		return TYPE_PK.equals(type);
	}

	public boolean isUK() {
		return TYPE_UK.equals(type);
	}

	public boolean isIndex() {
		return TYPE_INDEX.equals(type);
	}
}
