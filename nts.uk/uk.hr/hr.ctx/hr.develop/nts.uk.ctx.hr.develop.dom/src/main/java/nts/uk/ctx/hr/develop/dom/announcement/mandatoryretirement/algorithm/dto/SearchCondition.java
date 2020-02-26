package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.calendar.period.DatePeriod;
@AllArgsConstructor
@Getter
public class SearchCondition {

	private String employmentCode; // 雇用コード
	
	private DatePeriod birthdayPeriod; // 誕生日期間
}
