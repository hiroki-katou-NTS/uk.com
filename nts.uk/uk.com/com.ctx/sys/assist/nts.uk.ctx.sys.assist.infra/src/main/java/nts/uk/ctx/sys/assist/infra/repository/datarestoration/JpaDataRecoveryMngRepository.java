package nts.uk.ctx.sys.assist.infra.repository.datarestoration;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryMngRepository;
import nts.uk.ctx.sys.assist.infra.entity.datarestoration.SspmtDataRecoveryMng;

@Stateless
public class JpaDataRecoveryMngRepository extends JpaRepository implements DataRecoveryMngRepository {

	@Override
	public Optional<DataRecoveryMng> getDataRecoveryMngById(String dataRecoveryProcessId) {
		return Optional
				.ofNullable(this.getEntityManager().find(SspmtDataRecoveryMng.class, dataRecoveryProcessId).toDomain());
	}

	@Override
	public void add(DataRecoveryMng domain) {
		this.commandProxy().insert(SspmtDataRecoveryMng.toEntity(domain));
	}

	@Override
	public void update(DataRecoveryMng domain) {
		this.commandProxy().update(SspmtDataRecoveryMng.toEntity(domain));
	}

	@Override
	public void remove(String dataRecoveryProcessId) {
		this.commandProxy().remove(SspmtDataRecoveryMng.class, dataRecoveryProcessId);
	}

	@Override
	public void updateByOperatingCondition(String dataRecoveryProcessId, int operatingCondition) {
		Optional<SspmtDataRecoveryMng> entity = this.queryProxy().find(dataRecoveryProcessId,
				SspmtDataRecoveryMng.class);
		entity.ifPresent(x -> {
			x.operatingCondition = operatingCondition;
			this.commandProxy().update(x);
		});
	}

	@Override
	public void updateTotalNumOfProcesses(String dataRecoveryProcessId, int totalNumOfProcesses) {
		Optional<SspmtDataRecoveryMng> entity = this.queryProxy().find(dataRecoveryProcessId,
				SspmtDataRecoveryMng.class);
		entity.ifPresent(x -> {
			x.totalNumOfProcesses = totalNumOfProcesses;
			this.commandProxy().update(x);
		});
	}

	@Override
	public void updateProcessTargetEmpCode(String dataRecoveryProcessId, String processTargetEmpCode) {
		Optional<SspmtDataRecoveryMng> entity = this.queryProxy().find(dataRecoveryProcessId,
				SspmtDataRecoveryMng.class);
		entity.ifPresent(x -> {
			x.processTargetEmpCode = processTargetEmpCode;
			this.commandProxy().update(x);
		});
	}

	@Override
	public Optional<DataRecoveryMng> getByUploadId(String dataRecoveryProcessId) {
		return this.queryProxy().find(dataRecoveryProcessId, SspmtDataRecoveryMng.class)
				.map(SspmtDataRecoveryMng::toDomain);
	}

	@Override
	public void updateRecoveryDate(String dataRecoveryProcessId, String date) {
		Optional<SspmtDataRecoveryMng> entity = this.queryProxy().find(dataRecoveryProcessId,
				SspmtDataRecoveryMng.class);
		entity.ifPresent(x -> {
			x.recoveryDate = date;
			this.commandProxy().update(x);
		});
	}
}
