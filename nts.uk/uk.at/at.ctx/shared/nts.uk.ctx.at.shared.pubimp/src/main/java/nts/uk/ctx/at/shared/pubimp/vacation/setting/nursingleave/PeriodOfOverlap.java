package nts.uk.ctx.at.shared.pubimp.vacation.setting.nursingleave;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class PeriodOfOverlap {

	private GeneralDate startDateOverlap;

	private GeneralDate endDateOverlap;

}
