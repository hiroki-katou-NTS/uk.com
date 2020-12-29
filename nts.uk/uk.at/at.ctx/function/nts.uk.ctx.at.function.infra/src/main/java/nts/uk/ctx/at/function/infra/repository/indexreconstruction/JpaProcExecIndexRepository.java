package nts.uk.ctx.at.function.infra.repository.indexreconstruction;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.indexreconstruction.ProcExecIndex;
import nts.uk.ctx.at.function.dom.indexreconstruction.ProcExecIndexResult;
import nts.uk.ctx.at.function.dom.indexreconstruction.repository.ProcExecIndexRepository;
import nts.uk.ctx.at.function.infra.entity.processexecution.KfndtProcExecIndex;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaProcExecIndexRepository extends JpaRepository implements ProcExecIndexRepository {

	// Select all
	private static final String QUERY_SELECT_ALL = "SELECT f FROM KfndtProcExecIndex f";

	// Select one
	private static final String QUERY_SELECT_BY_ID = QUERY_SELECT_ALL
			+ " WHERE f.pk.execId = :execId AND f.pk.tableName = :tableName AND f.pk.indexName = :indexName";

	// Select one by execId
	private static final String QUERY_SELECT_BY_EXEC_ID = QUERY_SELECT_ALL + " WHERE f.pk.execId = :execId ORDER BY f.pk.tableName, f.pk.indexName ASC ";
		
	@Override
	public Optional<ProcExecIndex> findOne(String execId, String tableName, String indexName) {
		List<KfndtProcExecIndex> listEntity = this.queryProxy().query(QUERY_SELECT_BY_ID, KfndtProcExecIndex.class)
				.setParameter("execId", execId)
				.setParameter("tableName", tableName)
				.setParameter("indexName", indexName)
				.getList();
		List<ProcExecIndexResult> listResult = listEntity.stream()
				.map(ProcExecIndexResult::createFromMemento)
				.collect(Collectors.toList());
		return listEntity.isEmpty() 
				? Optional.empty() 
				: Optional.of(ProcExecIndex.builder()
						.executionId(execId)
						.indexReconstructionResult(listResult)
						.build());
	}

	@Override
	public void add(ProcExecIndex domain) {
		// Convert data to entity
		List<KfndtProcExecIndex> listEntity = JpaProcExecIndexRepository.toEntity(domain);
		// Insert entity
		this.commandProxy().insertAll(listEntity);
	}

	@Override
	public void delete(String execId, String tableName, String indexName) {
		List<KfndtProcExecIndex> listEntity = this.queryProxy().query(QUERY_SELECT_BY_ID, KfndtProcExecIndex.class)
				.setParameter("execId", execId)
				.setParameter("tableName", tableName)
				.setParameter("indexName", indexName)
				.getList();
		if (!listEntity.isEmpty()) {
			this.commandProxy().removeAll(listEntity);
		}
	}
	
	private static List<KfndtProcExecIndex> toEntity(ProcExecIndex domain) {
		String contractCd = AppContexts.user().contractCode();
		String cid = AppContexts.user().companyId();
		List<KfndtProcExecIndex> listEntity = domain.getIndexReconstructionResult().stream()
				.map(item -> {
					KfndtProcExecIndex entity = new KfndtProcExecIndex();
					item.setMemento(entity);
					entity.pk.execId = domain.getExecutionId();
					entity.companyId = cid;
					entity.contractCode = contractCd;
					return entity;
				})
				.collect(Collectors.toList());
		return listEntity;
	}

	@Override
	public Optional<ProcExecIndex> findByExecId(String execId) {
		List<ProcExecIndexResult> listResult = this.queryProxy().query(QUERY_SELECT_BY_EXEC_ID, KfndtProcExecIndex.class)
				.setParameter("execId", execId)
				.getList(ProcExecIndexResult::createFromMemento);
		return listResult.isEmpty()
				? Optional.empty()
				: Optional.of(ProcExecIndex.builder()
				.executionId(execId)
				.indexReconstructionResult(listResult)
				.build());
	}

	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	@Override
	public void update(ProcExecIndex domain) {
		// Convert data to entity
		List<KfndtProcExecIndex> listEntity = JpaProcExecIndexRepository.toEntity(domain);
		// Insert entity
		this.commandProxy().updateAll(listEntity);
	}
}
