package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.retirePlanCource;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetirePlanCource;

@Stateless
public class RetirePlanCourceServiceImpl implements RetirePlanCourceService {

	@Override
	public List<RetirePlanCource> getRetirePlanCourse(String companyID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addRetirePlanCourse(String cId, List<RetirePlanCource> retirePlanCourse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRetirePlanCourse(String cId, List<RetirePlanCource> retirePlanCourse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<RetirePlanCource> getAllRetirePlanCource(String companyID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RetirePlanCource getRetireTermByRetirePlanCourceIdList(String companyID, String retirePlanCourseIdList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RetirePlanCource> getEnableRetirePlanCourceByBaseDate(String companyID, GeneralDate baseDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
