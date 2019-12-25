package nts.uk.ctx.hr.develop.ws.test;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.MandatoryRetirementRegulation;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetireDateTerm;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.EmploymentDateDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.EvaluationInfoDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.RetirePlanParam;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.RetirementCourseInformationDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.RetirementDateDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.RetirementPlannedPersonDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.mandatoryRetirementRegulation.MandatoryRetirementRegulationService;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.enums.ReachedAgeTerm;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.primitiveValue.RetirementAge;
import nts.uk.ctx.hr.develop.ws.test.param.ParamCommon;
import nts.uk.ctx.hr.shared.dom.referEvaluationItem.ReferEvaluationItem;

@Path("mandaRetireReg")
@Produces(MediaType.APPLICATION_JSON)
public class MandatoryRetirementRegulationTestWS {
	
	@Inject
	private MandatoryRetirementRegulationService finder;
	
	@POST
	@Path("/get")
	public MandatoryRetirementRegulation getMandatoryRetirementRegulation(ParamCommon param){
		return finder.getMandatoryRetirementRegulation(param.companyId, param.historyId).orElse(null);
	}
	
	@POST
	@Path("/getReferByDate")
	public List<ReferEvaluationItem> getReferEvaluationItemByDate(ParamCommon param){
		return finder.getReferEvaluationItemByDate(param.companyId, param.baseDate);
	}
	
	@POST
	@Path("/getRetireByDate")
	public List<RetirementCourseInformationDto> getAppliedRetireCourseByDate(ParamCommon param){
		return finder.getAppliedRetireCourseByDate(param.companyId, param.baseDate);
	}
	
}
