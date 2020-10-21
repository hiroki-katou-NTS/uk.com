package nts.uk.ctx.at.record.dom.remainingnumber.yearholiday.checkexistholidaygrant;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetPeriodFromPreviousToNextGrantDate;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
import nts.uk.shr.com.context.AppContexts;
import nts.arc.time.calendar.period.DatePeriod;
/**
 * 次回年休付与日が指定期間内に存在するかチェック
 * @author tutk
 *
 */
@Stateless
public class CheckExistHolidayGrant {
	
	@Inject
	private GetPeriodFromPreviousToNextGrantDate getPeriodFromPreviousToNextGrantDate;
	public boolean checkExistHolidayGrant(String employeeId,GeneralDate designatedDate,Period period) {
		String cid = AppContexts.user().companyId();
		//指定した年月日を基準に、前回付与日から次回付与日までの期間を取得
		Optional<DatePeriod> periodGrantDate = getPeriodFromPreviousToNextGrantDate.getPeriodYMDGrant(cid, employeeId, designatedDate, null, null) ;
		
		if(!periodGrantDate.isPresent())
			return false;
		//EA修正履歴NO.3255
		GeneralDate endDate = periodGrantDate.get().end().addDays(1);	
		//INPUT．指定期間に次回年休付与日が含まれているか確認
		if(endDate.afterOrEquals(period.getStartDate()) && endDate.beforeOrEquals(period.getEndDate())) {
			return true;
		}
		return false;
	}
}
