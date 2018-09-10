/**
 * 4:21:18 PM Aug 22, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hungnm
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailyPerformanceEmployeeDto {
	private String id;
	private String code;
	private String businessName;
	private String workplaceName;
	private String workplaceId;
	private String depName;
	private boolean isLoginUser;
}
