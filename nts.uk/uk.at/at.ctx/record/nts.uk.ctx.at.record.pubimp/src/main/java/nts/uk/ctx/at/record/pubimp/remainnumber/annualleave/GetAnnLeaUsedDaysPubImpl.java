package nts.uk.ctx.at.record.pubimp.remainnumber.annualleave;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnLeaUsedDays;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.GetAnnLeaUsedDaysPub;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.ReferenceAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.SpecDateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 実装：年休使用日数を取得
 * @author shuichi_ishida
 */
@Stateless
public class GetAnnLeaUsedDaysPubImpl implements GetAnnLeaUsedDaysPub {

	/** 年休使用日数を取得 */
	@Inject
	private GetAnnLeaUsedDays getAnnLeaUsedDays;
	
	/** 社員の前回付与日から次回付与日までの年休使用日数を取得 */
	@Override
	public Optional<AnnualLeaveUsedDayNumber> ofGrantPeriod(String employeeId, GeneralDate criteria,
			ReferenceAtr referenceAtr, boolean fixedOneYear, SpecDateAtr specDateAtr) {
		return this.getAnnLeaUsedDays.ofGrantPeriod(employeeId, criteria, referenceAtr, fixedOneYear, specDateAtr);
	}
	
	/** 指定した期間の年休使用数を取得する */
	@Override
	public Optional<AnnualLeaveUsedDayNumber> ofPeriod(String employeeId, DatePeriod period,
			ReferenceAtr referenceAtr) {
		return this.getAnnLeaUsedDays.ofPeriod(employeeId, period, referenceAtr);
	}
}
