package nts.uk.ctx.at.record.infra.repository.stampmanagement.timestampsetting.stampinputfunctionsettings.notificationmessagesettings;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.stampinputfunctionsettings.notificationmessagesettings.NoticeSet;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.stampinputfunctionsettings.notificationmessagesettings.NoticeSetRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.stampinputfunctionsettings.notificationmessagesettings.KrcmtStampNoticeMessage;

/**
 * @author ThanhPV
 * お知らせメッセージ設定Repository
 */
@Stateless
public class NoticeSetRepositoryImpl  extends JpaRepository implements NoticeSetRepository  {

	@Override
	public void insert(NoticeSet noticeSet) {
		this.commandProxy().insert(new KrcmtStampNoticeMessage(noticeSet));
	}
	
	@Override
	public void update(NoticeSet noticeSet) {
		this.commandProxy().update(new KrcmtStampNoticeMessage(noticeSet));
	}
	
	@Override
	public Optional<NoticeSet> get(String cid) {
		Optional<KrcmtStampNoticeMessage> entity = this.queryProxy().find(cid, KrcmtStampNoticeMessage.class);
		return entity.map(c->c.toDomain());
	}
}
