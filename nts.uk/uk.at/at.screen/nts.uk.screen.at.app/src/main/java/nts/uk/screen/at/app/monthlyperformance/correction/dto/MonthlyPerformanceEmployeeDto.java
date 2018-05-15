/**
 * 4:21:18 PM Aug 22, 2017
 */
package nts.uk.screen.at.app.monthlyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyPerformanceEmployeeDto {
	private String id;
	private String code;
	private String businessName;
	private String workplaceName;
	private String workplaceId;
	private String depName;
	private boolean isLoginUser;
}
