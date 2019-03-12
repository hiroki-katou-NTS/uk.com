package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata;

import java.util.List;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

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

}
