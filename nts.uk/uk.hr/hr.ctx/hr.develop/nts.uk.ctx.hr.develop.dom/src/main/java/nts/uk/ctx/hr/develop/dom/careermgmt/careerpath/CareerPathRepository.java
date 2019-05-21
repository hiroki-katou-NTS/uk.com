package nts.uk.ctx.hr.develop.dom.careermgmt.careerpath;

public interface CareerPathRepository {
	
	CareerPath getCareerPath(String companyId, String historyId);
	
	void addCareerPath(CareerPath domain);
	
	void updateCareerPath(CareerPath domain);
	
	void removeCareerPath();
}
