package nts.uk.ctx.at.function.dom.processexecution.personalschedule;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.time.calendar.MonthDay;


/**
 * 個人スケジュール作成期間
 */
@Getter

public class PersonalScheduleCreationPeriod extends DomainObject {
	/* 作成期間 */
	private CreationPeriod creationPeriod;
	
	/* 対象日 */
	private TargetDate targetDate;
	
	/* 対象月 */
	private TargetMonth targetMonth;
	
	/* 指定年 */
	private Optional<CreateScheduleYear> designatedYear;
	
	/* 指定開始月日 */
	private Optional<MonthDay> startMonthDay;
	
	/* 指定終了月日*/
	private Optional<MonthDay> endMonthDay;

	public PersonalScheduleCreationPeriod(CreationPeriod creationPeriod, TargetDate targetDate, TargetMonth targetMonth,
			CreateScheduleYear designatedYear, MonthDay startMonthDay,
			MonthDay endMonthDay) {
		super();
		this.creationPeriod = creationPeriod;
		this.targetDate = targetDate;
		this.targetMonth = targetMonth;
		this.designatedYear = Optional.ofNullable(designatedYear);
		this.startMonthDay = Optional.ofNullable(startMonthDay);
		this.endMonthDay = Optional.ofNullable(endMonthDay);
	}
	
	
}
