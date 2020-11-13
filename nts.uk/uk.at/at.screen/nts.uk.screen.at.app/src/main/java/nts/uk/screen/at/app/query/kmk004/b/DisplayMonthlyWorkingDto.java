package nts.uk.screen.at.app.query.kmk004.b;



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
public class DisplayMonthlyWorkingDto {

	private String month;
	private Integer legalLaborTime;
	private Integer withinLaborTime;
	private Integer weekAvgTime;
}
