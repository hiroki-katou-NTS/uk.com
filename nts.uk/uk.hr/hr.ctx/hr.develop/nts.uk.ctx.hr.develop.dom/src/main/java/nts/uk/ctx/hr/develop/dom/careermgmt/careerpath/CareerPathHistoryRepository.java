package nts.uk.ctx.hr.develop.dom.careermgmt.careerpath;

import java.util.Optional;

public interface CareerPathHistoryRepository {

	CareerPathHistory getLatestCareerPathHist();
	
	Optional<CareerPathHistory> getCareerPathHist(String cId);
	
	void getCareerPathStartDate();
	
	void addCareerPathHist(CareerPathHistory domain);
	
	void updateCareerPathHist(CareerPathHistory domain);
	
	void removeCareerPathHist(String cId, String historyId);
}
