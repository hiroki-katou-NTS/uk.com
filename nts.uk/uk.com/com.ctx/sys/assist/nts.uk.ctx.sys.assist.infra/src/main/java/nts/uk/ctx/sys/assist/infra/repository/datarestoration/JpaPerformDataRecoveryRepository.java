package nts.uk.ctx.sys.assist.infra.repository.datarestoration;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecovery;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecoveryRepository;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;
import nts.uk.ctx.sys.assist.infra.entity.datarestoration.SspmtPerformDataRecovery;
import nts.uk.ctx.sys.assist.infra.entity.tablelist.SspmtTableList;

@Stateless
public class JpaPerformDataRecoveryRepository extends JpaRepository implements PerformDataRecoveryRepository {
	private static final String SELECT_PERFORM_DATA_RECOVERYBY_RECOVERY_PROCESSING_ID_QUERY_STRING = "SELECT f FROM SspmtPerformDataRecovery f WHERE  f.dataRecoveryProcessId =:dataRecoveryProcessId";
	private static final String SELECT_ALL_TABLE_LIST_QUERY_STRING = "SELECT f FROM SspmtTableList f";
	private static final String SELECT_BY_RECOVERY_PROCESSING_ID_QUERY_STRING = "SELECT t FROM SspmtTableList t WHERE  t.dataRecoveryProcessId =:dataRecoveryProcessId";

	@Override
	public Optional<PerformDataRecovery> getPerformDatRecoverById(String dataRecoveryProcessId) {
		return Optional.ofNullable(
				this.getEntityManager().find(SspmtPerformDataRecovery.class, dataRecoveryProcessId).toDomain());
	}

	@Override
	public void add(PerformDataRecovery domain) {
		this.commandProxy().insert(SspmtPerformDataRecovery.toEntity(domain));
	}

	@Override
	public void update(PerformDataRecovery domain) {
		this.commandProxy().update(SspmtPerformDataRecovery.toEntity(domain));
	}

	@Override
	public void remove(String dataRecoveryProcessId) {
		this.commandProxy().remove(SspmtPerformDataRecovery.class, dataRecoveryProcessId);
	}

	@Override
	public List<TableList> getAllTableList() {
		return this.queryProxy().query(SELECT_ALL_TABLE_LIST_QUERY_STRING, SspmtTableList.class)
				.getList(c -> c.toDomain());
	}

	@Override
	public List<TableList> getByRecoveryProcessingId(String dataRecoveryProcessId) {
		return this.queryProxy().query(SELECT_BY_RECOVERY_PROCESSING_ID_QUERY_STRING, SspmtTableList.class)
				.setParameter("dataRecoveryProcessId", dataRecoveryProcessId).getList(c -> c.toDomain());
	}

	@Override
	public List<PerformDataRecovery> getPerformDataByRecoveryProcessingId(String dataRecoveryProcessId) {
		return this.queryProxy().query(SELECT_PERFORM_DATA_RECOVERYBY_RECOVERY_PROCESSING_ID_QUERY_STRING, SspmtPerformDataRecovery.class)
				.setParameter("dataRecoveryProcessId", dataRecoveryProcessId).getList(c -> c.toDomain());
	}
}
