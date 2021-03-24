package nts.uk.cnv.infra.td.entity.snapshot.index;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "NEM_TD_SNAPSHOT_TABLE_INDEX_COLUMNS")
public class NemTdSnapshotTableIndexColumns extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final JpaEntityMapper<NemTdSnapshotTableIndexColumns> MAPPER = new JpaEntityMapper<>(NemTdSnapshotTableIndexColumns.class);

	@EmbeddedId
	public NemTdSnapshotTableIndexColumnsPk pk;
	
	@Column(name = "COLUMN_ID")
	public String columnId;

	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static List<NemTdSnapshotTableIndexColumns> toEntities(String snapshotId, String tableId, List<String> columnIds,
			NemTdSnapshotTableIndexPk indexPK){
		List<NemTdSnapshotTableIndexColumns> results = new ArrayList<>();
		for(int columnOrder = 0; columnOrder < columnIds.size(); columnOrder++) {
			toEntity(snapshotId, tableId, columnIds.get(columnOrder), indexPK, columnOrder++);
		}
		return results;
	}
	public static NemTdSnapshotTableIndexColumns toEntity(String snapshotId, String tableId, String columnId,
			NemTdSnapshotTableIndexPk indexPK, int columnOrder ) {
		val pk = new NemTdSnapshotTableIndexColumnsPk(tableId, snapshotId, indexPK.type, indexPK.suffix, columnOrder);
		return new NemTdSnapshotTableIndexColumns(
				pk, 
				columnId);
	}
}
