package nts.uk.ctx.at.request.dom.application.common.adapter.schedule.shift.businesscalendar.specificdate;

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
public class WpSpecificDateSettingImport {
	private GeneralDate date;
	private List<Integer> numberList;
}
