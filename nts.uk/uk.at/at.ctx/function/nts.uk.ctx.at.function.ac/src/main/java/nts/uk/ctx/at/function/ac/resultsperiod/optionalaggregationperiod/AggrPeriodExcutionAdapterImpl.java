package nts.uk.ctx.at.function.ac.resultsperiod.optionalaggregationperiod;

import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcutionAdapter;
import nts.uk.ctx.at.function.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcutionImport;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcution;
import nts.uk.ctx.at.record.pub.executionstatusmanage.optionalperiodprocess.AggrPeriodExcutionPub;

/**
 * The Class AggrPeriodExcutionAdapterImpl.
 */
public class AggrPeriodExcutionAdapterImpl implements AggrPeriodExcutionAdapter {

	/** The pub. */
	@Inject
	private AggrPeriodExcutionPub pub;

	/**
	 * Adds the excution.
	 *
	 * @param excution the excution
	 */
	@Override
	public void addExcution(AggrPeriodExcutionImport excution) {
		this.pub.addExcution(AggrPeriodExcution.createFromJavaType(
				excution.getCompanyId(), 
				excution.getExecutionEmpId(), 
				excution.getAggrFrameCode(), 
				excution.getAggrId(), 
				excution.getStartDateTime(), 
				excution.getEndDateTime(), 
				excution.getExecutionAtr(), 
				excution.getExecutionAtr(), 
				excution.getPresenceOfError())
		);
	}

}
