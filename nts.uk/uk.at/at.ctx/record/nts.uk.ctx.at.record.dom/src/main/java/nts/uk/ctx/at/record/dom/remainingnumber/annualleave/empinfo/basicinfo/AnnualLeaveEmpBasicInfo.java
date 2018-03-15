package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
//domain name: 年休社員基本情報
public class AnnualLeaveEmpBasicInfo extends AggregateRoot{
	
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
	
}
