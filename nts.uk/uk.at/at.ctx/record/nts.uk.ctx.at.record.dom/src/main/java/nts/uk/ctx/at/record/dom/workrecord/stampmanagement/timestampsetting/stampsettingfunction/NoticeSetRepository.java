package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.stampsettingfunction;

import java.util.Optional;

/**
 * お知らせメッセージ設定Repository
 * 
 * @author tutt
 *
 */
public interface NoticeSetRepository {

	/**
	 * 	[1]  insert(お知らせメッセージ設定)
	 * @param domain
	 */
	public void insert(NoticeSet domain);

	/**
	 * 	[2]  save(お知らせメッセージ設定)
	 * @param domain
	 */
	public void save(NoticeSet domain);

	/**
	 * 	[3]  get(会社ID)
	 * @param cid
	 * @return
	 */
	public Optional<NoticeSet> get(String cid);
}
