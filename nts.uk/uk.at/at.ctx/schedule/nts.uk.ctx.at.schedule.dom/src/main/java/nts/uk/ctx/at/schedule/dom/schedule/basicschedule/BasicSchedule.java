package nts.uk.ctx.at.schedule.dom.schedule.basicschedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author sonnh1
 *
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BasicSchedule extends AggregateRoot {
	/* employee Id */
	private String sId;

	private GeneralDate date;
	/* workType Code */
	private String workTypeCode;
	/* workTime Code */
	private String workTimeCode;

	public static BasicSchedule createFromJavaType(String sId, GeneralDate date, String workTypeCode,
			String workTimeCode) {
		return new BasicSchedule(sId, date, workTypeCode, workTimeCode);
	}
}
