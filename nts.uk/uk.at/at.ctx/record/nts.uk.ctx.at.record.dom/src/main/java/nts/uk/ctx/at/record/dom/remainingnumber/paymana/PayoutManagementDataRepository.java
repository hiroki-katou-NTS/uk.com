package nts.uk.ctx.at.record.dom.remainingnumber.paymana;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface PayoutManagementDataRepository {
	
	// ドメインモデル「振出管理データ」を取得
	List<PayoutManagementData> getSidWithCod(String cid, String sid, int state);
	
	// ドメインモデル「振出管理データ」を作成する
	void create(PayoutManagementData domain);
	
	List<PayoutManagementData> getSid(String cid, String sid);
	
	void delete(String payoutId);
	
	void update(PayoutManagementData domain);
	
	Optional<PayoutManagementData> findByID(String ID);
	// ドメイン「振休管理データ」より紐付け対象となるデータを取得する
	List<PayoutManagementData> getBySidDatePeriod(String sid, String subOfHDID, int digestionAtr);
	
	// ドメイン「振出管理データ」より指定されたデータを取得する: 消化区分　≠　未消化
	List<PayoutManagementData> getBySidDatePeriodDif(String sid, GeneralDate startDate, GeneralDate endDate, int digestionAtr);
	
	// ドメイン「振出管理データ」より指定されたデータを取得する
	List<PayoutManagementData> getBySidDatePeriodNoDigestion(String sid, GeneralDate startDate, GeneralDate endDate);
	
	
	
}
