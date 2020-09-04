package nts.uk.ctx.at.request.dom.applicationreflect.object;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         申請反映実行条件
 */
@Getter
public class AppReflectExecCond implements DomainAggregate {

	// 会社ID
	private final String companyId;

	// 事前申請を勤務予定に反映する
	private final NotUseAtr preAppSchedule;

	// 勤務予定が確定状態でも反映する
	private final NotUseAtr scheduleConfirm;

	// 勤務実績が確定状態でも反映する
	private final NotUseAtr workRecordConfirm;

	public AppReflectExecCond(String companyId, NotUseAtr preAppSchedule, NotUseAtr scheduleConfirm,
			NotUseAtr workRecordConfirm) {
		super();
		this.companyId = companyId;
		this.preAppSchedule = preAppSchedule;
		this.scheduleConfirm = scheduleConfirm;
		this.workRecordConfirm = workRecordConfirm;
	}

}
