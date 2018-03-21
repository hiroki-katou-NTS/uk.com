package nts.uk.ctx.at.record.app.command.divergence.time;

import java.util.List;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeErrorCancelMethod;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeGetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeName;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeUseSet;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceType;
import nts.uk.shr.com.context.AppContexts;

@AllArgsConstructor
public class DivergenceTimeSaveCommand implements DivergenceTimeGetMemento {

	/** The divergence time no. */

	private int divergenceTimeNo;

	/** The Use classification. */

	private int divergenceTimeUseSet;

	/** The divergence time name. */

	private String divergenceTimeName;

	/** The divergence type. */

	private int divergenceType;

	/** The divergence time error cancel method. */

	private boolean reasonInput;

	/** The reason select. */
	private boolean reasonSelect;

	/** Attendance Item list. */
	private List<Double> attendanceId;

	/**
	 * Instantiates a new divergence time save command.
	 */
	public DivergenceTimeSaveCommand() {
		super();
	}

	@Override
	public DivergenceTimeUseSet getDivTimeUseSet() {
		return DivergenceTimeUseSet.valueOf(this.divergenceTimeUseSet);
	}

	@Override
	public DivergenceTimeName getDivTimeName() {
		return new DivergenceTimeName(this.divergenceTimeName);
	}

	@Override
	public DivergenceType getDivType() {
		return DivergenceType.valueOf(this.divergenceType);
	}

	@Override
	public DivergenceTimeErrorCancelMethod getErrorCancelMedthod() {
		DivergenceTimeErrorCancelMethod object = new DivergenceTimeErrorCancelMethod();

		object.setReasonInputed(this.reasonInput);
		object.setReasonSelected(this.reasonSelect);

		return object;
	}

	@Override
	public List<Double> getTargetItems() {
		return this.attendanceId;

	}

	@Override
	public Integer getDivergenceTimeNo() {
		return this.divergenceTimeNo;
	}

	@Override
	public String getCompanyId() {
		return AppContexts.user().companyId();
	}

}
