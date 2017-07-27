package nts.uk.ctx.at.schedule.dom.schedule.basicschedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.worktime.SiftCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * 
 * @author sonnh1
 *
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BasicSchedule {
	/* employee Id */
	private String sId;

	private GeneralDate date;

	private WorkTypeCode workTypeCd;

	private SiftCode workTimeCd;

	public static BasicSchedule createFromJavaType(String sId, GeneralDate date, String workTypeCd, String workTimeCd) {
		return new BasicSchedule(sId, date, new WorkTypeCode(workTypeCd), new SiftCode(workTimeCd));
	}
}
