package nts.uk.ctx.hr.develop.dom.guidance;

import java.util.Optional;

public interface GuidanceRepository {

	Optional<Guidance> getGuidance(String companyId);
	
	void addGuidance(Guidance domain);
	
	void updateGuidance(String companyId, boolean usageFlgCommon, int guideMsgAreaRow, int guideMsgMaxNum);
}
