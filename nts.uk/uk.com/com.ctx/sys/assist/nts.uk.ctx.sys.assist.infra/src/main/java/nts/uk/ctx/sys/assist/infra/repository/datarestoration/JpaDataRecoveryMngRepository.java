package nts.uk.ctx.sys.assist.infra.repository.datarestoration;

import java.util.Objects;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryMngRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryOperatingCondition;
import nts.uk.ctx.sys.assist.infra.entity.datarestoration.SspttRecoveryMng;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
@Transactional(value = TxType.REQUIRES_NEW)
public class JpaDataRecoveryMngRepository extends JpaRepository implements DataRecoveryMngRepository {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Optional<DataRecoveryMng> getDataRecoveryMngById(String dataRecoveryProcessId) {
		SspttRecoveryMng  entity = this.getEntityManager().find(SspttRecoveryMng.class, dataRecoveryProcessId);
		if (Objects.isNull(entity)) {
			return Optional.empty();
		}
		return Optional.ofNullable(entity.toDomain());
	}

	@Override
	public void add(DataRecoveryMng domain) {
		this.commandProxy().insert(SspttRecoveryMng.toEntity(domain));
	}

	@Override
	public void update(DataRecoveryMng domain) {
		this.commandProxy().update(SspttRecoveryMng.toEntity(domain));
	}

	@Override
	public void remove(String dataRecoveryProcessId) {
		this.commandProxy().remove(SspttRecoveryMng.class, dataRecoveryProcessId);
	}

	@Override
	public void updateByOperatingCondition(String dataRecoveryProcessId, DataRecoveryOperatingCondition operatingCondition) {
		Optional<SspttRecoveryMng> entity = this.queryProxy().find(dataRecoveryProcessId,
				SspttRecoveryMng.class);
		entity.ifPresent(x -> {
			x.operatingCondition = operatingCondition.value;
			this.commandProxy().update(x);
		});
		em.flush();
	}

	@Override
	public void updateCategoryCnt(String dataRecoveryProcessId, int totalCategoryCnt) {
		Optional<SspttRecoveryMng> entity = this.queryProxy().find(dataRecoveryProcessId,
				SspttRecoveryMng.class);
		entity.ifPresent(x -> {
			x.categoryCnt = totalCategoryCnt;
			this.commandProxy().update(x);
		});
		em.flush();
	}

	@Override
	public void updateProcessTargetEmpCode(String dataRecoveryProcessId, String processTargetEmpCode) {
		Optional<SspttRecoveryMng> entity = this.queryProxy().find(dataRecoveryProcessId,
				SspttRecoveryMng.class);
		entity.ifPresent(x -> {
			x.processTargetEmpCode = processTargetEmpCode;
			this.commandProxy().update(x);
		});
	}

	@Override
	public Optional<DataRecoveryMng> getByUploadId(String dataRecoveryProcessId) {
		return this.queryProxy().find(dataRecoveryProcessId, SspttRecoveryMng.class)
				.map(SspttRecoveryMng::toDomain);
	}

	@Override
	public void updateRecoveryDate(String dataRecoveryProcessId, String date) {
		Optional<SspttRecoveryMng> entity = this.queryProxy().find(dataRecoveryProcessId,
				SspttRecoveryMng.class);
		entity.ifPresent(x -> {
			x.recoveryDate = StringUtil.isNullOrEmpty(date, true) ? null : date;
			this.commandProxy().update(x);
		});
	}

	@Override
	public void updateErrorCount(String dataRecoveryProcessId, int errorCount) {
		Optional<SspttRecoveryMng> entity = this.queryProxy().find(dataRecoveryProcessId,
				SspttRecoveryMng.class);
		entity.ifPresent(x -> {
			x.errorCount = errorCount;
			this.commandProxy().update(x);
		});
	}

	@Override
	public void updateSuspendedState(String dataRecoveryProcessId, NotUseAtr use) {
		Optional<SspttRecoveryMng> entity = this.queryProxy().find(dataRecoveryProcessId,
				SspttRecoveryMng.class);
		entity.ifPresent(x -> {
			x.suspendedState = use.value;
			this.commandProxy().update(x);
		});
		em.flush();
		
	}
}
