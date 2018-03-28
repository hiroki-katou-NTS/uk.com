package nts.uk.screen.at.app.kmk004.workplace.find;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.app.find.workrecord.monthcal.workplace.WkpMonthCalSetDto;
import nts.uk.ctx.at.shared.app.find.statutory.worktime.workplaceNew.WkpStatWorkTimeSetDto;

@Getter
@Setter
public class Kmk004WkpDto {

	/** The stat work time set dto. */
	private WkpStatWorkTimeSetDto statWorkTimeSetDto;

	/** The month cal set dto. */
	private WkpMonthCalSetDto monthCalSetDto;
}
