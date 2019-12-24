package test.empregulationhistory.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.uk.shr.com.history.DateHistoryItem;

@Getter
public class EmpRegulationHistListDto {

	public List<DateHistoryItemDto> dateHistoryItemDate;

	public EmpRegulationHistListDto(List<DateHistoryItem> dateHistoryItemDate) {
		super();
		this.dateHistoryItemDate = dateHistoryItemDate.stream().map(c-> new DateHistoryItemDto(c.identifier(), c.start(), c.end())).collect(Collectors.toList());
	}
	
}
