package nts.uk.ctx.at.request.pub.application.approvalstatus;

import java.util.Optional;

import lombok.Data;
import lombok.Setter;
import nts.arc.time.GeneralDateTime;

@Data
public class ReflectionInformation_NewDto {

	// 予定反映状態
	@Setter
	private int stateReflection;

	// 実績反映状態
	@Setter
	private int stateReflectionReal;

	// 予定強制反映
	private int forcedReflection;

	// 実績強制反映
	private int forcedReflectionReal;

	// 予定反映不可理由
	private Integer notReason;

	// 実績反映不可理由
	@Setter
	private Integer notReasonReal;

	// 予定反映日時
	private Optional<GeneralDateTime> dateTimeReflection;

	// 実績反映日時
	private Optional<GeneralDateTime> dateTimeReflectionReal;

	public ReflectionInformation_NewDto(int stateReflection, int stateReflectionReal, int forcedReflection, int forcedReflectionReal, Integer notReason, Integer notReasonReal, Optional<GeneralDateTime> dateTimeReflection,
			Optional<GeneralDateTime> dateTimeReflectionReal) {
		super();
		this.stateReflection = stateReflection;
		this.stateReflectionReal = stateReflectionReal;
		this.forcedReflection = forcedReflection;
		this.forcedReflectionReal = forcedReflectionReal;
		this.notReason = notReason;
		this.notReasonReal = notReasonReal;
		this.dateTimeReflection = dateTimeReflection;
		this.dateTimeReflectionReal = dateTimeReflectionReal;
	}


}
