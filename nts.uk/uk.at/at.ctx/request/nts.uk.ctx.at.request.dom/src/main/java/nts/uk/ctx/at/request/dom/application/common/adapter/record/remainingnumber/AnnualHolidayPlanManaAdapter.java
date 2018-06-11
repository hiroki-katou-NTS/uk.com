package nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;

public interface AnnualHolidayPlanManaAdapter {
	/**
	 * ドメインモデル「計画年休管理データ」を取得する
	 * @param sid
	 * @param workTypeCode
	 * @param dateData
	 * @return
	 */
	List<AnnualHolidayPlanManaRequest> lstDataByPeriod(String sid, String workTypeCode, Period dateData);
	/**
	 * 
	 * @param employeeId
	 * @param worktypeCode
	 * @param dateData
	 * @return
	 */
	List<GeneralDate> lstDetailPeriod(String employeeId, String worktypeCode, Period dateData);
}
