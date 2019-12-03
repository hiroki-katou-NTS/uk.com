package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.enums.ReachedAgeTerm;
import nts.uk.ctx.hr.shared.dom.publicTerm.PublicTerm;

/**
 * @author thanhpv
 * 定年退職の就業規則
 */
@AllArgsConstructor
@Getter
public class MandatoryRetirementRegulation extends AggregateRoot{

	/** 会社ID  */
	private String companyId;
	
	/** 履歴ID */
	private String historyId;
	
	/** 年齢到達条件 */
	private ReachedAgeTerm reachedAgeTerm;
	
	/** 公開条件 */
	private PublicTerm publicTerm;
	
	/** 退職日条件 */
	private RetireDateTerm retireDateTerm;
	
	/** 希望コース申請条件有無 */
	private boolean planCourseApplyEnable;
	
	/** 定年退職条件 */
	private List<MandatoryRetireTerm> mandatoryRetireTerm;
	
	/** 評価参考情報 */
	private List<ReferEvaluationItem> referEvaluationTerm;
	
	/** 希望コース申請条件 */
	private PlanCourseApplyTerm planCourseApplyTerm;
	
	public static MandatoryRetirementRegulation createFromJavaType(String companyId, String historyId, int reachedAgeTerm, PublicTerm publicTerm, RetireDateTerm retireDateTerm, 
			boolean planCourseApplyEnable, List<MandatoryRetireTerm> mandatoryRetireTerm, List<ReferEvaluationItem> referEvaluationTerm, PlanCourseApplyTerm planCourseApplyTerm) {
		return new MandatoryRetirementRegulation(
				companyId,
				historyId,
				EnumAdaptor.valueOf(reachedAgeTerm, ReachedAgeTerm.class),
				publicTerm,
				retireDateTerm,
				planCourseApplyEnable,
				mandatoryRetireTerm,
				referEvaluationTerm,
				planCourseApplyTerm
				);
	}
	
}
