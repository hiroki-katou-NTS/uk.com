package nts.uk.ctx.exio.dom.input.domain;

import java.util.List;

public interface ImportingDomainRepository {
	
	List<ImportingDomain> findAll();

	ImportingDomain find(ImportingDomainId domainId);
}
