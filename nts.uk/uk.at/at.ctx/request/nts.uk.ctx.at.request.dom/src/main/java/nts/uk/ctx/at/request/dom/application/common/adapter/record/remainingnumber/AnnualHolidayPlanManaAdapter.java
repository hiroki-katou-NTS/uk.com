package nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.vacation.history.service.PeriodVactionCalInfor;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface AnnualHolidayPlanManaAdapter {
	/**
	 * ドメインモデル「計画年休管理データ」を取得する
	 * @param sid
	 * @param workTypeCode
	 * @param dateData
	 * @return
	 */
	List<AnnualHolidayPlanManaRequest> lstDataByPeriod(String sid, String workTypeCode, DatePeriod dateData);
	/**
	 * 指定する期間の計画年休使用明細を取得する
	 * @param employeeId
	 * @param worktypeCode
	 * @param dateData
	 * @return
	 */
	List<GeneralDate> lstDetailPeriod(String cid, String employeeId, String worktypeCode, PeriodVactionCalInfor getCalByDate);
}
