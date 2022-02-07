package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.arc.time.calendar.period.DatePeriod;

public interface SpecialLeaveGrantRepository {

	List<SpecialLeaveGrantRemainingData> getAll(String employeeId, int specialCode);

	List<SpecialLeaveGrantRemainingData> getAllByExpStatus(String employeeId, int specialCode, int expirationStatus);
	
	void add(String cid, SpecialLeaveGrantRemainingData data);
	
	/**
	 * @author lanlt
	 * @param domains
	 */
	void addAll(String cid, List<SpecialLeaveGrantRemainingData> domains);

	void update(SpecialLeaveGrantRemainingData data);
	
	void updateWithGrantDate(SpecialLeaveGrantRemainingData data);

	void delete(String specialid);
	
	/** 当月以降の管理データを削除 */
	void deleteAfter(String sid, int specialCode, GeneralDate target);

	Optional<SpecialLeaveGrantRemainingData> getBySpecialId(String specialId);
	/**
	 * 特別休暇付与残数データ
	 * @param sid ・社員ID=INPUT．社員ID
	 * @param specialLeaveCode ・特別休暇コード=INPUT．特別休暇コード
	 * @param expirationStatus ・期限切れ状態＝”使用可能”
	 * @param grantDate ・付与日<=集計終了日．AddDays(1) 
	 * @param deadlineDate ・集計開始日<=期限日
	 * @return
	 */
	List<SpecialLeaveGrantRemainingData> getByPeriodStatus(String sid, int specialLeaveCode, LeaveExpirationStatus expirationStatus,
			GeneralDate grantDate, GeneralDate deadlineDate);
	boolean isHasData(String sid, String specialID,GeneralDate grantDate, int specialLeaCode);
	
	/**
	 * 
	 * @param employeeId
	 * @param specialCode
	 * @return
	 */
	List<SpecialLeaveGrantRemainingData> getAllByExpStatus(String cid, List<String> sids , int specialCode, int expirationStatus);

	/**
	 * @param listEmpID
	 * @param specialLeaveCD
	 * @return
	 */
	List<SpecialLeaveGrantRemainingData> getAllByListEmpID(List<String> listEmpID, int specialLeaveCD);

	/**
	 * 特別休暇付与残数データ
	 * @param sid ・社員ID=INPUT．社員ID
	 * @param speCode   ・特別休暇コード=INPUT．特別休暇コード
	 * @param datePriod ・集計開始日 < 付与日 <= INPUT．集計終了日 
	 * @param startDate ・集計開始日 <= 期限日
	 * @param expirationStatus ・期限切れ状態 ＝ ”使用可能”
	 * @return 
	 */
	List<SpecialLeaveGrantRemainingData> getByNextDate(String sid, int speCode, DatePeriod datePriod, GeneralDate startDate, LeaveExpirationStatus expirationStatus);
	
	/**
	 * for cps013
	 * @param listEmpID
	 * @param specialLeaveCD
	 * @return
	 */
	List<Object[]> getAllBySids(List<String> sids, int specialLeaveCD);

}
