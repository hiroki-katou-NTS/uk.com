package nts.uk.ctx.at.function.dom.executionstatusmanage.optionalperiodprocess;

import nts.arc.time.GeneralDate;

/**
 * The Interface AggrPeriodExcutionAdapter.
 */
public interface AggrPeriodExcutionAdapter {

	/**
	 * Adds the excution.
	 *
	 * @param excution the excution
	 */
	public void addExcution(AggrPeriodExcutionImport excution);

	public void addExcution(AggrPeriodExcutionImport excution, String aggrFrameName, GeneralDate startYmd, GeneralDate endYmd);
}
