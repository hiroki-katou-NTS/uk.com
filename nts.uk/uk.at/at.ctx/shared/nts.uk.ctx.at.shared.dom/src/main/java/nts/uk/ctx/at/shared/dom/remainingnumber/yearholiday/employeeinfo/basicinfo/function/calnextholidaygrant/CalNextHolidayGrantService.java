package nts.uk.ctx.at.shared.dom.remainingnumber.yearholiday.employeeinfo.basicinfo.function.calnextholidaygrant;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.NextAnnualLeaveGrant;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
/**
 * 次回年休付与を計算(期間の開始日を含む)
 * @author tutk
 *
 */
public interface CalNextHolidayGrantService {
	public List<NextAnnualLeaveGrant> getCalNextHolidayGrant(String companyId,String employeeId,Optional<Period> period);
}
