package nts.uk.ctx.at.record.infra.repository.stampmanagement.timestampsetting.stampsettingfunction;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.stampsettingfunction.NoticeSet;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.stampsettingfunction.NoticeSetRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.stampsettingfunction.KrcmtStampNoticeMessage;

/**
 * お知らせメッセージ設定Repository
 * 
 * @author tutt
 *
 */
@Stateless
public class JpaNoticeSetRepository extends JpaRepository implements NoticeSetRepository {

	@Override
	public void insert(NoticeSet domain) {
		this.commandProxy().insert(KrcmtStampNoticeMessage.toEntity(domain));
	}

	@Override
	public void save(NoticeSet domain) {
		this.getEntityManager().merge(KrcmtStampNoticeMessage.toEntity(domain));
	}

	@Override
	public Optional<NoticeSet> get(String cid) {
		Optional<KrcmtStampNoticeMessage> entityOpt = this.queryProxy().find(cid, KrcmtStampNoticeMessage.class);

		if (!entityOpt.isPresent()) {
			return Optional.empty();
		}
		
		return Optional.of(entityOpt.get().toDomain());
	}

}
