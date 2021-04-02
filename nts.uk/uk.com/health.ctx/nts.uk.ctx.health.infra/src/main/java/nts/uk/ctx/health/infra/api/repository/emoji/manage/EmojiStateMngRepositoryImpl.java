package nts.uk.ctx.health.infra.api.repository.emoji.manage;

import java.util.Optional;
import javax.ejb.Stateless;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.health.dom.emoji.manage.EmojiStateMng;
import nts.uk.ctx.health.dom.emoji.manage.EmojiStateMngRepository;
import nts.uk.ctx.health.infra.api.entity.emoji.manage.HhlmtMoodMgt;
import nts.uk.ctx.health.infra.api.entity.emoji.manage.HhlmtMoodMgtPK;
import nts.uk.shr.com.context.AppContexts;

/*
 * Repository Implements UKDesign.ドメインモデル.NittsuSystem.UniversalK.NittsuSystem.Common (NtsCommons).ヘルスライフ.感情状態管理.感情状態管理.感情状態管理
 * 感情状態管理Repository Implements					
 */
@Stateless
public class EmojiStateMngRepositoryImpl extends JpaRepository implements EmojiStateMngRepository {

	private static HhlmtMoodMgt toEntity(EmojiStateMng domain) {
		HhlmtMoodMgt entity = new HhlmtMoodMgt();
		domain.setMemento(entity);
		return entity;
	}

	@Override
	public void insert(EmojiStateMng domain) {
		HhlmtMoodMgt entity = EmojiStateMngRepositoryImpl.toEntity(domain);
		entity.setVersion(0);
		entity.setContractCd(AppContexts.user().contractCode());
		this.commandProxy().insert(entity);

	}

	@Override
	public void update(EmojiStateMng domain) {
		HhlmtMoodMgt entity = EmojiStateMngRepositoryImpl.toEntity(domain);
		Optional<HhlmtMoodMgt> oldEntity = this.queryProxy().find(entity.getPk(), HhlmtMoodMgt.class);
		oldEntity.ifPresent(updateEntity -> {
			updateEntity.setManageEmojiState(entity.getManageEmojiState());
			updateEntity.setWearyMoodName(entity.getWearyMoodName());
			updateEntity.setSadMoodName(entity.getSadMoodName());
			updateEntity.setAverageMoodName(entity.getAverageMoodName());
			updateEntity.setGoodMoodName(entity.getGoodMoodName());
			updateEntity.setHappyMoodName(entity.getHappyMoodName());
			this.commandProxy().update(updateEntity);
		});
	}

	@Override
	public Optional<EmojiStateMng> getByCid(String cid) {
		return this.queryProxy().find(new HhlmtMoodMgtPK(cid), HhlmtMoodMgt.class)
				.map(EmojiStateMng::createFromMemento);
	}
}
