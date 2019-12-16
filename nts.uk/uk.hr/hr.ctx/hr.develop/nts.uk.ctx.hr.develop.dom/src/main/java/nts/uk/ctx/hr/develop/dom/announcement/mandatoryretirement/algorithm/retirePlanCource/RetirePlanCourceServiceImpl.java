package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.retirePlanCource;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetirePlanCource;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetirePlanCourceRepository;

@Stateless
public class RetirePlanCourceServiceImpl implements RetirePlanCourceService {

	@Inject
	private RetirePlanCourceRepository repo;
	
	@Override
	public List<RetirePlanCource> getRetirePlanCourse(String companyId) {
		return repo.getlistRetirePlanCourceAsc(companyId);
	}

	@Override
	public void addRetirePlanCourse(String cId, List<RetirePlanCource> retirePlanCourse) {
		List<RetirePlanCource> retirePlanCourses = retirePlanCourse.stream().map(c->{
			c.setCompanyId(cId);
			return c;
		}).collect(Collectors.toList());
		repo.addAll(retirePlanCourses);
	}

	@Override
	public void updateRetirePlanCourse(String cId, List<RetirePlanCource> retirePlanCourse) {
		List<RetirePlanCource> retirePlanCourses = retirePlanCourse.stream().map(c->{
			c.setCompanyId(cId);
			return c;
		}).collect(Collectors.toList());
		repo.updateAll(retirePlanCourses);
	}

	@Override
	public List<RetirePlanCource> getAllRetirePlanCource(String companyId) {
		return repo.getlistRetirePlanCource(companyId);
	}

	@Override
	public List<RetirePlanCource> getRetireTermByRetirePlanCourceIdList(String companyId, List<String> retirePlanCourseIdList) {
		return repo.getRetireTermByRetirePlanCourceIdList(companyId, retirePlanCourseIdList);
	}

	@Override
	public List<RetirePlanCource> getEnableRetirePlanCourceByBaseDate(String companyId, GeneralDate baseDate) {
		return repo.getEnableRetirePlanCourceByBaseDate(companyId, baseDate);
	}

}
