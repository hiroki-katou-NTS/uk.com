package nts.uk.cnv.dom.td.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;

@AllArgsConstructor
@Getter
public class AddedResultDto {
	String eventId;
	List<AlterationSummary> summaries;

	
	public static AddedResultDto success(String eventId) {
		return new AddedResultDto(eventId, new ArrayList<>());
	}
	
	public static AddedResultDto fail(List<AlterationSummary> errorList) {
		return new AddedResultDto(null, errorList);
	}
}
