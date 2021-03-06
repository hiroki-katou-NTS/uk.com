package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.OvertimeDeclaration;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work.WorkGroup;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;

/**
 * VO : 実績への反映内容
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.実績への反映内容
 * @author tutk
 *
 */
public class RefectActualResult implements DomainValue, Cloneable {

	/**
	 * 勤務先情報
	 * chuyển 2 thuộc tính cũ workLocationCD, cardNumberSupport vào trong WorkInformationStamp
	 */
	@Getter
	private final Optional<WorkInformationStamp> workInforStamp;
	
	
	/**
	 * 就業時間帯コード
	 */
	@Getter
	private final Optional<WorkTimeCode> workTimeCode;
	
	/**
	 * 時間外の申告
	 */
	@Getter
	private final Optional<OvertimeDeclaration> overtimeDeclaration;

	/**
	 * 	作業グループ
	 */
	@Getter
	private final Optional<WorkGroup> workGroup;
	
	public RefectActualResult(WorkInformationStamp workInforStamp,
			WorkTimeCode workTimeCode,OvertimeDeclaration overtimeDeclaration, WorkGroup workGroup) {
		super();
		this.workInforStamp = Optional.ofNullable(workInforStamp);
		this.workTimeCode = Optional.ofNullable(workTimeCode);
		this.overtimeDeclaration = Optional.ofNullable(overtimeDeclaration);
		this.workGroup = Optional.ofNullable(workGroup);
	}
	
	@Override
	public RefectActualResult clone() {
		return new RefectActualResult(workInforStamp.map(x -> x.clone()).orElse(null),
				workTimeCode.map(x -> new WorkTimeCode(x.v())).orElse(null), overtimeDeclaration
						.map(x -> new OvertimeDeclaration(x.getOverTime(), x.getOverLateNightTime())).orElse(null),
						workGroup.map(m -> m).orElse(null));
	}
}
