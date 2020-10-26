package nts.uk.ctx.at.shared.dom.specialholiday.periodinformation;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.MonthDay;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.RegularGrantDays;

/**
 * 蓄積上限日数
 * @author masaaki_jinno
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LimitAccumulationDays {

	/** 蓄積上限日数を制限する */
	private boolean limit = true;

	/** 繰越上限日数 */
	private Optional<LimitCarryoverDays> limitAccumulationDays;

}
