package nts.uk.ctx.at.function.dom.adapter.holidaysremaining;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.function.dom.adapter.periodofspecialleave.SpecialHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.reserveleave.ReservedYearHolidayImported;
import nts.uk.ctx.at.function.dom.adapter.vacation.StatusHolidayImported;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.PublicHolidayPastSituation;
import nts.uk.ctx.at.function.dom.holidaysremaining.report.SpecialVacationPastSituation;

@Getter
@AllArgsConstructor
public class HolidayRemainMerEx {

	private List<AnnualLeaveUsageImported> result255;
	private List<ReservedYearHolidayImported> result258;
	private List<StatusHolidayImported> result259;
	private List<StatusOfHolidayImported> result260;
	//private List<SpecialHolidayImported> result263;
	private List<SpecialVacationPastSituation> result263;
	// 2022.01.24 - 3S - chinh.hm  - issues #122620  - 変更 START
	private List<PublicHolidayPastSituation> result262;
	// 2022.01.24 - 3S - chinh.hm  - issues #122620  - 変更 END
}
