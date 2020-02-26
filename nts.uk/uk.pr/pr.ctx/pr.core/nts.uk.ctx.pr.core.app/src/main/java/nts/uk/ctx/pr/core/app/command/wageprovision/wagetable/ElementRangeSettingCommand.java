package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.error.BusinessException;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementRangeSetting;

/**
 * 要素範囲設定
 */
@Data
@NoArgsConstructor
public class ElementRangeSettingCommand {

	/**
	 * 第一要素範囲
	 */
	private ElementRangeCommand firstElementRange;

	/**
	 * 第二要素範囲
	 */
	private ElementRangeCommand secondElementRange;

	/**
	 * 第三要素範囲
	 */
	private ElementRangeCommand thirdElementRange;

	/**
	 * 履歴ID
	 */
	private String historyID;

	public ElementRangeSetting fromCommandToDomain() {
		if (firstElementRange.getRangeLowerLimit() != null && firstElementRange.getRangeUpperLimit() != null
				&& firstElementRange.getRangeUpperLimit().compareTo(firstElementRange.getRangeLowerLimit()) < 0)
			throw new BusinessException("MsgQ_153");
		if (secondElementRange != null && secondElementRange.getRangeLowerLimit() != null
				&& secondElementRange.getRangeUpperLimit() != null
				&& secondElementRange.getRangeUpperLimit().compareTo(secondElementRange.getRangeLowerLimit()) < 0)
			throw new BusinessException("MsgQ_154");
		if (thirdElementRange != null && thirdElementRange.getRangeLowerLimit() != null
				&& thirdElementRange.getRangeUpperLimit() != null
				&& thirdElementRange.getRangeUpperLimit().compareTo(thirdElementRange.getRangeLowerLimit()) < 0)
			throw new BusinessException("MsgQ_155");
		return new ElementRangeSetting(firstElementRange.fromCommandToDomain(),
				secondElementRange == null ? null : secondElementRange.fromCommandToDomain(),
				thirdElementRange == null ? null : thirdElementRange.fromCommandToDomain(), historyID);
	}

}
