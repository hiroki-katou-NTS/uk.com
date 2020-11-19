package nts.uk.ctx.at.request.app.command.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimeGetMemento;
import nts.uk.shr.com.time.TimeWithDayAttr;

@AllArgsConstructor
@NoArgsConstructor
public class DeductionTimeCommand implements DeductionTimeGetMemento{
	/** The start. */
	public Integer start;

	/** The end. */
	public Integer end;
	
	public DeductionTime toDomain() {
		return new DeductionTime(this);
	}

	@Override
	public TimeWithDayAttr getStart() {
		return new TimeWithDayAttr(this.start);
	}

	@Override
	public TimeWithDayAttr getEnd() {
		return new TimeWithDayAttr(this.end);
	}
}
