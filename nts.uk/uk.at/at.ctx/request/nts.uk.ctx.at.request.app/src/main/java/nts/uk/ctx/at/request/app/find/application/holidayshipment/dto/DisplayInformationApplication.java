package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.time.TimeWithDayAttr;
@Getter
@Setter
@NoArgsConstructor
public class DisplayInformationApplication {

	private List<WorkType> workTypeList;
	
	private Optional<WorkTypeCode> selectionWorkType;
	
	private Optional<WorkTimeCode> selectionWorkTime;
	
	private Optional<TimeWithDayAttr> startTime;
	
	private Optional<TimeWithDayAttr> endTime;
	
	private Optional<TimeWithDayAttr> startTime2;
	
	private Optional<TimeWithDayAttr> endTime2;
}


