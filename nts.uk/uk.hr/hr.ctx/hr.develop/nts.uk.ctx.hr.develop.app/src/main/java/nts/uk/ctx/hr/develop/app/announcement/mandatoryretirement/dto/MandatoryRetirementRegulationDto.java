package nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.MandatoryRetirementRegulation;
import test.mandatoryretirement.dto.PlanCourseApplyTermDto;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MandatoryRetirementRegulationDto {

	/** 会社ID */
	private String companyId;

	/** 履歴ID */
	private String historyId;

	/** 年齢到達条件 */
	private Integer reachedAgeTerm;

	/** 公開条件 */
	private DateCaculationTermDto publicTerm;

	/** 退職日条件 */
	private RetireDateTermDto retireDateTerm;

	/** 希望コース申請条件有無 */
	private Boolean planCourseApplyFlg;

	/** 定年退職条件 */
	private List<MandatoryRetireTermDto> mandatoryRetireTerm;

	/** 評価参考情報 */
	private List<ReferEvaluationItemDto> referEvaluationTerm;

	/** 希望コース申請条件 */
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

	public MandatoryRetirementRegulationDto(MandatoryRetirementRegulation domain) {
		super();
		this.companyId = domain.getCompanyId();
		this.historyId = domain.getHistoryId();
		this.reachedAgeTerm = domain.getReachedAgeTerm().value;
		this.publicTerm = new DateCaculationTermDto(domain.getPublicTerm());
		this.retireDateTerm = new RetireDateTermDto(domain.getRetireDateTerm());
		this.planCourseApplyFlg = domain.isPlanCourseApplyFlg();
		this.mandatoryRetireTerm = domain.getMandatoryRetireTerm().stream().map(c-> new MandatoryRetireTermDto(c)).collect(Collectors.toList());
		this.referEvaluationTerm = domain.getReferEvaluationTerm().stream().map(c-> new ReferEvaluationItemDto(c)).collect(Collectors.toList());
		this.planCourseApplyTerm = new PlanCourseApplyTermDto(domain.getPlanCourseApplyTerm());
	}
}
