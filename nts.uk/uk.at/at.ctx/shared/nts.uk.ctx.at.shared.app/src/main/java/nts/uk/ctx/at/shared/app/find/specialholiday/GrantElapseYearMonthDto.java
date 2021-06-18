package nts.uk.ctx.at.shared.app.find.specialholiday;

import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GrantElapseYearMonthDto {

	/** 付与回数 */
	private int elapseNo;
	
	/** 付与日数 */
	private Integer grantedDays;
	
}
