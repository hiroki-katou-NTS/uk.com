package nts.uk.ctx.hr.develop.dom.careermgmt.careerpath;

import java.util.Optional;

public interface CareerPathHistoryRepository {

	String getLatestCareerPathHist(String cId);
	
	Optional<CareerPathHistory> getCareerPathHist(String cId);
	
	void getCareerPathStartDate();
	
	void add(CareerPathHistory domain);
	
	void update(CareerPathHistory domain);
	
	void delete(String cId, String historyId);
}
