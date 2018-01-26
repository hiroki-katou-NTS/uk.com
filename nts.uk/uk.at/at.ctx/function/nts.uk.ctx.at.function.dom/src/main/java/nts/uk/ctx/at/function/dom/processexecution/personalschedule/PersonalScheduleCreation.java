package nts.uk.ctx.at.function.dom.processexecution.personalschedule;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 個人スケジュール作成
 */
@Getter
@AllArgsConstructor
public class PersonalScheduleCreation {
	/* 作成期間 */
	private PersonalScheduleCreationPeriod period;
	
	/* 個人スケジュール作成区分 */
	private boolean perSchedule;
	
	/* 対象社員 */
	private PersonalScheduleCreationTarget target;
}
