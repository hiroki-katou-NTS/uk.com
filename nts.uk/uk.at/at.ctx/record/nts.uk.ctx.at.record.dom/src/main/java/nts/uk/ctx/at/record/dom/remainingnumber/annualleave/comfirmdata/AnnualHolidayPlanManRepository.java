package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.comfirmdata;

import java.util.List;

import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;

public interface AnnualHolidayPlanManRepository {
	/**
	 * ドメインモデル「計画年休管理データ」を取得する
	 * @param employeeid
	 * @param workTypeCd
	 * @param dateData INPUT．指定期間の開始日<=年月日<=INPUT．指定期間の終了日
	 * @return
	 */
	List<AnnualHolidayPlanMana> getDataBySidWorkTypePeriod(String employeeid, String workTypeCd, Period dateData);
}
