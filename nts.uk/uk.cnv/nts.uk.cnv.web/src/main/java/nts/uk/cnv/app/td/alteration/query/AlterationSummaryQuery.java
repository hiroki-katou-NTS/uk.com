package nts.uk.cnv.app.td.alteration.query;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import lombok.val;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummaryRepository;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.devstatus.DevelopmentStatus;
import nts.uk.cnv.dom.td.event.EventType;
import nts.uk.cnv.ws.alteration.summary.AlterationDevStatusDto;

public class AlterationSummaryQuery {
	
	@Inject
	AlterationSummaryRepository alterationSummaryRepo;
	
	public List<AlterationSummary> getAll(String featureId) {
		return alterationSummaryRepo.getByFeature(featureId);
	}
	
	public List<AlterationSummary> getOfNotOrderedByFeature(String featureId) {
		return alterationSummaryRepo.getByFeature(featureId, DevelopmentStatus.NOT_ORDER);
	}
	
	public List<AlterationSummary> getOfOrderedByEvent(String eventId) {
		return alterationSummaryRepo.getByEvent(eventId, EventType.ORDER);
	}
	
	public List<AlterationDevStatusDto> getDevState(String eventId, DevelopmentProgress devProgress){
		val alterSummary = alterationSummaryRepo.getByEvent(eventId, devProgress);
		val result = alterSummary.stream()
				.map(a -> new AlterationDevStatusDto(
						a.getAlterId() , 
						a.getTableId() , 
						a.getMetaData().getComment(), 
						a.getMetaData().getUserName(), 
						a.getState()))
				.collect(Collectors.toList());
		return result;
	}
}
