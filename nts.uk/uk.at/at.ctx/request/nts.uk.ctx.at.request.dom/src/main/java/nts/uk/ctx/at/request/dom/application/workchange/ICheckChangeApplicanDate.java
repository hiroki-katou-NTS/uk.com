package nts.uk.ctx.at.request.dom.application.workchange;

import nts.arc.time.GeneralDate;
/**
 * 申請日を変更する
 */
public interface ICheckChangeApplicanDate {
	void CheckChangeApplicationDate(GeneralDate startDate, GeneralDate endDate);
}
