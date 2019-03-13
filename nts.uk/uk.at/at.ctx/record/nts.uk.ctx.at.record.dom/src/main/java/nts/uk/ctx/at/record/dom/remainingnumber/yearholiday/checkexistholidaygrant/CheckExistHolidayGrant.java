package nts.uk.ctx.at.record.dom.remainingnumber.yearholiday.checkexistholidaygrant;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.periodnextgrantdate.PeriodNextGrantDate;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
/**
 * 次回年休付与日が指定期間内に存在するかチェック
 * @author tutk
 *
 */
@Stateless
public class CheckExistHolidayGrant {
	@Inject
	private PeriodNextGrantDate periodNextGrantDate;
	public boolean checkExistHolidayGrant(String employeeId,GeneralDate designatedDate,Period period) {
		//指定した年月日を基準に、前回付与日から次回付与日までの期間を取得
		Optional<Period> periodGrantDate = periodNextGrantDate.getPeriodNextGrantDate(employeeId, designatedDate);
		if(!periodGrantDate.isPresent())
			return false;
		//INPUT．指定期間に次回年休付与日が含まれているか確認
		if(periodGrantDate.get().getEndDate().afterOrEquals(period.getStartDate()) && periodGrantDate.get().getEndDate().beforeOrEquals(period.getEndDate())) {
			return true;
		}
		return false;
	}
}
