package nts.uk.ctx.at.record.dom.remainingnumber.paymana;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface PayoutManagementDataRepository {
	
	// ドメインモデル「振出管理データ」を取得
	List<PayoutManagementData> getSidWithCod(String cid, String sid, int state);
	
	// ドメインモデル「振出管理データ」を作成する
	void create(PayoutManagementData domain);
	
	List<PayoutManagementData> getSid(String cid, String sid);
	
	// ドメイン「振休管理データ」より紐付け対象となるデータを取得する
	List<PayoutManagementData> getBySidDatePeriod(String sid, GeneralDate startDate, GeneralDate endDate, int digestionAtr);
}
