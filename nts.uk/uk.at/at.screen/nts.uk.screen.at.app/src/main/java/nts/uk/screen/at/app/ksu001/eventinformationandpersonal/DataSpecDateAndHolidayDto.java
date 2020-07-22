/**
 * 
 */
package nts.uk.screen.at.app.ksu001.eventinformationandpersonal;

import java.util.List;
import java.util.Optional;

import lombok.Value;
import nts.uk.ctx.at.schedule.dom.shift.management.DateInformation;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.DisplayControlPersonalCondition;

/**
 * @author laitv
 *
 */
@Value
public class DataSpecDateAndHolidayDto {
	List<DateInformation> listDateInfo; // ・List<Temporary「年月日情報」>
	List<PersonalConditionsDto> listPersonalConditions; // ・List<Temporary「個人条件」>
	Optional<DisplayControlPersonalCondition> optDisplayControlPersonalCond; //・Optional<個人条件の表示制御>
}
