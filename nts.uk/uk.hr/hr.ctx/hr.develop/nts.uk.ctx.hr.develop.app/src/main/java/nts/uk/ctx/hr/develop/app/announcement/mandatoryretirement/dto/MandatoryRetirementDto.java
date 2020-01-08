package nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto;

import java.util.List;

import lombok.Data;
@Data
public class MandatoryRetirementDto {
	
	/** 公開条件 */
	private DateCaculationTermDto publicTerm;

	/** 年齢到達条件 */
	private int reachedAgeTerm;
	
	/** 退職日条件 */
	private DateTermDto retireDateTerm;
	
	/** 定年退職条件 */
	private List<RetireTermDto> mandatoryRetireTerm;
	
	/** 評価参考情報 */
	private List<ReferItemDto> referInfo;
	
	/** 希望コース申請条件有無 */
	private boolean planCourseApplyFlg;
	
	/** 希望コース申請条件 */
	private PlanCourseTermDto planCourseApplyTerm;

	public MandatoryRetirementDto(DateCaculationTermDto publicTerm, int reachedAgeTerm, DateTermDto retireDateTerm,
			List<RetireTermDto> mandatoryRetireTerm, List<ReferItemDto> referInfo, boolean planCourseApplyFlg,
			PlanCourseTermDto planCourseApplyTerm) {
		super();
		this.publicTerm = publicTerm;
		this.reachedAgeTerm = reachedAgeTerm;
		this.retireDateTerm = retireDateTerm;
		this.mandatoryRetireTerm = mandatoryRetireTerm;
		this.referInfo = referInfo;
		this.planCourseApplyFlg = planCourseApplyFlg;
		this.planCourseApplyTerm = planCourseApplyTerm;
	}
	
	
}
