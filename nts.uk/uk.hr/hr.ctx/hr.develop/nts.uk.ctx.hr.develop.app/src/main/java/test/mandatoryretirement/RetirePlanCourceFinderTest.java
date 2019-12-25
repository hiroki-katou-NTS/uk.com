package test.mandatoryretirement;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.retirePlanCource.RetirePlanCourceService;
import test.mandatoryretirement.dto.RetirePlanCourceDto;

@Stateless
public class RetirePlanCourceFinderTest {

	@Inject
	private RetirePlanCourceService repo;
	
	public List<RetirePlanCourceDto> getRetirePlanCourse(String companyId){
		return repo.getRetirePlanCourse(companyId).stream().map(c->new RetirePlanCourceDto(c)).collect(Collectors.toList());
	};
	
	public void addRetirePlanCourse(String cId, List<RetirePlanCourceDto> retirePlanCourseDto){
		repo.addRetirePlanCourse(cId, retirePlanCourseDto.stream().map(c->c.toDomain()).collect(Collectors.toList()));
	};
	
	public void updateRetirePlanCourse(String cId, List<RetirePlanCourceDto> retirePlanCourseDto){
		repo.updateRetirePlanCourse(cId, retirePlanCourseDto.stream().map(c->c.toDomain()).collect(Collectors.toList()));
	};
	
	public List<RetirePlanCourceDto> getAllRetirePlanCource(String companyId){
		return repo.getAllRetirePlanCource(companyId).stream().map(c->new RetirePlanCourceDto(c)).collect(Collectors.toList());
	};
	
	public List<RetirePlanCourceDto> getRetireTermByRetirePlanCourceIdList(String companyId, List<String> retirePlanCourseId){
		return repo.getRetireTermByRetirePlanCourceIdList(companyId, retirePlanCourseId).stream().map(c->new RetirePlanCourceDto(c)).collect(Collectors.toList());
	};
	
	public List<RetirePlanCourceDto> getEnableRetirePlanCourceByBaseDate(String companyId, GeneralDate baseDate){
		return repo.getEnableRetirePlanCourceByBaseDate(companyId, baseDate).stream().map(c->new RetirePlanCourceDto(c)).collect(Collectors.toList());
	};
}
