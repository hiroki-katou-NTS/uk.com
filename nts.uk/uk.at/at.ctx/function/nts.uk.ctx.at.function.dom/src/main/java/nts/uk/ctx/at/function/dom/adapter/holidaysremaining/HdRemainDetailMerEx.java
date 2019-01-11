package nts.uk.ctx.at.function.dom.adapter.holidaysremaining;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.reserveleave.ReserveHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.reserveleave.RsvLeaUsedCurrentMonImported;
import nts.uk.ctx.at.function.dom.adapter.vacation.CurrentHolidayImported;

@Getter
@AllArgsConstructor
public class HdRemainDetailMerEx {

	private AnnLeaveOfThisMonthImported result265;
	private ReserveHolidayImported result268;
	private List<CurrentHolidayImported> result269;
	private List<AnnLeaveUsageStatusOfThisMonthImported> result363;
	private List<RsvLeaUsedCurrentMonImported> result364;
	private Optional<GeneralDate> result369;
}
