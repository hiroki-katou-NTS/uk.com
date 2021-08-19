package nts.uk.ctx.exio.dom.input.workspace.domain;

import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;

public interface DomainWorkspaceRepository {

	DomainWorkspace get(ImportingDomainId domainId);
}
