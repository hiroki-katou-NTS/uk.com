package test.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.shr.com.history.DateHistoryItem;

@AllArgsConstructor
@Getter
public class EmpRegulationHistListDto {

	public List<DateHistoryItem> dateHistoryItemDate;
}
