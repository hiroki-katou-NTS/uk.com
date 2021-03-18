package nts.uk.cnv.infra.td.entity.snapshot.index;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

}
