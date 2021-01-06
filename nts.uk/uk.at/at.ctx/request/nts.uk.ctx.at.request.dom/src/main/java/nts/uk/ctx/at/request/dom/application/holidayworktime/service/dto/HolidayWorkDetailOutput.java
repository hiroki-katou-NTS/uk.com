package nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.PreActualColorResult;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork_Old;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;

@NoArgsConstructor
@Getter
@Setter
public class HolidayWorkDetailOutput {
	
	private AppHdWorkDispInfoOutput_Old appHdWorkDispInfoOutput;
	
	private AppHolidayWork_Old appHolidayWork;
	
	private List<CaculationTime> caculationTimes;
	
	private PreActualColorResult preActualColorResult;
	
}
