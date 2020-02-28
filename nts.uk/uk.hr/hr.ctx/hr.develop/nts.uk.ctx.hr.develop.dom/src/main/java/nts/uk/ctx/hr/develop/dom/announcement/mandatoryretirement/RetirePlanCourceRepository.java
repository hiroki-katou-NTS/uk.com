package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface RetirePlanCourceRepository {

	Optional<RetirePlanCource> findByKey(String retirePlanCourseId);
	
	void add(RetirePlanCource retirePlanCource);
	
	void update(RetirePlanCource retirePlanCource);
	
	void remove(String retirePlanCourseId);
	
	List<RetirePlanCource> getlistRetirePlanCourceAsc(String companyId);
	
	void addAll(List<RetirePlanCource> retirePlanCource);
	
	void updateAll(List<RetirePlanCource> retirePlanCource);
	
	List<RetirePlanCource> getlistRetirePlanCource(String companyId);
	
	List<RetirePlanCource> getRetireTermByRetirePlanCourceIdList(String companyId, List<String> retirePlanCourseId);
	
	List<RetirePlanCource> getEnableRetirePlanCourceByBaseDate(String companyId, GeneralDate baseDate);
}
