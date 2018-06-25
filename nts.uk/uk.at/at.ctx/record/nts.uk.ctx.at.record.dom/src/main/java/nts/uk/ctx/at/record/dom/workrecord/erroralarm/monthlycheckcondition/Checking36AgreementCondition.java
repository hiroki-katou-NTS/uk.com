package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;

public interface Checking36AgreementCondition {
	//36協定チェック条件をチェックする
	boolean check36AgreementCondition(String companyId,String employeeId,GeneralDate date,YearMonth yearMonth, Year year,AgreementCheckCon36 agreementCheckCon36);
	//36協定特例設定を取得する
	void acquire36AgreementExceptionSetting(String companyId, String employeeId, GeneralDate criteriaDate,YearMonth yearMonth,Year year,BasicAgreementSetting basicSet);
}
