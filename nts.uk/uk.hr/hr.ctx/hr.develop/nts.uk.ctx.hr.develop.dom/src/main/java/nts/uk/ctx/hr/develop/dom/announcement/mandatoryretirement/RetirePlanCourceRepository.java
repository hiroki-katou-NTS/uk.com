package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement;

import java.util.Optional;

public interface RetirePlanCourceRepository {

	Optional<RetirePlanCource> findByKey(String retirePlanCourseId);
	
	void add(RetirePlanCource retirePlanCource);
	
	void update(RetirePlanCource retirePlanCource);
	
	void remove(String retirePlanCourseId);
}
