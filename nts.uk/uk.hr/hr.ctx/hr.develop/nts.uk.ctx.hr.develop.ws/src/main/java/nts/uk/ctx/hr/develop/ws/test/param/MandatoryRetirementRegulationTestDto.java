package nts.uk.ctx.hr.develop.ws.test.param;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.MandatoryRetirementRegulation;
import test.mandatoryretirement.dto.PlanCourseApplyTermDto;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class MandatoryRetirementRegulationTestDto{

	private String companyId;
	
	private String historyId;
	
	private Integer reachedAgeTerm;
	
	private DateCaculationTermTestDto publicTerm;
	
	private RetireDateTermTestDto retireDateTerm;
	
	private Boolean planCourseApplyFlg;
	
	private List<MandatoryRetireTermTestDto> mandatoryRetireTerm;
	
	private List<ReferEvaluationItemTestDto> referEvaluationTerm;
	
	private PlanCourseApplyTermDto planCourseApplyTerm;
	
	public MandatoryRetirementRegulation toDomain() {
		return MandatoryRetirementRegulation.createFromJavaType(
				companyId, 
				historyId, 
				reachedAgeTerm, 
				publicTerm.toDomain(), 
				retireDateTerm.toDomain(), 
				planCourseApplyFlg, 
				mandatoryRetireTerm.stream().map(c->c.toDomain()).collect(Collectors.toList()), 
				referEvaluationTerm.stream().map(c->c.toDomain()).collect(Collectors.toList()), 
				planCourseApplyTerm.toDomain());
	}

	public MandatoryRetirementRegulationTestDto(MandatoryRetirementRegulation domain) {
		super();
		this.companyId = domain.getCompanyId();
		this.historyId = domain.getHistoryId();
		this.reachedAgeTerm = domain.getReachedAgeTerm().value;
		this.publicTerm = new DateCaculationTermTestDto(domain.getPublicTerm());
		this.retireDateTerm = new RetireDateTermTestDto(domain.getRetireDateTerm());
		this.planCourseApplyFlg = domain.isPlanCourseApplyFlg();
		this.mandatoryRetireTerm = domain.getMandatoryRetireTerm().stream().map(c-> new MandatoryRetireTermTestDto(c)).collect(Collectors.toList());
		this.referEvaluationTerm = domain.getReferEvaluationTerm().stream().map(c-> new ReferEvaluationItemTestDto(c)).collect(Collectors.toList());
		this.planCourseApplyTerm = new PlanCourseApplyTermDto(domain.getPlanCourseApplyTerm());
	}
	
}
