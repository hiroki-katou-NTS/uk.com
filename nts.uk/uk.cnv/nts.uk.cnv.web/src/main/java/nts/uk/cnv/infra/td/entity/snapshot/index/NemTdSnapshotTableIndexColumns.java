package nts.uk.cnv.infra.td.entity.snapshot.index;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "NEM_TD_SNAPSHOT_TABLE_INDEX_COLUMNS")
public class NemTdSnapshotTableIndexColumns extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final JpaEntityMapper<NemTdSnapshotTableIndexColumns> MAPPER = new JpaEntityMapper<>(NemTdSnapshotTableIndexColumns.class);

	@EmbeddedId
	public NemTdSnapshotTableIndexColumnsPk pk;
	
	@Column(name = "COLUMN_ID")
	public String columnId;

	@ManyToOne
	@JoinColumns({ 
		@JoinColumn(name = "SNAPSHOT_ID", referencedColumnName = "SNAPSHOT_ID", insertable = false, updatable = false),
		@JoinColumn(name = "TABLE_ID", referencedColumnName = "TABLE_ID", insertable = false, updatable = false),
		@JoinColumn(name = "TYPE", referencedColumnName = "TYPE", insertable = false, updatable = false),
		@JoinColumn(name = "SUFFIX", referencedColumnName = "SUFFIX", insertable = false, updatable = false),
	})
	public NemTdSnapshotTableIndex indexOfColumn;
	
	@Override
	protected Object getKey() {
		return pk;
	}
	
	public NemTdSnapshotTableIndexColumns(NemTdSnapshotTableIndexColumnsPk pk, String columnId) {
		super();
		this.pk = pk;
		this.columnId = columnId;
	}	
	
	public static List<NemTdSnapshotTableIndexColumns> toEntities(String snapshotId, String tableId, List<String> columnIds,
			NemTdSnapshotTableIndexPk indexPK){
		List<NemTdSnapshotTableIndexColumns> results = new ArrayList<>();
		if(columnIds.size() >=100) {
			throw new RuntimeException("Orderの桁数がオーバーします。");
		}
		for(int columnOrder = 0; columnOrder < columnIds.size(); columnOrder++) {
			results.add(toEntity(snapshotId, tableId, columnIds.get(columnOrder), indexPK, columnOrder));
		}
		return results;
	}
	public static NemTdSnapshotTableIndexColumns toEntity(String snapshotId, String tableId, String columnId,
			NemTdSnapshotTableIndexPk indexPK, int columnOrder ) {
		val pk = new NemTdSnapshotTableIndexColumnsPk(snapshotId, tableId,  indexPK.type, indexPK.suffix, columnOrder);
		return new NemTdSnapshotTableIndexColumns(
				pk, 
				columnId);
	}
}
