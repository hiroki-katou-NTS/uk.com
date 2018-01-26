package nts.uk.ctx.at.function.dom.processexecution.personalschedule;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 個人スケジュール作成期間
 */
@Getter
@AllArgsConstructor
public class PersonalScheduleCreationPeriod {
	/* 作成期間 */
	private CreationPeriod creationPeriod;
	
	/* 対象日 */
	private TargetDate targetDate;
	
	/* 対象月 */
	private TargetMonth targetMonth;
}
