package nts.uk.ctx.at.shared.dom.era.name;

import java.util.List;

public interface EraNameDomRepository {

	List<EraNameDom> getAllEraName();
	
	void deleteEraName(String eraNameId);
	
	void updateEraName(EraNameDom domain);
	
	void addNewEraName(EraNameDom domain);
}
