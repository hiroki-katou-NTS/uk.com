package nts.uk.cnv.infra.td.entity.uktabledesign;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ScvmtUkTableDesignPk implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "SNAPSHOT_ID")
	public String snapshotId;
	
	@Column(name = "TABLE_ID")
	public String tableId;

}
