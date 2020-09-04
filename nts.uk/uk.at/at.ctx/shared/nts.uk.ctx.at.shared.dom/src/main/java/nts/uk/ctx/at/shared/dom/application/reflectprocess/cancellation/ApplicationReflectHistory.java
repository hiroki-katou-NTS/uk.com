package nts.uk.ctx.at.shared.dom.application.reflectprocess.cancellation;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;

/**
 * @author thanh_nx
 *
 *         申請反映履歴
 */
@Getter
public class ApplicationReflectHistory implements DomainAggregate {

	// 社員ID
	private final String employeeId;

	// 年月日
	private final GeneralDate date;

	// 申請ID
	private final String applicationId;

	// 反映時刻
	private final GeneralDateTime reflectionTime;

	// 予定実績区分
	private final ScheduleRecordClassifi classification;

	// 取消区分
	private final boolean cancellationCate;

	// 反映前
	private final List<AttendanceBeforeApplicationReflect> lstAttBeforeAppReflect;

	public ApplicationReflectHistory(String employeeId, GeneralDate date, String applicationId,
			GeneralDateTime reflectionTime, ScheduleRecordClassifi classification, boolean cancellationCate,
			List<AttendanceBeforeApplicationReflect> lstAttBeforeAppReflect) {
		super();
		this.employeeId = employeeId;
		this.date = date;
		this.applicationId = applicationId;
		this.reflectionTime = reflectionTime;
		this.classification = classification;
		this.cancellationCate = cancellationCate;
		this.lstAttBeforeAppReflect = lstAttBeforeAppReflect;
	}

}
