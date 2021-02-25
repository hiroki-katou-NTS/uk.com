package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * 時刻の優先順位
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.Common.勤怠打刻.時刻の優先順位
 * 
 * @author tutk
 *
 */
public class TimePriority implements DomainAggregate {
	/**
	 * 会社ID
	 */
	@Getter
	private String companyId;

	/**
	 * 反映時刻優先
	 */
	@Getter
	private PriorityTimeReflectAtr PriorityTimeReflectAtr;

	public TimePriority(String companyId,
			PriorityTimeReflectAtr priorityTimeReflectAtr) {
		super();
		this.companyId = companyId;
		PriorityTimeReflectAtr = priorityTimeReflectAtr;
	}
}
