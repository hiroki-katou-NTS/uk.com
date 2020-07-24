/**
 * 
 */
package nts.uk.screen.at.app.ksu001.start;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.screen.at.app.ksu001.eventinformationandpersonal.DateInformationDto;
import nts.uk.screen.at.app.ksu001.eventinformationandpersonal.DisplayControlPersonalCondDto;
import nts.uk.screen.at.app.ksu001.eventinformationandpersonal.PersonalConditionsDto;
import nts.uk.screen.at.app.ksu001.extracttargetemployees.EmployeeInformationDto;

/**
 * @author laitv
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StartKSU001Dto {
	
	// data tra ve cua step1
	public DataBasicDto dataBasicDto;
	
	// data tra ve cua step2
	public List<EmployeeInformationDto> listEmpInfo;
	
	// data tra ve cua step3
	public List<DateInformationDto> listDateInfo;
	public List<PersonalConditionsDto> listPersonalConditions; 
	public DisplayControlPersonalCondDto displayControlPersonalCond;
	 
}
