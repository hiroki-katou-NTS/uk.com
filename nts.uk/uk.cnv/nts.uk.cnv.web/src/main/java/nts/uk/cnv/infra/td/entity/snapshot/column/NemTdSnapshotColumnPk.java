package nts.uk.cnv.infra.td.entity.snapshot.column;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class NemTdSnapshotColumnPk implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "SNAPSHOT_ID")
	private String snapshotId;
	
	@Column(name = "TABLE_ID")
	public String tableId;

	@Column(name = "ID")
	public String id;

}
