package nts.uk.ctx.at.shared.dom.remainingnumber.paymana;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;

public interface PayoutManagementDataRepository {
	
	// ドメインモデル「振出管理データ」を取得
	List<PayoutManagementData> getSidWithCod(String cid, String sid, int state);
	
	Map<String ,Double> getAllSidWithCod(String cid, List<String> sid, int state);
	/**
	 *  ドメインモデル「振出管理データ」を取得
	 * @param cid
	 * @param sid
	 * @param state
	 * @param ymd ・振出日<INPUT．集計開始日
	 * @return
	 */
	List<PayoutManagementData> getSidWithCodDate(String cid, String sid, int state, GeneralDate ymd);
	
	// ドメインモデル「振出管理データ」を作成する
	void create(PayoutManagementData domain);
	
	// ドメインモデル「振出管理データ」を作成する
	void addAll(List<PayoutManagementData> domains);
	
	
	List<PayoutManagementData> getSid(String cid, String sid);
	
	List<PayoutManagementData> getBySidAndStateAtr(String cid, String sid);
	
	List<PayoutManagementData> getBySidsAndCid(String cid, List<String> sid);
	
	void deletePayoutSubOfHDMana(String payoutId);
	
	void delete(String payoutId);
	
	void update(PayoutManagementData domain);
	
	Optional<PayoutManagementData> findByID(String ID);
	
	Optional<PayoutManagementData> find(String cID, String sID, GeneralDate payoutDate );
	
	List<PayoutManagementData> getByListPayoutDate(String cID, String sID, List<GeneralDate> listPayoutDate );
	
	// ドメイン「振休管理データ」より紐付け対象となるデータを取得する
	List<PayoutManagementData> getBySidDatePeriod(String sid, String subOfHDID, int digestionAtr);
	
	// 	ドメイン「振出管理データ」より指定されたデータを取得する: 消化区分　≠　未消化
	List<PayoutManagementData> getBySidStateAndInSub(String sid);
	
	// ドメイン「振出管理データ」より指定されたデータを取得する
	List<PayoutManagementData> getBySidPeriodAndInSub(String sid, GeneralDate startDate, GeneralDate endDate);
	
	List<PayoutManagementData> getDayoffDateBysubOfHDID(String subOfHDID);
	/**
	 * ドメインモデル「振出管理データ」を取得する
	 * @param sid
	 * @param dateData ・振出日が指定期間内
	 * ・振出日≧INPUT.期間.開始年月日
	 * ・振出日≦INPUT.期間.終了年月日
	 * @return
	 */
	List<PayoutManagementData> getBySidAndDatePeriod(String sid, DatePeriod dateData);
	/**
	 * ドメインモデル「振出管理データ」を取得
	 * @param sid
	 * @param stateAtr 振休消化区分 = stateAtr
	 * @return
	 */
	List<PayoutManagementData> getByStateAtr(String sid, DigestionAtr stateAtr);
	/**
	 * ドメインモデル「振出管理データ」を取得する
	 * @param sid
	 * @param dateTmp 振出日≦INPUT.期間.終了年月日
	 * @param dateData ・使用期限日≧INPUT.期間.開始年月日・使用期限日≦INPUT.期間.終了年月日
	 * OR ・消滅日≧INPUT.期間.開始年月日・消滅日≦INPUT.期間.終了年月日
	 * @param unUseDays ・未使用日数＞unUseDays	
	 * @param stateAtr 振休消化区分＝stateAtr
	 * @return
	 */
	List<PayoutManagementData> getEachPeriod(String sid, DatePeriod dateTmp, DatePeriod dateData, Double unUseDays, DigestionAtr stateAtr);
	List<PayoutManagementData> getByHoliday(String sid, Boolean unknownDate, DatePeriod dayOff);

	void deleteById(List<String> payoutId);
	/**
	 * 
	 * @param cid
	 * @param sid
	 * @param ymd (振出日<INPUT．集計開始日 OR 振出日がない)
	 * @param unUse 未使用日数 > 0
	 * @param state 振休消化区分
	 * @return
	 */
	List<PayoutManagementData> getByUnUseState(String cid, String sid, GeneralDate ymd, double unUse, DigestionAtr state);
	
	List<PayoutManagementData> getAllBySid(String sid);
	
	/**
	 * ドメインモデル「振出管理データ」を取得
	 * @param sid
	 * @param unknownDates
	 * @return
	 */
	List<PayoutManagementData> getAllByUnknownDate(String sid, List<String> unknownDates);
	
	void delete(List<PayoutManagementData> payoutManagementDatas);
	
	List<PayoutManagementData> getByListId(List<String> payoutIds);
}
