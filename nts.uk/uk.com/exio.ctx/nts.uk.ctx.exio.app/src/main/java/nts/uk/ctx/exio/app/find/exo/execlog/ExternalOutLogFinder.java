package nts.uk.ctx.exio.app.find.exo.execlog;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.execlog.ExternalOutLogRepository;
import nts.uk.ctx.exio.dom.exo.execlog.ProcessingClassification;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ExternalOutLogFinder {

	@Inject
	public ExternalOutLogRepository externalOutLogRepository;

	String companyId = AppContexts.user().companyId();
	
	int processingClassification = ProcessingClassification.ERROR.value;

	public List<ExternalOutLogDto> getExternalOutLogById(String storeProcessingId) {
		return externalOutLogRepository.getExternalOutLogById(companyId, storeProcessingId , processingClassification).stream().map(item -> {
			return ExternalOutLogDto.fromDomain(item);
		}).collect(Collectors.toList());	
	}
}
