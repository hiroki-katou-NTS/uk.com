package test.mandatoryretirement;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetirePlanCource;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.retirePlanCource.RetirePlanCourceService;

@Stateless
public class RetirePlanCourceFinderTest {

	@Inject
	private RetirePlanCourceService repo;
	
	List<RetirePlanCource> getRetirePlanCourse(String companyId){
		return null;
	};
	
	void addRetirePlanCourse(String cId, List<RetirePlanCource> retirePlanCourse){
		
	};
	
	void updateRetirePlanCourse(String cId, List<RetirePlanCource> retirePlanCourse){
		
	};
	
	List<RetirePlanCource> getAllRetirePlanCource(String companyId){
		return null;
	};
	
	List<RetirePlanCource> getRetireTermByRetirePlanCourceIdList(String companyId, List<String> retirePlanCourseId){
		return null;
	};
	
	List<RetirePlanCource> getEnableRetirePlanCourceByBaseDate(String companyId, GeneralDate baseDate){
		return null;
	};
}
