package nts.uk.ctx.at.shared.dom.worktype.service;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class HolidayAtrOutput {
	
	/**
	 * チェック結果（一致、不一致）
	 * true: 一致
	 * false: 不一致
	 */
	private boolean checkResult;
	
	/**
	 * 実績の休日区分
	 */
	private Optional<HolidayAtr> opActualHolidayAtr;
	
}
