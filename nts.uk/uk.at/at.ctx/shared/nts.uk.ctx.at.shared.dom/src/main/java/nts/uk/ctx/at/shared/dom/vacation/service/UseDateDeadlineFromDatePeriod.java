package nts.uk.ctx.at.shared.dom.vacation.service;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;

public interface UseDateDeadlineFromDatePeriod {
	/**
	 * 期限指定のある使用期限日を作成する
	 * @return
	 */
	GeneralDate useDateDeadline(String employmentCd, ExpirationTime expirationDate, GeneralDate baseDate);
}
