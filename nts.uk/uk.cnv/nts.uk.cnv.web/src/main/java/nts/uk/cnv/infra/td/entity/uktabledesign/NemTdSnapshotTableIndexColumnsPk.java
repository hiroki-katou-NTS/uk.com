package nts.uk.cnv.infra.td.entity.uktabledesign;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Embeddable
public class NemTdSnapshotTableIndexColumnsPk implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "TABLE_ID")
	public String tableId;

	@Column(name = "SNAPSHOT_ID")
	private String snapshotId;

	@Column(name = "TYPE")
	public String type;
	
	@Column(name = "SUFFIX")
	public String suffix;

}
