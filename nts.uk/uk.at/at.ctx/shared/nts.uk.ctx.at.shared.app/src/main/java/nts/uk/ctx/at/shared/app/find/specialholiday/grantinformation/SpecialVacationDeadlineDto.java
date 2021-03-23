package nts.uk.ctx.at.shared.app.find.specialholiday.grantinformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpecialVacationDeadlineDto {

	/** 月数 */
	private Integer months;
	
	/** 年数 */
	private Integer years;
}
