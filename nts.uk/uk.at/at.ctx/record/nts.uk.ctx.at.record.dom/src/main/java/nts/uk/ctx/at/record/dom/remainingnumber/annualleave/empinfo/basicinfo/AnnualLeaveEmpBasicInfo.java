package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.base.PerServiceLengthTableCD;

@Getter
// domain name: 年休社員基本情報
public class AnnualLeaveEmpBasicInfo extends AggregateRoot {

	/**
	 * 社員ID
	 */
	private String employeeId;

	/**
	 * 年間所定労働日数
	 */
	private WorkingDayPerYear workingDaysPerYear;

	/**
	 * 導入前労働日数
	 */
	private WorkingDayBeforeIntro workingDayBeforeIntroduction;

	/**
	 * 付与ルール
	 */
	private AnnualLeaveGrantRule grantRule;

	public static AnnualLeaveEmpBasicInfo createFromJavaType(String employeeId, int workingDaysPerYear,
			int workingDayBeforeIntro, String grantTableCode, GeneralDate grantStandardDate) {
		AnnualLeaveEmpBasicInfo domain = new AnnualLeaveEmpBasicInfo();
		domain.employeeId = employeeId;
		domain.workingDaysPerYear = new WorkingDayPerYear(workingDaysPerYear);
		domain.workingDayBeforeIntroduction = new WorkingDayBeforeIntro(workingDayBeforeIntro);
		domain.grantRule = new AnnualLeaveGrantRule(new PerServiceLengthTableCD(grantTableCode), grantStandardDate);
		return domain;
	}

}
