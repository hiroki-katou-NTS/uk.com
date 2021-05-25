package nts.uk.ctx.at.shared.app.find.specialholiday.grantinformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LimitAccumulationDaysDto {

	/** 蓄積上限日数を制限する */
	private boolean limit; 
	
	/** 繰越上限日数 */
	private Integer limitCarryoverDays;
}
