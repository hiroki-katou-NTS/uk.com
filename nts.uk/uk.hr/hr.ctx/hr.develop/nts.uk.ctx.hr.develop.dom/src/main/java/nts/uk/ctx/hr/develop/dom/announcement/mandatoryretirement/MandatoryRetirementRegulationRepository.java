package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement;

import java.util.Optional;

public interface MandatoryRetirementRegulationRepository {

	Optional<MandatoryRetirementRegulation> findByKey(String historyId);
	
	void add(MandatoryRetirementRegulation mandatoryRetirementRegulation);
	
	void update(MandatoryRetirementRegulation mandatoryRetirementRegulation);
	
	void remove(String historyId);
	
	Optional<MandatoryRetirementRegulation> getMandatoryRetirementRegulation(String historyId, String companyId);
}
