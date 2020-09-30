package nts.uk.ctx.at.function.infra.entity.processexecution;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class KfndtProcExecIndexPk implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** The exec id.
	 * 	実行ID
	 */
	@Column(name = "EXEC_ID")
	public String execId;
	
	/** The table name. 
	 * 	テーブル物理名
	 */
	@Column(name = "TABLE_NAME")
	public String tableName;
	
	/** The index name. 
	 *	インデックス名
	 */
	@Column(name = "INDEX_NAME")
	public String indexName;
}
