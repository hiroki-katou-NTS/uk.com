package nts.uk.ctx.at.shared.app.command.specialholiday.grantinformation;

import lombok.Data;

import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LimitAccumulationDaysCommand {

	/** 蓄積上限日数を制限する */
	private boolean limit = true;
	
	/** 繰越上限日数 */
	private Integer limitCarryoverDays;
	
}
