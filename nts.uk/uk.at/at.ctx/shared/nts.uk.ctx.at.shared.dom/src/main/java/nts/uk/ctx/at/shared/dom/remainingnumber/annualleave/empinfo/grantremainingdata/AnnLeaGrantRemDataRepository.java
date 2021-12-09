package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.arc.time.calendar.period.DatePeriod;

public interface AnnLeaGrantRemDataRepository {
	
	List<AnnualLeaveGrantRemainingData> find(String employeeId);
	
	Optional<AnnualLeaveGrantRemainingData> getLast(String employeeId);
	
	Optional<AnnualLeaveGrantRemainingData> findByID(String id);
	
	List<AnnualLeaveGrantRemainingData> find(String employeeId, GeneralDate grantDate);
	
	List<AnnualLeaveGrantRemainingData> findByPeriod(String employeeId, GeneralDate startDate, GeneralDate endDate);
	
	List<AnnualLeaveGrantRemainingData> findByGrantDateAndDeadline(String employeeId, GeneralDate grantDate, GeneralDate deadline);
	
	List<AnnualLeaveGrantRemainingData> findByCheckState(String employeeId, int checkState);
	
	List<AnnualLeaveGrantRemainingData> findNotExp(String employeeId);
	
	void add(String cid, AnnualLeaveGrantRemainingData data);
	
	void update(AnnualLeaveGrantRemainingData data);
	
	void updateWithGrantDate(AnnualLeaveGrantRemainingData data);
	
	void delete(String employeeID, GeneralDate grantDate);
	
	void deleteAfterDate(String employeeId, GeneralDate date);
	
	void delete(String annaLeavID);
	
	List<AnnualLeaveGrantRemainingData> checkConditionUniqueForAdd(String employeeId, GeneralDate grantDate);
	
	/**
	 * @author lanlt
	 * dùng check điều kiện cho nhiều nhân viên
	 * @param emp
	 * @return
	 */
	Map<String, List<AnnualLeaveGrantRemainingData>> checkConditionUniqueForAdd(String cid, Map<String, GeneralDate> emp);
	
	List<AnnualLeaveGrantRemainingData> checkConditionUniqueForUpdate(String employeeId, String annLeavID, GeneralDate grantDate);
	// get list annual leave grant remaining data by startDate <= grantDate <= endDate
	List<AnnualLeaveGrantRemainingData> findInDate(String employeeId, GeneralDate startDate, GeneralDate endDate);
	
	/**
	 * @author lanlt
	 * @param cid
	 * @param employeeId
	 * @return
	 */
	List<AnnualLeaveGrantRemainingData> findByCidAndSids(String cid, List<String> sids);
	
	/**
	 * @author lanlt
	 * @param domains
	 */
	void addAll(String cid, List<AnnualLeaveGrantRemainingData> domains);

	Map<String, List<AnnualLeaveGrantRemainingData>> findInDate(List<String> employeeId, GeneralDate startDate, GeneralDate endDate);


	List<AnnualLeaveGrantRemainingData> findBySidAndDate(String employeeId, GeneralDate grantDate);
	/**
	 * ドメインモデル「年休付与残数データ」を取得	
	 * @param sid 社員ID
	 * @param expStatus 期限切れ状態
	 * @param datePeriod INPUT．指定期間．開始日 <= 期限日 <= INPUT．指定期間．終了日
	 * @return
	 */
	List<AnnualLeaveGrantRemainingData> findByExpStatus(String sid, LeaveExpirationStatus expStatus, DatePeriod datePeriod);
}
