package nts.uk.cnv.app.td.query.feature;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import lombok.val;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummaryRepository;
import nts.uk.cnv.dom.td.feature.FeatureRepository;
import nts.uk.cnv.ws.alteration.summary.AlterationDevStatusDto;
import nts.uk.cnv.ws.feature.FeatureInfoDto;

public class FeatureQuery {
	
	@Inject
	FeatureRepository featureRepo;
	
	@Inject
	AlterationSummaryRepository alterationSummaryRepo;
	
	public List<FeatureInfoDto> get() {
		return featureRepo.get().stream()
				.map(f -> new FeatureInfoDto(f.getFeatureId(), f.getName(), f.getDescription()))
				.collect(Collectors.toList());
	}
	
	public List<AlterationDevStatusDto> getDevState(String featureId) {
		val alterSummary = alterationSummaryRepo.getByFeature(featureId);
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
