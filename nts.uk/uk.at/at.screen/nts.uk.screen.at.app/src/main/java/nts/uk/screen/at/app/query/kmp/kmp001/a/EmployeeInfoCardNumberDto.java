package nts.uk.screen.at.app.query.kmp.kmp001.a;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author chungnt
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeInfoCardNumberDto {
	// Infomation workplace
	private String workplaceId;
	private String workplaceName;
	
	//Infomaiton Employee
	private String pid;
	private String businessName;
	private GeneralDate entryDate;
	private int gender;
	private GeneralDate birthDay;
	private String employeeId;
	private String employeeCode;
	private GeneralDate retiredDate;
	
	//Infomation CardNumber
	private List<StampCardDto> stampCardDto;
}
