package nts.uk.ctx.at.record.app.find.divergence.time.history;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTime;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeRepository;
import nts.uk.shr.com.context.AppContexts;

public class CompanyDivergenceReferenceTimeFinder {
	@Inject
	private CompanyDivergenceReferenceTimeRepository comDivergenceRefTimeRepository;
	
	public List<CompanyDivergenceReferenceTimeDto> getAllDivergenceReferenceTimeItem(String historyId){
		String companyId = AppContexts.user().companyId();
		
		List<CompanyDivergenceReferenceTime> listDomain = this.comDivergenceRefTimeRepository.findAll(historyId);
		
		if (listDomain.isEmpty()){
			return Collections.emptyList();
		}
		
		return null;
	}
}
