package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.entranceandexit;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.objecttype.DomainObject;

/**
 * 日別勤怠のPCログオン情報
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.入退門.日別勤怠のPCログオン情報
 * @author tutk
 *
 */
@Getter
@NoArgsConstructor
public class PCLogOnInfoOfDailyAttd implements DomainObject {
	/** ログオン情報: ログオン情報 */
	private List<LogOnInfo> logOnInfo;

	public PCLogOnInfoOfDailyAttd(List<LogOnInfo> logOnInfo) {
		super();
		this.logOnInfo = logOnInfo;
	}
	
}
