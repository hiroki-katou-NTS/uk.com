package nts.uk.ctx.exio.dom.input.manage;

public interface ExternalImportCurrentStateRepository {

	ExternalImportCurrentState find(String companyId);
	
	void save(ExternalImportCurrentState currentState);
}
