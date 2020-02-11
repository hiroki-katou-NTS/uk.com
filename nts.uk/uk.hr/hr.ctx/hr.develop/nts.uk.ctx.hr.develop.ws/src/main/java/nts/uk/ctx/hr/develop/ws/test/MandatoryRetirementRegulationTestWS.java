package nts.uk.ctx.hr.develop.ws.test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.MandatoryRetirementRegulation;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.EvaluationInfoDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.RetirementCourseInformationDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.RetirementDateDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.RetirementPlannedPersonDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.mandatoryRetirementRegulation.MandatoryRetirementRegulationService;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.enums.ReachedAgeTerm;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.primitiveValue.RetirementAge;
import nts.uk.ctx.hr.develop.ws.test.param.MandatoryRetirementRegulationTestDto;
import nts.uk.ctx.hr.develop.ws.test.param.ParamCommon;
import nts.uk.ctx.hr.develop.ws.test.param.ReferEvaluationItemTestDto;

@Path("mandaRetireReg")
@Produces(MediaType.APPLICATION_JSON)
public class MandatoryRetirementRegulationTestWS {
	
	@Inject
	private MandatoryRetirementRegulationService finder;
	
	@POST
	@Path("/get")
	public MandatoryRetirementRegulationTestDto getMandatoryRetirementRegulation(ParamCommon param){
		Optional<MandatoryRetirementRegulation> result = finder.getMandatoryRetirementRegulation(param.companyId, param.historyId);
		if(result.isPresent()) {
			return new MandatoryRetirementRegulationTestDto(result.get());
		}
		return null;
	}
	
	@POST
	@Path("/getReferByDate")
	public List<ReferEvaluationItemTestDto> getReferEvaluationItemByDate(ParamCommon param){
		return finder.getReferEvaluationItemByDate(param.companyId, param.baseDate).stream().map(c-> new ReferEvaluationItemTestDto(c)).collect(Collectors.toList());
	}
	
	@POST
	@Path("/getRetireByDate")
	public List<RetirementCourseInformationDto> getAppliedRetireCourseByDate(ParamCommon param){
		return finder.getAppliedRetireCourseByDate(param.companyId, param.baseDate);
	}
	
	@POST
	@Path("/add")
	public void add(MandatoryRetirementRegulationTestDto param){
		finder.addMandatoryRetirementRegulation(
				param.getCompanyId(), 
				param.getHistoryId(), 
				param.getReachedAgeTerm(), 
				param.getPublicTerm().toDomain(), 
				param.getRetireDateTerm().toDomain(), 
				param.getPlanCourseApplyFlg(), 
				param.getMandatoryRetireTerm().stream().map(c->c.toDomain()).collect(Collectors.toList()), 
				param.getReferEvaluationTerm().stream().map(c->c.toDomain()).collect(Collectors.toList()), 
				param.getPlanCourseApplyTerm().toDomain());
	}
	
	@POST
	@Path("/update")
	public void update(MandatoryRetirementRegulationTestDto param){
		finder.updateMandatoryRetirementRegulation(
				param.getCompanyId(), 
				param.getHistoryId(), 
				param.getReachedAgeTerm(), 
				param.getPublicTerm().toDomain(), 
				param.getRetireDateTerm().toDomain(), 
				param.getPlanCourseApplyFlg(), 
				param.getMandatoryRetireTerm().stream().map(c->c.toDomain()).collect(Collectors.toList()), 
				param.getReferEvaluationTerm().stream().map(c->c.toDomain()).collect(Collectors.toList()), 
				param.getPlanCourseApplyTerm().toDomain());
	}
	
	@POST
	@Path("/getMandatoryRetirementList")
	public List<RetirementPlannedPersonDto> getMandatoryRetirementListByPeriodDepartmentEmployment(ParamCommon param){
		return finder.getMandatoryRetirementListByPeriodDepartmentEmployment(
				param.companyId, 
				param.startDate, 
				param.endDate,
				param.retirementAge == null ? Optional.empty(): Optional.of(new RetirementAge(param.retirementAge)), 
				param.departmentId, 
				param.employmentCode);
	}
	
	@POST
	@Path("/getRetireDateBySidList")
	public List<RetirementDateDto> getRetireDateBySidList(ParamCommon param){
		return finder.getRetireDateBySidList(param.retirePlan.stream().map(c->c.toDomain()).collect(Collectors.toList()), 
				EnumAdaptor.valueOf(param.reachedAgeTerm, ReachedAgeTerm.class), param.retireDateTerm.toDomain(), Optional.ofNullable(param.endDate), param.closingDate, param.attendanceDate);
	}
	
	@POST
	@Path("/getEvaluationInfoBySidList")
	public EvaluationInfoDto getEvaluationInfoBySidList(ParamCommon param){
		return finder.getEvaluationInfoBySidList(
				param.departmentId, 
				param.referEvaluationTerm.stream().map(c->c.toDomain()).collect(Collectors.toList()));
	}
	
}
