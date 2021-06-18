package nts.uk.ctx.at.function.dom.processexecution.repository;

import nts.uk.ctx.at.function.dom.processexecution.UpdateProcessAutoExecution;

import java.util.List;
import java.util.Optional;

/**
 * The interface Process execution repository.<br>
 * Repository 更新処理自動実行
 */
public interface ProcessExecutionRepository {

	/**
	 * Gets process execution by company id.
	 *
	 * @param companyId the company id
	 * @return the <code>UpdateProcessAutoExecution</code> list
	 */
	List<UpdateProcessAutoExecution> getProcessExecutionByCompanyId(String companyId);

	/**
	 * Gets process execution by cid and exec cd.
	 *
	 * @param companyId  the company id
	 * @param execItemCd the exec item cd
	 * @return the <code>UpdateProcessAutoExecution</code>
	 */
	Optional<UpdateProcessAutoExecution> getProcessExecutionByCidAndExecCd(String companyId, String execItemCd);

	/**
	 * Insert.
	 *
	 * @param domain the domain
	 */
	void insert(UpdateProcessAutoExecution domain);

	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	void update(UpdateProcessAutoExecution domain);

	/**
	 * Remove.
	 *
	 * @param companyId  the company id
	 * @param execItemCd the exec item cd
	 */
	void remove(String companyId, String execItemCd);

	/**
	 * Find by company id and exec item code list.
	 *
	 * @param cid         the company id
	 * @param execItemCds the exec item code list
	 * @return the <code>UpdateProcessAutoExecution</code> domain list
	 */
	List<UpdateProcessAutoExecution> findByCidAndExecItemCds(String cid, List<String> execItemCds);

}
