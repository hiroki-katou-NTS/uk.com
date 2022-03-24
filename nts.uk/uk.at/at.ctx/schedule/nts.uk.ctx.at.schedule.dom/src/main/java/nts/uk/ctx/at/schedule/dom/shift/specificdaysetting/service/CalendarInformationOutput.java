package nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.service;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
/**
 * カレンダー情報
 * @author Doan Duy Hung
 *
 */
@Value
@AllArgsConstructor
public class CalendarInformationOutput {
	private String workTypeCD;
	private String siftCD;
	private GeneralDate date;
}
