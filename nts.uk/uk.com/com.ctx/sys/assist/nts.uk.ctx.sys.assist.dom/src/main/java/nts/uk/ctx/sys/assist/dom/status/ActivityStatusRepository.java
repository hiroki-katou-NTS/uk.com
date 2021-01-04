package nts.uk.ctx.sys.assist.dom.status;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

/*
 * Repository UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.在席照会.在席のステータス
 */
public interface ActivityStatusRepository {
	/**
	 * [1] insert(在席のステータス)
	 * 
	 * @param domain 在席のステータス
	 */
	public void insert(ActivityStatus domain);

	/**
	 * [2] update(在席のステータス)
	 * 
	 * @param domain 在席のステータス
	 */
	public void update(ActivityStatus domain);

	/**
	 * [3] delete(在席のステータス)
	 * 
	 * @param domain 在席のステータス
	 */
	public void delete(ActivityStatus domain);

	/**
	 * [4]取得する
	 * 
	 * @param sids List<社員ID>
	 * @param date 年月日
	 * @return List<ActivityStatus> List<在席のステータス>
	 */
	public List<ActivityStatus> getByListSidAndDate(List<String> sids, GeneralDate date);

	/**
	 * [5]取得する
	 * 
	 * @param sid  社員ID
	 * @param date 年月日
	 * @return Optional<ActivityStatus> Optional<在席のステータス>
	 */
	public Optional<ActivityStatus> getBySidAndDate(String sid, GeneralDate date);

	/**
	 * [6]最新のステータスを取得する
	 * 
	 * @param sid  社員ID
	 * @param date 年月日
	 * @return Optional<ActivityStatus> Optional<在席のステータス>
	 */
	public Optional<ActivityStatus> getLastest(String sid, GeneralDate date);
}
