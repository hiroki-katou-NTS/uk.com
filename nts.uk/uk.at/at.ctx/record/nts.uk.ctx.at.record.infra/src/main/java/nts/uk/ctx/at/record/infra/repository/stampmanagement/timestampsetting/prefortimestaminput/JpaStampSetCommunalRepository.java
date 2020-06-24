package nts.uk.ctx.at.record.infra.repository.stampmanagement.timestampsetting.prefortimestaminput;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunal;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunalRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.prefortimestaminput.KrcmtStampCommunal;

/**
 * 共有打刻の打刻設定Repository
 * 
 * @author chungnt
 *
 */

@Stateless
public class JpaStampSetCommunalRepository extends JpaRepository implements StampSetCommunalRepository {

	@Override
	public void insert(StampSetCommunal domain) {
		this.commandProxy().insert(KrcmtStampCommunal.toEntity(domain));
	}

	@Override
	public void update(StampSetCommunal domain) {
		Optional<KrcmtStampCommunal> entity = this.queryProxy().find(domain.getCid(), KrcmtStampCommunal.class);
		if (!entity.isPresent()) {
			this.commandProxy().update(KrcmtStampCommunal.toEntity(domain));
		}
	}

	@Override
	public Optional<StampSetCommunal> gets(String comppanyID) {
		Optional<KrcmtStampCommunal> entity = this.queryProxy().find(comppanyID, KrcmtStampCommunal.class);
		if (!entity.isPresent()) {
			return Optional.empty();
		}
		return Optional.of(entity.get().toDomain());
	}
}
