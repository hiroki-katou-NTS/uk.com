package nts.uk.ctx.sys.assist.infra.repository.datarestoration;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryOperatingCondition;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryResult;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryResultRepository;
import nts.uk.ctx.sys.assist.infra.entity.datarestoration.SspmtDataRecoverResult;

@Stateless
public class JpaDataRecoverResultRepository extends JpaRepository implements DataRecoveryResultRepository {
	
	private static final String UPDATE_BY_DATARECOVERYPROCESSID = "UPDATE SspmtDataRecoverResult t SET t.executionResult =:executionResult, t.endDateTime =:endDateTime WHERE t.dataRecoveryProcessId =:dataRecoveryProcessId";
	
	@Override
	public Optional<DataRecoveryResult> getDataRecoverResultById(String dataRecoveryProcessId) {
		return Optional.ofNullable(
				this.getEntityManager().find(SspmtDataRecoverResult.class, dataRecoveryProcessId).toDomain());
	}

	@Override
	public void add(DataRecoveryResult domain) {
		this.commandProxy().insert(SspmtDataRecoverResult.toEntity(domain));
	}

	@Override
	public void update(DataRecoveryResult domain) {
		this.commandProxy().update(SspmtDataRecoverResult.toEntity(domain));
	}

	@Override
	public void remove(String dataRecoveryProcessId) {
		this.commandProxy().remove(SspmtDataRecoverResult.class, dataRecoveryProcessId);
	}

	@Override
	@Transactional(value = TxType.REQUIRES_NEW)
	public void updateEndDateTimeExecutionResult(String dataRecoveryProcessId,
			DataRecoveryOperatingCondition dataRecoveryOperatingCondition) {
		
		this.getEntityManager().createQuery(UPDATE_BY_DATARECOVERYPROCESSID, SspmtDataRecoverResult.class)
		.setParameter("executionResult",EnumAdaptor.convertToValueName(dataRecoveryOperatingCondition).getLocalizedName())
		.setParameter("endDateTime", GeneralDateTime.now())
		.setParameter("dataRecoveryProcessId", dataRecoveryProcessId).executeUpdate();
		
	}
}
