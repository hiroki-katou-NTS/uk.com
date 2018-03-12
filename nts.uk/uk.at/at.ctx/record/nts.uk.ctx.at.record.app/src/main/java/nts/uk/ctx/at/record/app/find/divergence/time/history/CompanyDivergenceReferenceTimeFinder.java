package nts.uk.ctx.at.record.app.find.divergence.time.history;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTime;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeRepository;

public class CompanyDivergenceReferenceTimeFinder {
	@Inject
	private CompanyDivergenceReferenceTimeRepository comDivergenceRefTimeRepository;
	
	public List<CompanyDivergenceReferenceTimeDto> getAllDivergenceReferenceTimeItem(String historyId){
		
		List<CompanyDivergenceReferenceTime> listDomain = this.comDivergenceRefTimeRepository.findAll(historyId);
		
		if (listDomain.isEmpty()){
			return Collections.emptyList();
		}
		
		return listDomain.stream().map(e -> {
			CompanyDivergenceReferenceTimeDto dto = new CompanyDivergenceReferenceTimeDto();
			e.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}
}
