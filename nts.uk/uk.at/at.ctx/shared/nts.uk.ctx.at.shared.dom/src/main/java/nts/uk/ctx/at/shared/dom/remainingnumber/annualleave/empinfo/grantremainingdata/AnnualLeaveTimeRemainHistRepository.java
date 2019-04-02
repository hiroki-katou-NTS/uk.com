package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author HungTT
 *
 */
public interface AnnualLeaveTimeRemainHistRepository {

	public void addOrUpdate(AnnualLeaveTimeRemainingHistory domain);
	
	public List<AnnualLeaveTimeRemainingHistory> findByCalcDateClosureDate(String employeeId, GeneralDate calculationStartDate, GeneralDate closureStartDate);
	
	public void deleteAfterDate(String employeeId, GeneralDate date);
	/**
	 * 付与時点の残数履歴データを取得 
	 * ORDER BY 付与日 DESC
	 * @param sid　社員ID
	 * @param ymd 付与処理日
	 * @return
	 */
	public List<AnnualLeaveTimeRemainingHistory> findBySid(String sid, GeneralDate ymd);
}
