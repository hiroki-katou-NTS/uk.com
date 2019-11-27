package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.enums.RetirePlanCourseClass;
import nts.uk.ctx.hr.shared.dom.primitiveValue.Integer_60_99;

/**
 * @author thanhpv
 * 定年退職コース
 */
@AllArgsConstructor
@Getter
public class RetirePlanCource extends AggregateRoot{

	/** 会社ID  */
	private String companyId;
	
	/** 定年退職コースID */
	private long retirePlanCourseId;
	
	/** 定年退職コースCD */
	private String retirePlanCourseCode;
	
	/** 定年退職コース名 */
	private String retirePlanCourseName;
	
	/** 定年退職コース区分 */
	private RetirePlanCourseClass retirePlanCourseClass;
	
	/** 定年年齢 */
	private Integer_60_99 retirementAge;
	
	/** 継続区分 */
	private boolean durationFlg;
	
	/** 退職理由区分1 ID */
	private long resignmentReason1Id;
	
	/** 未使用区分 */
	private boolean notUsageFlg;
	
	/** 使用開始日 */
	private GeneralDate usageStartDate;
	
	/** 使用終了日 */
	private GeneralDate usageEndDate;
	
	/** 希望コース申請条件 */
	private Optional<PlanCourseApplyTerm> planCourseApplyTerm;
	
	public static RetirePlanCource createFromJavaType(String companyId, long retirePlanCourseId, String retirePlanCourseCode, String retirePlanCourseName, int retirePlanCourseClass, 
			int retirementAge, boolean durationFlg, long resignmentReason1Id, boolean notUsageFlg, GeneralDate usageStartDate, GeneralDate usageEndDate, Optional<PlanCourseApplyTerm> planCourseApplyTerm) {
		return new RetirePlanCource(
				companyId,
				retirePlanCourseId,
				retirePlanCourseCode,
				retirePlanCourseName,
				EnumAdaptor.valueOf(retirePlanCourseClass, RetirePlanCourseClass.class),
				new Integer_60_99(retirementAge),
				durationFlg,
				resignmentReason1Id,
				notUsageFlg,
				usageStartDate,
				usageEndDate,
				planCourseApplyTerm
				);
	}
	
}
