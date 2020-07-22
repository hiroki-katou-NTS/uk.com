/**
 * 
 */
package nts.uk.screen.at.app.ksu001.start;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;
import nts.uk.screen.at.app.ksu001.eventinformationandpersonal.DataSpecDateAndHolidayDto;
import nts.uk.screen.at.app.ksu001.getinfoofInitstartup.DataScreenQueryGetInforDto;

/**
 * @author laitv
 *
 */
@Value
public class StartKSU001Dto {
	
	public DataScreenQueryGetInforDto dataStep1;
	public List<EmployeeInformationImport> dataStep2;
	public DataSpecDateAndHolidayDto dataStep3;

}
