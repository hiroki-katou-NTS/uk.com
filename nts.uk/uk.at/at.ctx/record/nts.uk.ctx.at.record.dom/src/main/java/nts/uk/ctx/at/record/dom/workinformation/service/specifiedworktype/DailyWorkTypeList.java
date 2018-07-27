package nts.uk.ctx.at.record.dom.workinformation.service.specifiedworktype;


import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailyWorkTypeList {
	
	private String employeeId;

	private List<NumberOfWorkTypeUsed> numberOfWorkTypeUsedList;
}
