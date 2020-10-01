/**
 * 
 */
package nts.uk.screen.at.app.ksu001.orderemployee;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class DataAfterSortEmpDto {
	
	List<EmployeeInformationDto> lstEmp;
	List<PersonalConditionsDto> listPersonalCond;
}
