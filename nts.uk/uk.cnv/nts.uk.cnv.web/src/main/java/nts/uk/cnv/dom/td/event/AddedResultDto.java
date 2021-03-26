package nts.uk.cnv.dom.td.event;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;

@AllArgsConstructor
@Getter
public class AddedResultDto {
	String eventId;
	List<AlterationSummary> summaries;
}
