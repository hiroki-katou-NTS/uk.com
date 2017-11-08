package nts.uk.ctx.at.schedule.pub.shift.businesscalendar.specificdate;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Value
@AllArgsConstructor
public class WpSpecificDateSettingExport {
	private GeneralDate date;
	private List<Integer> numberList;
}
