package nts.uk.ctx.at.function.dom.indexreconstruction.repository;

import nts.uk.ctx.at.function.dom.indexreconstruction.ProcExecIndex;

import java.util.Optional;

/**
 * The Interface ProcExecIndexRepository.
 * Repository インデックス再構成結果履歴
 */
public interface ProcExecIndexRepository {

	/**
	 * Find ProcExecIndex by id.
	 *
	 * @param execId the exec id
	 * @param tableName the table name
	 * @param indexName the index name
	 * @return the optional
	 */
	Optional<ProcExecIndex> findOne(String execId, String tableName, String indexName);
	
	/**
	 * Add new ProcExecIndex
	 * @param domain
	 */
	void add(ProcExecIndex domain);

	/**
	 * Delete ProcExecIndex
	 * @param domain
	 */
	void delete(String execId, String tableName, String indexName);


	Optional<ProcExecIndex> findByExecId(String execId);
}
