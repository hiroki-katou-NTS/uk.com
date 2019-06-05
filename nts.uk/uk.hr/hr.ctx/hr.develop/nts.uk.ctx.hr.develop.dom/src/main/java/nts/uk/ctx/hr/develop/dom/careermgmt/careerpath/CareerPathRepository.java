package nts.uk.ctx.hr.develop.dom.careermgmt.careerpath;

import java.util.Optional;

public interface CareerPathRepository {
	
	Optional<CareerPath> getCareerPath(String companyId, String historyId);
	
	void addCareerPath(CareerPath domain);
	
	void updateCareerPath(CareerPath domain);
	
	void removeCareerPath(String companyId, String historyId);
}
