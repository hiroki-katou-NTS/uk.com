package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
/**
 * 
 * @author HungTT
 *
 */
public interface AnnualLeaveRemainHistRepository {

	public void addOrUpdate(AnnualLeaveRemainingHistory domain);
	
	public void delete(String employeeId, YearMonth ym, ClosureId closureId, ClosureDate closureDate);
	/**
	 *   年休付与残数履歴データを検索
	 * @param sid　社員ID
	 * @param ym　年月
	 * @return
	 */
	List<AnnualLeaveRemainingHistory> getInfoBySidAndYM(String sid, YearMonth ym);
	/**
	 * ドメインモデル「年休付与残数履歴データ」を取得
	 * @param sid 社員ID
	 * @param ym 年月
	 * @param closureID 締めID
	 * @param closureDate 締め日
	 * @param expStatus 期限切れ状態
	 * @param datePeriod INPUT．指定期間．開始日 <= 期限日 <= INPUT．指定期間．終了日
	 * @return
	 */
	List<AnnualLeaveRemainingHistory> getInfoByExpStatus(String sid, YearMonth ym, ClosureId closureID, ClosureDate closureDate, LeaveExpirationStatus expStatus,
			DatePeriod datePeriod);

}
