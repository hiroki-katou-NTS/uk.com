package nts.uk.ctx.at.record.app.command.divergence.time;

import java.util.List;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeErrorCancelMethod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeGetMemento;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeUseSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceType;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DivergenceTimeSaveCommand.
 */
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
	private List<Integer> attendanceId;

	/**
	 * Instantiates a new divergence time save command.
	 */
	public DivergenceTimeSaveCommand() {
		super();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeGetMemento#getDivTimeUseSet()
	 */
	@Override
	public DivergenceTimeUseSet getDivTimeUseSet() {
		return DivergenceTimeUseSet.valueOf(this.divergenceTimeUseSet);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeGetMemento#getDivTimeName()
	 */
	@Override
	public DivergenceTimeName getDivTimeName() {
		return new DivergenceTimeName(this.divergenceTimeName);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeGetMemento#getDivType()
	 */
	@Override
	public DivergenceType getDivType() {
		return DivergenceType.valueOf(this.divergenceType);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeGetMemento#getErrorCancelMedthod()
	 */
	@Override
	public DivergenceTimeErrorCancelMethod getErrorCancelMedthod() {
		DivergenceTimeErrorCancelMethod object = new DivergenceTimeErrorCancelMethod();

		object.setReasonInputed(this.reasonInput);
		object.setReasonSelected(this.reasonSelect);

		return object;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeGetMemento#getTargetItems()
	 */
	@Override
	public List<Integer> getTargetItems() {
		return this.attendanceId;

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeGetMemento#getDivergenceTimeNo()
	 */
	@Override
	public Integer getDivergenceTimeNo() {
		return this.divergenceTimeNo;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return AppContexts.user().companyId();
	}

}
