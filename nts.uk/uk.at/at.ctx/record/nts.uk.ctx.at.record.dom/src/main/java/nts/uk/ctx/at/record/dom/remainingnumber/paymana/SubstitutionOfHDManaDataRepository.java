package nts.uk.ctx.at.record.dom.remainingnumber.paymana;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface SubstitutionOfHDManaDataRepository {
	
	// ドメインモデル「振休管理データ」を取得
	List<SubstitutionOfHDManagementData> getBysiDRemCod(String cid, String sid);
	
	// ドメインモデル「振休管理データ」を作成する
	void create(SubstitutionOfHDManagementData domain);
	
	List<SubstitutionOfHDManagementData> getBysiD(String cid, String sid);
	
	void delete(String subOfHDID);
	
	void delete(String sID, GeneralDate dayOff);
	
	void update(SubstitutionOfHDManagementData domain);
	
	Optional<SubstitutionOfHDManagementData> findByID(String Id);
	
	// ドメイン「振休管理データ」より紐付け対象となるデータを取得する
	List<SubstitutionOfHDManagementData> getBySidDatePeriod(String sid,String payoutID, Double remainDays);
	
	List<SubstitutionOfHDManagementData> getBySidDatePeriodNoRemainDay(String sid, GeneralDate startDate, GeneralDate endDate);
	
	List<SubstitutionOfHDManagementData> getBySidRemainDayAndInPayout(String sid);
}
