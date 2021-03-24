package nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetcom;

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
public class DeleteMonthlyWorkTimeSetComInput {

	public int year;
	
	public int workType;
}
