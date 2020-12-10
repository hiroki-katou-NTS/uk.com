package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.DataManagementAtr;
import nts.arc.time.calendar.period.DatePeriod;

public interface InterimRecAbasMngRepository {
	
	/**
	 * 暫定振出管理データ
	 * @param recId
	 * @return
	 */
	Optional<InterimRecMng> getReruitmentById(String recId);
	
	/**
	 * 暫定振休管理データ
	 * @param absId
	 * @return
	 */
	Optional<InterimAbsMng> getAbsById(String absId);
	
	/**
	 * ドメインモデル「暫定振出振休紐付け管理」を取得する
	 * @param interimId
	 * @param isRec: True: 振出管理データ, false: 振休管理データ
	 * @return
	 */
	List<InterimRecAbsMng> getRecOrAbsMng(String interimId, boolean isRec, DataManagementAtr mngAtr);
	
	/**
	 * ドメインモデル「暫定振出振休紐付け管理」を取得する
	 * @param List<interimId>
	 * @param isRec: True: 振出管理データ, false: 振休管理データ
	 * @return
	 */
	List<InterimRecAbsMng> getRecOrAbsMngs(List<String> interimIds, boolean isRec, DataManagementAtr mngAtr);
	
	/**
	 * ドメインモデル「暫定振出管理データ」を取得する
	 * @param recId
	 * @param unUseDays 未使用日数＞unUseDays
	 * @param dateData ・使用期限日が指定期間内
	 * ・使用期限日≧INPUT.期間.開始年月日
	 * ・使用期限日≦INPUT.期間.終了年月日
	 * @return
	 */
	List<InterimRecMng> getRecByIdPeriod(String sid, DatePeriod ymdPeriod, double unUseDays, DatePeriod dateData);
	
	/**
	 * ドメインモデル「暫定振出振休紐付け管理」を取得する
	 * @param sid
	 * @param recAtr: 振出管理データ区分
	 * @param absAtr: 振休管理データ区分 
	 * @param absId 振休ID
	 * @return
	 */
	List<InterimRecAbsMng> getBySidMng(DataManagementAtr recAtr, DataManagementAtr absAtr, String absId);
	/**
	 * ドメインモデル「暫定振出振休紐付け管理」を取得する
	 * @param recAtr 振出管理データ区分
	 * @param absAtr 振休管理データ区分 
	 * @param recId 振出ID
	 * @return
	 */
	List<InterimRecAbsMng> getRecBySidMngAtr(DataManagementAtr recAtr, DataManagementAtr absAtr, String recId);
	
	/**
	 * 暫定振出管理データ　を追加および更新
	 * @param domain
	 */
	void persistAndUpdateInterimRecMng(InterimRecMng domain);
	
	/**
	 * 暫定振休管理データ　 を追加および更新
	 * @param domain
	 */
	void persistAndUpdateInterimAbsMng(InterimAbsMng domain);
	
	/**
	 * 暫定振出振休紐付け管理 　を追加および更新
	 * @param domain
	 */
	void persistAndUpdateInterimRecAbsMng(InterimRecAbsMng domain);
	
	/**
	 * 暫定振出管理データ　を削除
	 * @param sid
	 * @param dateData
	 */
	void deleteInterimRecMng(String recruitmentMngId);

	/**
	 * 
	 * @param listRecMngId
	 */
	void deleteInterimRecMng(List<String> listRecMngId);
	
	/**
	 * 暫定振休管理データ 　を削除
	 * @param absenceMngId
	 */
	void deleteInterimAbsMng(String absenceMngId);
	
	/**
	 * 
	 * @param listAbsMngId
	 */
	void deleteInterimAbsMng(List<String> listAbsMngId);

	/**
	 * 暫定振出振休紐付け管理  を削除
	 * @param mndId
	 * @param isRec：　True：　振出、False：　振休
	 */
	void deleteInterimRecAbsMng(String mndId, boolean isRec);
	
	/**
	 * 暫定振出振休紐付け管理  を削除
	 * @param recId: 振出ID
	 * @param absId 振休ID
	 * @param recAtr 休出管理データ区分
	 * @param absAtr
	 */
	void deleteRecAbsMngByIdAndAtr(String recId, String absId, DataManagementAtr recAtr, DataManagementAtr absAtr);
	
	/**
	 * 暫定振出振休紐付け管理  を削除
	 * @param mngId
	 * @param recAtr
	 * @param isRec True：　振出、False：　振休
	 */
	void deleteRecAbsMngByIDAtr(String mngId, DataManagementAtr mngAtr, boolean isRec);
	/**
	 * ドメインモデル「暫定振出振休紐付け管理」を取得する
	 * @param recIds ・振出管理データ IN (振出管理データ.振出データID)
	 * @param recMngAtr ・振出管理データ区分
	 * @return
	 */
	List<InterimRecAbsMng> getRecByIdsMngAtr(List<String> recIds, DataManagementAtr recMngAtr);
	
	/**
	 * 
	 * @param absIds
	 * @param absMngAtr
	 * @return
	 */
	List<InterimRecAbsMng> getAbsByIdsMngAtr(List<String> absIds, DataManagementAtr absMngAtr);
	/**
	 * get 暫定振出管理データ
	 * @param sid
	 * @param period
	 * @return
	 */
	List<InterimRecMng> getRecBySidDatePeriod(String sid, DatePeriod period);
	/**
	 * 暫定振休管理データ 
	 * @param sid
	 * @param period
	 * @return
	 */
	List<InterimAbsMng> getAbsBySidDatePeriod(String sid, DatePeriod period);

	/**
	 * kdl035 ドメインモデル「暫定振出管理データ」を取得する
	 * @param mngIds
	 * @return
	 */
	List<InterimRecMng> getRecByIds(List<String> mngIds);
}
