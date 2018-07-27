package nts.uk.screen.at.app.dailyperformance.correction.kdw003b;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumConstant;

/**
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DailyPerformErrorReferDto {
	private String employeeIdLogin;
	private Map<String, List<EnumConstant>> mapErrCdAppTypeCd;
}
