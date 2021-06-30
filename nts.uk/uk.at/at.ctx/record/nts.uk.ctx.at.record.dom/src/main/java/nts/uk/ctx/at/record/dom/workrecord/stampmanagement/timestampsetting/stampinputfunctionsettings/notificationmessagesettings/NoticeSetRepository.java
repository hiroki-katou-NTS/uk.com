/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.stampinputfunctionsettings.notificationmessagesettings;

import java.util.Optional;

/**
 * @author ThanhPV
 * お知らせメッセージ設定Repository
 */
public interface NoticeSetRepository {

	/**
	 * 	[1] insert(お知らせメッセージ設定)
	 */
	public void insert(NoticeSet noticeSet);

	/**
	 *	[2] update(お知らせメッセージ設定)
	 */
	public void update(NoticeSet noticeSet);
	
	/**
	 * 	[3] get
	 */
	public Optional<NoticeSet> get(String cid);

}
