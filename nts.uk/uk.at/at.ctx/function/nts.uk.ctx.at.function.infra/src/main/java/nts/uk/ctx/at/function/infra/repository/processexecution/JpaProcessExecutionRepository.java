package nts.uk.ctx.at.function.infra.repository.processexecution;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.processexecution.UpdateProcessAutoExecution;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionRepository;
import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtProcessExecution;
import nts.uk.ctx.at.function.infra.entity.processexecution.KfnmtProcessExecutionPK;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

/**
 * The class Jpa process execution repository.<br>
 * Repository 更新処理自動実行
 */
@Stateless
public class JpaProcessExecutionRepository extends JpaRepository implements ProcessExecutionRepository {

	/**
	 * The query select all
	 */
	private static final String SELECT_ALL = "SELECT pe FROM KfnmtProcessExecution pe ";

	/**
	 * The query select all by company id
	 */
	private static final String SELECT_All_BY_CID = SELECT_ALL
			+ "WHERE pe.kfnmtProcExecPK.companyId = :companyId ORDER BY pe.kfnmtProcExecPK.execItemCd ASC";

	/**
	 * The query select by company id and execution item code
	 */
	private static final String SELECT_BY_CID_AND_EXEC_CD = SELECT_ALL
			+ "WHERE pe.kfnmtProcExecPK.companyId = :companyId AND pe.kfnmtProcExecPK.execItemCd = :execItemCd ";

	private static final String SELECY_BY_CID_AND_EXEC_CDS = SELECT_ALL
			+ "WHERE pe.kfnmtProcExecPK.companyId = :companyId AND pe.kfnmtProcExecPK.execItemCd IN :execItemCds";

	/**
	 * Gets process execution by company id.
	 *
	 * @param companyId the company id
	 * @return the <code>UpdateProcessAutoExecution</code> list
	 */
	@Override
	public List<UpdateProcessAutoExecution> getProcessExecutionByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_All_BY_CID, KfnmtProcessExecution.class)
								.setParameter("companyId", companyId)
								.getList(entity -> UpdateProcessAutoExecution.createFromMemento(companyId, entity));
	}

	/**
	 * Gets process execution by cid and exec cd.
	 *
	 * @param companyId  the company id
	 * @param execItemCd the exec item cd
	 * @return the <code>UpdateProcessAutoExecution</code>
	 */
	@Override
	public Optional<UpdateProcessAutoExecution> getProcessExecutionByCidAndExecCd(String companyId, String execItemCd) {
		return this.queryProxy().query(SELECT_BY_CID_AND_EXEC_CD, KfnmtProcessExecution.class)
								.setParameter("companyId", companyId)
								.setParameter("execItemCd", execItemCd)
								.getSingle(entity -> UpdateProcessAutoExecution.createFromMemento(companyId, entity));
	}

	/**
	 * Insert.
	 *
	 * @param domain the domain
	 */
	@Override
	public void insert(UpdateProcessAutoExecution domain) {
		this.commandProxy().insert(new KfnmtProcessExecution(AppContexts.user().contractCode(), domain));
	}

	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	@Override
	public void update(UpdateProcessAutoExecution domain) {
//		KfnmtProcessExecution newEntity = new KfnmtProcessExecution(AppContexts.user().contractCode(), domain);
		KfnmtProcessExecution currentEntity = this.queryProxy().find(
				new KfnmtProcessExecutionPK(AppContexts.user().companyId(), domain.getExecItemCode().v()), 
				KfnmtProcessExecution.class).get();
//			KfnmtProcessExecution entity = currentEntity.get();
//			entity.cloneFromOtherEntity(newEntity);
			domain.setMemento(currentEntity);
			this.commandProxy().update(currentEntity);
	}

	/**
	 * Remove.
	 *
	 * @param companyId  the company id
	 * @param execItemCd the exec item cd
	 */
	@Override
	public void remove(String companyId, String execItemCd) {
		KfnmtProcessExecutionPK kfnmtProcExecPK = new KfnmtProcessExecutionPK(companyId, execItemCd);
		this.commandProxy().remove(KfnmtProcessExecution.class, kfnmtProcExecPK);
	}

	/**
	 * Find by company id and exec item code list.
	 *
	 * @param cid         the company id
	 * @param execItemCds the exec item code list
	 * @return the <code>UpdateProcessAutoExecution</code> domain list
	 */
	@Override
	public List<UpdateProcessAutoExecution> findByCidAndExecItemCds(String cid, List<String> execItemCds) {
		return this.queryProxy().query(SELECY_BY_CID_AND_EXEC_CDS, KfnmtProcessExecution.class)
				.setParameter("companyId", cid)
				.setParameter("execItemCds", execItemCds)
				.getList(entity -> UpdateProcessAutoExecution.createFromMemento(cid, entity));
	}

}
