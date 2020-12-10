package nts.uk.screen.at.app.query.ksu.ksu002.a.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author chungnt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SystemDateDto {
	
	public int yearMonth;

	private List<PeriodsClose> periodsClose;
	
	private EmployeeInformationDto employeeInfo;
}
