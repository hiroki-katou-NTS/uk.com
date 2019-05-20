package nts.uk.ctx.hr.develop.dom.careermgmt.careerpath;

import java.util.Optional;

public interface CareerPathHistoryRepository {

	CareerPathHistory getLatestCareerPathHist();
	
	Optional<CareerPathHistory> getCareerPathHist(String cId);
	
	void getCareerPathStartDate();
	
	void add(CareerPathHistory domain);
	
	void update(CareerPathHistory domain);
	
	void delete(String cId, String historyId);
}
