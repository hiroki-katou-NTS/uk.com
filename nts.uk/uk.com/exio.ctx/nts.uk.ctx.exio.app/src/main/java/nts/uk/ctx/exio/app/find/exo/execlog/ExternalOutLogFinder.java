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

	public List<ExternalOutLogDto> getExternalOutLogById(String storeProcessingId) {
		String companyId = AppContexts.user().companyId();
		int processContent = ProcessingClassification.ERROR.value;
		return externalOutLogRepository.getExternalOutLogById(companyId, storeProcessingId, processContent).stream()
				.map(item -> {
					return ExternalOutLogDto.fromDomain(item);
				}).collect(Collectors.toList());
	}
}
