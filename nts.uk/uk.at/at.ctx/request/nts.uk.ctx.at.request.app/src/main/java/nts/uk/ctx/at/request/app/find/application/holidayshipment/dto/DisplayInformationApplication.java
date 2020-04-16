package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;
@Getter
@Setter
@NoArgsConstructor
public class DisplayInformationApplication {

	private List<WorkTypeDto> workTypeList;
	
	private String selectionWorkType;
	
	private String selectionWorkTime;
	
	private Integer startTime;
	
	private Integer endTime;
	
	private Integer startTime2;
	
	private Integer endTime2;
}


