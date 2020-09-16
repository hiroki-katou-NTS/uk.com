package nts.uk.ctx.at.function.infra.entity.indexreconstruction;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * The Class KfndtProcExecIndexPk.
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KfndtProcExecIndexPk implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The exec id. */
	@Column(name = "EXEC_ID")
	public String execId;
	
	/** The table name. */
	@Column(name = "TABLE_NAME")
	public String tableName;
	
	/** The index name. */
	@Column(name = "INDEX_NAME")
	public String indexName;
}
