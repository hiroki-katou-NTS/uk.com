package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.entranceandexit;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.objecttype.DomainObject;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;

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
	
	/**
	 * PCLogOnNoに一致するログオンまたはログオフを取得する
	 * @param workNo
	 * @param goLeavingWorkAtr
	 * @return　Optional<TimeWithDayAttr>
	 */
	public Optional<TimeWithDayAttr> getLogOnTime(PCLogOnNo workNo,GoLeavingWorkAtr goLeavingWorkAtr) {
		Optional<LogOnInfo> logOnInfo = getLogOnInfo(workNo);
		if(logOnInfo.isPresent()) {
			return goLeavingWorkAtr.isGO_WORK()?logOnInfo.get().getLogOn():logOnInfo.get().getLogOff();
		}
		return Optional.empty();
	}
	
	/**
	 * PCLogOnNoに一致するログオン情報を取得する
	 * @param workNo
	 * @return　Optional<LogOnInfo>
	 */
	public Optional<LogOnInfo> getLogOnInfo(PCLogOnNo workNo) {
		return this.logOnInfo.stream().filter(t->t.getWorkNo().equals(workNo)).findFirst();
	}
	
}
