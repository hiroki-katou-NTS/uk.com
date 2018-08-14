package nts.uk.ctx.at.shared.dom.remainingnumber.paymana;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface PayoutManagementDataRepository {
	
	// ドメインモデル「振出管理データ」を取得
	List<PayoutManagementData> getSidWithCod(String cid, String sid, int state);
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
	
	
	List<PayoutManagementData> getSid(String cid, String sid);
	
	void deletePayoutSubOfHDMana(String payoutId);
	
	void delete(String payoutId);
	
	void update(PayoutManagementData domain);
	
	Optional<PayoutManagementData> findByID(String ID);
	
	Optional<PayoutManagementData> find(String cID, String sID, GeneralDate payoutDate );
	
	// ドメイン「振休管理データ」より紐付け対象となるデータを取得する
	List<PayoutManagementData> getBySidDatePeriod(String sid, String subOfHDID, int digestionAtr);
	
	// ドメイン「振出管理データ」より指定されたデータを取得する: 消化区分　≠　未消化
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
}
